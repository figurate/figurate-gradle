package org.figurate.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Copy

/**
 * Created by fortuna on 8/05/14.
 */
class FiguratePlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.apply plugin: 'application'

        project.repositories {
            mavenCentral()
        }

        project.configurations {
            bundle
            bundle.transitive = false

            constellation$logback
            constellation$logback.transitive = false

            constellation$jcr
            constellation$jcr.transitive = false

            constellation$jackson
            constellation$jackson.transitive = false

            constellation$amdatu
            constellation$amdatu.transitive = false
        }

        project.dependencies {
//            runtime 'org.figurate:figurate-bootstrap:1.0'
            runtime project.files('/Users/fortuna/Development/figurate-core/bootstrap/build/libs/bootstrap.jar')
            runtime 'org.apache.felix:org.apache.felix.framework:4.0.2'
//            runtime project.dependencies.localGroovy()
            runtime 'org.codehaus.groovy:groovy-all:2.3.0-rc-1',
                    'org.slf4j:slf4j-api:1.7.5'

            constellation$logback 'ch.qos.logback:logback-core:1.1.1',
                    'ch.qos.logback:logback-classic:1.1.1'

            constellation$jcr 'javax.jcr:jcr:2.0',
                    'org.modeshape:modeshape-jcr:4.0.0.Alpha3',
                    'org.modeshape:modeshape-jcr-api:4.0.0.Alpha3',
                    'org.modeshape:modeshape-common:4.0.0.Alpha3',
                    'com.datastax.cassandra:cassandra-driver-core:2.0.2',
                    'com.codahale.metrics:metrics-core:3.0.2',
                    'org.slf4j:slf4j-api:1.7.5',
                    'com.google.guava:guava:16.0.1',
                    'io.netty:netty:3.9.1.Final',
                    'org.mongodb:mongo-java-driver:2.12.2',
                    'javax.security.jacc:javax.security.jacc-api:1.5',
                    'javax.servlet:javax.servlet-api:3.1.0',
                    'javax.transaction:javax.transaction-api:1.2',
                    'javax.enterprise:cdi-api:1.2',
                    'javax.el:javax.el-api:3.0.1-b01',
                    'javax.interceptor:javax.interceptor-api:1.2',
                    'org.apache.commons:commons-compress:1.8.1',
                    'org.apache.tika:tika-core:1.5',
                    'org.infinispan:infinispan-core:6.0.2.Final',
                    'org.infinispan:infinispan-commons:6.0.2.Final',
                    'org.jboss.logging:jboss-logging:3.2.0.Beta1',
                    'org.apache.logging.log4j:log4j-api:2.0-rc1',
                    'org.jboss.marshalling:jboss-marshalling-osgi:2.0.0.Beta1',
                    'org.jgroups:jgroups:3.5.0.Beta6',
                    'org.modeshape:modeshape-schematic:4.0.0.Alpha3',
                    'joda-time:joda-time:1.6.2',
                    'org.mapdb:mapdb:0.9.13',
                    'log4j:log4j:1.2.17'

            constellation$jackson 'org.codehaus.jackson:jackson-core-asl:1.9.13',
                    'org.codehaus.jackson:jackson-jaxrs:1.9.13',
                    'org.codehaus.jackson:jackson-mapper-asl:1.9.13'

            constellation$amdatu 'org.amdatu.web.resourcehandler:org.amdatu.web.resourcehandler:1.0.4',
                    'org.amdatu.web.rest.doc:org.amdatu.web.rest.doc:1.1.1',
                    'org.amdatu.web.rest.doc.swagger:org.amdatu.web.rest.doc.swagger:1.0.4',
                    'org.amdatu.web.rest.doc.swagger.ui:org.amdatu.web.rest.doc.swagger.ui:1.0.3',
                    'org.amdatu.web.rest.jaxrs:org.amdatu.web.rest.jaxrs:1.0.4',
                    'org.amdatu.web.rest.wink:org.amdatu.web.rest.wink:1.0.8'
        }

        project.mainClassName = 'org.figurate.FrameworkLauncher'
        project.tasks.run.args "${project.buildDir}/config/${project.name}.conf"
        project.tasks.run.workingDir 'build'
        project.applicationDefaultJvmArgs = ["-Xmx512m"]

        // copy bundles for osgi runtime..
        project.task('copyBundles', type: Copy) {
            from project.configurations.bundle
            into "${project.buildDir}/bundles"
        }

        // create launcher configuration:
        // * system properties
        // * bundles
        project.task('launcherConfig', type: LauncherConfigTask)
//        project.task('testLauncherConfig', type: LauncherConfigTask)  {
//            outputDir = project.file("$project.buildDir/test-config")
//        }

        project.applicationDistribution.from(project.tasks.copyBundles) {
            into "bundles"
        }
        project.applicationDistribution.from(project.tasks.launcherConfig) {
            into "config"
        }

        // assemble files for osgi runtime:
        // * bundles
        // * configuration (conf/figurate.conf)
        // * bootstrap (osgi framework - e.g. felix, figurate-bootstrap)
        // * start/stop scripts
//        project.task('assemble') {
//
//        }

        project.tasks.run.dependsOn project.tasks.copyBundles, project.tasks.launcherConfig
//        project.tasks.test.dependsOn project.tasks.copyBundles, project.tasks.testLauncherConfig
//        project.tasks.installApp.dependsOn project.tasks.launcherConfig
//        if (project.plugins.hasPlugin('java')) {
//            project.tasks.test.dependsOn project.tasks.copyBundles
//        }
    }
}
