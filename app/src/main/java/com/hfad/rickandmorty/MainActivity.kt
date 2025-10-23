package com.hfad.rickandmorty

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.hfad.rickandmorty.ui.navigation.AppNavigation
import com.hfad.rickandmorty.ui.screen.heroesCard.HeroesCard
import com.hfad.rickandmorty.ui.theme.RickAndMortyTheme
import com.hfad.rickandmorty.ui.theme.background2

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        enableEdgeToEdge()
        setContent {
            RickAndMortyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = background2
                ) {
                    AppNavigation()
                }
            }
        }
    }
}
