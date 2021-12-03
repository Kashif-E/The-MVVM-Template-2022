package com.example.nocontextviewmodel.utils


import android.util.Log
import org.json.JSONException
import org.json.JSONObject

import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException




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

    fun <T : Any> handleException(e: Throwable): Resource.CustomMessages {
        return when (e) {

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

private fun getErrorType(message: String): String {


    return if (isJSONValid(message)) {
        val msg = getMessageJSON(message)
        if (msg != "") {
            msg
        } else {
            message.substringAfter(":").replace("}", "")

        }

    } else {
        message.substringAfter(":").replace("}", "")

    }


}

fun isJSONValid(message: String): Boolean {
    try {
        val json = JSONObject(message)
        json.getString("message")
    } catch (ex: JSONException) {
        return false
    }
    return true
}

fun getMessageJSON(message: String): String {
    val messageString: String
    return try {
        val json = JSONObject(message)
        messageString = json.getString("message")
        if (isJSONValid(messageString)) {
            getMessageJSON(messageString)
        } else {
            messageString
        }

    } catch (ex: JSONException) {
        message
    }

}
