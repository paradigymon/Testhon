package eu.kanade.tachiyomi.data.track.shikimorifi.dto

import eu.kanade.tachiyomi.data.database.models.Track
import eu.kanade.tachiyomi.data.track.shikimorifi.ShikiRipApi
import eu.kanade.tachiyomi.data.track.shikimorifi.toShikiRipTrackStatus
import kotlinx.serialization.Serializable

@Serializable
data class SRFUserListEntry(
    val id: Long,
    val chapters: Double,
    val score: Int,
    val status: String,
) {
    fun toTrack(trackId: Long, manga: SRFManga): Track {
        return Track.create(trackId).apply {
            title = manga.name
            remote_id = this@SRFUserListEntry.id
            total_chapters = manga.chapters
            library_id = this@SRFUserListEntry.id
            last_chapter_read = this@SRFUserListEntry.chapters
            score = this@SRFUserListEntry.score.toDouble()
            status = toShikiRipTrackStatus(this@SRFUserListEntry.status)
            tracking_url = ShikiRipApi.BASE_URL + manga.url
        }
    }
}
