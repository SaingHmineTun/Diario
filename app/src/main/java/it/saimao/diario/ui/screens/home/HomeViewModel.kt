package it.saimao.diario.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import it.saimao.diario.DiarioApplication
import it.saimao.diario.data.DiarioEntity
import it.saimao.diario.data.DiarioRepository
import it.saimao.diario.ui.screens.state.DiarioUiModel
import it.saimao.diario.ui.screens.state.toUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DiarioViewModel(private val repository: DiarioRepository) : ViewModel() {


    val uiState: StateFlow<List<DiarioUiModel>> = repository.getAllDiario()
        .map { entities ->
            entities.map { it.toUiModel() }
        }
        .stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5000), listOf()
        )

    fun addDiario(diarioEntity: DiarioEntity) {
        viewModelScope.launch {
            repository.insert(diarioEntity)
        }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as DiarioApplication
                DiarioViewModel(
                    application.container.diarioRepository
                )
            }
        }
    }

}