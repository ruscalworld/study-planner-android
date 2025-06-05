package ru.ruscalworld.studyplanner.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import ru.ruscalworld.studyplanner.provisioning.backend.CredentialsSupplier
import ru.ruscalworld.studyplanner.provisioning.backend.dto.auth.Credentials

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
val BEARER_TOKEN = stringPreferencesKey("bearer_token")
val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
val ACTIVE_CURRICULUM = longPreferencesKey("active_curriculum")

class DataStoreSettingsProvider(private val context: Context) : CredentialsSupplier, ActiveCurriculumStore {
    override suspend fun loadCredentials(): Credentials? {
        val accessFlow = context.dataStore.data.map { preferences -> preferences[BEARER_TOKEN] }
        val refreshFlow = context.dataStore.data.map { preferences -> preferences[REFRESH_TOKEN] }

        val accessToken = accessFlow.firstOrNull()?.let { if (it.isNotEmpty()) it else null }
        val refreshToken = refreshFlow.firstOrNull()?.let { if (it.isNotEmpty()) it else null }

        if (accessToken == null || refreshToken == null)
            return null

        return Credentials(accessToken, refreshToken, "Bearer")
    }

    override suspend fun storeCredentials(credentials: Credentials) {
        context.dataStore.edit { preferences ->
            preferences[BEARER_TOKEN] = credentials.accessToken
            preferences[REFRESH_TOKEN] = credentials.refreshToken
        }
    }

    override suspend fun clearCredentials() {
        context.dataStore.edit { preferences ->
            preferences.remove(BEARER_TOKEN)
            preferences.remove(REFRESH_TOKEN)
        }
    }

    override suspend fun loadActiveCurriculum(): Long? {
        val flow = context.dataStore.data.map { preferences -> preferences[ACTIVE_CURRICULUM] }
        return flow.firstOrNull()
    }

    override suspend fun storeActiveCurriculum(id: Long?) {
        context.dataStore.edit { preferences ->
            if (id == null) {
                preferences.remove(ACTIVE_CURRICULUM)
            } else {
                preferences[ACTIVE_CURRICULUM] = id
            }
        }
    }
}
