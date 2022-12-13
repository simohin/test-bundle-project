package test.bundle.project.backend.service.security.model

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "app")
data class AppProperties(
    val auth: Auth,
    val oauth2: OAuth2
)
