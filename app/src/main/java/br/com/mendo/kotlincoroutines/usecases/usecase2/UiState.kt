package br.com.mendo.kotlincoroutines.usecases.usecase2

import br.com.mendo.kotlincoroutines.mock.VersionFeatures

sealed class UiState {
    data object Loading : UiState()
    data class Success(
        val versionFeatures: VersionFeatures
    ) : UiState()

    data class Error(val message: String) : UiState()
}