package br.com.mendo.kotlincoroutines.usecases.usecase3

import androidx.lifecycle.viewModelScope
import br.com.mendo.kotlincoroutines.base.BaseViewModel
import br.com.mendo.kotlincoroutines.mock.MockApi
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class PerformNetworkRequestsConcurrentlyViewModel (
    private val mockApi: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun performNetworkRequestsSequentially() {
        uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val oreoFeature = mockApi.getAndroidVersionFeatures(27)
                val pieFeature = mockApi.getAndroidVersionFeatures(28)
                val androidFeatures10 = mockApi.getAndroidVersionFeatures(29)

                val versionFeatures = listOf(oreoFeature, pieFeature, androidFeatures10)
                uiState.value = UiState.Success(versionFeatures)
            } catch (e: Exception) {
                uiState.value = UiState.Error("Error fetching data: ${e.message}")
            }
        }
    }

    fun performNetworkRequestsConcurrently() {
        uiState.value = UiState.Loading
        val oreoFeatureDeferred = viewModelScope.async {
            mockApi.getAndroidVersionFeatures(27)
        }
        val pieFeatureDeferred = viewModelScope.async {
            mockApi.getAndroidVersionFeatures(28)
        }
        val androidFeatures10Deferred = viewModelScope.async {
            mockApi.getAndroidVersionFeatures(29)
        }

        viewModelScope.launch {
            try {
                val versionFeatures = awaitAll(oreoFeatureDeferred, pieFeatureDeferred, androidFeatures10Deferred)
                uiState.value = UiState.Success(versionFeatures)
            } catch (e: Exception) {
                uiState.value = UiState.Error("Error fetching data: ${e.message}")
            }
        }
    }
}