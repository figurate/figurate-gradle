package org.figurate.gradle.plugin.aem

import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Copy
import org.gradle.api.tasks.Exec
import org.gradle.api.tasks.JavaExec

/**
 * Created by fortuna on 27/02/15.
 */
class AemPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.extensions.create("aem", AemPluginExtension)

        def installDir = { project.aem.installDir ?: "$project.buildDir/aem" }

        project.task('copyAemLicense', type: Copy) {
            from { project.aem.licenseDir }
            include 'license.properties'
            into installDir
        }
        project.task('unpackAem', type: JavaExec) {
            workingDir installDir
            jvmArgs '-Djava.awt.headless=true'
            main '-jar'
            args({ project.aem.installFile }, '-unpack')
        }
//        project.task('runAem', type: JavaExec, dependsOn: 'copyAem') {
//            workingDir "$buildDir/aem"
//            classpath "$buildDir/aem/cq5-author-p4502.jar"
//            main 'com.adobe.granite.quickstart.base.impl.Main'
//        }
        project.task('startAem', type: Exec) {
            workingDir { "${installDir()}/crx-quickstart/bin" }
            executable Os.isFamily(Os.FAMILY_WINDOWS) ? './start.bat' : './start'
        }
        project.task('stopAem', type: Exec) {
            workingDir { "${installDir()}/crx-quickstart/bin" }
            executable Os.isFamily(Os.FAMILY_WINDOWS) ? './stop.bat' : './stop'
        }

        project.tasks.with {
//            unpackAem.dependsOn copyAem
            startAem.dependsOn unpackAem
            startAem.dependsOn copyAemLicense
            stopAem.dependsOn unpackAem
            stopAem.mustRunAfter startAem
        }

    }
}
