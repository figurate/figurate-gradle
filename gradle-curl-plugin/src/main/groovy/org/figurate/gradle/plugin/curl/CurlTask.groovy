package org.figurate.gradle.plugin.curl

import org.gradle.api.tasks.AbstractExecTask

/**
 * Created by fortuna on 8/05/14.
 */
class CurlTask extends AbstractExecTask {

    CurlTask() {
        super(CurlTask)
        executable '/usr/bin/curl'
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
