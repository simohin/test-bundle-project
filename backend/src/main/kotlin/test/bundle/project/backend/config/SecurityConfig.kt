package test.bundle.project.backend.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import test.bundle.project.backend.service.security.HttpCookieOAuth2AuthorizationRequestRepository
import test.bundle.project.backend.service.security.RestAuthenticationEntryPoint
import test.bundle.project.backend.service.security.filter.RestOAuth2AuthorizationFilter
import test.bundle.project.backend.service.security.handler.OAuth2AuthenticationFailureHandler
import test.bundle.project.backend.service.security.handler.OAuth2AuthenticationSuccessHandler
import test.bundle.project.backend.service.security.model.AppProperties

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val restAuthenticationEntryPoint: RestAuthenticationEntryPoint,
    private val cookieAuthorizationRequestRepository: HttpCookieOAuth2AuthorizationRequestRepository,
    private val oAuth2AuthenticationSuccessHandler: OAuth2AuthenticationSuccessHandler,
    private val oAuth2AuthenticationFailureHandler: OAuth2AuthenticationFailureHandler,
    private val restOAuth2AuthorizationFilter: RestOAuth2AuthorizationFilter,
) {

    @Bean
    fun filterChain(
        appProperties: AppProperties,
        http: HttpSecurity
    ): SecurityFilterChain {

        http
            .authorizeHttpRequests {
                it.requestMatchers("/login/**")
                    .permitAll()
                it.anyRequest()
                    .authenticated()
            }
            .addFilterBefore(restOAuth2AuthorizationFilter, BasicAuthenticationFilter::class.java)
            .cors()
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .csrf().disable()
            .formLogin().disable()
            .httpBasic().disable()
            .exceptionHandling()
            .authenticationEntryPoint(restAuthenticationEntryPoint)
            .and()
            .oauth2Login()
            .defaultSuccessUrl(appProperties.oauth2.defaultSuccessUrl)
            .authorizationEndpoint()
            .baseUri("/login/oauth2/authorization")
            .authorizationRequestRepository(cookieAuthorizationRequestRepository)
            .and()
            .redirectionEndpoint()
            .baseUri("/login/oauth2/code/*")
            .and()
            .successHandler(oAuth2AuthenticationSuccessHandler)
            .failureHandler(oAuth2AuthenticationFailureHandler)
        return http.build()
    }
}
