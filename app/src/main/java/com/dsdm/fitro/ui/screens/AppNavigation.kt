package com.dsdm.fitro.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dsdm.fitro.ui.screens.home.HomeScreen
import com.dsdm.fitro.ui.screens.login.LoginScreen
import com.dsdm.fitro.ui.screens.register.RegisterScreen
import com.dsdm.fitro.ui.screens.settings.SettingsScreen
import com.dsdm.fitro.ui.screens.workout.AddWorkoutScreen
import com.dsdm.fitro.ui.screens.workout.WorkoutScreen
import com.dsdm.fitro.ui.screens.workout.WorkoutViewModel
import com.dsdm.fitro.util.PreferencesManager

@Composable
fun AppNavigation() {
    val context = LocalContext.current
    val prefs = remember { PreferencesManager(context) }
    val navController = rememberNavController()
    val startDestination = if (prefs.isLoggedIn()) Routes.HOME else Routes.LOGIN
    val workoutViewModel: WorkoutViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Routes.LOGIN) {
            LoginScreen(navController)
        }
        composable(Routes.REGISTER) {
            RegisterScreen(navController)
        }
        composable(Routes.HOME) {
            HomeScreen(navController)
        }
        composable(Routes.WORKOUT) {
            WorkoutScreen(navController)
        }
        composable(Routes.ADD_WORKOUT) {
            AddWorkoutScreen(navController, workoutViewModel)
        }
        composable(Routes.SETTINGS) {
            SettingsScreen(navController)
        }
    }
}
