package ru.ruscalworld.studyplanner.forms.link.create

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.ruscalworld.studyplanner.core.model.Discipline
import javax.inject.Inject

@HiltViewModel
class CreateDisciplineLinkModalViewModel @Inject constructor() : CreateLinkModalViewModel<Discipline.Link>()
