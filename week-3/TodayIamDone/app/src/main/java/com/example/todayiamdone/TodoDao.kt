package com.example.todayiamdone

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface TodoDao {
    @Query("SELECT * FROM todo")
    fun getAll(): List<TodoItem>

    @Query("SELECT * FROM todo WHERE is_bookmark = :isBookmark")
    fun getAllByBookmark(isBookmark: Boolean): List<TodoItem>

    @Query("SELECT * FROM todo WHERE is_done = :isDone")
    fun getAllByDone(isDone: Boolean): List<TodoItem>

    @Insert(onConflict = REPLACE)
    fun insert(vararg todo: TodoItem)

    @Delete
    fun delete(todo: TodoItem)

    @Query("DELETE from todo")
    fun deleteAll()
}