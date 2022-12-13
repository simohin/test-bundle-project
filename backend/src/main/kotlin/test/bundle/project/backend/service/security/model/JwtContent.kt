package test.bundle.project.backend.service.security.model

import org.springframework.security.oauth2.core.OAuth2AccessToken
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication

data class JwtContent(
    val principal: Principal? = null,
    val accessToken: AccessToken
)

fun JwtContent.toAuthentication(): BearerTokenAuthentication {
    val principal = this.principal!!.toOAuth2User()
    val accessToken = this.accessToken.toOAuth2AccessToken()
    return BearerTokenAuthentication(
        principal,
        accessToken,
        principal.authorities
    )
}

fun OAuth2User.toJwtContent(accessToken: OAuth2AccessToken) = JwtContent(
    Principal(
        this.attributes["id"].toString(),
        this.attributes["login"].toString(),
        this.attributes["name"].toString(),
        this.authorities.map { it.authority }
    ),
    accessToken.toJwtContentAccessToken()
)
