package org.figurate.gradle.plugin

import groovy.text.Template
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction

/**
 * Created by fortuna on 10/05/14.
 */
abstract class AbstractTemplateTask extends DefaultTask {

    static final String DEFAULT_CONFIG_DIR = 'conf'

    @OutputDirectory
    File outputDir = project.file("$project.buildDir/$DEFAULT_CONFIG_DIR")

    @TaskAction
    def createConfig() {
        outputFile.write(template.make(binding.clone()) as String)
    }

    abstract Template getTemplate()

    @Input
    abstract Map getBinding()

    @OutputFile
    abstract File getOutputFile()
}
