package test.bundle.project.backend.service.security.model

import org.springframework.security.oauth2.core.OAuth2AccessToken
import java.time.Instant

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
