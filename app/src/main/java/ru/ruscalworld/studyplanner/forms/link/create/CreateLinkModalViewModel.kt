package ru.ruscalworld.studyplanner.forms.link.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.ruscalworld.studyplanner.core.model.EntityLink
import javax.inject.Inject

open class CreateLinkModalViewModel<L : EntityLink> : ViewModel() {
    val uiState = MutableStateFlow(CreateLinkState<L>())

    fun createLink(request: CreateLinkRequest, factory: suspend (CreateLinkRequest) -> L) {
        uiState.update { it.copy(isLoading = true, error = null) }

        viewModelScope.launch {
            delay(1000)
            uiState.update { it.copy(isLoading = false, error = null, link = factory(request)) }
        }
    }
}