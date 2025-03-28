package br.com.mendo.kotlincoroutines.usecases.usecase1

import br.com.mendo.kotlincoroutines.base.BaseViewModel
import br.com.mendo.kotlincoroutines.mock.MockApi

class PerformSingleNetworkRequestViewModel(
    private val mockApi: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun performSingleNetworkRequest() {

        uiState.value = UiState.Loading
    }
}