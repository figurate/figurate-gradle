package org.figurate.gradle.plugin

import groovy.text.GStringTemplateEngine
import groovy.text.Template

/**
 * Created by fortuna on 21/06/14.
 */
class LoggerConfigTask extends AbstractTemplateTask {

    URL configTemplate = getClass().getResource('/config/logback.groovy')

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
    String getOutputFilename() {
        'logback.groovy'
    }
}
