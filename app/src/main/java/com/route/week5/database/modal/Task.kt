package com.route.week5.database.modal

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

// Class is table
@Entity()
data class Task (
    @PrimaryKey(autoGenerate = true)
    val id:Int?=null,
    @ColumnInfo(name = "name")
    var title:String?=null,
    @ColumnInfo
    var content :String?=null,
    @ColumnInfo
    var isDone:Boolean =false,
    @ColumnInfo
    var dateTime:Long?=null,
)