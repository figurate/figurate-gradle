package org.figurate.gradle.plugin

import groovy.text.SimpleTemplateEngine
import groovy.text.Template

/**
 * Created by fortuna on 10/05/14.
 */
class LauncherConfigTask extends AbstractConfigTask {

    static final String DEFAULT_CONFIG_EXT = '.groovy'

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

    @Override
    Template getTemplate() {
        new SimpleTemplateEngine().createTemplate(configTemplate)
    }

    @Override
    Map getBinding() {
        def resolvedBinding = new HashMap(binding)
        resolvedBinding.bundles = project.configurations.hasProperty('bundle') ?
                project.configurations.bundle.collect { "$bundleDir/${it.name}" } : []
        resolvedBinding
    }

    @Override
    String getConfigFilename() {
        "${project.name}$DEFAULT_CONFIG_EXT"
    }

    def propertyMissing(String name, value) { binding[name] = value }

    def propertyMissing(String name) { binding[name] }
}
