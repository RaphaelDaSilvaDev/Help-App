package com.raphaelsilva.help.app.config

import com.raphaelsilva.help.app.service.UserService
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.stereotype.Component
import java.util.*

@Component
class JWTUtils(
    private val userService: UserService
) {
    private val secret: String = System.getenv("JWT_SECRET")
    private val expiration: Long = 900000
    fun generateToken(username: String, authorities: MutableCollection<out GrantedAuthority>): String? {
        return Jwts.builder()
            .setSubject(username)
            .claim("role", authorities)
            .setExpiration(Date(System.currentTimeMillis() + expiration))
            .signWith(SignatureAlgorithm.HS512, secret.toByteArray())
            .compact()
    }

    fun isValid(jwt: String?): Boolean {
        return try {
            Jwts.parser().setSigningKey(secret.toByteArray()).parseClaimsJws(jwt)
            true
        }catch (e: IllegalArgumentException){
            false
        }
    }

    fun getAuthentication(jwt: String?): UsernamePasswordAuthenticationToken {
        val username = Jwts.parser().setSigningKey(secret.toByteArray()).parseClaimsJws(jwt).body.subject
        val authorities = userService.loadUserByUsername(username).authorities
        return UsernamePasswordAuthenticationToken(username, null, authorities)
    }
}