package com.todolist.base

import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Fragment.showSnackbar(view:View,text:String){
    Snackbar.make(view,text,Snackbar.LENGTH_SHORT).show()
}