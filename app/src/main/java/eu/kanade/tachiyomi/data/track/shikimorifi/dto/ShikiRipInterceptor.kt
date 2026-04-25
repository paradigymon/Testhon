package eu.kanade.tachiyomi.data.track.shikimorifi

import eu.kanade.tachiyomi.BuildConfig
import eu.kanade.tachiyomi.data.track.shikimorifi.dto.SRFOAuth
import eu.kanade.tachiyomi.data.track.shikimorifi.dto.isExpired
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.Response
import uy.kohesive.injekt.injectLazy

class ShikiRipInterceptor(private val shikiRip: ShikiRip) : Interceptor {

    private val json: Json by injectLazy()

    private var oauth: SRFOAuth? = shikiRip.restoreToken()

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val currAuth = oauth ?: throw Exception("Not authenticated with Shikimori.fi")

        val refreshToken = currAuth.refreshToken!!

        if (currAuth.isExpired()) {
            val response = chain.proceed(ShikiRipApi.refreshTokenRequest(refreshToken))
            if (response.isSuccessful) {
                newAuth(json.decodeFromString<SRFOAuth>(response.body.string()))
            } else {
                response.close()
            }
        }

        val authRequest = originalRequest.newBuilder()
            .addHeader("Authorization", "Bearer ${oauth!!.accessToken}")
            .header("User-Agent", "Mihon v${BuildConfig.VERSION_NAME} (${BuildConfig.APPLICATION_ID})")
            .build()

        return chain.proceed(authRequest)
    }

    fun newAuth(oauth: SRFOAuth?) {
        this.oauth = oauth
        shikiRip.saveToken(oauth)
    }
}
