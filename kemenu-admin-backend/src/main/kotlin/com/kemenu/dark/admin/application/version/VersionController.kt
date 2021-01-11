package com.kemenu.dark.admin.application.version

import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class VersionController(
        @Value("\${app.version}") val appVersion: String,
) {

    @GetMapping("/v1/app/version")
    fun getVersion() = VersionResponse(appVersion.replace("-SNAPSHOT", ""))

    data class VersionResponse(val version: String)
}