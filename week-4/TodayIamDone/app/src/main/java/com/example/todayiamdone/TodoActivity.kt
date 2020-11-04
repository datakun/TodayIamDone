package com.example.todayiamdone

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_todo.*
import kotlinx.coroutines.runBlocking

class TodoActivity : AppCompatActivity() {
    lateinit var viewManager: LinearLayoutManager
    lateinit var viewAdapter: TodoAdapter
    lateinit var db: TodoDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo)

        viewManager = LinearLayoutManager(this)
        viewAdapter = TodoAdapter()
        db = Room.databaseBuilder(
            applicationContext,
            TodoDatabase::class.java, "todo"
        ).build()

        // 메뉴 클릭하면 메인 화면 상단의 제목이 바뀜
        groupMenu.setOnCheckedChangeListener { radioGroup, i ->
            if (i == R.id.btnBookmark) {
                textTitle.text = getString(R.string.bookmark)
            } else if (i == R.id.btnTodo) {
                textTitle.text = getString(R.string.todo)
            } else {
                textTitle.text = getString(R.string.done)
            }

            getTodos()
        }

        // 추가 버튼을 클릭하면 다이얼로그 띄움
        btnAdd.setOnClickListener {
            val builder = AlertDialog.Builder(this);

            val dialogView = layoutInflater.inflate(R.layout.layout_insert_todo, null)

            val editTodo = dialogView.findViewById<EditText>(R.id.editTodo)
            // TODO: checkAlarm 가져오기
            // TODO: datetimeAlarm 가져오기

            // TODO: checkAlarm 이벤트 연결 (datetimeAlarm 표시/숨기기)

            builder.setView(dialogView)
                .setPositiveButton(getString(R.string.add)) { dialogInterface, i ->
                    if (editTodo.text.toString().length == 0) {
                        Toast.makeText(
                            this@TodoActivity,
                            getString(R.string.msg_please_insert),
                            Toast.LENGTH_LONG
                        ).show()

                        return@setPositiveButton
                    }
                    val todo = TodoItem(0, editTodo.text.toString(), false, false)
                    insertTodo(todo)

                    getTodos()

                    // TODO: 알람 설정
                }
                .setNegativeButton(getString(R.string.cancel)) { dialogInterface, i ->
                }
                .show()
        }

        // RecyclerView 준비
        rvTodo.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        viewAdapter.setTodoChangeListener(object : TodoAdapter.TodoChangeListener {
            override fun onDoneChanged(todo: TodoItem) {
                insertTodo(todo)
            }

            override fun onBookmarkChanged(todo: TodoItem) {
                insertTodo(todo)
            }
        })

        viewAdapter.setTodoLongClickListener(object : TodoAdapter.TodoLongClickListener {
            override fun onLongClicked(todo: TodoItem) {
                val builder = AlertDialog.Builder(this@TodoActivity);

                builder.setMessage(getString(R.string.msg_want_delete))
                    .setPositiveButton(getString(R.string.delete)) { dialogInterface, i ->
                        deleteTodo(todo)

                        getTodos()
                    }
                    .setNegativeButton(getString(R.string.cancel)) { dialogInterface, i ->
                    }
                    .show()
            }
        })

        getTodos()
    }

    fun getTodos() {
        if (textTitle.text == getString(R.string.bookmark)) {
            getBookmarkTodo()
        } else if (textTitle.text == getString(R.string.todo)) {
            getAllTodo()
        } else {
            getDoneTodo()
        }
    }

    fun getAllTodo() = runBlocking {
        val todos = db.todoDao().getAll()
        viewAdapter.setItems(todos)
        runOnUiThread {
            viewAdapter.notifyDataSetChanged()
        }
    }

    fun getBookmarkTodo() = runBlocking {
        val todos = db.todoDao().getAllByBookmark()
        viewAdapter.setItems(todos)
        runOnUiThread {
            viewAdapter.notifyDataSetChanged()
        }
    }

    fun getDoneTodo() = runBlocking {
        val todos = db.todoDao().getAllByDone()
        viewAdapter.setItems(todos)
        runOnUiThread {
            viewAdapter.notifyDataSetChanged()
        }
    }

    fun insertTodo(todo: TodoItem) = runBlocking {
        db.todoDao().insert(todo)
    }

    fun deleteTodo(todo: TodoItem) = runBlocking {
        db.todoDao().delete(todo)
    }

    fun setNotification(time: Long, message: String) {
        // TODO: 알람 설정하기
        if (time > 0) {

        }
    }
}
