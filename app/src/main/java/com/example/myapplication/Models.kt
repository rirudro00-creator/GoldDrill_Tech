package com.example.myapplication

data class LocationPoint(val latitude: Double, val longitude: Double)

data class MarketItem(
    val id: Int,
    val title: String,
    val model: String,
    val price: String,
    val category: String,
    val sellerName: String,
    val manufacturer: String,
    val year: String,
    val imageUrl: Any, // Changed to Any so it accepts R.drawable.gold_detector or String URL
    val description: String
)

enum class LocationType { MINE, MARKET }

data class MapLocation(
    val id: Int,
    val name: String,
    val type: LocationType,
    val position: LocationPoint
)