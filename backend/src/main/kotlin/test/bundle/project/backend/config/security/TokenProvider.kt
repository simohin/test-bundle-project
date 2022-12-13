package test.bundle.project.backend.config.security

import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtParser
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.impl.DefaultJwtParserBuilder
import io.jsonwebtoken.security.Keys
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.stereotype.Service
import java.security.SignatureException
import java.util.Date
import java.util.logging.Logger

@Service
@EnableConfigurationProperties(AppProperties::class)
class TokenProvider(
    private val appProperties: AppProperties,
    private val objectMapper: ObjectMapper,
    private val oauth2AuthorizedClientService: OAuth2AuthorizedClientService
) {

    private val signingKey = Keys.hmacShaKeyFor(appProperties.auth.tokenSecret.toByteArray())
    private val jwtParser: JwtParser = DefaultJwtParserBuilder().setSigningKey(signingKey).build()

    companion object {
        val logger: Logger = Logger.getLogger(TokenProvider::class.simpleName)
    }

    fun createToken(authentication: Authentication): String? {
        val userPrincipal = authentication.principal as DefaultOAuth2User
        val now = Date()
        val expiryDate = Date(now.time + appProperties.auth.tokenExpirationMsec)
        val oauthToken: OAuth2AuthenticationToken = authentication as OAuth2AuthenticationToken
        val loadAuthorizedClient = oauth2AuthorizedClientService.loadAuthorizedClient<OAuth2AuthorizedClient>(
            oauthToken.authorizedClientRegistrationId,
            oauthToken.name
        )

        val jwtContent = userPrincipal.toJwtContent(loadAuthorizedClient.accessToken)
        val jwt = objectMapper.writeValueAsString(jwtContent)
        return Jwts.builder().setSubject(jwt).setIssuedAt(Date()).setExpiration(expiryDate)
            .signWith(signingKey).compact()
    }

    fun readToken(token: String): Authentication {
        val subject: String

        try {
            subject = jwtParser.parseClaimsJws(token).body.subject
        } catch (e: SignatureException) {
            logger.info("Invalid JWT signature")
            throw e
        } catch (e: MalformedJwtException) {
            logger.info("Invalid JWT token")
            throw e
        } catch (e: ExpiredJwtException) {
            logger.info("Expired JWT token")
            throw e
        } catch (e: UnsupportedJwtException) {
            logger.info("Unsupported JWT token")
            throw e
        } catch (e: IllegalArgumentException) {
            logger.info("JWT claims string is empty.")
            throw e
        }

        val jwtContent = objectMapper.readValue(subject, JwtContent::class.java)

        return jwtContent.toAuthentication()
    }
}
