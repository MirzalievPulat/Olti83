package uz.frodo.olti83.fragments

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Matrix
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import uz.frodo.olti83.R
import uz.frodo.olti83.databinding.FragmentCourseEditBinding
import uz.frodo.olti83.room.AppDataBase
import uz.frodo.olti83.room.entities.Course
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CourseEditFragment : Fragment() {
    lateinit var binding: FragmentCourseEditBinding
    var path=""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCourseEditBinding.inflate(layoutInflater)
        val dao = AppDataBase.getInstance(requireContext()).dao()

        val course = arguments?.getSerializable("course") as Course

        binding.courseImageEdit.setImageURI(Uri.parse(course.coursePhoto))
        binding.courseNameEdit.text = course.name
        binding.etCourseNameEdit.setText(course.name)

        binding.courseImageEdit.setOnClickListener {
            getImageFromStorage.launch("image/*")
        }

        binding.fabSettingsEdit.setOnClickListener {
            val name = binding.etCourseNameEdit.text.toString().trim()

            if (path == ""){
                path = course.coursePhoto
            }
            println(path)
            if (name.isBlank()){
                binding.etCourseNameEdit.error = "Name required"
                binding.etCourseNameEdit.requestFocus()
                return@setOnClickListener
            }


            val c = Course(name,path)
            c.id = course.id
            dao.updateCourse(c)
            findNavController().popBackStack()

        }

        return binding.root
    }

    val getImageFromStorage = registerForActivityResult(ActivityResultContracts.GetContent()){uri->
        uri ?: return@registerForActivityResult

        requireContext().contentResolver.openInputStream(uri).use {ins->
            val format = SimpleDateFormat("HH:mm:ss_dd.MM.yyyy", Locale.getDefault()).format(Date())
            val file = File(requireContext().filesDir,"image_${format}.jpg")
            FileOutputStream(file).use {outs ->
                val bitmap = BitmapFactory.decodeStream(ins)
                val resizedBitmap = resizeBitmap(bitmap)
                resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 50, outs)
            }
            path = file.absolutePath
            binding.courseImageEdit.setImageURI(Uri.parse(path))
        }

    }
    private fun resizeBitmap(bitmap: Bitmap): Bitmap {
        val originalWidth = bitmap.width
        val originalHeight = bitmap.height

        val scaleWidth = (originalWidth/3).toFloat() / originalWidth
        val scaleHeight = (originalHeight/3).toFloat() / originalHeight

        val matrix = Matrix()
        matrix.postScale(scaleWidth, scaleHeight)

        return Bitmap.createBitmap(bitmap, 0, 0, originalWidth, originalHeight, matrix, false)
    }

}