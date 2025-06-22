package dev.surovtsev.trainmateai.core

import dev.surovtsev.trainmateai.feature.exercises.domain.MediaKind
import kotlinx.serialization.Serializable

@Serializable
data class MediaDto(
    val id: String,
    val exerciseId: String,
    val kind: MediaKind,
    val url: String,
    val order: Int = 0
)