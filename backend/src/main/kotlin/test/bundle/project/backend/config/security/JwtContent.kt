package test.bundle.project.backend.config.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.core.OAuth2AccessToken
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication
import java.time.Instant

data class JwtContent(
    val principal: Principal? = null,
    val accessToken: AccessToken
)

data class Principal(
    val userId: String? = null,
    val login: String? = null,
    val name: String? = null,
    val authorities: List<String>? = emptyList(),
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

data class AccessToken(
    val tokenType: String? = null,
    val tokenValue: String? = null,
    val scopes: Set<String>? = emptySet(),
    val issuedAt: Instant? = null,
    val expiredAt: Instant? = null,
)

fun AccessToken.toOAuth2AccessToken() = OAuth2AccessToken(
    OAuth2AccessToken.TokenType.BEARER,
    this.tokenValue,
    this.issuedAt,
    this.expiredAt,
    this.scopes
)

fun OAuth2AccessToken.toJwtContentAccessToken() = AccessToken(
    this.tokenType.value,
    this.tokenValue,
    this.scopes,
    this.issuedAt,
    this.expiresAt
)

fun Principal.toOAuth2User() = DefaultOAuth2User(
    this.authorities?.map { GrantedAuthority { it } } ?: emptyList(),
    mapOf(
        "id" to this.userId,
        "login" to this.login,
        "name" to this.name
    ),
    "id"
)

fun DefaultOAuth2User.toJwtContent(accessToken: OAuth2AccessToken) = JwtContent(
    Principal(
        this.attributes["id"].toString(),
        this.attributes["login"].toString(),
        this.attributes["name"].toString(),
        this.authorities.map { it.authority }
    ),
    accessToken.toJwtContentAccessToken()
)
