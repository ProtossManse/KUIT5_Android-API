package com.example.kuitandroidapiexample.ui.register.viewmodel

import android.R.attr.name
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.kuitandroidapiexample.data.repository.AnimalRepository
import com.example.kuitandroidapiexample.ui.home.uistate.AnimalUiState
import com.example.kuitandroidapiexample.ui.home.uistate.toUiState
import com.example.kuitandroidapiexample.ui.home.viewmodel.AnimalViewModel
import com.example.kuitandroidapiexample.ui.model.AnimalType
import com.example.kuitandroidapiexample.ui.register.uistate.AnimalAddUiState
import com.example.kuitandroidapiexample.ui.register.uistate.toDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimalRegisterViewModel @Inject constructor(
    private val animalRepository: AnimalRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(AnimalAddUiState())
    val uiState = _uiState.asStateFlow()

    fun onUrlChanged(url: String) {
        _uiState.update {
            it.copy(
                url = url
            )
        }
    }

    fun onReporterChanged(reporter: String) {
        _uiState.update {
            it.copy(
                reporter = reporter
            )
        }
    }

    fun onAddressChanged(address: String) {
        _uiState.update {
            it.copy(
                address = address
            )
        }
    }

    fun onAnimalNameChanged(animalName: String) {
        _uiState.update {
            it.copy(
                animalName = animalName
            )
        }
    }

    fun onTypeChanged (animalType: AnimalType) {
        _uiState.update {
            it.copy(
                animalType = animalType
            )
        }
    }


    fun postAnimal() {
        viewModelScope.launch {
            animalRepository.postAnimal(_uiState.value.toDto()).fold(
                onSuccess = { data ->
                    _uiState.update { it.copy(isAdded = true) }
                },
                onFailure = { error ->
                    Log.e("okHttpError", error.message.toString())
                }
            )
        }
    }
}