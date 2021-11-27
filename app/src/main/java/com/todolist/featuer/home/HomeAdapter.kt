package com.todolist.featuer.home

import android.annotation.SuppressLint
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.todolist.databinding.TasksRvBinding
import com.todolist.data.Task

class HomeAdapter(taskEventListener: TaskEventListener) :
    RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    val eventListener = taskEventListener
    var tasks = ArrayList<Task>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = TasksRvBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        holder.bind(tasks[position])
    }
        override fun getItemCount(): Int = tasks.size
        inner class ViewHolder(itemView: TasksRvBinding) : RecyclerView.ViewHolder(itemView.root) {
            val item = itemView
            fun bind(task: Task) {
                
                item.checkBox.text = task.name
                item.checkBox.isChecked = task.isChecked!!

                //task isChecked
                item.checkBox.setOnClickListener {
                    task.isChecked=item.checkBox.isChecked
                    eventListener.onClick(task)
                }

                item.ivRvDelete.setOnClickListener {
                    eventListener.deleteTask(task)
                }
                // task update
                itemView.setOnLongClickListener {
                    eventListener.onLongClick(task)
                    false
                }
            }

        }
        interface TaskEventListener {
            fun onClick(task: Task)
            fun deleteTask(task: Task)
            fun onLongClick(task : Task)
        }
    }
