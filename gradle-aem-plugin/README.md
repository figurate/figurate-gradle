Gradle AEM Plugin
=================

This plugin adds tasks used to control a local AEM instance.

## Unpack AEM
The unpack task is used to initialise an AEM environment by unpacking the installation JAR using the specified port and run mode.

### Configuration:
* aemInstallFile - the location of the installation file
* aemLicenseFile - the location of the AEM license file. The default value is `$projectDir/license.properties`
* aemInstallDir - the location to unpack the AEM instance. The default value is `$buildDir/aem`

## Start AEM
The start task will launch an AEM instance.

### Configuration:
* aemInstallDir - the location of the AEM instance to launch. The default value is `$buildDir/aem`

## Stop AEM
The stop task will shutdown a running AEM instance.

### Configuration:
* aemInstallDir - the location of the AEM instance to launch. The default value is `$buildDir/aem`
