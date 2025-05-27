package com.example.kuitandroidapiexample.ui.home.viewmodel

import android.util.Log
import android.util.Log.i
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.kuitandroidapiexample.data.ServicePool
import com.example.kuitandroidapiexample.data.ServicePool.animalService
import com.example.kuitandroidapiexample.data.dto.request.RequestAddAnimalDto
import com.example.kuitandroidapiexample.data.dto.response.BaseResponse
import com.example.kuitandroidapiexample.data.dto.response.ResponseAnimalDetailDto
import com.example.kuitandroidapiexample.data.dto.response.ResponseAnimalDto
import com.example.kuitandroidapiexample.data.repository.AnimalRepository
import com.example.kuitandroidapiexample.data.service.AnimalService
import com.example.kuitandroidapiexample.ui.detail.uistate.AnimalDetailUiState
import com.example.kuitandroidapiexample.ui.detail.uistate.toUiState
import com.example.kuitandroidapiexample.ui.home.uistate.AnimalUiState
import com.example.kuitandroidapiexample.ui.home.uistate.toUiState
import com.example.kuitandroidapiexample.ui.model.AnimalType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimalViewModel @Inject constructor(
    private val animalRepository: AnimalRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(listOf<AnimalUiState>())
    val uiState = _uiState.asStateFlow()


    fun getAnimal() {
        viewModelScope.launch {
            animalRepository.getAnimal().fold(
                onSuccess = { data ->
                    _uiState.value = data.data.map { it.toUiState() }
                },
                onFailure = { error ->
                    Log.e("okHttpError", error.message.toString())
                }
            )
        }
    }
}