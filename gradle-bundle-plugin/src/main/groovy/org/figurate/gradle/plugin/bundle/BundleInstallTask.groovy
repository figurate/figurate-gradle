package org.figurate.gradle.plugin.bundle

import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional

class BundleInstallTask extends AbstractBundleTask {

    @Input
    File bundlefile

    @Input
    @Optional
    Boolean bundlestart
    
    @Input
    @Optional
    Integer bundlestartlevel
    
    BundleInstallTask() {
        action = 'install'
    }
    
    void executeRequest() {
        parameters = [bundlefile: bundlefile]
        if (bundlestart) {
            parameters['bundlestart'] = bundlestart
        }
        if (bundlestartlevel) {
            parameters['bundlestartlevel'] = bundlestartlevel
        }
        super.executeRequest()
    }
}
