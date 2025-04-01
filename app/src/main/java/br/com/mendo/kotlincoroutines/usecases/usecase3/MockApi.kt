package br.com.mendo.kotlincoroutines.usecases.usecase3

import br.com.mendo.kotlincoroutines.mock.createMockApi
import br.com.mendo.kotlincoroutines.mock.mockVersionFeaturesAndroid10
import br.com.mendo.kotlincoroutines.mock.mockVersionFeaturesOreo
import br.com.mendo.kotlincoroutines.mock.mockVersionFeaturesPie
import br.com.mendo.kotlincoroutines.utils.MockNetworkInterceptor
import com.google.gson.Gson

fun mockApi() = createMockApi(
    MockNetworkInterceptor()
        .mock(
            "http://localhost/android-version-features/27",
            { Gson().toJson(mockVersionFeaturesOreo) },
            200,
            1000
        )
        .mock(
            "http://localhost/android-version-features/28",
            { Gson().toJson(mockVersionFeaturesPie) },
            200,
            1000
        )
        .mock(
            "http://localhost/android-version-features/29",
            { Gson().toJson(mockVersionFeaturesAndroid10) },
            200,
            1000
        )
)