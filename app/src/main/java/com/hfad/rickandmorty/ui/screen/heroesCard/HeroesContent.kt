package com.hfad.rickandmorty.ui.screen.heroesCard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hfad.rickandmorty.ui.viewmodel.HeroViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeroContent(heroViewModel: HeroViewModel) {
    val heroes = heroViewModel.heroes.collectAsState().value
    val isLoading = heroViewModel.isLoading.collectAsState().value
    val error = heroViewModel.error.collectAsState().value

    var query by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                },
                actions = {
                    DockedSearchBar(
                        query = query,
                        onQueryChange = {
                            query = it
                            heroViewModel.loadHero(name = it.ifEmpty { null })
                        },
                        onSearch = {
                            heroViewModel.loadHero(name = it.ifEmpty { null })
                        },
                        active = active,
                        onActiveChange = { active = it },
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 8.dp),
                        placeholder = {
                            Text("Search characters...")
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search"
                            )
                        },
                        trailingIcon = {
                            if (active) {
                                IconButton(
                                    onClick = {
                                        if (query.isNotEmpty()) {
                                            query = ""
                                            heroViewModel.loadHero()
                                        } else {
                                            active = false
                                        }
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Close"
                                    )
                                }
                            }
                        },
                        colors = SearchBarDefaults.colors(
                            dividerColor = Color.Transparent
                        )
                    ) {

                    }
                }
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when {
                isLoading && heroes.isEmpty() -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                heroes.isNotEmpty() -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(heroes) { hero ->
                            HeroesCard(hero = hero)
                        }
                    }
                }

                else -> {
                    Text(
                        text = "No characters found",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }

            if (isLoading && heroes.isNotEmpty()) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp)
                )
            }
        }
    }
}