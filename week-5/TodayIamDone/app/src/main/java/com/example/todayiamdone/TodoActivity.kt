package com.example.todayiamdone

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker
import kotlinx.android.synthetic.main.activity_todo.*
import kotlinx.coroutines.runBlocking

class TodoActivity : AppCompatActivity() {
    lateinit var viewManager: LinearLayoutManager
    lateinit var viewAdapter: TodoAdapter
    lateinit var db: TodoDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: 환경설정 값 불러오기
        // TODO: 테마 적용하기

        setContentView(R.layout.activity_todo)

        // TODO: 테마 값에 따라 Dark Mode 스위치 On/Off 하기

        // TODO: Dark Mode 스위치 이벤트 구현
        // TODO: - 스위치 체크 여부에 따라 테마 아이디 가져오기
        // TODO: - 환경설정 값 저장하기

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
            // TODO: AlertDialog 에 테마 적용하기
            val builder = AlertDialog.Builder(this);

            val dialogView = layoutInflater.inflate(R.layout.layout_insert_todo, null)

            val editTodo = dialogView.findViewById<EditText>(R.id.editTodo)
            val checkAlarm = dialogView.findViewById<CheckBox>(R.id.checkAlarm)
            val datetimeAlarm = dialogView.findViewById<SingleDateAndTimePicker>(R.id.datetimeAlarm)

            checkAlarm.setOnCheckedChangeListener { compoundButton, b ->
                if (b == true) {
                    datetimeAlarm.visibility = View.VISIBLE
                } else {
                    datetimeAlarm.visibility = View.GONE
                }
            }

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

                    // 알람 설정
                    if (checkAlarm.isChecked == true) {
                        setNotification(datetimeAlarm.date.time, todo.name)
                    }
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
                // TODO: AlertDialog 에 테마 적용하기
                val builder = AlertDialog.Builder(this@TodoActivity);

                val textView = TextView(this@TodoActivity)
                textView.text = getString(R.string.msg_want_delete)

                builder.setView(textView)
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

    fun setNotification(time: Long, todo: String) {
        if (time > 0) {
            val alarmManager = getSystemService(Activity.ALARM_SERVICE) as AlarmManager
            val alarmIntent = Intent(
                applicationContext,
                AlarmReceiver::class.java
            )

            alarmIntent.putExtra("timestamp", time)
            alarmIntent.putExtra("todo", todo)

            val pendingIntent = PendingIntent.getBroadcast(
                this,
                0,
                alarmIntent,
                PendingIntent.FLAG_CANCEL_CURRENT
            )
            alarmManager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent)
        }
    }
}
