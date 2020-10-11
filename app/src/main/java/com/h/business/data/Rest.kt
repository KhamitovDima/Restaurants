package com.h.business.data

data class Rest(
    val name: String,
    val distance: Double,
    val rating: Double,
    val rest_image: List<String>
)

data class ResponceGraphQL(
    val rests: List<Rest>
)