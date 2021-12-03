package com.example.nocontextviewmodel

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.nocontextviewmodel.base.BaseActivity
import com.example.nocontextviewmodel.databinding.ActivityMainBinding
import com.example.nocontextviewmodel.utils.inVisible
import com.example.nocontextviewmodel.utils.visible
import com.example.nocontextviewmodel.viewmodels.ActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: ActivityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        observeMutables()
    }

    private fun observeMutables() {
        lifecycleScope.launch {
            launch {
                viewModel.responseMessage.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED).collect {message ->
                    showSnackBar(binding.root, message)
                }
            }
            launch {
                viewModel.showProgress.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED).collect {showProgressBar->
                    if (showProgressBar){
                        binding.progressBar.visible()
                    }else{
                        binding.progressBar.inVisible()
                    }
                }
            }
        }
    }
}