package org.figurate.gradle.plugin.rsync

import org.gradle.api.tasks.AbstractExecTask

/**
 * Created by fortuna on 24/09/14.
 */
class RsyncTask extends AbstractExecTask {

    RsyncTask() {
        super(RsyncTask)
        executable 'rsync'
    }

    def propertyMissing(String name) {
        args "--$name"
    }

    def propertyMissing(String name, String value) {
        args "--$name", value
    }
}
