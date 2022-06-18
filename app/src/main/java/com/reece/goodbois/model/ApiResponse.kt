package com.reece.goodbois.model

import com.squareup.moshi.Json

data class Breed(
    val id: Int, val name: String, val origin: String, val temperament: String,
    val weight: Measurement, val height: Measurement, val image: Image?,
    @field:Json(name = "bred_for") val bredFor: String,
    @field:Json(name = "breed_group") val breedGroup: String,
    @field:Json(name = "life_span") val lifeSpan: String,
    @field:Json(name = "reference_image_id") val refImageId: String
) {
    internal fun getImageUrl() = "https://cdn2.thedogapi.com/images/$refImageId.jpg"
}

data class Measurement(val imperial: String, val metric: String)

data class Image(val id: String, val width: Int, val Height: Int, val url: String)


//Breed detail
data class SearchResult(
    val id: String, val url: String, val width: Int,
    val height: Int, val breeds: List<Breed>
)