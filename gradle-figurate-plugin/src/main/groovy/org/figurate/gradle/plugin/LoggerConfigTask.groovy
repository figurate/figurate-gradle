package org.figurate.gradle.plugin

import groovy.text.GStringTemplateEngine
import groovy.text.Template

/**
 * Created by fortuna on 21/06/14.
 */
class LoggerConfigTask extends AbstractTemplateTask {

    URL configTemplate = getClass().getResource('/config/logback.groovy')

    void setConfigTemplate(URL configTemplate) {
        this.configTemplate = configTemplate
    }

    void setConfigTemplate(URI configTemplate) {
        setConfigTemplate(configTemplate.toURL())
    }

    void setConfigTemplate(File configTemplate) {
        setConfigTemplate(configTemplate.toURI())
    }

    @Override
    Template getTemplate() {
        new GStringTemplateEngine().createTemplate(configTemplate)
    }

    @Override
    Map getBinding() {
        [
            logDir: 'logs',
            logFilename: "${project.name}.log"
        ]
    }

    @Override
    File getOutputFile() {
        new File(outputDir, 'logback.groovy')
    }
}
