package com.example.bankchallenge.ui.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bankchallenge.ui.screens.home.HomeScreen
import com.example.bankchallenge.ui.screens.login.LoginScreen
import com.example.bankchallenge.ui.screens.registration.RegisterScreen

private const val HOME_ROUTE = "home"
private const val REGISTER_ROUTE = "register"
private const val LOGIN_ROUTE = "login"

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = LOGIN_ROUTE) {
        composable(LOGIN_ROUTE) {
            LoginScreen({ navController.navigate(REGISTER_ROUTE) }) {
                navController.navigate(
                    HOME_ROUTE
                )
            }
        }
        composable(HOME_ROUTE) { HomeScreen() }

        composable(REGISTER_ROUTE) { RegisterScreen { navController.navigate(HOME_ROUTE) } }
    }
}
