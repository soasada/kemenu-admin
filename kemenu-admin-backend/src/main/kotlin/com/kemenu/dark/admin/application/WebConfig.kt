package com.kemenu.dark.admin.application

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.kemenu.dark.admin.application.security.JWTService
import com.kemenu.dark.admin.application.security.authentication.AdminReactiveUserDetailsService
import com.kemenu.dark.admin.application.security.authorization.JWTReactiveAuthorizationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.buffer.DataBufferFactory
import org.springframework.core.io.buffer.DefaultDataBufferFactory
import org.springframework.http.HttpMethod
import org.springframework.http.codec.ServerCodecConfigurer
import org.springframework.http.codec.json.AbstractJackson2Decoder
import org.springframework.http.codec.json.AbstractJackson2Encoder
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.http.codec.json.Jackson2JsonEncoder
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authentication.AuthenticationWebFilter
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.CorsConfigurationSource
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource
import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.web.reactive.config.WebFluxConfigurer
import org.springframework.web.reactive.function.client.WebClient

@Configuration
@EnableWebFlux
@EnableWebFluxSecurity
class WebConfig(
        private val mapper: ObjectMapper
) : WebFluxConfigurer {

    companion object {
        val EXCLUDED_PATHS = arrayOf("/v1/login")
    }

    @Bean
    fun configureSecurity(http: ServerHttpSecurity,
                          jwtAuthenticationFilter: AuthenticationWebFilter,
                          jwtService: JWTService
    ): SecurityWebFilterChain {
        return http
                .csrf().disable()
                .logout().disable()
                .authorizeExchange()
                .pathMatchers(*EXCLUDED_PATHS).permitAll()
                .pathMatchers(HttpMethod.GET, "/v1/customers").hasAnyRole("SUPER_ADMIN", "ADMIN", "MODERATOR")
                .pathMatchers("/v1/blog/**").hasAnyRole("SUPER_ADMIN", "ADMIN", "MODERATOR")
                .pathMatchers(HttpMethod.GET, "/v1/customers/**").hasAnyRole("SUPER_ADMIN", "ADMIN", "MODERATOR")
                .pathMatchers(HttpMethod.DELETE, "/v1/customers/**").hasAnyRole("SUPER_ADMIN")
                .pathMatchers("/v1/admins/**").hasAnyRole("SUPER_ADMIN")
                .anyExchange().authenticated()
                .and()
                .addFilterAt(jwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .addFilterAt(JWTReactiveAuthorizationFilter(jwtService), SecurityWebFiltersOrder.AUTHORIZATION)
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
                .build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun authenticationWebFilter(reactiveAuthenticationManager: ReactiveAuthenticationManager,
                                jwtConverter: ServerAuthenticationConverter,
                                serverAuthenticationSuccessHandler: ServerAuthenticationSuccessHandler,
                                jwtServerAuthenticationFailureHandler: ServerAuthenticationFailureHandler): AuthenticationWebFilter {
        val authenticationWebFilter = AuthenticationWebFilter(reactiveAuthenticationManager)
        authenticationWebFilter.setRequiresAuthenticationMatcher { ServerWebExchangeMatchers.pathMatchers(HttpMethod.POST, "/v1/login").matches(it) }
        authenticationWebFilter.setServerAuthenticationConverter(jwtConverter)
        authenticationWebFilter.setAuthenticationSuccessHandler(serverAuthenticationSuccessHandler)
        authenticationWebFilter.setAuthenticationFailureHandler(jwtServerAuthenticationFailureHandler)
        authenticationWebFilter.setSecurityContextRepository(NoOpServerSecurityContextRepository.getInstance())
        return authenticationWebFilter
    }

    @Bean
    fun jacksonDecoder(mapper: ObjectMapper): AbstractJackson2Decoder = Jackson2JsonDecoder(mapper)

    @Bean
    fun jacksonEncoder(mapper: ObjectMapper): AbstractJackson2Encoder = Jackson2JsonEncoder(mapper)

    @Bean
    fun dataBufferFactory(): DataBufferFactory = DefaultDataBufferFactory()

    @Bean
    fun webClient(): WebClient = WebClient.builder().build()

    @Bean
    fun reactiveAuthenticationManager(reactiveUserDetailsService: AdminReactiveUserDetailsService,
                                      passwordEncoder: PasswordEncoder): ReactiveAuthenticationManager {
        val manager = UserDetailsRepositoryReactiveAuthenticationManager(reactiveUserDetailsService)
        manager.setPasswordEncoder(passwordEncoder)
        return manager
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf("http://localhost:8080", "https://admin.kemenu.com")
        configuration.allowedMethods = listOf("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
        configuration.allowCredentials = true
        configuration.allowedHeaders = listOf("Authorization", "Cache-Control", "Origin", "Content-Type", "Accept")
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }

    override fun configureHttpMessageCodecs(configurer: ServerCodecConfigurer) {
        mapper.registerModule(JavaTimeModule())
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        mapper.enable(DeserializationFeature.USE_LONG_FOR_INTS)
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)

        configurer.defaultCodecs().jackson2JsonEncoder(jacksonEncoder(mapper))
        configurer.defaultCodecs().jackson2JsonDecoder(jacksonDecoder(mapper))
    }
}
