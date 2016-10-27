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
            extensions.create("bundle", BundlePluginExtension)
            extensions.create("bundleInstall", BundleInstallTaskExtension)
            extensions.create("bundleStart", BundleActionTaskExtension)
            extensions.create("bundleStop", BundleActionTaskExtension)
            extensions.create("bundleRefresh", BundleActionTaskExtension)
            extensions.create("bundleUpdate", BundleActionTaskExtension)
            extensions.create("bundleUninstall", BundleActionTaskExtension)
            extensions.create("bundleRefreshAll", BundleActionTaskExtension)
            
            /** Installs (or updates) and optionally starts one or more bundles. */
            task('bundleInstall', type: BundleInstallTask) << { 
                uri = bundleInstall.uri
                username = bundleInstall.username
                password = bundleInstall.password
                bundlestart = bundleInstall.bundlestart
                bundlestartlevel = bundleInstall.bundlestartlevel
                bundlefile = bundleInstall.bundlefile
                userAgent = project.name
            }
            
            /** Starts the bundle addressed by the request URL. */
            task('bundleStart', type: BundleActionTask) << { 
                uri = extensions.bundleStart.uri
                username = extensions.bundleStart.username
                password = extensions.bundleStart.password
                parameters = extensions.bundleStart.parameters
                action = 'start'
                userAgent = project.name
            }
            
            /** Stops the bundle addressed by the request URL. */
            task('bundleStop', type: BundleActionTask) << { 
                uri = extensions.bundleStop.uri
                username = extensions.bundleStop.username
                password = extensions.bundleStop.password
                parameters = extensions.bundleStop.parameters
                action = 'stop'
                userAgent = project.name
            }
            
            /** Calls PackageAdmin.refreshPackages(Bundle[]) with the bundle as its sole argument thus forcing the bundle to be rewired. */
            task('bundleRefresh', type: BundleActionTask) << { 
                uri = extensions.bundleRefresh.uri
                username = extensions.bundleRefresh.username
                password = extensions.bundleRefresh.password
                parameters = extensions.bundleRefresh.parameters
                action = 'refresh'
                userAgent = project.name
            }
            
            /** Calls Bundle.update() on the bundle addressed by the request URL or tries to update the bundle through the OBR. */
            task('bundleUpdate', type: BundleActionTask) << { 
                uri = extensions.bundleUpdate.uri
                username = extensions.bundleUpdate.username
                password = extensions.bundleUpdate.password
                parameters = extensions.bundleUpdate.parameters
                action = 'update'
                userAgent = project.name
            }
            
            /** Calls Bundle.uninstall() on the bundle addressed by the request URL. After the installation the framework must be refreshed (see refreshPackages above). */
            task('bundleUninstall', type: BundleActionTask) << { 
                uri = extensions.bundleUninstall.uri
                username = extensions.bundleUninstall.username
                password = extensions.bundleUninstall.password
                parameters = extensions.bundleUninstall.parameters
                action = 'uninstall'
                userAgent = project.name
            }
            
            /** Calls PackageAdmin.refreshPackages(Bundle[]) with a null argument thus refreshing all pending bundles. */
            task('bundleRefreshAll', type: BundleActionTask) << { 
                uri = extensions.bundleRefreshAll.uri
                username = extensions.bundleRefreshAll.username
                password = extensions.bundleRefreshAll.password
                parameters = extensions.bundleRefreshAll.parameters
                action = 'refreshPackages'
                userAgent = project.name
            }

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

    static class BundleActionTaskExtension {
        Object uri
        String username
        String password
        Map parameters
    }

    static class BundleInstallTaskExtension {
        Object uri
        String username
        String password
        File bundlefile = file("$buildDir/libs/$jar.archiveName")
        Boolean bundlestart = true
        Integer bundlestartlevel = 20
    }
}
