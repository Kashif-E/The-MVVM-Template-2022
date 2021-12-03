package com.example.nocontextviewmodel.utils

import android.view.View


fun View.visible(){
    this.visibility = View.VISIBLE
}

fun View.inVisible(){
    this.visibility = View.INVISIBLE
}