package it.saimao.diario.ui.screens.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import it.saimao.diario.ui.screens.detail.DetailScreen
import it.saimao.diario.ui.screens.home.HomeScreen


object Destinations {
    const val Home = "home"
    const val Detail = "detail/{id}"
}

@Composable
fun NavCompose() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Destinations.Home) {
        composable(Destinations.Home) {
            HomeScreen(onClick = {
                navController.navigate("detail/${it}")
            })
        }
        composable(Destinations.Detail) {
            val arg = it.arguments?.getString("id") ?: ""
            Log.d("Kham", "Arg: $arg")
            DetailScreen(navigateBack = {
                navController.popBackStack()
            })
        }
    }
}