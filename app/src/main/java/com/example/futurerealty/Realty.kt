package com.example.futurerealty

data class Realty(
    var price: String,
    var description: String,
    var location: String,
    var contact: String,
    var imageUrl: String? = null
)
