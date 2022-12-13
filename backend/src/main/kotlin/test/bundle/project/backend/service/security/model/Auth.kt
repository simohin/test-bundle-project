package test.bundle.project.backend.service.security.model

data class Auth(
    val tokenSecret: String,
    val tokenExpirationMsec: Long
)
