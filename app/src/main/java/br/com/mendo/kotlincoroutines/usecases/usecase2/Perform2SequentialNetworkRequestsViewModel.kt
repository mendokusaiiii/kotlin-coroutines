package br.com.mendo.kotlincoroutines.usecases.usecase2

import br.com.mendo.kotlincoroutines.base.BaseViewModel
import br.com.mendo.kotlincoroutines.mock.MockApi
import br.com.mendo.kotlincoroutines.usecases.usecase2.callbacks.UiState

class Perform2SequentialNetworkRequestsViewModel ( private val mockApi: MockApi
) : BaseViewModel<UiState>() {

    fun perform2SequentialNetworkRequest() {

    }
}

