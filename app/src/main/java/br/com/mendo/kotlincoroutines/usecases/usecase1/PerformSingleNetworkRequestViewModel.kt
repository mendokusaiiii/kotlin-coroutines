package br.com.mendo.kotlincoroutines.usecases.usecase1

import androidx.lifecycle.viewModelScope
import br.com.mendo.kotlincoroutines.base.BaseViewModel
import br.com.mendo.kotlincoroutines.mock.MockApi
import kotlinx.coroutines.launch

class PerformSingleNetworkRequestViewModel(
    private val mockApi: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun performSingleNetworkRequest() {
        uiState.value = UiState.Loading

        viewModelScope.launch {
            val recentAndroidVersions = mockApi.getRecentAndroidVersions()
            uiState.value = UiState.Success(recentAndroidVersions)
        }
    }
}