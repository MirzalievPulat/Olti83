package uz.frodo.olti83.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import uz.frodo.olti83.databinding.ItemBinding
import uz.frodo.olti83.listeners.InnerInterface
import uz.frodo.olti83.listeners.MainInterface
import uz.frodo.olti83.room.AppDataBase
import uz.frodo.olti83.room.Dao
import uz.frodo.olti83.room.entities.Course
import uz.frodo.olti83.room.entities.Module
import uz.frodo.olti83.room.relations.CourseWithModules

class MainAdapter(val context: Context,val mainInterface: MainInterface,val innerInterface: InnerInterface)
    :ListAdapter<Course,MainAdapter.MainVH>(MyDifUtil()) {
    lateinit var innerAdapter:InnerAdapter
    lateinit var list: ArrayList<Module>
    lateinit var dao:Dao

    inner class MainVH(val binding: ItemBinding):ViewHolder(binding.root)

    class MyDifUtil():DiffUtil.ItemCallback<Course>(){
        override fun areItemsTheSame(oldItem: Course, newItem: Course): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Course, newItem: Course): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainVH {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MainVH(binding)
    }

    override fun onBindViewHolder(holder: MainVH, position: Int) {
        holder.binding.courseName.text = getItem(position).name
        holder.binding.all.setOnClickListener {
            mainInterface.allClick(getItem(position))
        }
        dao = AppDataBase.getInstance(context).dao()
        list = ArrayList(dao.getCourseWithModules(getItem(position).id).first().modules)
        innerAdapter = InnerAdapter(innerInterface)
        innerAdapter.submitList(list)
        holder.binding.inRv.adapter = innerAdapter
    }
}