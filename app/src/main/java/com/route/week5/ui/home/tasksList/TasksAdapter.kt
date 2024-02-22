package com.route.week5.ui.home.tasksList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.route.week5.database.dao.MyDataBase
import com.route.week5.database.modal.Task
import com.route.week5.databinding.ItemTaskBinding

class TasksAdapter (var tasksList:MutableList<Task>?=null):RecyclerView.Adapter<TasksAdapter.ViewHolder>() {
    class ViewHolder(val itemBinding : ItemTaskBinding):RecyclerView.ViewHolder(itemBinding.root){
        fun bin(taskItem: Task){
            itemBinding.title.text =taskItem.title
            itemBinding.description.text =taskItem.content
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding :ItemTaskBinding =
            ItemTaskBinding.inflate(LayoutInflater.from(parent.context),
                parent,false)
        return ViewHolder(itemBinding)
    }

    override fun getItemCount(): Int =tasksList?.size?:0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val taskItem = tasksList!![position]
        holder.bin(taskItem)
        onTaskItemClickListener?.let {
            holder.itemBinding.dragItem.setOnClickListener {
                onTaskItemClickListener?.onTaskItemClicked(taskItem,position)
            }
            onDeleteClickListener?.let {
                holder.itemBinding.leftItem.setOnClickListener{
                    onDeleteClickListener?.onDeleteClicked(taskItem ,position)
                }
            }
//
//            holder.itemBinding.leftItem.setOnClickListener {
//                MyDataBase.getInstance()
//                    .getTasksDao().deleteTask(taskItem)
//                tasksList!!.remove(taskItem)
//                notifyItemRemoved(position)
//
//            }

        }

    }
    fun changeData(allTasks:MutableList<Task>){
        if (tasksList==null){
            tasksList= mutableListOf()
        }
       tasksList?.clear()
        tasksList?.addAll(allTasks)
        // effect on performance
        notifyDataSetChanged()
    }
     var onTaskItemClickListener : OnTaskItemClickListener?=null
    var onDeleteClickListener : OnDeleteClickListener?=null
//    fun setOnTaskClickListener(listener: OnTaskItemClickListener){
//        onTaskItemClickListener = listener
//    }
    fun interface OnTaskItemClickListener{
        fun onTaskItemClicked(item:Task,position: Int)
    }
    fun interface OnDeleteClickListener{
        fun onDeleteClicked(item:Task,position: Int)
    }
}