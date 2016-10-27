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

            task('genscr', dependsOn: [compileJava, compileGroovy]) {
                // genscr requires the src dir to exist, using output to create as needed..
                outputs.dir ant.properties.classes

                doLast {
                    ant.taskdef(resource: 'scrtask.properties', classpath: sourceSets.main.compileClasspath.asPath)
                    ant.scr(srcdir: ant.properties.src, destdir: ant.properties.classes, scanClasses: true,
                        classpath: sourceSets.main.compileClasspath.asPath)
                }
            }
            
            jar {
                dependsOn(genscr)
                includeEmptyDirs = false
            }

            // define bundle tasks..
            
            /** Installs (or updates) and optionally starts one or more bundles. */
            task bundleInstall(type: BundleInstallTask) { 
                bundlestart = true
                bundlestartlevel = 20
                bundlefile = file("$buildDir/libs/$jar.archiveName")
                userAgent = project.name
            }
            
            /** Starts the bundle addressed by the request URL. */
            task bundleStart(type: BundleActionTask) { 
                action = 'start'
                userAgent = project.name
            }
            
            /** Stops the bundle addressed by the request URL. */
            task bundleStop(type: BundleActionTask) { 
                action = 'stop'
                userAgent = project.name
            }
            
            /** Calls PackageAdmin.refreshPackages(Bundle[]) with the bundle as its sole argument thus forcing the bundle to be rewired. */
            task bundleRefresh(type: BundleActionTask) { 
                action = 'refresh'
                userAgent = project.name
            }
            
            /** Calls Bundle.update() on the bundle addressed by the request URL or tries to update the bundle through the OBR. */
            task bundleUpdate(type: BundleActionTask) { 
                action = 'update'
                userAgent = project.name
            }
            
            /** Calls Bundle.uninstall() on the bundle addressed by the request URL. After the installation the framework must be refreshed (see refreshPackages above). */
            task bundleUninstall(type: BundleActionTask) { 
                action = 'uninstall'
                userAgent = project.name
            }
            
            /** Calls PackageAdmin.refreshPackages(Bundle[]) with a null argument thus refreshing all pending bundles. */
            task bundleRefreshAll(type: BundleActionTask) { 
                action = 'refreshPackages'
                userAgent = project.name
            }
            
            extensions.create("bundle", BundlePluginExtension)

            afterEvaluate {
                jar {
                    into(bundle.embedPath) {
                        from configurations.embed
                    }

                    manifest {
                        instruction 'Bundle-ClassPath', ".,${configurations.embed.dependencies.collect { "$bundle.embedPath/$it.name-${it.version}.jar" }.join(',')}"
                    }
                }
            }
        }
    }

    static class BundlePluginExtension {
        String embedPath = 'lib'
    }
}
