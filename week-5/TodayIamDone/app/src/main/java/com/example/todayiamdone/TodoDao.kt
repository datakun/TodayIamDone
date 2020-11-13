package com.example.todayiamdone

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import org.intellij.lang.annotations.Flow

@Dao
interface TodoDao {

    @Query("SELECT * FROM todo")
    suspend fun getAll(): List<TodoItem>

    @Query("SELECT * FROM todo WHERE is_bookmark = 1")
    suspend fun getAllByBookmark(): List<TodoItem>

    @Query("SELECT * FROM todo WHERE is_done = 1")
    suspend fun getAllByDone(): List<TodoItem>

    @Insert(onConflict = REPLACE)
    suspend fun insert(todo: TodoItem)

    @Delete
    suspend fun delete(todo: TodoItem)

    @Query("DELETE from todo")
    suspend fun deleteAll()
}