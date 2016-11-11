# Gradle Bundle Plugin

This plugin provides support for creating, deploying and maintaining OSGi bundles.
Whilst rudimentary [OSGi support] is already provide by the Gradle platform, this
plugin provides additional features to augment the features of the default OSGi plugin.

## SCR Annotations

At the time of writing, the default OSGi plugin doesn't support generating Service Component Runtime (SCR)
descriptors from [Java annotations]. This plugin adds such support via the genscr task.

    $ ./gradlew genscr # Scan for SCR annotations and generates XML descriptors
    
By default the jar task depends on genscr so it will be performed automatically
as part of artefact generation.

# Deploying Bundles

This plugin also supports bundle deployment via the [Felix Web Console API]. At a
minimum you must provide configuration for the target URI.

    # build.gradle
    ...
    
    bundleInstall {
        uri = "http://$sling_host:$sling_port/system/console/bundles"
        username = sling_username
        password = sling_password
    }

[OSGi support]: https://docs.gradle.org/current/userguide/osgi_plugin.html
[Java annotations]: http://felix.apache.org/documentation/subprojects/apache-felix-maven-scr-plugin/scr-annotations.html
[Felix Web Console API]: http://felix.apache.org/documentation/subprojects/apache-felix-web-console/web-console-restful-api.html
