package com.example.kuitandroidapiexample.ui.detail.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.kuitandroidapiexample.data.repository.AnimalRepository
import com.example.kuitandroidapiexample.ui.detail.uistate.AnimalDetailUiState
import com.example.kuitandroidapiexample.ui.detail.uistate.toUiState
import com.example.kuitandroidapiexample.ui.home.viewmodel.AnimalViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimalDetailViewModel @Inject constructor(
    private val animalRepository: AnimalRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AnimalDetailUiState())
    val uiState = _uiState.asStateFlow()

    fun getAnimalDetail(id: Int) {
        viewModelScope.launch {
            animalRepository.getAnimalDetail(id).fold(
                onSuccess = { data ->
                    _uiState.value = data.data.toUiState()
                },
                onFailure = { error ->
                    Log.e("okHttpError", error.message.toString())
                }
            )
        }
    }

    fun deleteAnimal(id: Int) {
        viewModelScope.launch {
            animalRepository.deleteAnimal(id).fold(
                onSuccess = { data ->
//                    _uiState.value.isDeleted = true
//                    왜 안되는지?
                    _uiState.update {
                        it.copy(
                            isDeleted = true
                        )
                    }
                },
                onFailure = { error ->
                    Log.e("okHttpError", error.message.toString())
                }
            )
        }

    }
}
