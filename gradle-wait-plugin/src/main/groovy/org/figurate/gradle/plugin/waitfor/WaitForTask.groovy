package org.figurate.gradle.plugin.waitfor

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

import java.util.concurrent.TimeUnit

class WaitForTask extends DefaultTask {

    String host
    int port
    int timeout

    @TaskAction
    void waitFor() {
        println "Waiting for service availability: $host:$port"
        long startTime = System.currentTimeMillis()
        def executor = java.util.concurrent.Executors.newSingleThreadScheduledExecutor()
        executor.scheduleWithFixedDelay({
            try {
                new Socket(host, port)
                return
            } catch (IOException e) {}
        }, 0, 300, TimeUnit.MILLISECONDS)
        executor.awaitTermination(timeout, TimeUnit.SECONDS)
    }
}
