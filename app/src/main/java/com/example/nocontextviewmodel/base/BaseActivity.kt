package com.example.nocontextviewmodel.base

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.nocontextviewmodel.R
import com.example.nocontextviewmodel.utils.Resource
import com.example.nocontextviewmodel.utils.Resource.CustomMessages.*
import com.google.android.material.snackbar.Snackbar


open class BaseActivity : AppCompatActivity() {

    fun showSnackBar(
        view: View,
        error: Resource.CustomMessages? = null,

        ) {


        val text = when (error) {
            is SomethingWentWrong -> {
                getString(R.string.something_went_wrong)
            }
            is NetworkError -> {
                getString(R.string.no_internet_connection)
            }

            is NoInternet -> {
                getString(R.string.no_internet_connection)
            }
            is Timeout ->{
                getString(R.string.socket_time_out)
            }
            is Unauthorized -> {
                getString(R.string.unauthorized)
            }
            else -> {
                ""
            }
        }
        Snackbar.make(view, text, Snackbar.LENGTH_LONG)
            .setAction("CLOSE") {}
            .show()

    }


}
