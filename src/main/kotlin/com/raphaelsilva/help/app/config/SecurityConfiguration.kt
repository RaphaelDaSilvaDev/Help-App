package com.raphaelsilva.help.app.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfiguration {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http.authorizeHttpRequests {
            it.requestMatchers(HttpMethod.POST, "/api/post").authenticated()
        }.authorizeHttpRequests {
            it.requestMatchers(HttpMethod.POST, "/api/answer").authenticated()
        }.authorizeHttpRequests {
            it.requestMatchers(HttpMethod.PUT, "/api/answer/like").authenticated()
        }.authorizeHttpRequests {
            it.anyRequest().permitAll()
        }.formLogin {
            it.disable()
        }.csrf { it.disable() }.httpBasic { }.build()
    }

    @Bean
    fun encoder(): PasswordEncoder? {
        return BCryptPasswordEncoder()
    }
}