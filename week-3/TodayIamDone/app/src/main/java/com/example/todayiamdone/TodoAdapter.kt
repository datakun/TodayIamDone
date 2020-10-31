package com.example.todayiamdone

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_todo_item.view.*

class TodoAdapter() : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {
    private lateinit var mContext: Context

    private var arrayItem: List<TodoItem> = ArrayList()

    private var todoChangeListener: TodoChangeListener? = null

    private var todoLongClickListener: TodoLongClickListener? = null

    fun setTodoChangeListener(todoChangeListener: TodoChangeListener) {
        this.todoChangeListener = todoChangeListener
    }

    fun setTodoLongClickListener(todoLongClickListener: TodoLongClickListener) {
        this.todoLongClickListener = todoLongClickListener
    }

    interface TodoChangeListener {
        fun onDoneChanged(todo: TodoItem)
        fun onBookmarkChanged(todo: TodoItem)
    }

    interface TodoLongClickListener {
        fun onLongClicked(todo: TodoItem)
    }

    inner class TodoViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val checkDone: CheckBox = v.checkDone
        val toggleBookmark: ToggleButton = v.toggleBookmark

        init {
            // Done 체크박스 클릭 이벤트 처리
            checkDone.setOnCheckedChangeListener { compoundButton, b ->
                // TODO:
            }

            // Bookmark 체크박스 클릭 이벤트 처리
            toggleBookmark.setOnCheckedChangeListener { compoundButton, b ->
                // TODO:
            }

            // 체크박스 길게 누르면 삭제 처리
            checkDone.setOnLongClickListener {
                // TODO:

                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        mContext = parent.context
        val inflatedView =
            LayoutInflater.from(mContext).inflate(R.layout.layout_todo_item, parent, false)

        return TodoViewHolder(inflatedView)
    }


    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        arrayItem[position].let { item ->
            holder.checkDone.isChecked = item.isDone
            holder.checkDone.text = item.name
            holder.toggleBookmark.isChecked = item.isBookmark
        }
    }

    override fun getItemCount() = arrayItem.size

    fun setItems(arrTodo: List<TodoItem>) {
        arrayItem = arrTodo
    }
}