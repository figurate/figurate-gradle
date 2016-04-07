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

        project.repositories {
            // amdatu for rest/web services..
            ivy {
                url 'http://repository.amdatu.org/release/'
                layout 'pattern', {
                    artifact "[artifact]/[artifact]-[revision].[ext]"
                }
            }
            ivy {
                url 'http://repository.amdatu.org/dependencies/'
                layout 'pattern', {
                    artifact "[artifact]/[artifact]-[revision].[ext]"
                }
            }
        }

        // parse a list of constellation configurations that provide an alias for a group of bundles.
        def constellations = new GroovyShell(getClass().classLoader).evaluate(
                getClass().getResourceAsStream('/constellations.groovy').newReader())

        // create a (non-transitive) configuration for each constellation.
        constellations.keySet().each { key ->
            addConfiguration(project, key, constellations[key])
        }
    }

    void addConfiguration(Project project, String key, String resource) {
        def inputStream = getClass().getResourceAsStream("/configurations/${resource}.groovy")
        if (inputStream) {
            def constellation = new GroovyShell(getClass().classLoader).evaluate(inputStream.newReader())

            log.info "Adding configuration [$key]"

            def configuration = project.configurations.create("constellation\$$key")
            configuration.transitive = false
            // add constellation bundles to configuration dependency set
            constellation.each {
                project.dependencies.add(configuration.name, it)
            }
        } else {
            log.warn "Resource for constellation not found [$resource]"
        }
    }
}
