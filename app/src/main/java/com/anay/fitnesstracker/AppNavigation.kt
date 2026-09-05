package com.anay.fitnesstracker

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.anay.fitnesstracker.Screens.DashboardScreen
import com.anay.fitnesstracker.screens.ProfileScreen
import com.anay.fitnesstracker.screens.ProgressScreen
import com.anay.fitnesstracker.screens.WorkoutsScreen
import com.anay.fitnesstracker.Routes

object Routes {
    const val DASHBOARD = "dashboard"
    const val WORKOUTS = "workouts"
    const val PROGRESS = "progress"
    const val PROFILE = "profile"
}

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.DASHBOARD
    ) {

        composable(Routes.DASHBOARD) {
            DashboardScreen(
                onNavigate = { route ->
                    navController.navigate(route) {
                        popUpTo(Routes.DASHBOARD) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }

        composable(Routes.WORKOUTS) {
            WorkoutsScreen(
                onNavigate = { route ->
                    navController.navigate(route) {
                        popUpTo(Routes.DASHBOARD) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }

        composable(Routes.PROGRESS) {
            ProgressScreen(
                onNavigate = { route ->
                    navController.navigate(route) {
                        popUpTo(Routes.DASHBOARD) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }

        composable(Routes.PROFILE) {
            ProfileScreen(
                onNavigate = { route ->
                    navController.navigate(route) {
                        popUpTo(Routes.DASHBOARD) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}