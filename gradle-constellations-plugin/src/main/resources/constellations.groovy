[
        felix: ['org.apache.felix:org.apache.felix.dependencymanager:3.1.0',
                'org.apache.felix:org.apache.felix.http.bundle:2.2.2',
                'org.apache.felix:org.apache.felix.log:1.0.1',
                'org.apache.felix:org.apache.felix.scr:1.8.2',
                'org.apache.felix:org.apache.felix.scr.annotations:1.9.6',
                'org.osgi:org.osgi.compendium:5.0.0'],

        scr: [
                'org.apache.felix:org.apache.felix.scr:1.8.2',
                'org.apache.felix:org.apache.felix.scr.annotations:1.9.6',
                'org.osgi:org.osgi.compendium:5.0.0'
        ],

        http: [
                'org.apache.felix:org.apache.felix.dependencymanager:3.1.0',
//                'org.apache.felix:org.apache.felix.http.bundle:2.3.0',
                'org.apache.felix:org.apache.felix.http.bundle:2.2.2',
        ],

        jcr: ['javax.jcr:jcr:2.0',
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
              'log4j:log4j:1.2.17'],

        logback: ['ch.qos.logback:logback-core:1.1.1',
                  'ch.qos.logback:logback-classic:1.1.1'],

        jackson: ['org.codehaus.jackson:jackson-core-asl:1.9.13',
                  'org.codehaus.jackson:jackson-jaxrs:1.9.13',
                  'org.codehaus.jackson:jackson-mapper-asl:1.9.13'],

        amdatu: ['org.amdatu.web.resourcehandler:org.amdatu.web.resourcehandler:1.0.4',
                 'org.amdatu.web.rest.doc:org.amdatu.web.rest.doc:1.1.1',
                 'org.amdatu.web.rest.doc.swagger:org.amdatu.web.rest.doc.swagger:1.0.4',
                 'org.amdatu.web.rest.doc.swagger.ui:org.amdatu.web.rest.doc.swagger.ui:1.0.3',
                 'org.amdatu.web.rest.jaxrs:org.amdatu.web.rest.jaxrs:1.0.4',
                 'org.amdatu.web.rest.wink:org.amdatu.web.rest.wink:1.0.8'],

        mongodb: [
                'org.mongodb:mongo-java-driver:2.11.3'
        ],

        useradmin: [
                'org.apache.felix:org.apache.felix.useradmin:1.0.3',
                'org.apache.felix:org.apache.felix.useradmin.mongodb:1.0.1'
        ],

        requestlog: [
                'org.amdatu.web.requestlog.mongo:org.amdatu.web.requestlog.mongo:1.0.0',
                'org.amdatu.web.requestlog.rest:org.amdatu.web.requestlog.rest:1.0.0',
                'org.amdatu.web.requestlog.webviewer:org.amdatu.web.requestlog.webviewer:1.0.1',
                'org.amdatu.web.requestlog.api:org.amdatu.web.requestlog.api:1.0.0',
                'org.amdatu.web.requestlog.filter:org.amdatu.web.requestlog.filter:1.0.0',
                'de.undercouch:bson4jackson:1.3.0',
                'net.vz.mongodb.jackson.mongo-jackson-mapper:net.vz.mongodb.jackson.mongo-jackson-mapper:1.4.3',
                'javax.persistence:javax.persistence:2.0.3',
                'org.amdatu.mongo:org.amdatu.mongo:1.1.1',
//                'javax.servlet:javax.servlet:2.5.0',
        ],

        'karaf.jaas': [
                'org.apache.karaf.jaas:org.apache.karaf.jaas.modules:3.0.1',
                'org.apache.karaf.jaas:org.apache.karaf.jaas.config:3.0.1',
                'org.apache.aries.blueprint:org.apache.aries.blueprint.core:1.4.0',
                'org.apache.aries.proxy:org.apache.aries.proxy:1.0.1',
                'org.apache.aries:org.apache.aries.util:1.1.0',
                'org.apache.felix:org.apache.felix.fileinstall:3.4.0'

        ],

        webconsole: [
                'org.apache.felix:org.apache.felix.webconsole:4.2.2:all',
                // provides type information for configuration admin
                'org.apache.felix:org.apache.felix.metatype:1.0.10'
        ],

        sling: [
                // 1: Basic bundles required for the correct operation of Sling
                'org.apache.sling:org.apache.sling.commons.log:4.0.0',
                // 5: Apache Felix Web Console
                'org.apache.felix:org.apache.felix.bundlerepository:2.0.2', 'org.apache.felix:org.apache.felix.webconsole:4.2.2',
                'org.apache.sling:org.apache.sling.extensions.threaddump:0.2.2',
                // 10: OSGi Compendium Service Implementations
                'org.apache.felix:org.apache.felix.configadmin:1.8.0', 'org.apache.felix:org.apache.felix.eventadmin:1.3.2', 'org.apache.felix:org.apache.felix.metatype:1.0.10',
                'org.apache.felix:org.apache.felix.scr:1.8.2',
                // 15: JCR Repository (Jackrabbit)
                'commons-collections:commons-collections:3.2.1', 'commons-io:commons-io:1.4', 'commons-lang:commons-lang:2.6', 'org.apache.jackrabbit:jackrabbit-api:2.8.0', 'org.apache.jackrabbit:jackrabbit-jcr-commons:2.8.0',
                'org.apache.sling:org.apache.sling.commons.mime:2.1.4', 'org.apache.sling:org.apache.sling.commons.osgi:2.2.0', //'org.apache.sling:org.apache.sling.jcr.api:2.0.2-incubator',
                'org.apache.sling:org.apache.sling.jcr.api:2.2.0',
                'org.apache.sling:org.apache.sling.jcr.base:2.1.2', 'org.apache.sling:org.apache.sling.jcr.jackrabbit.server:2.1.2', 'org.apache.sling:org.apache.sling.jcr.webdav:2.2.2',
                // 0: Actual Sling Application bundles
                'org.apache.sling:org.apache.sling.adapter:2.1.0', 'org.apache.sling:org.apache.sling.api:2.7.0', 'org.apache.sling:org.apache.sling.bundleresource.impl:2.2.0',
                'org.apache.sling:org.apache.sling.commons.json:2.0.6', 'org.apache.sling:org.apache.sling.engine:2.3.2', 'org.apache.sling:org.apache.sling.extensions.apt.parser:2.0.2-incubator',
                'org.apache.sling:org.apache.sling.extensions.apt.servlet:2.0.2-incubator', 'org.apache.sling:org.apache.sling.httpauth:2.0.4-incubator', 'org.apache.sling:org.apache.sling.jcr.classloader:3.2.0',
                'org.apache.sling:org.apache.sling.jcr.contentloader:2.1.8', 'org.apache.sling:org.apache.sling.jcr.ocm:2.0.4-incubator', 'org.apache.sling:org.apache.sling.jcr.resource:2.3.6',
                'org.apache.sling:org.apache.sling.launchpad.content:2.0.6', 'org.apache.sling:org.apache.sling.samples.path-based.rtp:2.0.2-incubator',
                'org.apache.sling:org.apache.sling.scripting.api:2.1.6', 'org.apache.sling:org.apache.sling.scripting.core:2.0.26', 'org.apache.sling:org.apache.sling.scripting.javascript:2.0.14',
                'org.apache.sling:org.apache.sling.scripting.jsp:2.1.4', 'org.apache.sling:org.apache.sling.scripting.jsp.taglib:2.2.0', 'org.apache.sling:org.apache.sling.servlets.get:2.1.8',
                'org.apache.sling:org.apache.sling.servlets.post:2.3.4', 'org.apache.sling:org.apache.sling.servlets.resolver:2.3.2'
        ],

        // http://sling.apache.org/documentation/bundles/sling-health-check-tool.html
        healthcheck: [
                'org.apache.sling:org.apache.sling.hc.webconsole:1.1.0',
                'org.apache.sling:org.apache.sling.hc.core:1.1.0',
                'org.apache.sling:org.apache.sling.commons.threads:3.2.0',
                'org.apache.sling:org.apache.sling.hc.support:1.0.4',
                'org.apache.sling:org.apache.sling.hc.samples:1.0.4'
        ]
]
