package it.saimao.diario.ui.screens.detail

import it.saimao.diario.ui.screens.state.DiarioUiModel

data class DetailUiState(
    val diarioUiModel: DiarioUiModel = DiarioUiModel(),
    val isUpdated: Boolean = false
)