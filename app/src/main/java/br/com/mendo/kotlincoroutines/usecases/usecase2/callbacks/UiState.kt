package br.com.mendo.kotlincoroutines.usecases.usecase2.callbacks

import br.com.mendo.kotlincoroutines.mock.VersionFeatures

sealed class UiState {
    object Loading : UiState()
    data class Success(
        val versionFeatures: VersionFeatures
    ) : UiState()

    data class Error(val message: String) : UiState()
}