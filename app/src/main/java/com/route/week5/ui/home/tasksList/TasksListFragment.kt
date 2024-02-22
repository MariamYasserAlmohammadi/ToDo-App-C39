package com.route.week5.ui.home.tasksList

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.route.week5.database.dao.MyDataBase
import com.route.week5.database.modal.Constants
import com.route.week5.database.modal.Task
import com.route.week5.databinding.FragmentTasksBinding
import com.route.week5.ui.MainActivity
import com.route.week5.ui.getDateOnly
import com.route.week5.ui.home.editTask.EditTaskActivity
import java.util.Calendar


// parcelize ->interface name
// serialization
// Android components -> activity ,service ,content provider, broadcast receiver
// need to share objects between 4 components
// take object and make decoding for this object
// :Serializable
// content provider akter comp need serialization

class TasksListFragment:Fragment (){
    lateinit var binding: FragmentTasksBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =FragmentTasksBinding.inflate(inflater,
            container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()

    }

    override fun onResume() {
        super.onResume()
        retrieveTasksList()
    }
    val adapter =TasksAdapter()
    val currentDate = Calendar.getInstance()
    lateinit var allTasks :MutableList<Task>
     fun retrieveTasksList() {

       allTasks = MyDataBase.getInstance()
          .getTasksDao().getTasksByDate( currentDate.getDateOnly()).toMutableList()

        this.adapter.changeData(allTasks)
    }



    private fun setUpViews() {
        binding.rvTasks.adapter = adapter


        binding.calendarView.setOnDateChangedListener { calendarView, date, selected ->
            if (selected) {
                // calendar start month from 0
                currentDate.set(date.year, date.month - 1, date.day)
                retrieveTasksList()
            }
        }
        // opening day
        binding.calendarView.selectedDate = CalendarDay.today()
        adapter.onDeleteClickListener =TasksAdapter.OnDeleteClickListener{
            task,position ->
            MyDataBase.getInstance()
                    .getTasksDao().deleteTask(task)
                allTasks!!.remove(task)
                retrieveTasksList()
        }
        adapter.onTaskItemClickListener = TasksAdapter.OnTaskItemClickListener { task, position ->
            openEditTaskActivity(task, position)
        }
    }
//            val fragment =EditTaskActivity()
//            // intent as bundle
//            val bundle =Bundle()
//            bundle.putParcelable(Constants.PASSED_TASK,task)
//            fragment.arguments = bundle
//
//            if (activity ==null) return@setOnTaskClickListener
//           //childFragmentManager
//            parentFragmentManager
//                .beginTransaction()
//                .replace(R.id.fragment_container,fragment)
//                .commit()

//        }
//
//        adapter.setOnTaskClickListener{
//            parentFragmentManager
//                .beginTransaction()
//                .replace(R.id.fragment_container , )
//                .commit()
//        }


    private fun openEditTaskActivity(task: Task,position :Int) {
        val intent = Intent(activity, EditTaskActivity::class.java)
        intent.putExtra(Constants.PASSED_TASK, task)
        intent.putExtra(Constants.PASSED_TASK,position)
        startActivity(intent)
      //  (activity as Activity?)!!.overridePendingTransition(0, 0)
    }
}
