package org.figurate.gradle.plugin.cloudinit

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import org.yaml.snakeyaml.DumperOptions
import org.yaml.snakeyaml.Yaml

/**
 * Created by fortuna on 27/03/15.
 */
class CloudConfigTask extends DefaultTask {

    static final String DEFAULT_CONFIG_DIR = 'cloudinit'

    def binding = [
            // a list of packages to install
            packages   : [],
            // the locale timezone
            timezone   : '',
            // a map of users to create
            users      : [:],
            // a list of commands to run
            runcmd     : '',
            // file definitions
            write_files: [:]
    ]

    @OutputDirectory
    File outputDir = project.file("$project.buildDir/$DEFAULT_CONFIG_DIR")

    DumperOptions yamlOptions = [defaultFlowStyle: DumperOptions.FlowStyle.BLOCK]

    @Input
    Map getBinding() {
        binding
    }

    @OutputFile
    File getOutputFile() {
        new File(outputDir, 'cloud-config.yml')
    }

    @TaskAction
    def createConfig() {
        Yaml yaml = [yamlOptions]
        outputFile.withWriter('UTF-8') { out ->
            out.writeLine '#cloud-config\n'
            yaml.dump binding, out
        }
    }

    /*
     * Missing properties delegated to binding. Not using @Delegate here as
     * we only want to delegate the get/set of properties.
     */

    def propertyMissing(String name, value) { binding[name] = value }

    def propertyMissing(String name) { binding[name] }
}
