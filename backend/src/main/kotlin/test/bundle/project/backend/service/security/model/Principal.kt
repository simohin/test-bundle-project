package test.bundle.project.backend.service.security.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.core.user.DefaultOAuth2User

data class Principal(
    val userId: String? = null,
    val login: String? = null,
    val name: String? = null,
    val authorities: List<String>? = emptyList(),
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
