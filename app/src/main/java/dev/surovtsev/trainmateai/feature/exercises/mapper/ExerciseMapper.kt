package dev.surovtsev.trainmateai.feature.exercises.mapper

import dev.surovtsev.trainmateai.core.ExerciseDto
import dev.surovtsev.trainmateai.core.MediaDto
import dev.surovtsev.trainmateai.feature.exercises.domain.UiExerciseEntity
import dev.surovtsev.trainmateai.feature.exercises.domain.UiExerciseMediaEntity

fun UiExerciseEntity.toDto() = ExerciseDto(id, name, description, category)
fun ExerciseDto.toEntity() = UiExerciseEntity(id, name, description, category)

fun UiExerciseMediaEntity.toDto() = MediaDto(id, exerciseId, kind, url, order)
fun MediaDto.toEntity() = UiExerciseMediaEntity(id, exerciseId, kind, url, order)
