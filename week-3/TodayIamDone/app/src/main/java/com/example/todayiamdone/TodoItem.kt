package com.example.todayiamdone

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo")
class TodoItem(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "is_done") var isDone: Boolean,
    @ColumnInfo(name = "is_bookmark")var isBookmark: Boolean
)