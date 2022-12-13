package test.bundle.project.backend.config.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.UnsupportedJwtException
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.stereotype.Service
import java.security.SignatureException
import java.util.Date
import java.util.logging.Logger

@Service
@EnableConfigurationProperties(AppProperties::class)
class TokenProvider(
    private val appProperties: AppProperties
) {

    companion object {
        val logger: Logger = Logger.getLogger(TokenProvider::class.simpleName)
    }

    fun createToken(authentication: Authentication): String? {
        val userPrincipal = authentication.principal as DefaultOAuth2User
        val now = Date()
        val expiryDate = Date(now.time + appProperties.auth.tokenExpirationMsec)
        return Jwts.builder()
            .setSubject(userPrincipal.attributes["id"].toString())
            .setIssuedAt(Date())
            .setExpiration(expiryDate)
            .signWith(SignatureAlgorithm.HS512, appProperties.auth.tokenSecret)
            .compact()
    }

    fun getUserIdFromToken(token: String?): Long? {
        val claims: Claims = Jwts.parser()
            .setSigningKey(appProperties.auth.tokenSecret)
            .parseClaimsJws(token)
            .body
        return claims.subject.toLong()
    }

    fun validateToken(authToken: String?): Boolean {
        try {
            Jwts.parser().setSigningKey(appProperties.auth.tokenSecret).parseClaimsJws(authToken)
            return true
        } catch (ex: SignatureException) {
            logger.info("Invalid JWT signature")
        } catch (ex: MalformedJwtException) {
            logger.info("Invalid JWT token")
        } catch (ex: ExpiredJwtException) {
            logger.info("Expired JWT token")
        } catch (ex: UnsupportedJwtException) {
            logger.info("Unsupported JWT token")
        } catch (ex: IllegalArgumentException) {
            logger.info("JWT claims string is empty.")
        }
        return false
    }
}
