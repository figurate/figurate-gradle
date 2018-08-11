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
                compile 'org.apache.felix:org.apache.felix.scr.ant:1.18.0',
                        'biz.aQute.bnd:biz.aQute.bndlib:4.0.0'
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
            extensions.create("bundle", BundlePluginExtension)

            /** Installs (or updates) and optionally starts one or more bundles. */
            task('bundleInstall', type: BundleInstallTask)
            
            /** Starts the bundle addressed by the request URL. */
            BundleActionTask bundleStartTask = task('bundleStart', type: BundleActionTask)
            bundleStartTask.action = 'start'

            /** Stops the bundle addressed by the request URL. */
            BundleActionTask bundleStopTask = task('bundleStop', type: BundleActionTask)
            bundleStopTask.action = 'stop'
            
            /** Calls PackageAdmin.refreshPackages(Bundle[]) with the bundle as its sole argument thus forcing the bundle to be rewired. */
            BundleActionTask bundleRefreshTask = task('bundleRefresh', type: BundleActionTask)
            bundleRefreshTask.action = 'refresh'
            
            /** Calls Bundle.update() on the bundle addressed by the request URL or tries to update the bundle through the OBR. */
            BundleActionTask bundleUpdateTask = task('bundleUpdate', type: BundleActionTask)
            bundleUpdateTask.action = 'update'
            
            /** Calls Bundle.uninstall() on the bundle addressed by the request URL. After the installation the framework must be refreshed (see refreshPackages above). */
            BundleActionTask bundleUninstallTask = task('bundleUninstall', type: BundleActionTask)
            bundleUninstallTask.action = 'uninstall'
            
            /** Calls PackageAdmin.refreshPackages(Bundle[]) with a null argument thus refreshing all pending bundles. */
            BundleActionTask bundleRefreshAllTask = task('bundleRefreshAll', type: BundleActionTask)
            bundleRefreshAllTask.action = 'refreshPackages'

            afterEvaluate {
                jar {
                    into(bundle.embedPath) {
                        from configurations.embed
                    }

                    manifest {
                        instruction 'Bundle-ClassPath', ".,${configurations.embed.dependencies.collect { "$bundle.embedPath/$it.name-${it.version}.jar" }.join(',')}"
                    }
                }
                
                bundleInstall {
                    bundlefile = bundlefile ?: file("$buildDir/libs/$jar.archiveName")
                    bundlestart = bundlestart ?: true
                    bundlestartlevel = bundlestartlevel ?: 20
                }
                
                [bundleInstall, bundleStart, bundleStop, bundleRefresh, bundleUpdate, bundleUninstall, bundleRefreshAll].each { task ->
                    task.userAgent = task.userAgent ?: project.name
                }
            }
        }
    }

    static class BundlePluginExtension {
        String embedPath = 'lib'
    }
}
