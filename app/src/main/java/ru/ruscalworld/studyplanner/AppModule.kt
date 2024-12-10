package ru.ruscalworld.studyplanner

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
//    @Provides
//    @Singleton
//    fun getInstitutionRepository() = ... // TODO https://www.geeksforgeeks.org/dagger-hilt-in-android-with-example/
}