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
    private var arrayItem: MutableList<TodoItem> = ArrayList()

    class TodoViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val checkDone: CheckBox = v.checkDone
        val toggleBookmark: ToggleButton = v.toggleBookmark
    }

    private var todoChangeListener: TodoChangeListener? = null

    interface TodoChangeListener {
        fun onDoneChanged(todo: TodoItem)
        fun onBookmarkChanged(todo: TodoItem)
    }

    fun setTodoChangeListener(listener: TodoChangeListener) {
        this.todoChangeListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoAdapter.TodoViewHolder {
        mContext = parent.context
        val inflatedView = LayoutInflater.from(mContext)
            .inflate(R.layout.layout_todo_item, parent, false)
        return TodoViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: TodoAdapter.TodoViewHolder, position: Int) {
        arrayItem[position].let {item ->
            holder.checkDone.isChecked = item.isDone
            holder.checkDone.text = item.name
            holder.toggleBookmark.isChecked = item.isBookmark

            holder.checkDone.setOnCheckedChangeListener { compoundButton, b ->
                item.isDone = b
                todoChangeListener?.onDoneChanged(item)
            }

            holder.toggleBookmark.setOnCheckedChangeListener {compoundButton, b ->
                item.isBookmark = b
                todoChangeListener?.onDoneChanged(item)
            }
        }
    }

    override fun getItemCount(): Int {
        return arrayItem.count()
    }

    fun setItems(list: MutableList<TodoItem>) {
        arrayItem = list
    }

}
