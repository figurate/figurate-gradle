package org.figurate.gradle.plugin.bundle

import org._10ne.gradle.rest.RestTask
import org.apache.http.entity.mime.MultipartEntityBuilder

class BundleInstallTask extends RestTask {

    BundleInstallTask() {
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
}
