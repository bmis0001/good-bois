package com.reece.goodbois.viewmodel

import androidx.lifecycle.ViewModel
import com.reece.goodbois.repository.DoggieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BaseViewModel @Inject constructor(private val repository: DoggieRepository) : ViewModel() {

    internal suspend fun getAllBreeds() = repository.getAllBreeds()

    internal suspend fun getBreedByName(keyword: String) = repository.getBreedByName(keyword)

    internal suspend fun getBreedDetail(breedId: Int) = repository.getBreedDetail(breedId)
}