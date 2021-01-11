package com.kemenu.dark.admin.helper

import org.apache.commons.lang3.RandomStringUtils

object WordHelper {

    fun random() = RandomStringUtils.random(15, 0, 7, true, false, 'Á', 'á', 't', 'T', 'í', 'é', 't', 'Ú')
}