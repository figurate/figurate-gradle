package org.figurate.gradle.plugin

import groovy.text.Template
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.OutputDirectory
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
        new File(outputDir, getOutputFilename()).write(template.make(binding) as String)
    }

    abstract Template getTemplate()

    abstract Map getBinding()

    abstract String getOutputFilename()
}
