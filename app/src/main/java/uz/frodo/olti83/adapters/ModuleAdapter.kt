package uz.frodo.olti83.adapters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.squareup.picasso.Picasso
import uz.frodo.olti83.R
import uz.frodo.olti83.databinding.CourseItemBinding
import uz.frodo.olti83.listeners.CourseInterface
import uz.frodo.olti83.listeners.ModuleInterface
import uz.frodo.olti83.room.entities.Course
import uz.frodo.olti83.room.entities.Module
import java.io.ByteArrayOutputStream
import java.io.File

class ModuleAdapter(val moduleInterface: ModuleInterface,val courseImage:String):ListAdapter<Module,ModuleAdapter.ModuleVH>
    (ModuleDU()) {
    inner class ModuleVH(val binding: CourseItemBinding):ViewHolder(binding.root)

    class  ModuleDU:DiffUtil.ItemCallback<Module>() {
        override fun areItemsTheSame(oldItem: Module, newItem: Module): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Module, newItem: Module): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModuleVH {
        val binding = CourseItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ModuleVH(binding)
    }

    override fun onBindViewHolder(holder: ModuleVH, position: Int) {
        val item = getItem(position)


        holder.binding.courseImage.setImageURI(Uri.parse(courseImage))
        holder.binding.courseName.text = item.name
        holder.binding.order.visibility = View.VISIBLE
        holder.binding.orderNumber.text = item.order.toString()

        holder.binding.courseDelete.setOnClickListener {
            moduleInterface.delete(item)
        }

        holder.binding.courseEdit.setOnClickListener {
            moduleInterface.edit(item)
        }

        holder.binding.courseCard.setOnClickListener {
            moduleInterface.itemClick(item)
        }
    }

}