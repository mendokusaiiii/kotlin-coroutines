package br.com.mendo.kotlincoroutines.usecases.usecase2.rx

import android.os.Bundle
import androidx.activity.viewModels
import br.com.mendo.kotlincoroutines.base.BaseActivity
import br.com.mendo.kotlincoroutines.databinding.ActivityPerform2sequentialnetworkrequestsBinding
import br.com.mendo.kotlincoroutines.utils.fromHtml
import br.com.mendo.kotlincoroutines.utils.setGone
import br.com.mendo.kotlincoroutines.utils.setVisible
import br.com.mendo.kotlincoroutines.utils.toast

class SequentialNetworkRequestsRxActivity: BaseActivity() {

    private val binding: ActivityPerform2sequentialnetworkrequestsBinding by lazy {
        ActivityPerform2sequentialnetworkrequestsBinding.inflate(
            layoutInflater
        )
    }

    private val viewModel: SequentialNetworkRequestsRxViewModel by viewModels()

    override fun getToolbarTitle() = "Sequential Network Requests with Callbacks"

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

    private fun render(uiState: br.com.mendo.kotlincoroutines.usecases.usecase2.rx.UiState) {
        when (uiState) {
            is br.com.mendo.kotlincoroutines.usecases.usecase2.rx.UiState.Loading -> {
                onLoad()
            }
            is br.com.mendo.kotlincoroutines.usecases.usecase2.rx.UiState.Success -> {
                onSuccess(uiState)
            }
            is br.com.mendo.kotlincoroutines.usecases.usecase2.rx.UiState.Error -> {
                onError(uiState)
            }
        }
    }

    private fun onLoad()  {
        binding.progressBar.setVisible()
        binding.textViewResult.text = ""
    }

    private fun onSuccess(uiState: br.com.mendo.kotlincoroutines.usecases.usecase2.rx.UiState) = with(binding) {
        progressBar.setGone()
        textViewResult.text = fromHtml(
            "<b>Features of most recent Android Version \" ${uiState.versionFeatures.androidVersion.name} \"</b><br>" +
                    uiState.versionFeatures.features.joinToString(
                        prefix = "- ",
                        separator = "<br>- "
                    )
        )
    }

    private fun onError(uiState: br.com.mendo.kotlincoroutines.usecases.usecase2.rx.UiState) = with(binding) {
        progressBar.setGone()
        btnRequestsSequentially.isEnabled = true
        toast(uiState.message)
    }
}