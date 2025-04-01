package br.com.mendo.kotlincoroutines.usecases.usecase3

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import br.com.mendo.kotlincoroutines.R
import br.com.mendo.kotlincoroutines.base.BaseActivity
import br.com.mendo.kotlincoroutines.databinding.ActivityPerformnetworkrequestsconcurrentlyBinding
import br.com.mendo.kotlincoroutines.utils.fromHtml
import br.com.mendo.kotlincoroutines.utils.setGone
import br.com.mendo.kotlincoroutines.utils.setVisible
import br.com.mendo.kotlincoroutines.utils.toast
import kotlin.getValue

class PerformNetworkRequestsConcurrentlyActivity : BaseActivity() {

    private val binding by lazy {
        ActivityPerformnetworkrequestsconcurrentlyBinding.inflate(
            layoutInflater
        )
    }

    private val viewModel: PerformNetworkRequestsConcurrentlyViewModel by viewModels()

    override fun getToolbarTitle() = "Use Case 3: Perform Network Requests Concurrently"

    private var operationStartTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel.uiState().observe(this){ uiState ->
            if (uiState != null) {
                render(uiState)
            }
        }
        binding.btnRequestsSequentially.setOnClickListener {
            viewModel.performNetworkRequestsSequentially()
        }
        binding.btnRequestsConcurrently.setOnClickListener {
            viewModel.performNetworkRequestsConcurrently()
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
        operationStartTime = System.currentTimeMillis()
        binding.progressBar.setVisible()
        binding.textViewDuration.text = ""
        binding.textViewResult.text = ""
        disableButtons()
    }

    private fun onSuccess(uiState: UiState.Success)  {
        enableButtons()
        binding.progressBar.setGone()
        val duration = System.currentTimeMillis() - operationStartTime
        binding.textViewDuration.text = getString(
            R.string.duration,
            duration
        )

        val versionFeatures = uiState.versionFeatures
        val versionFeaturesString = versionFeatures.joinToString(separator = "<br><br>") {
            "<b>New Features of ${it.androidVersion.name} </b> <br> ${it.features.joinToString(
                prefix = "- ",
                separator = "<br>- "
            )}"
        }

        binding.textViewResult.text = fromHtml(versionFeaturesString)
    }

    private fun onError(uiState: UiState.Error) {
        binding.progressBar.setGone()
        binding.textViewDuration.setGone()
        toast(uiState.message)
        enableButtons()
    }

    private fun enableButtons() {
        binding.btnRequestsSequentially.isEnabled = true
        binding.btnRequestsConcurrently.isEnabled = true
    }

    private fun disableButtons() {
        binding.btnRequestsSequentially.isEnabled = false
        binding.btnRequestsConcurrently.isEnabled = false
    }
}