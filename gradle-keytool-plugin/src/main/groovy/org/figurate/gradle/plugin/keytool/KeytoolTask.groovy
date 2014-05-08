package org.figurate.gradle.plugin.keytool

import org.gradle.api.tasks.Exec
import org.gradle.api.tasks.TaskAction

/**
 * Created by fortuna on 6/05/14.
 */
class KeytoolTask extends Exec {

    KeytoolTask() {
        executable "${new File(environment['JAVA_HOME'], 'bin').canonicalPath}/keytool"
    }

    def propertyMissing(String name) {
        args "-$name"
    }

    def propertyMissing(String name, String value) {
        if (value) {
            args "-$name", value
        }
    }

    void setKeystore(String keystore) {
        args '-keystore', new File(keystore).canonicalPath
    }
}
