package com.route.week5.ui.home.editTask

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.route.week5.database.dao.MyDataBase
import com.route.week5.database.modal.Constants
import com.route.week5.database.modal.Task
import com.route.week5.databinding.ActivityTaskDetailsBinding
import com.route.week5.ui.formatDate
import com.route.week5.ui.formatTime
import com.route.week5.ui.getDateOnly
import com.route.week5.ui.getTimeOnly
import java.util.Calendar

class EditTaskActivity :AppCompatActivity() {
    lateinit var binding: ActivityTaskDetailsBinding
    private lateinit var taskObj:Task

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getPassedTask()
        bindTask(taskObj)
        setUpViews()
        binding.content.btnSaveTask.setOnClickListener {
            saveTaskBtn()
        }

    }
    override fun onResume() {
        super.onResume()

    }
    fun getPassedTask() {
        taskObj = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(Constants.PASSED_TASK, Task::class.java)!!
        }
        else { intent.getParcelableExtra<Task>(Constants.PASSED_TASK) as Task
        }

//        arguments?.let {
//            task = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//                requireArguments().getParcelable(Constants.PASSED_TASK ,Task::class.java) ?:Task()
//            } else {
//                requireArguments().getParcelable(Constants.PASSED_TASK ) ?:Task()
//            }
//
//        }

    }
    fun setUpViews(){
        binding.content.selectDateTil.setOnClickListener {
            showDatePicker()
        }
        binding.content.selectTimeTil.setOnClickListener {
            showTimePicker()
        }

    }
    val calendar = Calendar.getInstance()
    private fun showTimePicker() {
        val timePicker = TimePickerDialog(
            this,
            // Listener
            { dialog , hourOfDay, minute ->
                calendar.set(Calendar.HOUR_OF_DAY,hourOfDay)
                calendar.set(Calendar.MINUTE,minute)
                binding.content.selectTimeTv.text =calendar.formatTime()
                binding.content.selectDateTil.error=null
                //"${hourOfDay}:${minute}"
            },
            // default time  when open picker
            // to read from calendar
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            false)
        timePicker.show()
    }

    private fun showDatePicker() {
        // no builder then it may have constructor
        val datePicker = DatePickerDialog(this)

//        datePicker.setOnDateSetListener(DatePickerDialog.OnDateSetListener {
//                view, year, month, dayOfMonth ->  })

        datePicker.setOnDateSetListener {
                dialog, year, month, dayOfMonth ->
            // set to change calendar field (year , day , month)
            //calender.set(field,value)
            calendar.set(Calendar.DAY_OF_YEAR,year)
            calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth)
            calendar.set(Calendar.MONTH,month)

            // format
            binding.content.selectDateTv.text = calendar.formatDate();
            binding.content.selectDateTil.error=null
            //"$year/${month+1}/$dayOfMonth"
        }
        datePicker.show()

    }
    private fun bindTask(task:Task){
        binding.content.title.setText( task.title.toString())
        binding.content.description.setText(task.content.toString())
        binding.content.selectDateTv.text = task.date.toString()
        binding.content.selectTimeTv.text = task.time.toString()
        binding.content.checkboxIsDone.isChecked =task.isDone

    }
    private fun saveTaskBtn(){
        MyDataBase
            .getInstance()
            .getTasksDao()
            .updateTask( Task(
                id= taskObj.id ,
                title = binding.title.text.toString(),
                content = binding.content.description.text.toString(),
                isDone = binding.content.checkboxIsDone.isChecked,
                date = calendar.getDateOnly(),
                time = calendar.getTimeOnly(),
            ))

    }


}