package com.reece.goodbois.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reece.goodbois.model.Breed
import com.reece.goodbois.repository.DoggieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BaseViewModel @Inject constructor(private val repository: DoggieRepository) : ViewModel() {

    internal val allBreeds: MutableLiveData<List<Breed>> by lazy { MutableLiveData() }

    internal suspend fun getAllBreeds() {
        viewModelScope.launch {
            val result = repository.getAllBreeds()
            if (result.isSuccessful)
                result.body()?.let { allBreeds.postValue(result.body()) }
        }
    }

    internal suspend fun getBreedByName(keyword: String) {
        viewModelScope.launch {
            val result = repository.getBreedByName(keyword)
            if (result.isSuccessful)
                result.body()?.let { allBreeds.postValue(it) }
        }
    }

    internal suspend fun getBreedDetail(breedId: Int) = repository.getBreedDetail(breedId)
}