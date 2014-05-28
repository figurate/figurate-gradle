package org.figurate.gradle.plugin.keytool

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by fortuna on 6/05/14.
 */
class KeytoolPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.buildDir.mkdirs()
        project.task('keytool', type: KeytoolTask)

        project.task('genkey', type: KeytoolTask) {
            genkey
            alias = 'default'
            keystore = "${project.buildDir}/client.keystore"
            storepass = 'changeit'
            keypass = 'changeit'
            dname = 'CN=client,OU=Figurate,O=Modularity,L=Melbourne,ST=Victoria,C=AU'
        }

        project.task('storepasswd', type: KeytoolTask) {
            storepasswd
            $new = 'changeit'
            keystore = "${project.buildDir}/client.keystore"
            storepass = 'changeit'
        }

        project.task('genkeypair', type: KeytoolTask) {
            genkeypair
            alias = 'clientkey'
            keyalg = 'RSA'
            dname = 'CN=client,OU=Figurate,O=Modularity,L=Melbourne,ST=Victoria,C=AU'
            keypass = 'changeit'
            keystore = "${project.buildDir}/client.keystore"
            storepass = 'changeit'
        }

        project.task('importcert', type: KeytoolTask) {
            importcert
            noprompt
            alias = 'serverkey'
            file = 'server.cer'
            keystore = "${project.buildDir}/client.keystore"
            storepass = 'changeit'
        }

        project.task('exportcert', type: KeytoolTask) {
            exportcert
            rfc
            alias = 'clientkey'
            file = 'client.cer'
            keypass = 'changeit'
            keystore = "${project.buildDir}/client.keystore"
            storepass = 'changeit'
        }

    }
}
