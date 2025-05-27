package com.example.kuitandroidapiexample.data.repository

import com.example.kuitandroidapiexample.data.dto.request.RequestAddAnimalDto
import com.example.kuitandroidapiexample.data.service.AnimalService

class AnimalRepository(
    private val animalService: AnimalService
) {
    suspend fun getAnimal() = runCatching { animalService.getTotalAnimalList() }

    suspend fun getAnimalDetail(id: Int) = runCatching { animalService.getAnimalDetail(id) }

    suspend fun postAnimal(data: RequestAddAnimalDto) = runCatching { animalService.postAddAnimal(data) }

    suspend fun deleteAnimal(id: Int) = runCatching { animalService.deleteAnimal(id) }
}