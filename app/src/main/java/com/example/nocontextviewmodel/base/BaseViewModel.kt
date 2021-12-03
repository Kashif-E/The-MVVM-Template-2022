package com.example.nocontextviewmodel.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nocontextviewmodel.utils.Resource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

    var responseMessage: MutableSharedFlow<Resource.CustomMessages> = MutableSharedFlow()
    var showProgress : MutableSharedFlow<Boolean> = MutableSharedFlow()

    protected fun onResponseComplete(message : Resource.CustomMessages){
        viewModelScope.launch {
            responseMessage.emit(message)
        }

    }

    protected fun showProgressBar(){
        viewModelScope.launch {
            showProgress.emit(true)
        }

    }
    protected fun hideProgressBar(){
        viewModelScope.launch {
            showProgress.emit(false)
        }

    }

}