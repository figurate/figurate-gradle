package org.figurate.gradle.plugin

import spock.lang.Shared
import spock.lang.Specification

/**
 * Created by fortuna on 12/06/14.
 */
class ConstellationSpec extends Specification {

    @Shared def constellations = [:]

    def setupSpec() {
//        new File('src/main/resources/constellations').listFiles().each {
//            constellations << new GroovyShell(ConstellationSpec.classLoader).evaluate(it)
//        }
        constellations = new GroovyShell(ConstellationSpec.classLoader).evaluate(getClass().getResourceAsStream('/constellations.groovy').newReader())
    }

    def 'verify constellation formats'() {
        expect:
        constellation instanceof Map.Entry

        where:
        constellation << constellations
    }
}
