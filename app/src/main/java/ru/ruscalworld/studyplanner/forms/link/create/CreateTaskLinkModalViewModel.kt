package ru.ruscalworld.studyplanner.forms.link.create

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.ruscalworld.studyplanner.core.model.Task
import javax.inject.Inject

@HiltViewModel
class CreateTaskLinkModalViewModel @Inject constructor() : CreateLinkModalViewModel<Task.Link>()
