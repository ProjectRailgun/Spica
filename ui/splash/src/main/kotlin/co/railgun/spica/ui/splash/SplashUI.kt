package co.railgun.spica.ui.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
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
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = null
        )
    }
}
