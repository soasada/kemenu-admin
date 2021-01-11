package com.kemenu.dark.admin.application.utils

import org.springframework.core.ResolvableType
import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.core.io.buffer.DataBufferFactory
import org.springframework.core.io.buffer.DataBufferUtils
import org.springframework.http.codec.json.AbstractJackson2Decoder
import org.springframework.http.codec.json.AbstractJackson2Encoder
import org.springframework.stereotype.Component
import org.springframework.util.MimeTypeUtils
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.nio.charset.StandardCharsets

@Component
class JsonUtils(
        private val jacksonDecoder: AbstractJackson2Decoder,
        private val jacksonEncoder: AbstractJackson2Encoder,
        private val dataBufferFactory: DataBufferFactory,
) {
    fun <T> serialize(obj: T, clazz: Class<T>): Mono<String> {
        val objectToSerialize = Mono.just(obj)
        val type = ResolvableType.forClass(clazz)
        val mimeType = MimeTypeUtils.APPLICATION_JSON

        return jacksonEncoder
                .encode(objectToSerialize, dataBufferFactory, type, mimeType, mapOf())
                .flatMap { buffer -> Mono.just(String(buffer.asByteBuffer().array(), StandardCharsets.UTF_8)) }
                .next()
    }

    fun <T> deserialize(obj: String, clazz: Class<T>): Mono<T> {
        val stringToDeserialize = DataBufferUtils.read(ByteArrayResource(obj.encodeToByteArray()), 0, dataBufferFactory, obj.length)
        return deserialize(stringToDeserialize, clazz)
    }

    fun <T> deserialize(bufferToDeserialize: Flux<DataBuffer>, clazz: Class<T>): Mono<T> {
        val type = ResolvableType.forClass(clazz)
        val mimeType = MimeTypeUtils.APPLICATION_JSON

        return jacksonDecoder
                .decodeToMono(bufferToDeserialize, type, mimeType, mapOf())
                .onErrorResume { Mono.empty<T>() }
                .cast(clazz)
    }
}