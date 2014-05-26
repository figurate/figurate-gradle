package org.figurate.gradle.plugin

import groovy.text.SimpleTemplateEngine
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * Created by fortuna on 10/05/14.
 */
class LauncherConfigTask extends DefaultTask {

    File outputDir = project.file("$project.buildDir/config")

    URL configTemplate = getClass().getResource('/config/figurate.config.template')

    def binding = [
            configProps: [
                    'org.osgi.framework.executionenvironment': 'J2SE-1.7,J2SE-1.6,J2SE-1.5,J2SE-1.4,J2SE-1.3,JavaSE-1.6',
                    'org.osgi.framework.storage'      : 'bundle-cache',
                    'org.osgi.framework.storage.clean': 'onFirstInit'
            ],

            startLevels: [:]
    ]

    String bundleDir = "bundles"

    @TaskAction
    def createConfig() {
//        def outputDir
//        if (project.hasProperty('launcher') && project.launcher.outputDir) {
//            outputDir = project.launcher.outputDir
//        } else {
//            outputDir = this.outputDir
//        }
        outputs.dir outputDir
        outputDir.mkdirs()
//        def configTemplate = new GStringTemplateEngine().createTemplate(configTemplate)
        def configTemplate = new SimpleTemplateEngine().createTemplate(configTemplate)
        def resolvedBinding = new HashMap(binding)
        resolvedBinding.bundles = project.configurations.hasProperty('bundle') ?
                project.configurations.bundle.collect { "$bundleDir/${it.name}" } : []
        def config = configTemplate.make(resolvedBinding)
        new File(outputDir, "${project.name}.conf").write(config as String)
    }

    def propertyMissing(String name, value) { binding[name] = value }

    def propertyMissing(String name) { binding[name] }
}
