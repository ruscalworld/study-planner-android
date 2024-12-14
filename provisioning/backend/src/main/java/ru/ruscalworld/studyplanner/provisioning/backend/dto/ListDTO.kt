package ru.ruscalworld.studyplanner.provisioning.backend.dto

data class ListDTO<I, T: DTO<I>>(
    val list: List<T>,
): DTO<List<I>> {
    override fun toInternalObject(): List<I> {
        return list.map { it.toInternalObject() }
    }
}
