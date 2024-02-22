package com.route.week5.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.route.week5.database.modal.Task
@Dao // data access option
interface TaskDao{
    @Insert
    fun insertTask(task: Task)
    @Update
    fun updateTask(task: Task)
    @Delete
    fun deleteTask(task: Task)
    @Query("select * from task")
    fun getAllTasks():List<Task>
    // auto complete in hard coded string
    @Query("select * from task where date = :date order by time ASC ")
    fun getTasksByDate(date:Long):List<Task>

}