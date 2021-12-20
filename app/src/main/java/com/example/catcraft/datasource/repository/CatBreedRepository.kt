package com.example.catcraft.datasource.repository

import com.example.catcraft.arch.Resource
import com.example.catcraft.arch.getResult
import com.example.catcraft.datasource.apiservice.CatBreedService
import com.example.catcraft.datasource.model.CatBreedData
import javax.inject.Inject

class CatBreedRepository @Inject constructor(private var breedService: CatBreedService) : ICatBreedRepository {

    override suspend fun getCatBreedList(): Resource<List<CatBreedData>> {
        return getResult {
            breedService.getCatBreedList()
        }
    }

}