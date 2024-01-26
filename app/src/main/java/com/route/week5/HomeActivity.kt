package com.route.week5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.route.week5.database.dao.MyDataBase

// Room persistence library provide an abstraction layer over SQL
// 1.Compile time verification of SQL queries
// 2.KAPT Kotlin Annotations Processor , @...
// 3.support database migration paths
// DAO -> data access object

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MyDataBase.getInstance().getTasksDao()
            .getAllTasks()
    }
}