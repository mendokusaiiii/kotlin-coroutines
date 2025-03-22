package br.com.mendo.kotlincoroutines.usecases.usecase2

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import br.com.mendo.kotlincoroutines.base.BaseActivity
import br.com.mendo.kotlincoroutines.databinding.ActivityPerform2sequentialnetworkrequestsBinding
import br.com.mendo.kotlincoroutines.usecases.usecase2.callbacks.UiState
import br.com.mendo.kotlincoroutines.utils.fromHtml
import br.com.mendo.kotlincoroutines.utils.setGone
import br.com.mendo.kotlincoroutines.utils.setVisible
import br.com.mendo.kotlincoroutines.utils.toast

class Perform2SequentialNetworkRequestsActivity  : BaseActivity() {

    private val binding: ActivityPerform2sequentialnetworkrequestsBinding by lazy {
        ActivityPerform2sequentialnetworkrequestsBinding.inflate(
            layoutInflater
        )
    }

    private val viewModel: Perform2SequentialNetworkRequestsViewModel by viewModels()

    override fun getToolbarTitle() = "Sequential Network Requests"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel.uiState().observe(this) { uiState ->
            if (uiState != null) {
                render(uiState)
            }
        }
        binding.btnRequestsSequentially.setOnClickListener {
            viewModel.perform2SequentialNetworkRequest()
        }
    }

    private fun render(uiState: UiState) {
        when (uiState) {
            is UiState.Loading -> {
                onLoad()
            }
            is UiState.Success -> {
                onSuccess(uiState)
            }
            is UiState.Error -> {
                onError(uiState)
            }
        }
    }

    private fun onLoad()  {
        binding.progressBar.setVisible()
        binding.textViewResult.text = ""
    }

    private fun onSuccess(uiState: UiState.Success)  {
        binding.progressBar.setGone()
        binding.textViewResult.text = fromHtml(
            "<b>Features of most recent Android Version \" ${uiState.versionFeatures.androidVersion.name} \"</b><br>" +
                    uiState.versionFeatures.features.joinToString(
                        prefix = "- ",
                        separator = "<br>- "
                    )
        )
    }

    private fun onError(uiState: UiState.Error) {
        binding.progressBar.setGone()
        binding.btnRequestsSequentially.isEnabled = true
        toast(uiState.message)
    }
}