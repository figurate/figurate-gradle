package org.figurate.gradle.plugin.bundle

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by fortuna on 5/05/15.
 */
class BundlePlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.with {
            apply plugin: 'groovy'
            apply plugin: 'osgi'

            configurations {
                // the embed configuration identifies dependencies to embed in an OSGi bundle.
                embed
                embed.transitive = false
            }

            dependencies {
                compile 'org.apache.felix:org.apache.felix.scr.ant:1.9.0',
                        'biz.aQute.bnd:biz.aQute.bndlib:3.1.0'
            }

            ant.properties.src = "$project.buildDir/classes/main"
            ant.properties.classes = "$project.buildDir/classes/main"

            task('genscr', dependsOn: [compileJava, compileGroovy]) << {
                // genscr requires the src dir to exist, using output to create as needed..
                outputs.dir ant.properties.classes
                ant.taskdef(resource: 'scrtask.properties', classpath: sourceSets.main.compileClasspath.asPath)
                ant.scr(srcdir: ant.properties.src, destdir: ant.properties.classes, scanClasses: true,
                        classpath: sourceSets.main.compileClasspath.asPath)
            }
            jar.dependsOn(genscr)

            afterEvaluate {
                jar {
                    into('lib') {
                        from configurations.embed
                    }

                    manifest {
                        instruction 'Bundle-ClassPath', ".,${configurations.embed.dependencies.collect { "lib/$it.name-${it.version}.jar" }.join(',')}"
                    }
                }
            }
        }
    }
}
