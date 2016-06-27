package org.figurate.gradle.plugin.curl

/**
 * Created by fortuna on 8/12/14.
 */
class DigitalOceanTask extends CurlTask {

    private static def URL_PATTERN = 'https://api.digitalocean.com%s'

    void setToken(String token) {
        args '--header', "'Authorization: Bearer $token'"
    }

    void setCommand(String command) {
        args String.format(URL_PATTERN, command)
    }
}
