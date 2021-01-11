package com.kemenu.dark.admin.helper

import com.kemenu.dark.admin.domain.Admin
import java.util.UUID

object AdminHelper {

    fun random(): Admin {
        return Admin(
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString() + "@example.com",
                UUID.randomUUID().toString(),
                Admin.Role.SUPER_ADMIN
        )
    }
}