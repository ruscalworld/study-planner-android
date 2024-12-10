package ru.ruscalworld.studyplanner.navigation

import ru.ruscalworld.studyplanner.core.model.Curriculum
import ru.ruscalworld.studyplanner.core.model.Discipline
import ru.ruscalworld.studyplanner.core.model.Institution

data class EntityRouteKey<I>(val entityPathName: String, val entityId: I) {
    companion object {
        fun from(entity: Institution) = EntityRouteKey("institutions", entity.id)
        fun from(entity: Curriculum) = EntityRouteKey("curriculums", entity.id)
        fun from(entity: Discipline) = EntityRouteKey("disciplines", entity.id)
    }
}
