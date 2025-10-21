import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.hfad.rickandmorty.ui.screen.HeroContent
import com.hfad.rickandmorty.ui.viewmodel.HeroViewModel
import kotlinx.serialization.Serializable
import okhttp3.Route

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

    fun currentRoute(): String? {
        return navController?.currentBackStackEntry?.destination?.route//текущий путь
    }


}

val LocalNavigator = staticCompositionLocalOf<Navigator> {
    error("No navigation")
}

@Serializable
object HeroContent

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
            startDestination = HeroContent
        ) {

            composable(route = HeroContent.toString()) {
                HeroContent(
                    heroViewModel
                )

            }
        }
    }
}