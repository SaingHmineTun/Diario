package it.saimao.diario.ui.screens.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import it.saimao.diario.DiarioApplication
import it.saimao.diario.data.DiarioRepository
import it.saimao.diario.ui.screens.state.DiarioUiModel
import it.saimao.diario.ui.screens.state.toEntity
import it.saimao.diario.ui.screens.state.toUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date

class DetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: DiarioRepository
) : ViewModel() {
    private val itemId: String? = savedStateHandle["id"]

    var uiState by mutableStateOf(DetailUiState())

    init {
        if (itemId != null && itemId.toInt() > 0) {
            viewModelScope.launch {
                uiState = DetailUiState(
                    repository.getDiarioById(itemId.toInt()).first().toUiModel(),
                    isUpdated = true
                )
            }
        }
    }

    fun updateValue(value: DiarioUiModel) {
        uiState = uiState.copy(
            diarioUiModel = value,
        )
    }

    fun addOrUpdate() {
        if (uiState.isUpdated) {
            updateDiario()
        } else {
            addDiario()
        }
    }

    private fun updateDiario() {
        val diarioEntity = uiState.diarioUiModel.toEntity()
            .copy(
                updated = getCurrentTime()
            )
        viewModelScope.launch {
            repository.update(diarioEntity)
        }
    }

    private fun addDiario() {
        val currentTime = getCurrentTime()
        val diarioEntity = uiState.diarioUiModel.toEntity()
            .copy(
                created = currentTime,
                updated = currentTime
            )
        viewModelScope.launch {
            repository.insert(diarioEntity)
        }
    }

    fun deleteDiario() {
        viewModelScope.launch {
            repository.delete(uiState.diarioUiModel.toEntity())
        }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                DetailViewModel(

                    createSavedStateHandle(),
                    (this[APPLICATION_KEY] as DiarioApplication).container.diarioRepository
                )
            }
        }
    }

    private fun getCurrentTime(): Date {
        return Date()
    }

}