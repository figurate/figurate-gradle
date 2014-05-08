package org.figurate.gradle.plugin.curl

import org.gradle.api.tasks.Exec

/**
 * Created by fortuna on 8/05/14.
 */
class CurlTask extends Exec {

    CurlTask() {
        executable 'curl'
    }

    def propertyMissing(String name) {
        args "--$name"
    }

    def propertyMissing(String name, String value) {
        args "--$name", value
    }

    void setUrl(String url) {
        args url
    }
}
