package org.figurate.gradle.plugin.bundle

import org._10ne.gradle.rest.RestTask
import org.apache.http.entity.mime.MultipartEntityBuilder
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional

class BundleActionTask extends RestTask {

    @Input
    String action
    
    @Input
    @Optional
    String userAgent
    
    @Input
    @Optional
    Map parameters
    
    BundleActionTask() {
        client.encoder.putAt('multipart/form-data', { body ->
            MultipartEntityBuilder multipartRequestEntity = MultipartEntityBuilder.create()
            body.each { part ->
                if (part.value instanceof File) {
                    multipartRequestEntity.addBinaryBody(part.key, part.value).build()
                } else {
                    multipartRequestEntity.addTextBody(part.key, part.value).build()
                }
            }
            multipartRequestEntity.build()
        })

        httpMethod = 'post'
        contentType = 'multipart/form-data'
    }
    
    void executeRequest() {
        def headersMap = ['Cache-Control': 'no-cache']
        if (userAgent) {
            headersMap['User-Agent'] = userAgent
        }
        requestHeaders = headersMap
        
        def paramsMap = [action: action]
        if (parameters) {
            paramsMap.putAll(parameters)
        }
        requestBody = paramsMap
        
        super.executeRequest()
    }
}
