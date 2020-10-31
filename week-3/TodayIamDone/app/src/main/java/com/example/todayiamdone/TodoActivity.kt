package com.example.todayiamdone

import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker
import kotlinx.android.synthetic.main.activity_todo.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
        // TODO: 데이터베이스 객체 생성

        // 메뉴 클릭하면 메인 화면 상단의 제목이 바뀜
        groupMenu.setOnCheckedChangeListener { radioGroup, i ->
            if (i == R.id.btnBookmark) {
                textTitle.text = getString(R.string.bookmark)

                // TODO: 조회
            } else if (i == R.id.btnTodo) {
                textTitle.text = getString(R.string.todo)

                // TODO: 조회
            } else {
                textTitle.text = getString(R.string.done)

                // TODO: 조회
            }
        }

        // 추가 버튼을 클릭하면 다이얼로그 띄움
        btnAdd.setOnClickListener {
            val builder = AlertDialog.Builder(this);

            val dialogView = layoutInflater.inflate(R.layout.layout_insert_todo, null)

            // TODO: View 가져와서 이벤트 연결

            // TODO: View 설정한 뒤 다이얼로그 띄우기

            // TODO: 할 일 등록
        }

        // RecyclerView 준비
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


        viewAdapter.setTodoLongClickListener(object : TodoAdapter.TodoLongClickListener {
            override fun onLongClicked(todo: TodoItem) {
                // TODO: 할 일 삭제
            }
        })
    }

    fun getAllTodo() {
        // TODO:
    }

    fun getBookmarkTodo() {
        // TODO:
    }

    fun getDoneTodo() {
        // TODO:
    }

    fun insertTodo(todo: TodoItem) {
        // TODO:
    }

    fun deleteTodo(todo: TodoItem) {
        // TODO:
    }
}
