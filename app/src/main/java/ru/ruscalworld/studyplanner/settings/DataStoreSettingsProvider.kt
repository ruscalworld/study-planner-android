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
val ACTIVE_CURRICULUM = longPreferencesKey("active_curriculum")

class DataStoreSettingsProvider(private val context: Context) : CredentialsSupplier, ActiveCurriculumStore {
    override suspend fun loadCredentials(): Credentials? {
        val flow = context.dataStore.data.map { preferences -> preferences[BEARER_TOKEN] }

        return flow.firstOrNull()?.let {
            Credentials(it, "Bearer")
        }
    }

    override suspend fun storeCredentials(credentials: Credentials) {
        context.dataStore.edit { preferences ->
            preferences[BEARER_TOKEN] = credentials.accessToken
        }
    }

    override suspend fun clearCredentials() {
        context.dataStore.edit { preferences ->
            preferences.remove(BEARER_TOKEN)
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
