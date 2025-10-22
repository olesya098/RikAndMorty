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

private data class RequestParams(//класс для хранения кэшированных данных
    val page: Int,
    val name: String?,
    val status: String?,
    val species: String?,
    val gender: String?
)

class HeroViewModel : ViewModel() {

    private val repository = HeroRepository()

    private val _heroes = MutableStateFlow<List<Results>>(emptyList())
    val heroes = _heroes.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()


    // Кэш для хранения данных
    private var dataCache: List<Results>? = null

    // Кэш для параметров запроса
    private var lastRequestParams: RequestParams? = null

    // Сохранение данных в кэш
    private fun setDataToCache(data: List<Results>) {
        dataCache = data
    }

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
        val currentParams = RequestParams(page, name, status, species, gender)

        // Если параметры не изменились и есть кэшированные данные, используем кэш
        if (currentParams == lastRequestParams && dataCache != null) {
            _heroes.value = dataCache!!
            _error.value = null
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val response = repository.getHeroes(page, name, status, species, gender)
                val heroesList = response.results

                // Сохраняем данные в кэш
                setDataToCache(heroesList)
                lastRequestParams = currentParams

                _heroes.value = heroesList

            } catch (e: IOException) {
                // При ошибке сети пытаемся использовать кэшированные данные
                if (dataCache != null) {
                    _heroes.value = dataCache!!
                    _error.value = "Network error. Showing cached data. ${e.message}"
                    Log.w("My", "Network error, showing cached data", e)
                } else {
                    _error.value = "Network error: ${e.message}"
                    Log.e("My", "Network error, no cached data available", e)
                }
            } catch (e: HttpRequestTimeoutException) {
                // При таймауте также пытаемся использовать кэш
                if (dataCache != null) {
                    _heroes.value = dataCache!!
                    _error.value = "Request timeout. Showing cached data."
                    Log.w("My", "Timeout error, showing cached data", e)
                } else {
                    _error.value = "Request timeout. Please try again."
                    Log.e("My", "Timeout error, no cached data available", e)
                }
            } catch (e: Exception) {
                // При других ошибках также пытаемся использовать кэш
                if (dataCache != null) {
                    _heroes.value = dataCache!!
                    _error.value = "Error. Showing cached data. ${e.message}"
                    Log.w("My", "General error, showing cached data", e)
                } else {
                    _error.value = "Error: ${e.message}"
                    Log.e("My", "General error, no cached data available", e)
                }
            } finally {
                _isLoading.value = false
            }
        }
    }
}
