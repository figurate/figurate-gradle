package org.figurate.gradle.plugin

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

/**
 * Created by fortuna on 11/05/14.
 */
class LauncherConfigTaskSpec extends Specification {

    def 'test create config'() {
        given:
        Project project = ProjectBuilder.builder().withName('figurate-test')
                .withProjectDir(new File('build', 'figurate-test')).build()
        project.task('launcher', type: LauncherConfigTask)

        and:
        project.tasks.launcher.outputDir = project.file("$project.buildDir/test-config")

        when:
        project.tasks.launcher.execute()

        then:
        def configFile = new File(project.buildDir, "test-config/$project.tasks.launcher.outputFilename")
        configFile.exists()

        and:
        configFile.text == '''{ binding ->
    config {
        ['org.osgi.framework.executionenvironment':'J2SE-1.7,J2SE-1.6,J2SE-1.5,J2SE-1.4,J2SE-1.3,JavaSE-1.6',
'org.osgi.framework.storage':'bundle-cache',
'org.osgi.framework.storage.clean':'onFirstInit',
]
    }
    bundles {
        [].each { bundle ->
            start bundle
        }
    }
    startLevels {
        [:]
    }
}
'''
    }

}
