package com.example.myapplication

object SampleData {
    val marketItems = listOf(
        MarketItem(
            id = 1,
            title = "Minelab GPZ 7000 Gold Detector",
            model = "GPZ 7000 (ZVT Tech)",
            price = "8,500 USD",
            category = "Markets",
            sellerName = "Ahmed Ould Sidi",
            manufacturer = "Minelab Electronics",
            year = "2022",
            imageUrl = R.drawable.gold_detector,
            description = "Advanced gold prospecting detector equipped with Zero Voltage Transmission (ZVT) technology and a Super-D coil. Exceptional depth penetration in highly mineralized soil and deep quartz veins."
        ),
        MarketItem(
            id = 2,
            title = "Heavy Duty Electric Demolition Hammer",
            model = "Bosch GSH 27 VC (2000W)",
            price = "2,400 USD",
            category = "Markets",
            sellerName = "Mohamed El Mokhtar",
            manufacturer = "Bosch Power Tools",
            year = "2023",
            imageUrl = "https://images.unsplash.com/photo-1504307651254-35680f356dfd?w=600",
            description = "Heavy-duty 2000W breaker jackhammer with built-in vibration control. Engineered for breaking tough granite, quartz rock formations, and concrete foundations in mining sites."
        ),
        MarketItem(
            id = 3,
            title = "Mobile Gold Wash Plant & Trommel",
            model = "Modular 30 TPH Recovery Unit",
            price = "35,000 USD",
            category = "Mines",
            sellerName = "Chinguetti Mining Equipment Co.",
            manufacturer = "Sahara Heavy Industries",
            year = "2024",
            imageUrl = "https://images.unsplash.com/photo-1578328819058-b69f3a3b0f6b?w=600",
            description = "High-efficiency diesel-driven mobile trommel wash plant. Includes fine gold sluice riffle mats, high-pressure water pump system, and continuous material processing up to 30 tons per hour."
        )
    )

    // South Korea Map Locations
    val mapLocations = listOf(
        MapLocation(1, "Taebaek Mining District", LocationType.MINE, LocationPoint(37.1759, 128.9889)),
        MapLocation(2, "Samcheok Mineral Zone", LocationType.MINE, LocationPoint(37.4406, 129.1708)),
        MapLocation(3, "Seoul Heavy Equipment Hub", LocationType.MARKET, LocationPoint(37.5665, 126.9780)),
        MapLocation(4, "Busan Port Machinery Market", LocationType.MARKET, LocationPoint(35.1027, 129.0403))
    )

    // User Location centered near Seoul
    val userLocation = LocationPoint(37.5665, 126.9780)
}