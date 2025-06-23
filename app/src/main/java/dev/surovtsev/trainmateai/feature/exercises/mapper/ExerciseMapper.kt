package dev.surovtsev.trainmateai.feature.exercises.mapper

import dev.surovtsev.trainmateai.core.ExerciseDto
import dev.surovtsev.trainmateai.core.MediaDto
import dev.surovtsev.trainmateai.feature.exercises.domain.Exercise
import dev.surovtsev.trainmateai.feature.exercises.domain.UiExerciseMediaEntity

fun Exercise.toDto() = ExerciseDto(id, name, description, category)
fun ExerciseDto.toEntity() = Exercise(id, name, description, category)

fun UiExerciseMediaEntity.toDto() = MediaDto(id, exerciseId, kind, url, order)
fun MediaDto.toEntity() = UiExerciseMediaEntity(id, exerciseId, kind, url, order)
