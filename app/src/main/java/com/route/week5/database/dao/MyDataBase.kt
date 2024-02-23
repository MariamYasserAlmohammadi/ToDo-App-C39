package com.route.week5.database.dao

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.route.week5.database.modal.Task
// 1.views , create virtual table take its data from more than one place
// 2.autoMigrations , how to migrate tables
// 3.exportSchema = true , take schema of database SQL  put it in file and then put it in project
// to make project more quicker  (to quick generation of data base)
// 4.version =1 , every edit in schema (structure of database) should upgrade version
@Database(entities = [Task::class], version = 2, exportSchema = true)
abstract class MyDataBase : RoomDatabase() {
    // return Dao
    abstract fun getTasksDao(): TaskDao

    // creation of object is time consuming
    companion object { //  static اي حاجه بعرفها هنا
        private const val DATABASE_NAME = "tasks_database"
        private var database: MyDataBase? = null
        // fun create database
        fun init(context:Context){

            if (database == null) {
                // create object from database
                database = Room.databaseBuilder(
                    context.applicationContext,
                    // class name of dataBase
                    MyDataBase::class.java,
                    // name of dataBase
                    DATABASE_NAME
                )
                    // in case you have migrations add migrations
                    // if not delete old database and add new one
                    // (auto migrations , manual migrations)
                    .addMigrations()
                    // data form can change(change in schema)
                    // developer should provide migration (add table -> how to add this table)
                    .fallbackToDestructiveMigration()
                    // database is IO operations
                    // UIThread (user interface thread)
                    // ANR App Not Responding
                    // make operations on database in back ground thread
                    .allowMainThreadQueries()
                    // builder -> build object with diff configurations
                    // it(configurations) may exist or it may not exist
                    // design pattern made for create object with diff configuration
                    .build()

            }
        }
       // not use getInstance before using init inMYApplication class
        fun getInstance(): MyDataBase {
            return database!!
        }

    }

}