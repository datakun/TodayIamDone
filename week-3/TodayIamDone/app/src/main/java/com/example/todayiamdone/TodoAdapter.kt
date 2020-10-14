package com.example.todayiamdone

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ToggleButton
import kotlinx.android.synthetic.main.layout_todo_item.view.*

class TodoAdapter() : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {
    private lateinit var mContext: Context

    private var arrayItem: MutableList<TodoItem> = ArrayList()

    private var todoChangeListener: TodoChangeListener? = null

    fun setTodoChangeListener(todoChangeListener: TodoChangeListener) {
        this.todoChangeListener = todoChangeListener
    }

    interface TodoChangeListener {
        fun onDoneChanged(todo: TodoItem)
        fun onBookmarkChanged(todo: TodoItem)
    }

    class TodoViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val checkDone: CheckBox = v.checkDone
        val toggleBookmark: ToggleButton = v.toggleBookmark
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

            holder.checkDone.setOnCheckedChangeListener { compoundButton, b ->
                item.isDone = b
                todoChangeListener?.onDoneChanged(item)
            }

            holder.toggleBookmark.setOnCheckedChangeListener { compoundButton, b ->
                item.isBookmark = b
                todoChangeListener?.onBookmarkChanged(item)
            }
        }
    }

    override fun getItemCount() = arrayItem.size

    fun setItems(arrTodo: MutableList<TodoItem>) {
        this.arrayItem = arrTodo
    }
}