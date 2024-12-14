package ru.ruscalworld.studyplanner.screens.welcome.start

import android.content.Context
import android.util.Base64
import android.util.Log
import androidx.compose.material3.Snackbar
import androidx.compose.ui.res.stringResource
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.ruscalworld.studyplanner.R
import java.security.SecureRandom
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(@ApplicationContext val appContext: Context): ViewModel() {
    companion object {
        private const val TAG = "StartViewModel"
        private const val WEB_CLIENT_ID = "458392076697-scp9osmjk1rsnhqe1e0275e9d1pgn1fh.apps.googleusercontent.com"
    }

    private val credentialManager: CredentialManager = CredentialManager.create(appContext)
    val uiState = MutableStateFlow(StartState())

    private fun generateNonce(): String {
        val rand = SecureRandom.getInstance("SHA1PRNG")
        val nonce = ByteArray(128/8)
        rand.nextBytes(nonce)

        return Base64.encodeToString(nonce, Base64.DEFAULT)
    }

    fun signIn() {
        uiState.update { it.copy(successfulAuth = false, errorMessage = null, isLoading = true) }

        val googleIdOption: GetSignInWithGoogleOption = GetSignInWithGoogleOption.Builder(WEB_CLIENT_ID)
//            .setServerClientId()
//            .setNonce(this.generateNonce())
            .build()

        val request: GetCredentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
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

    private fun onGoogleAuthenticated(credentialResponse: GetCredentialResponse) {
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

                    Log.d(TAG, "Received google ID token: " + googleIdTokenCredential.idToken) // TODO
                    uiState.update { it.copy(isLoading = false, errorMessage = null, successfulAuth = true) }
                } catch (e: GoogleIdTokenParsingException) {
                    Log.e(TAG, "Received an invalid google id token response", e)
                    uiState.update { it.copy(isLoading = false, errorMessage = R.string.auth_error_invalid_credential) }
                }
            }

            else -> {
                Log.e(TAG, "Invalid credential class faced: " + credentialResponse.credential)
                uiState.update { it.copy(isLoading = false, errorMessage = R.string.auth_error_invalid_credential) }
            }
        }
    }
}
