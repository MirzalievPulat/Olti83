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
import uz.frodo.olti83.databinding.LessonRoundBinding
import uz.frodo.olti83.listeners.CourseInterface
import uz.frodo.olti83.listeners.LessonInterface
import uz.frodo.olti83.listeners.ModuleInterface
import uz.frodo.olti83.room.entities.Course
import uz.frodo.olti83.room.entities.Lesson
import uz.frodo.olti83.room.entities.Module
import java.io.ByteArrayOutputStream
import java.io.File

class LessonReviewAdapter(val lessonInterface: LessonInterface):ListAdapter<Lesson,LessonReviewAdapter.LessonVH>
    (LessonDU()) {
    inner class LessonVH(val binding: LessonRoundBinding):ViewHolder(binding.root)

    class  LessonDU:DiffUtil.ItemCallback<Lesson>() {
        override fun areItemsTheSame(oldItem: Lesson, newItem: Lesson): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Lesson, newItem: Lesson): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonVH {
        val binding = LessonRoundBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return LessonVH(binding)
    }

    override fun onBindViewHolder(holder: LessonVH, position: Int) {
        val item = getItem(position)

        holder.binding.lessonOrderRound.text = item.order.toString()


        holder.binding.lessonCardRound.setOnClickListener {
            lessonInterface.itemClick(item)
        }
    }

}