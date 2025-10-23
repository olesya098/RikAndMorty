package com.hfad.rickandmorty.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.hfad.rickandmorty.ui.screen.heroCard.HeroCard
import com.hfad.rickandmorty.ui.screen.heroesCard.HeroContent
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

    fun navigateToHeroCard(heroId: Int) {
        navigate(Routes.createHeroRoute(heroId))

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
            composable(
                route = Routes.HEROCARD,
                arguments = listOf(
                    navArgument("heroId") { type = NavType.IntType }
                )
            ) { backStackEntry -> //текущий маршрут
                val heroId = backStackEntry.arguments?.getInt("heroId") ?: 0
                HeroCard(
                    heroId = heroId,
                    heroViewModel = heroViewModel,
                    onBackClick = {
                        navigator.navigateWithBack(
                            Routes.HEROCARD,
                            Routes.HEROCONTENT,
                        )
                    }
                )

            }
        }
    }
}