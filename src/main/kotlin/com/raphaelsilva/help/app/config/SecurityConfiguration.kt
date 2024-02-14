package com.raphaelsilva.help.app.config

import com.raphaelsilva.help.app.security.JWTAuthenticationFilter
import com.raphaelsilva.help.app.security.JWTLoginFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfiguration(
    private val jwtUtils: JWTUtils,
    private val configuration: AuthenticationConfiguration,
) {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        val request = http.authorizeHttpRequests {
            it.requestMatchers(HttpMethod.POST, "/api/post").hasAuthority("USER")
            it.requestMatchers(HttpMethod.POST, "/api/answer").hasAuthority("USER")
            it.requestMatchers(HttpMethod.POST, "/api/user").permitAll()
            it.requestMatchers(HttpMethod.POST, "/login").permitAll()
            it.requestMatchers(HttpMethod.PUT, "/api/answer/like").authenticated()
            it.requestMatchers(HttpMethod.DELETE, "/api/user/**").hasAuthority("USER")
            it.anyRequest().permitAll()
        }.csrf {
            it.disable()
        }.addFilterBefore(
            JWTLoginFilter(authManager = configuration.authenticationManager, jwtUtils = jwtUtils),
            UsernamePasswordAuthenticationFilter().javaClass
        ).addFilterBefore(
            JWTAuthenticationFilter(jwtUtils = jwtUtils),
            UsernamePasswordAuthenticationFilter().javaClass
        ).sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
        return request.build()
    }

    @Bean
    fun encoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}