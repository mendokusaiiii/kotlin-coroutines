package br.com.mendo.kotlincoroutines.usecases.usecase3

import br.com.mendo.kotlincoroutines.mock.VersionFeatures

sealed class UiState {
    object Loading : UiState()
    data class Success(
        val versionFeatures: List<VersionFeatures>
    ) : UiState()

    data class Error(val message: String) : UiState()
}