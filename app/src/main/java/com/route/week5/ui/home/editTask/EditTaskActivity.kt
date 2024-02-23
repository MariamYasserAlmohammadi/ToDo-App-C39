package com.route.week5.ui.home.editTask

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.route.week5.database.dao.MyDataBase
import com.route.week5.database.modal.Constants
import com.route.week5.database.modal.Task
import com.route.week5.databinding.ActivityTaskDetailsBinding
import com.route.week5.ui.formatDate
import com.route.week5.ui.formatTime
import com.route.week5.ui.getDateOnly
import com.route.week5.ui.getTimeOnly
import com.route.week5.ui.home.HomeActivity
import com.route.week5.ui.showDialog
import java.util.Calendar
import java.util.Date

class EditTaskActivity :AppCompatActivity() {
    lateinit var ViewBinding: ActivityTaskDetailsBinding
    private  var taskObj:Task?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ViewBinding = ActivityTaskDetailsBinding.inflate(layoutInflater)
        setContentView(ViewBinding.root)
        getPassedTask()
        bindTask(taskObj)
        setUpViews()


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
        ViewBinding.content.selectDateTil.setOnClickListener {
            showDatePicker()
        }
        ViewBinding.content.selectTimeTil.setOnClickListener {
            showTimePicker()
        }
        ViewBinding.content.btnSaveTask.setOnClickListener {
            saveTaskBtn()
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
                ViewBinding.content.selectTimeTv.text =calendar.formatTime()
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
            ViewBinding.content.selectDateTv.text = calendar.formatDate()
            //"$year/${month+1}/$dayOfMonth"
        }
        datePicker.show()

    }

    private fun bindTask(task:Task?){
        ViewBinding.content.title.setText( task?.title.toString())
        ViewBinding.content.description.setText(task?.content.toString())
        ViewBinding.content.selectDateTv.text = task?.date?.getDateOnly()
        ViewBinding.content.selectTimeTv.text = task?.time?.getTimeOnly()
        ViewBinding.content.checkboxIsDone.isChecked =task?.isDone ?: false

    }
    private fun saveTaskBtn(){
        MyDataBase
            .getInstance()
            .getTasksDao()
            .updateTask( Task(
                id= taskObj?.id ,
                title = ViewBinding.content.title.text.toString(),
                content = ViewBinding.content.description.text.toString(),
                isDone = ViewBinding.content.checkboxIsDone.isChecked,
                date = calendar.getDateOnly(),
                time = calendar.getTimeOnly(),
            ))
        val intent = Intent(this , HomeActivity::class.java)
        startActivity(intent)



    }


}