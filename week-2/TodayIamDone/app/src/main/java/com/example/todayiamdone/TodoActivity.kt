package com.example.todayiamdone

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_todo.*

class TodoActivity : AppCompatActivity() {

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

        val arrayTodo = mutableListOf<TodoItem>()
        arrayTodo.add(TodoItem(1, "설거지 하기", true, true))
        arrayTodo.add(TodoItem(2, "전기 요금 납부하기", true, false))
        arrayTodo.add(TodoItem(3, "퇴근길에 장보기", false, false))
        arrayTodo.add(TodoItem(4, "자동차 점검하기", false, true))

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
                Toast.makeText(this@TodoActivity, "Done 클릭", Toast.LENGTH_SHORT).show()
            }

            override fun onBookmarkChanged(todo: TodoItem) {
                Toast.makeText(this@TodoActivity, "Bookmark 클릭", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
