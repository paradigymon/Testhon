package eu.kanade.tachiyomi.data.track.shikimorifi.dto

import eu.kanade.tachiyomi.data.track.model.TrackSearch
import eu.kanade.tachiyomi.data.track.shikimorifi.ShikiRipApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SRFManga(
    val id: Long,
    val name: String,
    val chapters: Long,
    val image: SRFMangaCover,
    val score: Double,
    val url: String,
    val status: String,
    val kind: String,
    @SerialName("aired_on")
    val airedOn: String?,
) {
    fun toTrack(trackId: Long): TrackSearch {
        return TrackSearch.create(trackId).apply {
            remote_id = this@SRFManga.id
            title = name
            total_chapters = chapters
            cover_url = ShikiRipApi.BASE_URL + image.preview
            summary = ""
            score = this@SRFManga.score
            tracking_url = ShikiRipApi.BASE_URL + url
            publishing_status = this@SRFManga.status
            publishing_type = kind
            start_date = airedOn ?: ""
        }
    }
}

@Serializable
data class SRFMangaCover(
    val preview: String,
)
