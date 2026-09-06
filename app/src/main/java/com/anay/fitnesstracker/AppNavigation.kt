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
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.core.tween
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

        composable(
            route = Routes.DASHBOARD,
            enterTransition = {
                fadeIn(animationSpec = tween(220))
            },
            exitTransition = {
                fadeOut(animationSpec = tween(220))
            }
        ) {
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

        composable(
            route = Routes.WORKOUTS,
            enterTransition = {
                fadeIn(animationSpec = tween(220))
            },
            exitTransition = {
                fadeOut(animationSpec = tween(220))
            }
        ) {
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

        composable(
            route = Routes.PROGRESS,
            enterTransition = {
                fadeIn(animationSpec = tween(220))
            },
            exitTransition = {
                fadeOut(animationSpec = tween(220))
            }
        ) {
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

        composable(
            route = Routes.PROFILE,
            enterTransition = {
                fadeIn(animationSpec = tween(220))
            },
            exitTransition = {
                fadeOut(animationSpec = tween(220))
            }
        ) {
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