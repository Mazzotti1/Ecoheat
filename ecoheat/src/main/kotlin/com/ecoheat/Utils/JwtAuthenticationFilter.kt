package com.ecoheat.Utils
import com.ecoheat.Service.Impl.UserServiceImpl
import io.github.cdimascio.dotenv.dotenv
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
import java.security.Key
import java.util.*

class JwtAuthenticationFilter(
    private val jwtToken: JwtToken,
    private val messageSource: MessageSource,
    private val userService: UserServiceImpl) : OncePerRequestFilter() {
    private val dotenv = dotenv()
    private val secretKey = dotenv["JWT_SECRET"]!!
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain
    ) {
        val token = extractTokenFromRequest(request)

        if (token != null) {
            try {
                val key = Keys.hmacShaKeyFor(secretKey.toByteArray())
                val claims = Jwts
                    .parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .body

                logger.info("Claims: $claims")
                val userIdString = claims?.id
                val userId = userIdString?.toLongOrNull()
                if (userId != null) {
                    val user = userService.getUserById(userId)

                    if (user != null) {
                        val authorities = user.role.name.let { listOf(SimpleGrantedAuthority(it)) } ?: emptyList()
                        val authentication: Authentication = UsernamePasswordAuthenticationToken(userId, null, authorities)
                        SecurityContextHolder.getContext().authentication = authentication
                    } else {
                        logger.error("UserID is null or not a valid Long. Value: $userIdString")
                    }

                } else {
                    logger.error("UserID is null. Type of claims.id: ${claims?.id?.javaClass}")
                }

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