package com.route.week5.ui.home.adddTask

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.route.week5.database.dao.MyDataBase
import com.route.week5.database.modal.Task
import com.route.week5.databinding.FragmentAddTaskBinding
import com.route.week5.ui.formatDate
import com.route.week5.ui.formatTime
import com.route.week5.ui.getDateOnly
import com.route.week5.ui.getTimeOnly
import com.route.week5.ui.showDialog
import java.text.SimpleDateFormat
import java.util.Calendar

class AddTaskBottomSheet :BottomSheetDialogFragment (){
    lateinit var binding: FragmentAddTaskBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddTaskBinding.inflate(
            inflater ,
            container,
            false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
        binding.addTaskBtn.setOnClickListener {
            addTask()
        }
    }

    private fun setUpViews() {
        binding.selectDateTil.setOnClickListener{
            showDatePicker()

        }
        binding.selectTimeTil.setOnClickListener {
            showTimePicker()

        }

    }
    // to deal with date and time
    // object from calender have ( date ) day and month and year now
    val calendar = Calendar.getInstance()
    private fun showTimePicker() {
        val timePicker =TimePickerDialog(
            requireContext(),
            // Listener
            { dialog , hourOfDay, minute ->
                calendar.set(Calendar.HOUR_OF_DAY,hourOfDay)
                calendar.set(Calendar.MINUTE,minute)
                binding.selectTimeTv.text =calendar.formatTime()
                binding.selectDateTil.error=null
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
        val datePicker = DatePickerDialog(requireContext())

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
            binding.selectDateTv.text = calendar.formatDate();
            binding.selectDateTil.error=null
                //"$year/${month+1}/$dayOfMonth"
        }
        datePicker.show()

    }

var  onTaskAddedListener :OnTaskAddedListener?=null
    fun interface OnTaskAddedListener{
        fun onTaskAdded()
    }


    private fun isValidTaskInput(): Boolean {
        var isValid =true

        val title = binding.title.text.toString()
        val description =binding.description.text.toString()

        if (title.isBlank()){  // blank first trim then check
            binding.titleTil.error ="Please enter Task Title"
            // not valid
            isValid = false
        }
        else{
            binding.titleTil.error=null
            isValid =true
        }
        if (description.isBlank()){
            binding.descriptionTil.error ="Please enter Task description"
            // not valid
            isValid = false

        }
        else{
            binding.descriptionTil.error=null
            isValid =true
        }
        if (binding.selectTimeTv.text.isBlank()){
            binding.selectTimeTil.error="Please select time"
            isValid=false
        }
        if (binding.selectDateTv.text.isBlank()){
            binding.selectDateTil.error="Please select time"
            isValid=false
        }
        return isValid

    }

    private fun addTask() {
        // 1.validate
        if (!isValidTaskInput()){
            return
        }
        MyDataBase.getInstance()
            .getTasksDao().insertTask(
                Task(
                    title = binding.title.text.toString(),
                    content = binding.description.text.toString(),
                    date =calendar.getDateOnly() ,
                    time =calendar.getTimeOnly() )
            )
        showDialog("Task Added successfully",posActionName = "ok",
            posActionCallBack = {
                // disappear bottom sheet
                dismiss()
                onTaskAddedListener?.onTaskAdded()
            },
            isCancelable = false)


    }


}
