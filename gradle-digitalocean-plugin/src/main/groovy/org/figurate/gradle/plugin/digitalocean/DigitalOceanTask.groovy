package org.figurate.gradle.plugin.digitalocean

import org._10ne.gradle.rest.RestTask

/**
 * Created by fortuna on 17/03/15.
 */
class DigitalOceanTask extends RestTask {

    def apiVersion = 'v2'
    def baseUrl = 'https://api.digitalocean.com'

    DigitalOceanTask() {
        contentType = groovyx.net.http.ContentType.JSON
    }

    void setCommand(def action) {
        uri = "$baseUrl/$apiVersion/$action"
    }

    void setToken(def token) {
        requestHeaders = [Authorization: "Bearer $token", 'Content-Type': 'application/json']
    }
}
