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
import uz.frodo.olti83.listeners.LessonInterface
import uz.frodo.olti83.listeners.ModuleInterface
import uz.frodo.olti83.room.entities.Course
import uz.frodo.olti83.room.entities.Lesson
import uz.frodo.olti83.room.entities.Module
import java.io.ByteArrayOutputStream
import java.io.File

class LessonAdapter(val lessonInterface: LessonInterface, val courseImage:String):ListAdapter<Lesson,LessonAdapter.LessonVH>
    (LessonDU()) {
    inner class LessonVH(val binding: CourseItemBinding):ViewHolder(binding.root)

    class  LessonDU:DiffUtil.ItemCallback<Lesson>() {
        override fun areItemsTheSame(oldItem: Lesson, newItem: Lesson): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Lesson, newItem: Lesson): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonVH {
        val binding = CourseItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return LessonVH(binding)
    }

    override fun onBindViewHolder(holder: LessonVH, position: Int) {
        val item = getItem(position)


        holder.binding.courseImage.setImageURI(Uri.parse(courseImage))
        holder.binding.courseName.text = item.order.toString()+"-dars"
        holder.binding.lessonName.visibility = View.VISIBLE
        holder.binding.lessonName.text = item.name

        holder.binding.courseDelete.setOnClickListener {
            lessonInterface.delete(item)
        }

        holder.binding.courseEdit.setOnClickListener {
            lessonInterface.edit(item)
        }

        holder.binding.courseCard.setOnClickListener {
            lessonInterface.itemClick(item)
        }
    }

}