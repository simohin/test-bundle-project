package test.bundle.project.backend.util

import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.util.SerializationUtils
import java.util.Arrays
import java.util.Base64
import java.util.Optional

object CookieUtils {
    fun getCookie(request: HttpServletRequest, name: String?): Optional<Cookie> = Arrays.stream(request.cookies)
        .filter { it.name.equals(name) }
        .findFirst()

    fun addCookie(
        response: HttpServletResponse,
        name: String?,
        value: String?,
        maxAge: Int,
        httpOnly: Boolean? = false
    ) {
        val cookie = Cookie(name, value)
        cookie.path = "/"
        cookie.isHttpOnly = httpOnly!!
        cookie.maxAge = maxAge
        response.addCookie(cookie)
    }

    fun deleteCookie(request: HttpServletRequest, response: HttpServletResponse, name: String?) {
        val cookies: Array<Cookie> = request.cookies
        if (cookies.isNotEmpty()) {
            for (cookie in cookies) {
                if (cookie.name.equals(name)) {
                    cookie.value = ""
                    cookie.path = "/"
                    cookie.maxAge = 0
                    response.addCookie(cookie)
                }
            }
        }
    }

    fun serialize(value: Any?): String? {
        return Base64.getUrlEncoder()
            .encodeToString(SerializationUtils.serialize(value))
    }

    fun <T> deserialize(cookie: Cookie, cls: Class<T>): T {
        return cls.cast(
            SerializationUtils.deserialize(
                Base64.getUrlDecoder().decode(cookie.value)
            )
        )
    }
}
