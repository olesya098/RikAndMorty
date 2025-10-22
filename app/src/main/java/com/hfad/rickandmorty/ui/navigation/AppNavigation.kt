package com.hfad.rickandmorty.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hfad.rickandmorty.ui.screen.HeroContent
import com.hfad.rickandmorty.ui.viewmodel.HeroViewModel

class Navigator {
    private var navController: NavController? = null
    fun bind(navController: NavController) {
        this.navController = navController

    }

    fun navigate(
        route: String,
    ) {
        navController?.navigate(route)
    }

    fun navigateWithBack(
        route: String,
        popBackRoute: String
    ) {
        navController?.navigate(route) {
            popUpTo(popBackRoute) { inclusive = true }
        }
    }
}

val LocalNavigator = staticCompositionLocalOf<Navigator> {
    error("No navigation")
}


@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val navigator = remember { Navigator() }.apply { bind(navController) }

    val heroViewModel = viewModel<HeroViewModel>()

    val context = LocalContext.current

    CompositionLocalProvider(
        LocalNavigator provides navigator
    ) {
        NavHost(
            navController = navController,
            startDestination = Routes.HEROCONTENT
        ) {

            composable(route = Routes.HEROCONTENT) {
                HeroContent(
                    heroViewModel
                )

            }
        }
    }
}