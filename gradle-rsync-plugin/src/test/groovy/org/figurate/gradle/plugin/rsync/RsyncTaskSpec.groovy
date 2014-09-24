package org.figurate.gradle.plugin.rsync

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

/**
 * Created by fortuna on 24/09/14.
 */
class RsyncTaskSpec extends Specification {

    def 'verify rsync version'() {
        given: 'a project with the rsync task applied'
        Project project = ProjectBuilder.builder().build()
        project.task('rsync', type: RsyncTask)

        and: 'the version option specified'
        project.tasks.rsync.version

        and: 'standard output being captured'
        project.tasks.rsync.standardOutput = new ByteArrayOutputStream()

        when: 'the task is executed'
        project.tasks.rsync.execute()

        then: 'the output contains a version number'
        project.tasks.rsync.standardOutput.toString().startsWith('rsync  version ')
    }
}
