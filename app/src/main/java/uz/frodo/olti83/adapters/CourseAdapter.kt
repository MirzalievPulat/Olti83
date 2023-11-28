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
import uz.frodo.olti83.room.entities.Course
import java.io.ByteArrayOutputStream
import java.io.File

class CourseAdapter(val courseInterface: CourseInterface):ListAdapter<Course,CourseAdapter.CourseVH>(CourseDU()) {
    inner class CourseVH(val binding: CourseItemBinding):ViewHolder(binding.root)

    class  CourseDU:DiffUtil.ItemCallback<Course>() {
        override fun areItemsTheSame(oldItem: Course, newItem: Course): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Course, newItem: Course): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseVH {
        val binding = CourseItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CourseVH(binding)
    }

    override fun onBindViewHolder(holder: CourseVH, position: Int) {
        val item = getItem(position)


        holder.binding.courseImage.setImageURI(Uri.parse(item.coursePhoto))
        holder.binding.courseName.text = item.name

        holder.binding.courseDelete.setOnClickListener {
            courseInterface.delete(item)
        }

        holder.binding.courseEdit.setOnClickListener {
            courseInterface.edit(item)
        }

        holder.binding.courseCard.setOnClickListener {
            courseInterface.itemClick(item)
        }
    }

}