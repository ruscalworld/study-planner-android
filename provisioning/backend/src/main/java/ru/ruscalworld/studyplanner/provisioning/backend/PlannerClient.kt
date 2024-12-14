package ru.ruscalworld.studyplanner.provisioning.backend

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.request
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import ru.ruscalworld.studyplanner.core.auth.AuthenticationManager
import ru.ruscalworld.studyplanner.provisioning.backend.dto.auth.Credentials
import ru.ruscalworld.studyplanner.provisioning.backend.dto.auth.SignInParams

class PlannerClient(private val credentialsSupplier: CredentialsSupplier) : AuthenticationManager {
    companion object {
        const val API_HOST = "api.diary.ruscal.world"
        const val TAG = "PlannerClient"
    }

    private var tokens: BearerTokens? = null

    val httpClient: HttpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }

        install(Auth) {
            bearer {
                loadTokens {
                    val credentials: Credentials? = credentialsSupplier.loadCredentials()

                    if (credentials != null) {
                        Log.i(TAG, "Loaded credentials")
                        BearerTokens(credentials.accessToken, credentials.accessToken)
                    } else {
                        Log.i(TAG, "No stored credentials found")
                        null
                    }
                }

                refreshTokens {
                    if (response.request.url.encodedPath.contains("auth/"))
                        return@refreshTokens null

                    Log.d(TAG, "Refreshing an access token")
                    val response: Credentials = client.post("auth/refresh").body()
                    credentialsSupplier.storeCredentials(response)

                    Log.i(TAG, "Access token refresh completed")
                    BearerTokens(response.accessToken, response.accessToken)
                }

                sendWithoutRequest { request -> request.url.host == API_HOST }
            }
        }

        defaultRequest {
            url("https://$API_HOST/v1/")
        }
    }

    override suspend fun authenticate(token: String) {
        val response: Credentials = httpClient.post("auth/sign-in") {
            contentType(ContentType.Application.Json)
            setBody(SignInParams(token))
        }.body()

        tokens = BearerTokens(response.accessToken, response.accessToken)
        credentialsSupplier.storeCredentials(response)
    }

    override fun isAuthenticated(): Boolean {
        return tokens == null
    }
}
