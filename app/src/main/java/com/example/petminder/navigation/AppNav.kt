package com.example.petminder.navigation

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavType
import androidx.navigation.compose.*
import com.example.petminder.BottomDest
import com.example.petminder.PetMinderBottomBar
import com.example.petminder.data.TaskViewModel
import com.example.petminder.feature.edit.EditTaskScreen
import com.example.petminder.feature.home.HomeScreen

@Composable
fun PetMinderNav(vm: TaskViewModel) {
    val nav = rememberNavController()

    Scaffold(bottomBar = {
        val backStack by nav.currentBackStackEntryAsState()
        val current = backStack?.destination?.route
        PetMinderBottomBar(currentRoute = current) { route ->
            nav.navigate(route) {
                popUpTo(nav.graph.startDestinationId) { saveState = true }
                launchSingleTop = true; restoreState = true
            }
        }
    }) { inner ->
        NavHost(navController = nav, startDestination = BottomDest.Home.route, modifier = Modifier.padding(inner)) {
            composable(BottomDest.Home.route) {
                HomeScreen(vm = vm, onAdd = { nav.navigate("edit") }, onOpenTask = { nav.navigate("edit?id=${it.id}") })
            }
            composable(BottomDest.Calendar.route) { Placeholder("Calendar") }
            composable(BottomDest.Pets.route) { Placeholder("Pets") }
            composable(BottomDest.Family.route) { Placeholder("Family") }
            composable("edit?id={id}", arguments = listOf(navArgument("id") { type = NavType.StringType; nullable = true })) {
                val id = it.arguments?.getString("id")
                EditTaskScreen(vm, id, onDone = { nav.popBackStack() }, onDelete = { nav.popBackStack() })
            }
            composable("edit") {
                EditTaskScreen(vm, null, onDone = { nav.popBackStack() }, onDelete = { nav.popBackStack() })
            }
        }
    }
}

@Composable
private fun Placeholder(name: String) {
    Surface { Text("($name screen coming soon)", modifier = Modifier.padding(24.dp)) }
}
