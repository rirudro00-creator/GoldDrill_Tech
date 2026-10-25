package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

enum class AuthStage {
    WELCOME,
    PHONE_VERIFY,
    LOCATION_PERMISSION,
    MAIN_APP
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var currentStage by remember { mutableStateOf(AuthStage.WELCOME) }

            when (currentStage) {
                AuthStage.WELCOME -> {
                    WelcomeScreen(onGetStartedClick = { currentStage = AuthStage.PHONE_VERIFY })
                }
                AuthStage.PHONE_VERIFY -> {
                    PhoneLoginScreen(onVerifySuccess = { currentStage = AuthStage.LOCATION_PERMISSION })
                }
                AuthStage.LOCATION_PERMISSION -> {
                    LocationPermissionScreen(onPermissionGranted = { currentStage = AuthStage.MAIN_APP })
                }
                AuthStage.MAIN_APP -> {
                    MainAppNavigation()
                }
            }
        }
    }
}

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen("home", "Home", Icons.Default.Home)
    object Legal : Screen("legal", "Legal", Icons.Default.Gavel)
    object Maps : Screen("maps", "Maps", Icons.Default.Place)
    object Chats : Screen("chats", "Chats", Icons.Default.Chat)
    object Profile : Screen("profile", "My Profile", Icons.Default.Person)
}

@Composable
fun MainAppNavigation() {
    var currentTab by remember { mutableStateOf<Screen>(Screen.Home) }
    var selectedMarketItem by remember { mutableStateOf<MarketItem?>(null) }

    val tabs = listOf(Screen.Home, Screen.Legal, Screen.Maps, Screen.Chats, Screen.Profile)

    Scaffold(
        bottomBar = {
            NavigationBar {
                tabs.forEach { tab ->
                    NavigationBarItem(
                        selected = currentTab == tab && selectedMarketItem == null,
                        onClick = {
                            currentTab = tab
                            selectedMarketItem = null
                        },
                        label = { Text(tab.title) },
                        icon = { Icon(tab.icon, contentDescription = tab.title) }
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            if (selectedMarketItem != null) {
                ItemDetailScreen(
                    item = selectedMarketItem!!,
                    onBackClick = { selectedMarketItem = null }
                )
            } else {
                when (currentTab) {
                    Screen.Home -> MarketScreen(onItemClick = { selectedMarketItem = it })
                    Screen.Maps -> MapScreen()
                    Screen.Chats -> ChatScreen()
                    else -> ScreenPlaceholder(currentTab.title)
                }
            }
        }
    }
}

@Composable
fun ScreenPlaceholder(title: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("$title Screen Coming Soon")
    }
}