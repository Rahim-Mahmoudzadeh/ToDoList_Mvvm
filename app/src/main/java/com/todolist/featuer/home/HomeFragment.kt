package com.todolist.featuer.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.todolist.base.App
import com.todolist.databinding.HomeFragmentBinding
import com.todolist.featuer.addTask.AddTasksDialog
import com.todolist.data.Task
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(), AddTasksDialog.AddTask, HomeAdapter.TaskEventListener {
    val viewModelHome: ViewModelHome by viewModel()
    lateinit var binding: HomeFragmentBinding
    lateinit var homeAdapter: HomeAdapter
    var alertDialog: AddTasksDialog? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeAdapter = HomeAdapter(this)
        alertDialog = AddTasksDialog(this)

        showDialog()
        getTasks()
        binding.recyclerView.layoutManager =
            LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        binding.recyclerView.adapter = homeAdapter

        binding.ivHomeDeleteAllTasks.setOnClickListener {
            deleteAllTask()
        }
        binding.etHomeSearch.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty())
                {
                    getTasks()
                }
                else{
                    showSearchName(s.toString())
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

    }

    private fun showDialog() {
        binding.floatingActionButton.setOnClickListener {
            alertDialog?.show(parentFragmentManager, null)
        }
    }

    private fun getTasks() {
        viewModelHome.getTask.observe(viewLifecycleOwner) { tasks ->
            tasks?.let {
                homeAdapter.tasks = it as ArrayList
                chekEmpty()
            }
        }
    }

    private fun deleteAllTask() {
        viewModelHome.deleteAllTasks()
    }

    private fun chekEmpty() {
        viewModelHome.getTask.observe(viewLifecycleOwner) {
            it?.let { list ->
                if (list.isEmpty()) {
                    binding.rlHomeEmpty.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                } else {
                    binding.recyclerView.visibility = View.VISIBLE
                    binding.rlHomeEmpty.visibility = View.GONE
                }
            }
        }
    }

    override fun addTask(task: Task) {
        viewModelHome.addTask(task)
    }

    override fun update(task: Task) {
        viewModelHome.update(task)
    }

    override fun onClick(task: Task) {
        viewModelHome.update(task)
    }

    override fun deleteTask(task: Task) {
        viewModelHome.deleteTask(task)
    }

    override fun onLongClick(task: Task) {
        alertDialog?.let { alertDialog->
            alertDialog.arguments= Bundle().apply {
                putString(App.EDIT_DIALOG, App.EDIT_DIALOG)
                putParcelable(App.EDIT_TASK,task)
            }
            alertDialog.show(parentFragmentManager,null)
        }
    }

    fun showSearchName(name:String)
    {
        viewModelHome.searchTaskName(name)
        viewModelHome.searchTask.observe(viewLifecycleOwner){
            it?.let { tasks->
                homeAdapter.tasks=tasks as ArrayList<Task>
            }
        }
    }
}