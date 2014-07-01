package org.figurate.gradle.plugin

import groovy.text.SimpleTemplateEngine
import groovy.text.Template

/**
 * Created by fortuna on 21/06/14.
 */
class ConfigurationConfigTask extends AbstractConfigTask {

    URL configTemplate = getClass().getResource('/config/configuration.groovy.template')

    def binding = [configProps:[]]

    @Override
    Template getTemplate() {
        new SimpleTemplateEngine().createTemplate(configTemplate)
    }

    @Override
    Map getBinding() {
        binding
    }

    @Override
    String getConfigFilename() {
        'configuration.groovy'
    }

    /*
     * Missing properties delegated to binding. Not using @Delegate here as
     * we only want to delegate the get/set of properties.
     */

    def propertyMissing(String name, value) { binding[name] = value }

    def propertyMissing(String name) { binding[name] }
}
