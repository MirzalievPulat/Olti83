package uz.frodo.olti83.fragments

import android.content.Context
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
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import uz.frodo.olti83.R
import uz.frodo.olti83.adapters.CourseAdapter
import uz.frodo.olti83.databinding.FragmentSettingsBinding
import uz.frodo.olti83.listeners.CourseInterface
import uz.frodo.olti83.room.AppDataBase
import uz.frodo.olti83.room.Dao
import uz.frodo.olti83.room.entities.Course
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class SettingsFragment : Fragment(),CourseInterface {
    lateinit var binding: FragmentSettingsBinding
    lateinit var dao: Dao
    lateinit var list: ArrayList<Course>
    lateinit var adapter:CourseAdapter
    var filePath = ""


    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(layoutInflater)
        dao = AppDataBase.getInstance(requireContext()).dao()

        list = ArrayList(dao.getAllCourses())
        println(list)
        adapter = CourseAdapter(this)
        adapter.submitList(list)
        binding.rvSettings.adapter = adapter
        binding.rvSettings.setHasFixedSize(true)

        binding.imageSettings.setOnClickListener {
            getImageFromStorage.launch("image/*")
        }


        binding.fabSettings.setOnClickListener {
            val name = binding.etCourseName.text.toString().trim()

            if (filePath == ""){
                binding.underImage.text = "Image required"
                binding.underImage.setTextColor(Color.RED)
                return@setOnClickListener
            }
            if (name.isBlank()){
                binding.etCourseName.error = "Name required"
                binding.etCourseName.requestFocus()
                return@setOnClickListener
            }


            val course = Course(name,filePath)
            dao.insertCourse(course)
            val id = dao.getCourseId(name,filePath)
            course.id = id
            list.add(course)
            adapter.submitList(list)
            binding.rvSettings.adapter = adapter

            binding.imageSettings.setImageResource(R.drawable.im)
            binding.etCourseName.setText("")
            filePath = ""

            binding.underImage.text = "Kursga mos rasm qo'shish"
            binding.underImage.setTextColor(Color.BLACK)
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
            filePath = file.absolutePath
            binding.imageSettings.setImageURI(Uri.parse(filePath))
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

    override fun delete(course: Course) {

        val dialog = AlertDialog.Builder(requireContext())
        dialog.setMessage("Bu kurs ichida modullar kiritilgan. Modullar bilan birgalikda o’chib ketishiga rozimisiz?")
        dialog.setNegativeButton("Yo'q"){d,i->

        }
        dialog.setPositiveButton("Ha"){d,i->
            list.remove(course)
            adapter.submitList(list)
            binding.rvSettings.adapter = adapter
            dao.deleteCourseWithModulesAndLessons(course.id)
        }
        dialog.show()

    }

    override fun edit(course: Course) {
        findNavController().navigate(R.id.courseEditFragment, bundleOf("course" to course))
    }

    override fun itemClick(course: Course) {
        findNavController().navigate(R.id.moduleSttingsFragment, bundleOf("course" to course))
    }

    companion object {

        fun newInstance(param1: String, param2: String) =
            SettingsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}