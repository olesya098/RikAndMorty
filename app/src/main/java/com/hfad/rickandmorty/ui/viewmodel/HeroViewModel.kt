package com.hfad.rickandmorty.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfad.rickandmorty.data.model.Results
import com.hfad.rickandmorty.domain.repository.HeroRepository
import io.ktor.client.plugins.HttpRequestTimeoutException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

class HeroViewModel : ViewModel() {
    private val repository = HeroRepository()

    private val _heroes = MutableStateFlow<List<Results>>(emptyList())
    val heroes = _heroes.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    init {
        loadHero()
    }

    fun loadHero(
        page: Int = 1,
        name: String? = null,
        status: String? = null,
        species: String? = null,
        gender: String? = null
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val response = repository.getHeroes(page, name, status, species, gender)
                _heroes.value = response.results
            } catch (e: IOException) {
                _error.value = "Network error: ${e.message}"
                Log.e("My", "Network error", e)
            } catch (e: HttpRequestTimeoutException) {
                _error.value = "Request timeout. Please try again."
                Log.e("My", "Timeout error", e)
            } catch (e: Exception) {
                _error.value = "Error: ${e.message}"
                Log.e("My", "General error", e)
            } finally {
                _isLoading.value = false
            }
        }
    }
}
