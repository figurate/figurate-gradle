package org.figurate.gradle.plugin

import groovy.text.SimpleTemplateEngine
import groovy.text.Template

/**
 * Created by fortuna on 10/05/14.
 */
class LauncherConfigTask extends AbstractTemplateTask {

    URL configTemplate = getClass().getResource('/config/launcher.groovy.template')

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
        resolvedBinding.bundles = new File(project.buildDir, 'libs').listFiles({it.name.endsWith 'jar'} as FileFilter).collect {"$bundleDir/$it.name" }
        if (project.configurations.hasProperty('bundle')) {
            resolvedBinding.bundles.addAll project.configurations.bundle.collect { "$bundleDir/$it.name" }
        }
        resolvedBinding
    }

    @Override
    File getOutputFile() {
        new File(outputDir, 'launcher.groovy')
    }

    /*
     * Missing properties delegated to binding. Not using @Delegate here as
     * we only want to delegate the get/set of properties.
     */

    def propertyMissing(String name, value) { binding[name] = value }

    def propertyMissing(String name) { binding[name] }
}
