package com.anay.fitnesstracker

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.anay.fitnesstracker.screens.*
import com.anay.fitnesstracker.data.viewmodel.FitnessViewModel
import com.anay.fitnesstracker.Screens.DashboardScreen
import com.anay.fitnesstracker.Screens.ProfileScreen
import com.anay.fitnesstracker.Screens.OnboardingScreen
import com.anay.fitnesstracker.Screens.MealLogScreen


object Routes {
    const val SPLASH = "splash"
    const val ONBOARDING = "onboarding"
    const val ONBOARDING_CONTINUED = "onboarding_continued"
    const val PREFERENCES = "preferences"
    const val WORKOUT_SPLIT = "workout_split"
    const val CONFIRMATION = "confirmation"
    const val DASHBOARD = "dashboard"
    const val WORKOUTS = "workouts"
    const val PROGRESS = "progress"
    const val PROFILE = "profile"
    const val MEAL_LOG = "meal_log"
}

@Composable
fun FitnessAppNavHost(
    fitnessViewModel: FitnessViewModel = viewModel()
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH
    ) {
        composable(Routes.SPLASH) {
            SplashScreen(
                viewModel = fitnessViewModel,
                onLoginSuccess = {
                    navController.navigate(Routes.DASHBOARD) {
                        popUpTo(Routes.SPLASH) { inclusive = true }
                    }
                },
                onNavigateSignUp = { navController.navigate(Routes.ONBOARDING) }
            )
        }

        composable(Routes.ONBOARDING) {
            OnboardingScreen(
                viewModel = fitnessViewModel,
                onNext = { navController.navigate(Routes.ONBOARDING_CONTINUED) }
            )
        }

        composable(Routes.ONBOARDING_CONTINUED) {
            OnboardingContinuedScreen(
                viewModel = fitnessViewModel,
                onNext = { navController.navigate(Routes.PREFERENCES) }
            )
        }

        composable(Routes.PREFERENCES) {
            PreferencesScreen(
                viewModel = fitnessViewModel,
                onNavigateWorkoutSplit = { navController.navigate(Routes.WORKOUT_SPLIT) },
                onNext = { navController.navigate(Routes.CONFIRMATION) }
            )
        }

        composable(Routes.WORKOUT_SPLIT) {
            WorkoutSplitScreen(
                viewModel = fitnessViewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.CONFIRMATION) {
            ConfirmationScreen(
                viewModel = fitnessViewModel,
                onEnterDashboard = {
                    navController.navigate(Routes.DASHBOARD) {
                        popUpTo(Routes.SPLASH) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.DASHBOARD) {
            DashboardScreen(
                onNavigate = { route: String -> navController.navigate(route) },
                onNavigateMealLog = { navController.navigate(Routes.MEAL_LOG) },
                fitnessViewModel = fitnessViewModel
            )
        }

        composable(Routes.WORKOUTS) {
            WorkoutsScreen(onNavigate = { route -> navController.navigate(route) })
        }

        composable(Routes.PROGRESS) {
            ProgressScreen(onNavigate = { route -> navController.navigate(route) })
        }

        composable(Routes.PROFILE) {
            ProfileScreen(
                onNavigate = { route: String -> navController.navigate(route) },
                fitnessViewModel = fitnessViewModel
            )
        }

        composable(Routes.MEAL_LOG) {
            MealLogScreen(
                viewModel = fitnessViewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}