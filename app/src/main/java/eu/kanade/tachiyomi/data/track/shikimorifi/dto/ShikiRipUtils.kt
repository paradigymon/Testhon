package eu.kanade.tachiyomi.data.track.shikimorifi

import eu.kanade.tachiyomi.data.database.models.Track

fun Track.toShikiRipStatus() = when (status) {
    ShikiRip.READING -> "watching"
    ShikiRip.COMPLETED -> "completed"
    ShikiRip.ON_HOLD -> "on_hold"
    ShikiRip.DROPPED -> "dropped"
    ShikiRip.PLAN_TO_READ -> "planned"
    ShikiRip.REREADING -> "rewatching"
    else -> throw NotImplementedError("Unknown status: $status")
}

fun toShikiRipTrackStatus(status: String) = when (status) {
    "watching" -> ShikiRip.READING
    "completed" -> ShikiRip.COMPLETED
    "on_hold" -> ShikiRip.ON_HOLD
    "dropped" -> ShikiRip.DROPPED
    "planned" -> ShikiRip.PLAN_TO_READ
    "rewatching" -> ShikiRip.REREADING
    else -> throw NotImplementedError("Unknown status: $status")
}
