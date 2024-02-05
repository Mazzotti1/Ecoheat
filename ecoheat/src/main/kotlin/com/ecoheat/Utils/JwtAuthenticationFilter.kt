package com.ecoheat.Utils
import com.ecoheat.Service.Impl.UserServiceImpl
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.MessageSource
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import java.util.*

class JwtAuthenticationFilter(
    private val jwtToken: JwtToken,
    private val messageSource: MessageSource,
    private val userService: UserServiceImpl) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain
    ) {
        val token = extractTokenFromRequest(request)

        if (token != null) {
            try {
                val claims = Jwts
                    .parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(jwtToken.secretKey))
                    .build()
                    .parseClaimsJws(token)
                    .body

                val userId = claims.id
                val user = userService.getUserById(userId.toLong())
                val authorities = user?.role?.name?.let { listOf(SimpleGrantedAuthority(it)) } ?: emptyList()

                val authentication: Authentication = UsernamePasswordAuthenticationToken(userId, null, authorities)
                SecurityContextHolder.getContext().authentication = authentication
            } catch (ex: Exception) {
                logger.error("Erro ao processar token JWT", ex)
            }
        }

        chain.doFilter(request, response)
    }

    private fun extractTokenFromRequest(request: HttpServletRequest): String? {
        val locale = Locale("pt")
        val header = request.getHeader(messageSource.getMessage("authorization.header", null, locale))
        return if (header != null && header.startsWith(messageSource.getMessage("bearer.prefix", null, locale))) {
            header.substring(7)
        } else null
    }
}