package com.kemenu.dark.admin.helper

import com.kemenu.dark.admin.domain.Business
import java.util.UUID

object BusinessHelper {

    fun random() = Business(
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString(),
            listOf(),
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString()
    )
}