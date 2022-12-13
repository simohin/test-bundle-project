package test.bundle.project.backend.service.security.handler

import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder
import test.bundle.project.backend.service.security.HttpCookieOAuth2AuthorizationRequestRepository
import test.bundle.project.backend.service.security.HttpCookieOAuth2AuthorizationRequestRepository.Companion.COOKIES_LIFETIME_SECONDS
import test.bundle.project.backend.service.security.HttpCookieOAuth2AuthorizationRequestRepository.Companion.OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME
import test.bundle.project.backend.service.security.HttpCookieOAuth2AuthorizationRequestRepository.Companion.REDIRECT_URI_PARAM_COOKIE_NAME
import test.bundle.project.backend.service.security.TokenProvider
import test.bundle.project.backend.service.security.model.AppProperties
import test.bundle.project.backend.util.CookieUtils
import java.net.URI
import java.util.Optional

@Component
@EnableConfigurationProperties(AppProperties::class)
class OAuth2AuthenticationSuccessHandler(
    private val tokenProvider: TokenProvider,
    private val appProperties: AppProperties,
    private val httpCookieOAuth2AuthorizationRequestRepository: HttpCookieOAuth2AuthorizationRequestRepository
) : SimpleUrlAuthenticationSuccessHandler() {

    override fun onAuthenticationSuccess(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authentication: Authentication?
    ) {
        val targetUrl = determineTargetUrl(request, response, authentication)

        if (response!!.isCommitted) {
            logger.debug("Response has already been committed. Unable to redirect to $targetUrl")
            return
        }

        CookieUtils.addCookie(
            response,
            OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME,
            tokenProvider.createToken(authentication!!),
            COOKIES_LIFETIME_SECONDS
        )
        redirectStrategy.sendRedirect(request, response, targetUrl)
    }

    override fun determineTargetUrl(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authentication: Authentication?
    ): String? {
        val redirectUri: Optional<String> = CookieUtils.getCookie(request!!, REDIRECT_URI_PARAM_COOKIE_NAME)
            .map(Cookie::getValue)
        if (redirectUri.isPresent && !isAuthorizedRedirectUri(redirectUri.get())) {
            throw RuntimeException("Sorry! We've got an Unauthorized Redirect URI and can't proceed with the authentication")
        }
        val targetUrl = redirectUri.orElse(appProperties.oauth2.defaultSuccessUrl)
        return UriComponentsBuilder.fromUriString(targetUrl).build().toUriString()
    }

    protected fun clearAuthenticationAttributes(request: HttpServletRequest?, response: HttpServletResponse?) {
        super.clearAuthenticationAttributes(request)
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response)
    }

    private fun isAuthorizedRedirectUri(uri: String): Boolean {
        val clientRedirectUri = URI.create(uri)
        return appProperties.oauth2.authorizedRedirectUris
            .stream()
            .anyMatch { authorizedRedirectUri ->
                // Only validate host and port. Let the clients use different paths if they want to
                val authorizedURI = URI.create(authorizedRedirectUri)
                if (authorizedURI.host.equals(clientRedirectUri.host, ignoreCase = true)
                    && authorizedURI.port == clientRedirectUri.port
                ) {
                    return@anyMatch true
                }
                false
            }
    }
}
