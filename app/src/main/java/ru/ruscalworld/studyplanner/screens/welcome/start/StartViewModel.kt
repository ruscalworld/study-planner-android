package ru.ruscalworld.studyplanner.screens.welcome.start

import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.ruscalworld.studyplanner.R
import ru.ruscalworld.studyplanner.core.auth.AuthenticationManager
import ru.ruscalworld.studyplanner.provisioning.backend.CredentialsSupplier
import ru.ruscalworld.studyplanner.settings.ActiveCurriculumStore
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    @ApplicationContext val appContext: Context,
    private val credentialsSupplier: CredentialsSupplier,
    private val authenticationManager: AuthenticationManager,
    private val activeCurriculumStore: ActiveCurriculumStore,
): ViewModel() {
    companion object {
        private const val TAG = "StartViewModel"
        private const val WEB_CLIENT_ID = "458392076697-scp9osmjk1rsnhqe1e0275e9d1pgn1fh.apps.googleusercontent.com"
    }

    private val credentialManager: CredentialManager = CredentialManager.create(appContext)
    val uiState = MutableStateFlow(StartState())

    fun load() {
        uiState.value = StartState(isInitialLoading = true)

        viewModelScope.launch {
            try {
                val hasActiveCurriculum: Boolean = activeCurriculumStore.loadActiveCurriculum() != null
                val isAuthenticated: Boolean = credentialsSupplier.loadCredentials() != null

                Log.d(TAG, "Completed steps check completed (hasActiveCurriculum: $hasActiveCurriculum, isAuthenticated: $isAuthenticated)")

                uiState.update { it.copy(
                    isInitialLoading = isAuthenticated, // Smoother redirect
                    isCurriculumPicked = hasActiveCurriculum,
                    successfulAuth = isAuthenticated,
                ) }

                return@launch
            } catch (e: Exception) {
                Log.e(TAG, "Error checking completed steps", e)
                uiState.update { it.copy(isInitialLoading = false) }
            }
        }
    }

    fun signIn() {
        uiState.update { it.copy(successfulAuth = false, errorMessage = null, isLoading = true) }

        val request: GetCredentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(GetSignInWithGoogleOption.Builder(WEB_CLIENT_ID).build())
            .build()

        viewModelScope.launch {
            try {
                val result = credentialManager.getCredential(
                    request = request,
                    context = appContext,
                )

                onGoogleAuthenticated(result)
            } catch (e: NoCredentialException) {
                Log.e(TAG, "No credential available", e)
                uiState.update { it.copy(isLoading = false, errorMessage = R.string.auth_error_no_credential) }
            } catch (e: GetCredentialException) {
                Log.e(TAG, "Credential fetch failed", e)
                uiState.update { it.copy(isLoading = false, errorMessage = R.string.auth_error_failed) }
            }
        }
    }

    private suspend fun onGoogleAuthenticated(credentialResponse: GetCredentialResponse) {
        when (credentialResponse.credential) {
            is CustomCredential -> {
                if (credentialResponse.credential.type != GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    Log.e(TAG, "Invalid credential type faced: " + credentialResponse.credential.type)
                    uiState.update { it.copy(isLoading = false, errorMessage = R.string.auth_error_invalid_credential) }
                    return
                }

                try {
                    val googleIdTokenCredential = GoogleIdTokenCredential
                        .createFrom(credentialResponse.credential.data)

                    Log.d(TAG, "Received Google ID token")
                    onTokenObtained(googleIdTokenCredential.idToken)
                    uiState.update { it.copy(isLoading = false, errorMessage = null, successfulAuth = true) }
                } catch (e: GoogleIdTokenParsingException) {
                    Log.e(TAG, "Received an invalid Google ID token response", e)
                    uiState.update { it.copy(isLoading = false, errorMessage = R.string.auth_error_invalid_credential) }
                } catch (e: Exception) {
                    Log.e(TAG, "Unable to authenticate", e)
                    uiState.update { it.copy(isLoading = false, errorMessage = R.string.auth_error_unknown) }
                }
            }

            else -> {
                Log.e(TAG, "Invalid credential class faced: " + credentialResponse.credential)
                uiState.update { it.copy(isLoading = false, errorMessage = R.string.auth_error_invalid_credential) }
            }
        }
    }

    private suspend fun onTokenObtained(token: String) {
        authenticationManager.authenticate(token)
        Log.i(TAG, "Successfully authenticated")
    }
}
