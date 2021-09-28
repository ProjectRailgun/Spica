package co.railgun.spica.ui.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun SplashUI(
    navController: NavController = rememberNavController(),
    viewModel: SplashViewModel = viewModel(),
) {
    val route by viewModel.route.collectAsState()
    LaunchedEffect(route) {
        if (route == SplashScreen.route) return@LaunchedEffect
        navController.popBackStack()
        navController.navigate(route)
    }
}
