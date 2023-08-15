package com.rechaniz.techtestbackend.models

data class Product(
    val refType: String,
    val refNumber: String,
    val weight: Double,
    val totalWeight: Double,
    val quantity: Int,
    val totalQuantity: Int,
    val description: String
)
