package com.example.todayiamdone

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_todo.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodoActivity : AppCompatActivity() {
    val arrayTodo = mutableListOf<TodoItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo)

        // 메뉴 클릭하면 메인 화면 상단의 제목이 바뀜
        groupMenu.setOnCheckedChangeListener { radioGroup, i ->
            if (i == R.id.btnBookmark) {
                textTitle.text = getString(R.string.bookmark)
            } else if (i == R.id.btnTodo) {
                textTitle.text = getString(R.string.todo)
            } else {
                textTitle.text = getString(R.string.done)
            }
        }

        arrayTodo.add(TodoItem(1, "전기 요금 납부", false, false))
        arrayTodo.add(TodoItem(2, "자동차 점검", true, true))
        arrayTodo.add(TodoItem(3, "수업 준비", false, true))
        arrayTodo.add(TodoItem(4, "메일 정리", true, false))

        // 추가 버튼을 클릭하면 다이얼로그 띄움
        btnAdd.setOnClickListener {
            // TODO: 할 일 등록 다이얼로그 생성
        }

        // RecyclerView 준비
        val viewManager = LinearLayoutManager(this)
        val viewAdapter = TodoAdapter()
        viewAdapter.setItems(arrayTodo)
        rvTodo.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        viewAdapter.setTodoChangeListener(object : TodoAdapter.TodoChangeListener {
            override fun onDoneChanged(todo: TodoItem) {
                // TODO: 할 일 완료
            }

            override fun onBookmarkChanged(todo: TodoItem) {
                // TODO: 중요한 일로 등록
            }
        })
    }
}
