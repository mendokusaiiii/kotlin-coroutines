package br.com.mendo.kotlincoroutines.usecases.usecase1

import br.com.mendo.kotlincoroutines.mock.createMockApi
import br.com.mendo.kotlincoroutines.mock.mockAndroidVersions
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
)