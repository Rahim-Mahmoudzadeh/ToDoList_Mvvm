package com.todolist.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.todolist.R

abstract class TodoListFragment : Fragment(), ToDoListView {
    override val rootView: ConstraintLayout
        get() = view as ConstraintLayout
    override val viewContext: Context?
        get() = context
}

interface ToDoListView {
    val rootView: ConstraintLayout?
    val viewContext: Context?

    fun showProgressBar(show: Boolean) {
        rootView?.let { rootView ->
            viewContext?.let { context ->
                var loadingView = rootView.findViewById<View>(R.id.loadingView)
                if (loadingView == null && show) {
                    loadingView =
                        LayoutInflater.from(context).inflate(R.layout.view_loading, rootView, false)
                    rootView.addView(loadingView)
                }
                loadingView?.visibility = if (show) View.VISIBLE else View.GONE
            }
        }
    }
}