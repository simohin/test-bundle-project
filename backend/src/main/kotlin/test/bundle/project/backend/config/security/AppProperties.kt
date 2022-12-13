package test.bundle.project.backend.config.security

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "app")
data class AppProperties(
    val auth: Auth,
    val oauth2: OAuth2
)

data class Auth(
    val tokenSecret: String,
    val tokenExpirationMsec: Long
)

data class OAuth2(
    val defaultSuccessUrl: String,
    val authorizedRedirectUris: List<String>
)
