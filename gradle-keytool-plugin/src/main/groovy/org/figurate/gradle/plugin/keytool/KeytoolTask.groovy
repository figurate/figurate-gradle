package org.figurate.gradle.plugin.keytool

import org.gradle.api.tasks.Exec

import java.security.KeyStore

/**
 * Created by fortuna on 6/05/14.
 */
class KeytoolTask extends Exec {

    def argMap = [:]

    KeytoolTask() {
        executable "${new File(environment['JAVA_HOME'], 'bin').canonicalPath}/keytool"
    }

    def propertyMissing(String name) {
        // allow dollar prefix to work around reserved keywords (e.g. 'new')
        args "-${name - ~/^\$/}"
    }

    def propertyMissing(String name, String value) {
        if (value) {
            // allow dollar prefix to work around reserved keywords (e.g. 'new')
            String argKey = "-${name - ~/^\$/}"
            if (argMap[name]) {
                def newArgs = args
                newArgs[newArgs.indexOf(argKey) + 1] = value
                setArgs newArgs
            } else {
                args argKey, value
            }
            argMap[name] = value
        }
    }

    void setKeystore(String keystore) {
        args '-keystore', new File(keystore).canonicalPath
    }

    def extractPrivateKey = { keystoreName, keystorePassword, alias ->
        KeyStore ks = KeyStore.getInstance('jks');
        ks.load(new FileInputStream(keystoreName), keystorePassword.toCharArray());
        StringBuilder builder = new StringBuilder()
        builder << "-----BEGIN PRIVATE KEY-----\n"
        builder << new sun.misc.BASE64Encoder().encode(ks.getKey(alias, keystorePassword.toCharArray()).getEncoded())
        builder << "-----END PRIVATE KEY-----\n"
        builder.toString()
    }
}
