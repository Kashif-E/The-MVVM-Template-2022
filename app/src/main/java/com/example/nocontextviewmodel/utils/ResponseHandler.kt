package com.example.nocontextviewmodel.utils


import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException


enum class ErrorCodes(val code: Int) {
    SocketTimeOut(-1),
    BadRequest(400),
    NotFound(404),
    Conflict(409),
    InternalServerError(500),
    Forbidden(403),
    NotAcceptable(406),
    ServiceUnavailable(503),
    UnAuthorized(401),
}


open class ResponseHandler {
    fun <T : Any> handleSuccess(data: T?): Resource<T> {
        return Resource.Success(data)
    }

    fun <T : Any> handleException(throwable: Throwable): Resource.CustomMessages {


        return when ( Exception(throwable)) {


            is  TimeoutException -> Resource.CustomMessages.Timeout
            is ConnectivityInterceptor.NoNetworkException -> Resource.CustomMessages.NoInternet
            is UnknownHostException -> Resource.CustomMessages.ServerBusy
            is ConnectException -> Resource.CustomMessages.NoInternet
            is SocketTimeoutException -> Resource.CustomMessages.SocketTimeOutException
            else -> Resource.CustomMessages.NoInternet
        }
    }



    fun <T : Any> handleException(statusCode: Int): Resource.CustomMessages {
        return getErrorType(statusCode)
    }
}

private fun getErrorType(code: Int): Resource.CustomMessages {
    return when (code) {
        ErrorCodes.SocketTimeOut.code -> Resource.CustomMessages.Timeout
        ErrorCodes.UnAuthorized.code -> Resource.CustomMessages.Unauthorized
        ErrorCodes.InternalServerError.code -> Resource.CustomMessages.InternalServerError

        ErrorCodes.BadRequest.code -> Resource.CustomMessages.BadRequest
        ErrorCodes.Conflict.code -> Resource.CustomMessages.Conflict
        ErrorCodes.InternalServerError.code -> Resource.CustomMessages.InternalServerError

        ErrorCodes.NotFound.code -> Resource.CustomMessages.NotFound
        ErrorCodes.NotAcceptable.code -> Resource.CustomMessages.NotAcceptable
        ErrorCodes.ServiceUnavailable.code -> Resource.CustomMessages.ServiceUnavailable
        ErrorCodes.Forbidden.code -> Resource.CustomMessages.Forbidden
        else -> Resource.CustomMessages.SomethingWentWrong
    }
}




