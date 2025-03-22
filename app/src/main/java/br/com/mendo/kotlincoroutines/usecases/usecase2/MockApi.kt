package br.com.mendo.kotlincoroutines.usecases.usecase2

import br.com.mendo.kotlincoroutines.mock.mockAndroidVersions
import br.com.mendo.kotlincoroutines.mock.mockVersionFeaturesAndroid10
import br.com.mendo.kotlincoroutines.usecases.usecase2.callbacks.createMockApi
import br.com.mendo.kotlincoroutines.utils.MockNetworkInterceptor
import com.google.gson.Gson

fun mockApi() = createMockApi(
    MockNetworkInterceptor()
        .mock(
            "http://localhost/recent-android-versions",
            { Gson().toJson(mockAndroidVersions) },
            200,
            1500
        )
        .mock(
            "http://localhost/android-version-features/29",
            { Gson().toJson(mockVersionFeaturesAndroid10) },
            200,
            1500
        )
)