package ru.ruscalworld.studyplanner

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.ruscalworld.studyplanner.core.auth.AuthenticationManager
import ru.ruscalworld.studyplanner.core.repository.CurriculumRepository
import ru.ruscalworld.studyplanner.core.repository.DisciplineLinkRepository
import ru.ruscalworld.studyplanner.core.repository.DisciplineRepository
import ru.ruscalworld.studyplanner.core.repository.TaskGroupRepository
import ru.ruscalworld.studyplanner.core.repository.TaskLinkRepository
import ru.ruscalworld.studyplanner.core.repository.TaskRepository
import ru.ruscalworld.studyplanner.provisioning.backend.CredentialsSupplier
import ru.ruscalworld.studyplanner.provisioning.backend.PlannerClient
import ru.ruscalworld.studyplanner.provisioning.backend.repository.PlannerCurriculumRepository
import ru.ruscalworld.studyplanner.provisioning.backend.repository.PlannerDisciplineLinkRepository
import ru.ruscalworld.studyplanner.provisioning.backend.repository.PlannerDisciplineRepository
import ru.ruscalworld.studyplanner.provisioning.backend.repository.PlannerTaskGroupRepository
import ru.ruscalworld.studyplanner.provisioning.backend.repository.PlannerTaskLinkRepository
import ru.ruscalworld.studyplanner.provisioning.backend.repository.PlannerTaskRepository
import ru.ruscalworld.studyplanner.settings.ActiveCurriculumStore
import ru.ruscalworld.studyplanner.settings.DataStoreSettingsProvider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun getDataStoreSettingsProvider(@ApplicationContext context: Context) = DataStoreSettingsProvider(context = context)

    @Provides
    @Singleton
    fun getCredentialsSupplier(settingsProvider: DataStoreSettingsProvider): CredentialsSupplier = settingsProvider

    @Provides
    @Singleton
    fun getActiveCurriculumStore(settingsProvider: DataStoreSettingsProvider): ActiveCurriculumStore = settingsProvider

    @Provides
    @Singleton
    fun getClient(credentialsSupplier: CredentialsSupplier): PlannerClient = PlannerClient(credentialsSupplier)

    @Provides
    @Singleton
    fun getAuthenticationManager(client: PlannerClient): AuthenticationManager = client

    @Provides
    @Singleton
    fun getCurriculumRepository(client: PlannerClient): CurriculumRepository = PlannerCurriculumRepository(client)

    @Provides
    @Singleton
    fun getDisciplineRepository(client: PlannerClient): DisciplineRepository = PlannerDisciplineRepository(client)

    @Provides
    @Singleton
    fun getDisciplineLinkRepository(client: PlannerClient): DisciplineLinkRepository = PlannerDisciplineLinkRepository(client)

    @Provides
    @Singleton
    fun getTaskRepository(client: PlannerClient): TaskRepository = PlannerTaskRepository(client)

    @Provides
    @Singleton
    fun getTaskGroupRepository(client: PlannerClient): TaskGroupRepository = PlannerTaskGroupRepository(client)

    @Provides
    @Singleton
    fun getTaskLinkRepository(client: PlannerClient): TaskLinkRepository = PlannerTaskLinkRepository(client)
}
