package com.route.week5.ui

import java.text.SimpleDateFormat
import java.util.Calendar

    fun Calendar.getDateOnly(): Long{
        val calendar = Calendar.getInstance()
        calendar.set(get(Calendar.YEAR),get(Calendar.MONTH)
            ,get(Calendar.DATE),0,0,0)
        calendar.set(Calendar.MILLISECOND ,0)
        return calendar.time.time
    }
    fun Calendar.getTimeOnly(): Long{
        val calendar= Calendar.getInstance()
        calendar.set(0,0,0,get(Calendar.HOUR_OF_DAY),get(Calendar.MINUTE),0)
        calendar.set(Calendar.MILLISECOND ,0)
        return calendar.time.time
    }
    // extension fun
    fun Calendar.formatTime():String{
        // this is pointer on object
        // a -> am 12hour  -24
        val format = SimpleDateFormat("hh:mm a")
        return format.format(time)
    }
    fun Calendar.formatDate():String{
        val format = SimpleDateFormat("dd/MM/yyyy")
        return format.format(time)
    }