package br.com.mendo.kotlincoroutines.usecases.usecase2

import androidx.lifecycle.viewModelScope
import br.com.mendo.kotlincoroutines.base.BaseViewModel
import br.com.mendo.kotlincoroutines.mock.MockApi
import br.com.mendo.kotlincoroutines.usecases.usecase2.callbacks.UiState
import kotlinx.coroutines.launch

class Perform2SequentialNetworkRequestsViewModel ( private val mockApi: MockApi
) : BaseViewModel<UiState>() {

    fun perform2SequentialNetworkRequest() {
        uiState.value = UiState.Loading

        viewModelScope.launch {
            try {
                val recentVersions = mockApi.getRecentAndroidVersions()
                val mostRecentVersion = recentVersions.last()
                val featuresOfMostRecentVersion = mockApi.getAndroidVersionFeatures(mostRecentVersion.apiLevel)
                uiState.value = UiState.Success(featuresOfMostRecentVersion)
            } catch (exception: Exception) {
                uiState.value = UiState.Error("Network Request failed")
            }

        }
    }
}

