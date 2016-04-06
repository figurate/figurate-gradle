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
            args '-genkey'
            options {
                alias = 'default'
                keystore = "${project.buildDir}/client.keystore"
                storepass = 'changeit'
                keypass = 'changeit'
                dname = 'CN=client,OU=Figurate,O=Modularity,L=Melbourne,ST=Victoria,C=AU'
            }
        }

        project.task('storepasswd', type: KeytoolTask) {
            args '-storepasswd'
            options {
                $new = 'changeit'
                keystore = "${project.buildDir}/client.keystore"
                storepass = 'changeit'
            }
        }

        project.task('genkeypair', type: KeytoolTask) {
            args '-genkeypair'
            options {
                alias = 'clientkey'
                keyalg = 'RSA'
                dname = 'CN=client,OU=Figurate,O=Modularity,L=Melbourne,ST=Victoria,C=AU'
                keypass = 'changeit'
                keystore = "${project.buildDir}/client.keystore"
                storepass = 'changeit'
            }
        }

        project.task('importcert', type: KeytoolTask) {
            args '-importcert', '-noprompt'
            options {
                alias = 'serverkey'
                file = 'server.cer'
                keystore = "${project.buildDir}/client.keystore"
                storepass = 'changeit'
            }
        }

        project.task('exportcert', type: KeytoolTask) {
            args '-exportcert', '-rfc'
            options {
                alias = 'clientkey'
                file = 'client.cer'
                keypass = 'changeit'
                keystore = "${project.buildDir}/client.keystore"
                storepass = 'changeit'
            }
        }
    }
}
