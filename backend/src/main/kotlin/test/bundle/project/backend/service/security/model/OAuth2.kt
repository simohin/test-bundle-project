package test.bundle.project.backend.service.security.model

data class OAuth2(
    val defaultSuccessUrl: String,
    val authorizedRedirectUris: List<String>
)
