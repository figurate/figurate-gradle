package org.figurate.gradle.plugin.digitalocean

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by fortuna on 17/03/15.
 */
class DigitalOceanPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.repositories {
            jcenter()
        }

        project.dependencies {
            runtime('org._10ne.gradle:rest-gradle-plugin:0.3.2')
        }
        project.apply plugin: 'org.10ne.rest'

        project.with {
            task droplets(type: DigitalOceanTask) {
                token = System.getenv()['DO_API_TOKEN']
                command = 'droplets'
            }
        }
    }
}
