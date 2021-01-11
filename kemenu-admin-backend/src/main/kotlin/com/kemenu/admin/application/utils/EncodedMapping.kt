package com.kemenu.admin.application.utils

import org.mapstruct.Qualifier

@Qualifier
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class EncodedMapping