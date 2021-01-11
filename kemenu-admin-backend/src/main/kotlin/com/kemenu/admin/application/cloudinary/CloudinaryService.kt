package com.kemenu.admin.application.cloudinary

import com.cloudinary.Cloudinary
import com.kemenu.admin.application.utils.logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.core.io.buffer.DataBufferUtils
import org.springframework.http.codec.multipart.FilePart
import org.springframework.stereotype.Service
import reactor.core.scheduler.Schedulers

@Service
class CloudinaryService(
        @Value("\${app.cloudinary.key}") val key: String,
        @Value("\${app.cloudinary.secret}") val secret: String,
        @Value("\${app.cloudinary.cloudname}") val cloudName: String
) {

    val logger = logger()
    val cloudinary = Cloudinary(
            mapOf(
                    Pair("api_key", key),
                    Pair("api_secret", secret),
                    Pair("cloud_name", cloudName)
            )
    )

    fun upload(file: FilePart) = file.content()
            .map { dataBuffer -> toBytes(dataBuffer) }
            .next()
            .map { cloudinary.uploader().upload(it, mapOf(Pair("resource_type", "auto"))) }
            .map { it["secure_url"] }
            .map { getOptimizedUrl(it as String) }
            .subscribeOn(Schedulers.boundedElastic())
            .doOnError { logger.error("Cloudinary ERROR", it) }

    private fun toBytes(dataBuffer: DataBuffer): ByteArray {
        val bytes = ByteArray(dataBuffer.readableByteCount())
        dataBuffer.read(bytes)
        DataBufferUtils.release(dataBuffer)
        return bytes
    }

    private fun getOptimizedUrl(url: String): String {
        val lastDashIndex = url.lastIndexOf("/")
        if (lastDashIndex == -1) {
            return ""
        }
        val imageName = url.substring(lastDashIndex + 1)
        val transformation = cloudinary.url().transformation()
        return cloudinary
                .url()
                .transformation(transformation.quality("auto").fetchFormat("auto"))
                .secure(true)
                .generate(imageName)
    }
}