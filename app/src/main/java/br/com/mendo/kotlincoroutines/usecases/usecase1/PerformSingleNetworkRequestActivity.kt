package br.com.mendo.kotlincoroutines.usecases.usecase1

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import br.com.mendo.kotlincoroutines.base.BaseActivity
import br.com.mendo.kotlincoroutines.databinding.ActivityPerformsinglenetworkrequestBinding
import br.com.mendo.kotlincoroutines.utils.fromHtml
import br.com.mendo.kotlincoroutines.utils.setGone
import br.com.mendo.kotlincoroutines.utils.setVisible
import br.com.mendo.kotlincoroutines.utils.toast
import kotlin.getValue

class PerformSingleNetworkRequestActivity  : BaseActivity() {

    override fun getToolbarTitle() = "Perform Single Network Request"

    private val binding by lazy { ActivityPerformsinglenetworkrequestBinding.inflate(layoutInflater) }

    private val viewModel: PerformSingleNetworkRequestViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel.uiState().observe(this) { uiState ->
            if (uiState != null) {
                render(uiState)
            }
        })
        binding.btnPerformSingleNetworkRequest.setOnClickListener {
            viewModel.performSingleNetworkRequest()
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
        binding.btnPerformSingleNetworkRequest.isEnabled = false
    }

    private fun onSuccess(uiState: UiState.Success)  {
        binding.progressBar.setGone()
        binding.btnPerformSingleNetworkRequest.isEnabled = true
        val readableVersions = uiState.recentVersions.map { "API ${it.apiLevel}: ${it.name}" }
        binding.textViewResult.text = fromHtml(
            "<b>Recent Android Versions</b><br>${readableVersions.joinToString(separator = "<br>")}"
        )
    }

    private fun onError(uiState: UiState.Error)  {
        binding.progressBar.setGone()
        binding.btnPerformSingleNetworkRequest.isEnabled = true
        toast(uiState.message)
    }
}