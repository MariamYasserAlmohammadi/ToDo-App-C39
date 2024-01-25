package com.route.week5.database.dao

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.route.week5.database.modal.Task

// views , create virtual table take its data from more than one place
// autoMigrations , how to migrate tables
// exportSchema = true , take schema of database SQL  put it in file and then put it in project to make project more quicker
// version =1 , every edit in schema (structure of database) should upgrade version
@Database(entities = [Task::class], version = 1, exportSchema = true)
abstract class MyDataBase : RoomDatabase() {
    // return Dao
    abstract fun getTasksDao(): TaskDao

    // creation of object is time consuming
    companion object {   // static
        // only one object
        private const val DATABASE_NAME = "tasks_database"
        private var database: MyDataBase? = null

        // App context not Activity context because object is alive will lifecycle of App
        fun init(app: Application) {
            if (database == null) {
                //create database
                // design pattern call context
                database = Room.databaseBuilder(
                    app.applicationContext,
                    //database class name
                    MyDataBase::class.java,
                    DATABASE_NAME,
                )

                    .addMigrations()
                    // data form can change(change in schema)
                    // developer should provide migration (add table -> how to add this table)
                    .fallbackToDestructiveMigration()
                    // UIThread (user interface thread)
                    // ANR App Not Responding
                    // make operations on data base in back ground thread
                    .allowMainThreadQueries()
                    // build object
                    // it(configurations) may exist or it may not exist
                    // design pattern made for create object with diff configuration
                    .build()
            }
        }
        fun getInstance(): MyDataBase {
            return database!!

        }
    }

}