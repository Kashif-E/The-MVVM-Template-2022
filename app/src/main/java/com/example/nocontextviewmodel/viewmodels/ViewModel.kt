package com.example.nocontextviewmodel.viewmodels

import androidx.lifecycle.viewModelScope
import com.example.nocontextviewmodel.base.BaseViewModel
import com.example.nocontextviewmodel.repository.UserRepository
import com.example.nocontextviewmodel.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ActivityViewModel @Inject constructor(private val userRepository: UserRepository) :
    BaseViewModel() {
    private val _dataStateFlow: MutableStateFlow<Resource<String>> =
        MutableStateFlow(Resource.Loading())
    val dataStateFlow = _dataStateFlow.asStateFlow()

    fun getSocketTimeoutError() {
        showProgressBar()
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.stimulateSocketTimeout().flowOn(Dispatchers.IO)
                .collectLatest { response ->
                    hideProgressBar()
                    when (response) {
                        is Resource.Success -> {
                            _dataStateFlow.emit(response)
                        }
                        else -> {
                            onResponseComplete(response.error)
                        }

                    }

                }

        }
    }


    fun getUnauthorizedError() {
        showProgressBar()
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.stimulateUnAuthorizedError().flowOn(Dispatchers.IO)
                .collectLatest { response ->
                    hideProgressBar()
                    when (response) {
                        is Resource.Success -> {
                            _dataStateFlow.emit(response)
                        }
                        else -> {
                            onResponseComplete(response.error)
                        }

                    }

                }

        }
    }

    fun getData() {
        showProgressBar()
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.stimulateSuccessfulNetworkCall().flowOn(Dispatchers.IO)
                .collectLatest { response ->
                    hideProgressBar()
                    when (response) {

                        is Resource.Success -> {
                            _dataStateFlow.emit(response)
                        }
                        else -> {
                            onResponseComplete(response.error)
                        }

                    }

                }

        }
    }
}