package org.figurate.gradle.plugin.rsync

import org.gradle.api.tasks.Exec

/**
 * Created by fortuna on 24/09/14.
 */
class RsyncTask extends Exec {

    RsyncTask() {
        executable 'rsync'
    }

    def propertyMissing(String name) {
        args "--$name"
    }

    def propertyMissing(String name, String value) {
        args "--$name", value
    }
}
