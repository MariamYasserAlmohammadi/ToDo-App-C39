package com.route.week5.database.modal


import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


// Class is table
@Parcelize
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
    var date:Long?=null,
    @ColumnInfo
    var time:Long?=null,
): Parcelable