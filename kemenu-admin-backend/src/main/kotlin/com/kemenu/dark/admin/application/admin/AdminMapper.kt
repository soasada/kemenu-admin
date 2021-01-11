package com.kemenu.dark.admin.application.admin

import com.kemenu.dark.admin.application.utils.EncodedMapping
import com.kemenu.dark.admin.application.utils.PasswordEncoderMapper
import com.kemenu.dark.admin.domain.Admin
import org.mapstruct.InjectionStrategy.CONSTRUCTOR
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import org.mapstruct.NullValueCheckStrategy.ALWAYS
import java.time.Instant

@Mapper(
        componentModel = "spring",
        nullValueCheckStrategy = ALWAYS,
        injectionStrategy = CONSTRUCTOR,
        uses = [PasswordEncoderMapper::class]
)
interface AdminMapper {

    @Mappings(
            Mapping(target = "id", expression = "java(java.util.UUID.randomUUID().toString())"),
            Mapping(target = "password", qualifiedBy = [EncodedMapping::class]),
            Mapping(target = "createdAt", expression = "java(java.time.Instant.now())")
    )
    fun toEntity(request: CreateAdminRequest): Admin

    @Mappings(
            Mapping(source = "pa", target = "password"),
            Mapping(source = "ca", target = "createdAt")
    )
    fun toEntity(pa: String, ca: Instant, request: UpdateAdminRequest): Admin
}