package com.ecoheat.Utils

import com.ecoheat.Exception.CustomAccessDeniedHandler
import com.ecoheat.Service.Impl.UserServiceImpl
import io.github.cdimascio.dotenv.dotenv
import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.util.*

@Configuration
@EnableWebSecurity
class SecurityConfig{
    val locale = Locale("pt")
    val dotenv = dotenv()
    val routeA = dotenv["ROUTE_A"]!!
    val routeB = dotenv["ROUTE_B"]!!
    val routeC = dotenv["ROUTE_C"]!!
    val routeD = dotenv["ROUTE_D"]!!
    val routeE = dotenv["ROUTE_E"]!!
    @Bean
    fun encoder(): PasswordEncoder? {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun accessDeniedHandler(messageSource: MessageSource): AccessDeniedHandler {
        return CustomAccessDeniedHandler(messageSource)
    }

    @Bean
    fun filterChain(http: HttpSecurity, messageSource: MessageSource, userService: UserServiceImpl): SecurityFilterChain {
        http {
            authorizeRequests {
                authorize(routeA, hasRole("ADMIN"))
                authorize(routeB, permitAll)
                authorize(routeC, permitAll)
                authorize(routeD, hasRole("USER"))
                authorize(routeE, hasRole("USER"))
            }
            cors {  }
            headers { frameOptions { disable() } }
            csrf { disable() }
            sessionManagement {SessionCreationPolicy.STATELESS}
            authorizeRequests {  }
            addFilterBefore<UsernamePasswordAuthenticationFilter>(JwtAuthenticationFilter(JwtToken(messageSource), messageSource,userService))
            formLogin {disable()}
            httpBasic {}
            exceptionHandling {
                accessDeniedHandler = accessDeniedHandler(messageSource)
            }
        }

        return http.build()
    }
}