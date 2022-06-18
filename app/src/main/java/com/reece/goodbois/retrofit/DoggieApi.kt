package com.reece.goodbois.retrofit

import com.reece.goodbois.model.Breed
import com.reece.goodbois.model.SearchResult
import com.reece.goodbois.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface DoggieApi {

    @GET("/v1/breeds")
    suspend fun getAllBreeds(
        //@Query("limit") limit: Int, @Query("page") page: Int
    ): Response<List<Breed>>

    @GET("/v1/breeds/search")
    suspend fun getBreedsByName(@Query("q") q: String): Response<List<Breed>>

    @Headers("x-api-key: ${Constants.API_KEY}")
    @GET("/v1/images/search")
    suspend fun getBreedDetails(@Query("breed_ids") breedId: Int): Response<List<SearchResult>>
}