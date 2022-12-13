package test.bundle.project.backend.service.security

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class RestAuthenticationEntryPoint : AuthenticationEntryPoint {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(RestAuthenticationEntryPoint::class.simpleName)
    }

    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        logger.error("Responding with unauthorized error. Message - {}", authException.message)
        response.sendError(
            HttpServletResponse.SC_UNAUTHORIZED,
            authException.localizedMessage
        )
    }
}
