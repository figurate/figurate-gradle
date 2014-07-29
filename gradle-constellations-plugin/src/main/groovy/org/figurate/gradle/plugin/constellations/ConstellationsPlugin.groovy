package org.figurate.gradle.plugin.constellations

import groovy.util.logging.Slf4j
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by fortuna on 29/07/14.
 */
@Slf4j
class ConstellationsPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {

        // parse a list of constellation configurations that provide an alias for a group of bundles.
        def constellations = new GroovyShell(getClass().classLoader).evaluate(
                getClass().getResourceAsStream('/constellations.groovy').newReader())

        // create a (non-transitive) configuration for each constellation.
        constellations.keySet().each { key ->
            log.info "Adding configuration [$key]"

            def configuration = project.configurations.create("constellation\$$key")
            configuration.transitive = false
            // add constellation bundles to configuration dependency set
            constellations[key].each {
                project.dependencies.add(configuration.name, it)
            }
        }
    }
}
