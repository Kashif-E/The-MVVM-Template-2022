package com.example.nocontextviewmodel.utils


/**
 * A generic class that holds a value with its loading status.
 * @param <T>
</T> */
sealed  class Resource<T>(
    val data: T? = null,
    val error : CustomMessages= CustomMessages.SomethingWentWrong("Something went wrong.")
) {
    class Success<T>(data: T?) : Resource<T>(data)
    class Loading<T> : Resource<T>()
    class Error<T>(customMessages: CustomMessages):Resource<T>(error =  customMessages)
    sealed class CustomMessages(val message: String="")  {
        object BusinessDetailsUpdatedSuccessfully: CustomMessages()
        object EmptyOtp: CustomMessages()
        object InvalidOtp : CustomMessages()
        object NetworkError : CustomMessages()
        object InvalidInput : CustomMessages()
        object InvalidPhoneNumber : CustomMessages()
        object Timeout : CustomMessages()
        object ServerBusy : CustomMessages()
        object PhoneNumberLengthError : CustomMessages()
        object InputEmptyError : CustomMessages()
        object HttpException : CustomMessages()
        object SocketTimeOutException : CustomMessages()
        object NoInternet : CustomMessages()
        object Unauthorized : CustomMessages()
        object InternalServerError : CustomMessages()
        object BadRequest : CustomMessages()
        object Conflict : CustomMessages()
        object NotFound : CustomMessages()
        object NotAcceptable : CustomMessages()
        object ServiceUnavailable : CustomMessages()
        object Forbidden : CustomMessages()
        data class SomethingWentWrong(val customMessage :String) : CustomMessages(customMessage)


    }

}
