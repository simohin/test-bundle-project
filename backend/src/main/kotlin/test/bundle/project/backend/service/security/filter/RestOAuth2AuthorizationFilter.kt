package test.bundle.project.backend.service.security.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.springframework.web.filter.GenericFilterBean
import test.bundle.project.backend.service.security.TokenProvider
import java.util.logging.Logger

@Component
class RestOAuth2AuthorizationFilter(
    private val tokenProvider: TokenProvider
) : GenericFilterBean() {

    companion object {
        const val AUTH_HEADER = "Authorization"
        const val AUTH_SCHEME = "Bearer"

        val log: Logger = Logger.getLogger(RestOAuth2AuthorizationFilter::class.simpleName)
    }

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val token = extractToken(request)

        if (token != null) {
            val authentication = tokenProvider.readToken(token)
            SecurityContextHolder.getContext().authentication = authentication
        }
        chain.doFilter(request, response)
    }

    private fun extractToken(request: ServletRequest): String? {
        val bearerToken = (request as HttpServletRequest).getHeader(AUTH_HEADER)
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("$AUTH_SCHEME ")) {
            return bearerToken.substring(AUTH_SCHEME.length + 1)
        }
        return null
    }
}
