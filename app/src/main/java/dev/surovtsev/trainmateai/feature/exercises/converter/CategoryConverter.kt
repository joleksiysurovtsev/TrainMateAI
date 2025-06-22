package dev.surovtsev.trainmateai.feature.exercises.converter

import androidx.room.TypeConverter
import dev.surovtsev.trainmateai.feature.exercises.domain.ExerciseCategory
import dev.surovtsev.trainmateai.feature.exercises.domain.MediaKind

class CategoryConverter {
    @TypeConverter fun fromEnum(e: ExerciseCategory) = e.name
    @TypeConverter fun toEnum(s: String) = ExerciseCategory.valueOf(s)
}

class MediaKindConverter {
    @TypeConverter fun fromEnum(e: MediaKind) = e.name
    @TypeConverter fun toEnum(s: String) = MediaKind.valueOf(s)
}