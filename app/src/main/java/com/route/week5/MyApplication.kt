package com.route.week5

import android.app.Application
import com.route.week5.database.dao.MyDataBase

// Android has class called Application
// his object is made before splash screen
// the first class initialized in the hall App is Application class
class MyApplication :Application(){
    override fun onCreate() {
        super.onCreate()
        // initialize
        MyDataBase.init(this)
    }

}