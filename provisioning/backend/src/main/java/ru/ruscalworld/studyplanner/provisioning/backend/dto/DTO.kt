package ru.ruscalworld.studyplanner.provisioning.backend.dto

interface DTO<T> {
    fun toInternalObject(): T
}
