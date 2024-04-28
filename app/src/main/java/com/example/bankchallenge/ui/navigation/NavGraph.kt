package com.example.bankchallenge.ui.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bankchallenge.ui.screens.home.HomeScreen
import com.example.bankchallenge.ui.screens.login.LoginScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Login.route) {
        composable(Screen.Login.route) { LoginScreen { navController.navigate("home") } }
        composable(Screen.Home.route) { HomeScreen() }
        // composable(Screen.Register.route) { RegisterScreen() }
    }
}

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Register : Screen("register")
    object Login : Screen("login")

    object Detail : Screen("detail")
}