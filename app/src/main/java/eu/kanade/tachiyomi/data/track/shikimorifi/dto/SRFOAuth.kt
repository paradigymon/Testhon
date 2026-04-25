package eu.kanade.tachiyomi.data.track.shikimorifi.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SRFOAuth(
    @SerialName("access_token")
    val accessToken: String,
    @SerialName("token_type")
    val tokenType: String,
    @SerialName("created_at")
    val createdAt: Long,
    @SerialName("expires_in")
    val expiresIn: Long,
    @SerialName("refresh_token")
    val refreshToken: String?,
)

fun SRFOAuth.isExpired() = (System.currentTimeMillis() / 1000) > (createdAt + expiresIn - 3600)
