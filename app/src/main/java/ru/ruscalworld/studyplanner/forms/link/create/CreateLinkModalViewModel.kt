package ru.ruscalworld.studyplanner.forms.link.create

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.ruscalworld.studyplanner.R
import ru.ruscalworld.studyplanner.core.model.EntityLink
import ru.ruscalworld.studyplanner.ui.exceptions.VisibleException

open class CreateLinkModalViewModel<L : EntityLink> : ViewModel() {
    companion object {
        const val TAG = "CreateLinkModalViewModel"
    }

    val uiState = MutableStateFlow(CreateLinkState<L>())

    fun createLink(request: CreateLinkRequest, factory: suspend (CreateLinkRequest) -> L) {
        uiState.update { it.copy(isLoading = true, error = null) }

        viewModelScope.launch {
            try {
                request.name.ifBlank { throw VisibleException(R.string.form_link_create_error_empty_name) }
                request.url.ifBlank { throw VisibleException(R.string.form_link_create_error_invalid_url) }

                val link = factory(request)
                uiState.update { it.copy(isLoading = false, error = null, link = link) }
            } catch (e: Exception) {
                Log.e(TAG, "Unable to create link", e)
                uiState.update { it.copy(isLoading = false, error = e) }
            }
        }
    }
}
