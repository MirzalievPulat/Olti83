package uz.frodo.olti83.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import uz.frodo.olti83.R
import uz.frodo.olti83.databinding.FragmentLessonEditBinding
import uz.frodo.olti83.databinding.FragmentModuleEditBinding
import uz.frodo.olti83.room.AppDataBase
import uz.frodo.olti83.room.entities.Lesson
import uz.frodo.olti83.room.entities.Module


class LessonEditFragment : Fragment() {
    lateinit var binding:FragmentLessonEditBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLessonEditBinding.inflate(layoutInflater)

        val dao = AppDataBase.getInstance(requireContext()).dao()

        val lesson = arguments?.getSerializable("lesson") as Lesson

        binding.lessonNameEdit.text = lesson.order.toString()+"-dars"
        binding.etLessonNameEdit.setText(lesson.name)
        binding.lessonInfoEdit.setText(lesson.content)
        binding.etLessonOrderEdit.setText(lesson.order.toString())


        binding.fabLessonEdit.setOnClickListener {
            val name = binding.etLessonNameEdit.text.toString().trim()
            val info = binding.lessonInfoEdit.text.toString().trim()
            val order = binding.etLessonOrderEdit.text.toString().trim()


            if (name.isBlank()){
                binding.etLessonNameEdit.error = "Name required"
                binding.etLessonNameEdit.requestFocus()
                return@setOnClickListener
            }

            if (info.isBlank()) {
                binding.lessonInfoEdit.error = "Information required"
                binding.lessonInfoEdit.requestFocus()
                return@setOnClickListener
            }

            if (order.isBlank()) {
                binding.etLessonOrderEdit.error = "Order required"
                binding.etLessonOrderEdit.requestFocus()
                return@setOnClickListener
            }


            val l = Lesson(lesson.moduleId,name,info,order.toInt())
            l.id = lesson.id
            dao.updateLesson(l)
            findNavController().popBackStack()

        }

        return binding.root
    }


}