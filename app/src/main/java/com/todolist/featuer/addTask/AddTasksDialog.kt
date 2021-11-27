package com.todolist.featuer.addTask

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.todolist.R
import com.todolist.base.App
import com.todolist.databinding.DialogAddBinding
import com.todolist.data.Task
import java.lang.IllegalStateException

class AddTasksDialog(addTask: AddTask) : DialogFragment() {
    var binding: DialogAddBinding? = null
    private val addTask = addTask
    var dialog: String? = null
    var editTask: Task? = null
    var isChecked: Boolean? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments.also { bundle ->
            bundle?.let {
                dialog = it.getString(App.EDIT_DIALOG).toString()
                editTask = it.getParcelable(App.EDIT_TASK)
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            binding = DialogAddBinding.inflate(LayoutInflater.from(it))
            val alertDialog = AlertDialog.Builder(it)
            alertDialog.setView(binding?.root)
            binding?.let { binding ->
                if (dialog == App.EDIT_DIALOG) {
                    editTask?.let {editTask->
                        binding.tvAddDialogTitle.text = resources.getString(R.string.edit_task)
                        binding.tieDialog.text = Editable.Factory.getInstance().newEditable(editTask.name)
                        binding.button.setOnClickListener {
                            editTask.name=binding.tieDialog.text.toString()
                            addTask.update(editTask)
                            dismiss()
                        }
                    }

                } else {
                    binding.button.setOnClickListener {
                        val newTask = Task(binding.tieDialog.text.toString(), false)
                        addTask.addTask(newTask)
                        dismiss()
                    }
                }
            }

            alertDialog.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    interface AddTask {
        fun addTask(task: Task)
        fun update(task: Task)
    }

    override fun onDestroy() {
        super.onDestroy()
        arguments = null
        editTask = null
        dialog = null
        isChecked = null
        binding = null
    }

}