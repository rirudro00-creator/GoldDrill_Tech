package com.example.myapplication

import android.graphics.Color as AndroidColor
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.OnlineTileSourceBase
import org.osmdroid.util.GeoPoint
import org.osmdroid.util.MapTileIndex
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline

// 100% Free Esri ArcGIS World Street Map Tile Source (No API key required)
val ESRI_WORLD_STREET_MAP: OnlineTileSourceBase = object : OnlineTileSourceBase(
    "EsriWorldStreetMap",
    0, 19, 256, "",
    arrayOf("https://server.arcgisonline.com/ArcGIS/rest/services/World_Street_Map/MapServer/tile/")
) {
    override fun getTileURLString(pMapTileIndex: Long): String {
        return baseUrl + MapTileIndex.getZoom(pMapTileIndex) + "/" +
                MapTileIndex.getY(pMapTileIndex) + "/" +
                MapTileIndex.getX(pMapTileIndex)
    }
}

@Composable
fun MapScreen() {
    val context = LocalContext.current
    var filterType by remember { mutableStateOf("All") }
    var selectedTarget by remember { mutableStateOf<MapLocation?>(null) }

    LaunchedEffect(Unit) {
        Configuration.getInstance().userAgentValue = context.packageName
    }

    val filteredLocations = remember(filterType) {
        when (filterType) {
            "Mines" -> SampleData.mapLocations.filter { it.type == LocationType.MINE }
            "Markets" -> SampleData.mapLocations.filter { it.type == LocationType.MARKET }
            else -> SampleData.mapLocations
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { ctx ->
                MapView(ctx).apply {
                    setTileSource(ESRI_WORLD_STREET_MAP)
                    setMultiTouchControls(true)
                    controller.setZoom(7.0)
                    controller.setCenter(GeoPoint(36.5000, 127.8000)) // Centered over South Korea
                }
            },
            update = { mapView ->
                mapView.overlays.clear()

                // User Location Pin
                val userGeoPoint = GeoPoint(SampleData.userLocation.latitude, SampleData.userLocation.longitude)
                val userMarker = Marker(mapView).apply {
                    position = userGeoPoint
                    title = "Your Location (Seoul)"
                    setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                }
                mapView.overlays.add(userMarker)

                // Location Pins (Mines & Markets)
                filteredLocations.forEach { location ->
                    val locGeoPoint = GeoPoint(location.position.latitude, location.position.longitude)
                    val marker = Marker(mapView).apply {
                        position = locGeoPoint
                        title = location.name
                        snippet = location.type.name
                        setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                        setOnMarkerClickListener { _, _ ->
                            selectedTarget = location
                            true
                        }
                    }
                    mapView.overlays.add(marker)
                }

                // Polyline Route Overlay
                selectedTarget?.let { target ->
                    val line = Polyline().apply {
                        outlinePaint.color = AndroidColor.BLUE
                        outlinePaint.strokeWidth = 10f
                        setPoints(
                            listOf(
                                userGeoPoint,
                                GeoPoint(
                                    (userGeoPoint.latitude + target.position.latitude) / 2,
                                    (userGeoPoint.longitude + target.position.longitude) / 2
                                ),
                                GeoPoint(target.position.latitude, target.position.longitude)
                            )
                        )
                    }
                    mapView.overlays.add(line)
                }

                mapView.invalidate()
            }
        )

        // Category Filter Chips
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf("All", "Mines", "Markets").forEach { category ->
                Button(
                    onClick = { filterType = category },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (filterType == category) Color.Black else Color.White,
                        contentColor = if (filterType == category) Color.White else Color.Black
                    ),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(category)
                }
            }
        }

        // Navigation Route Banner
        selectedTarget?.let { target ->
            Card(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            "NAVIGATING ROUTE",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.Gray
                        )
                        Text(target.name, style = MaterialTheme.typography.titleMedium)
                    }
                    Button(
                        onClick = { selectedTarget = null },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                    ) {
                        Text("Cancel")
                    }
                }
            }
        }
    }
}