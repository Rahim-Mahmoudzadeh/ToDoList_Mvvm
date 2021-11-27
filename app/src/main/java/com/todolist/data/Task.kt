package com.todolist.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Task(

    var name:String,
    var isChecked:Boolean?
):Parcelable
{
    @PrimaryKey(autoGenerate = true)
    var id:Int?=null
}

