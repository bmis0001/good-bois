package com.reece.goodbois.repository

import com.reece.goodbois.retrofit.DoggieApi
import javax.inject.Inject

class DoggieRepository @Inject constructor(private val api: DoggieApi) {

    internal suspend fun getAllBreeds() = api.getAllBreeds()

    internal suspend fun getBreedByName(keyword: String) = api.getBreedsByName(keyword)

    internal suspend fun getBreedDetail(breedId: Int) = api.getBreedDetails(breedId)
}