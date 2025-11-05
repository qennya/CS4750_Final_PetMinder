package com.example.petminder

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

enum class BottomDest(val route: String, val label: String, val icon: ImageVector) {
    Home("home", "Home", Icons.Filled.Home),
    Calendar("calendar", "Calendar", Icons.Filled.CalendarMonth),
    Pets("pets", "Pets", Icons.Filled.Pets),
    Family("family", "Family", Icons.Filled.SupervisorAccount)
}

@Composable
fun PetMinderBottomBar(currentRoute: String?, onNavigate: (String) -> Unit) {
    NavigationBar {
        BottomDest.values().forEach { d ->
            NavigationBarItem(
                selected = currentRoute == d.route,
                onClick = { onNavigate(d.route) },
                icon = { Icon(d.icon, contentDescription = d.label) },
                label = { Text(d.label) }
            )
        }
    }
}
