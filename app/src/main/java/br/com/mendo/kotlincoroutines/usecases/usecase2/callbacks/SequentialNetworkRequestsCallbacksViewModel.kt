package br.com.mendo.kotlincoroutines.usecases.usecase2.callbacks

import br.com.mendo.kotlincoroutines.base.BaseViewModel
import br.com.mendo.kotlincoroutines.mock.AndroidVersion
import br.com.mendo.kotlincoroutines.mock.VersionFeatures
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SequentialNetworkRequestsCallbacksViewModel(
    private val mockApi: CallbackMockApi = mockApi()
): BaseViewModel<UiState>() {

    private var getAndroidVersionsCall: Call<List<AndroidVersion>>? = null
    private var getAndroidFeaturesCall: Call<VersionFeatures>? = null

    fun perform2SequentialNetworkRequest() {
        uiState.value = UiState.Loading

        getAndroidVersionsCall = mockApi.getRecentAndroidVersions()
        getAndroidVersionsCall?.enqueue(object: Callback<List<AndroidVersion>>{
            override fun onResponse(
                call: Call<List<AndroidVersion>>,
                response: Response<List<AndroidVersion>>
            ) {
                if (response.isSuccessful) {
                    val mostRecentVersion = response.body()?.last()
                    getAndroidFeaturesCall = mockApi.getAndroidVersionFeatures(mostRecentVersion?.apiLevel ?: 0)
                    getAndroidFeaturesCall?.enqueue(object : Callback<VersionFeatures> {
                        override fun onResponse(
                            call: Call<VersionFeatures>,
                            response: Response<VersionFeatures>
                        ) {
                            if (response.isSuccessful) {
                                val featureOfMostRecentVersion = response.body()
                                uiState.value = featureOfMostRecentVersion?.let { UiState.Success(it) }

                            } else {
                                uiState.value = UiState.Error("Network request failed with code ${response.code()}")
                            }
                        }

                        override fun onFailure(call: Call<VersionFeatures>, e: Throwable) {
                            uiState.value = UiState.Error("Something unexpected happened")
                        }

                    })
                } else {
                    uiState.value = UiState.Error("Network request failed with code ${response.code()}")
                }

            }

            override fun onFailure(call: Call<List<AndroidVersion>>, e: Throwable) {
                uiState.value = UiState.Error("Error loading recent Android versions")
            }

        })
    }

    override fun onCleared() {
        super.onCleared()

        getAndroidFeaturesCall?.cancel()
        getAndroidVersionsCall?.cancel()
    }
}