package com.example.todayiamdone

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface TodoDao {
    @Query("SELECT * FROM todo")
    suspend fun getAll(): List<TodoItem>

    @Query("SELECT * FROM todo WHERE is_bookmark = :isBookmark")
    suspend fun getAllByBookmark(isBookmark: Boolean): List<TodoItem>

    @Query("SELECT * FROM todo WHERE is_done = :isDone")
    suspend fun getAllByDone(isDone: Boolean): List<TodoItem>

    @Insert(onConflict = REPLACE)
    suspend fun insert(vararg todo: TodoItem)

    @Delete
    suspend fun delete(todo: TodoItem)

    @Query("DELETE from todo")
    suspend fun deleteAll()
}