package br.com.mendo.kotlincoroutines.usecases.usecase1

import br.com.mendo.kotlincoroutines.mock.AndroidVersion

sealed class UiState {
    object Loading : UiState()
    data class Success(val recentVersions: List<AndroidVersion>) : UiState()
    data class Error(val message: String) : UiState()
}