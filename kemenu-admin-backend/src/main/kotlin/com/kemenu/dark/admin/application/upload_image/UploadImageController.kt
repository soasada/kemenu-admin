package com.kemenu.dark.admin.application.upload_image

import com.kemenu.dark.admin.application.cloudinary.CloudinaryService
import org.springframework.http.codec.multipart.FilePart
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1")
class UploadImageController(
        private val cloudinaryService: CloudinaryService
) {

    @PostMapping("/upload/image")
    fun uploadImage(@RequestPart("file") file: FilePart) = cloudinaryService
            .upload(file)
            .map { UploadImageResponse(it) }
}