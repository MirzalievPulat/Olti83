package uz.frodo.olti83.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import uz.frodo.olti83.R
import uz.frodo.olti83.adapters.LessonAdapter
import uz.frodo.olti83.adapters.ModuleAdapter
import uz.frodo.olti83.databinding.FragmentLessonSettingsBinding
import uz.frodo.olti83.databinding.FragmentModuleSttingsBinding
import uz.frodo.olti83.listeners.LessonInterface
import uz.frodo.olti83.room.AppDataBase
import uz.frodo.olti83.room.Dao
import uz.frodo.olti83.room.entities.Course
import uz.frodo.olti83.room.entities.Lesson
import uz.frodo.olti83.room.entities.Module


class LessonSettingsFragment : Fragment(), LessonInterface{
    lateinit var binding: FragmentLessonSettingsBinding
    var list=ArrayList<Lesson>()
    lateinit var adapter: LessonAdapter
    lateinit var dao: Dao
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLessonSettingsBinding.inflate(layoutInflater)
        dao = AppDataBase.getInstance(requireContext()).dao()

        val module = arguments?.getSerializable("module") as Module
        val courseImage = arguments?.getString("coursePhoto")

        binding.lessonTitle.text =module.name


        list = ArrayList(dao.getModuleWithLessons(module.id).first().lessons)
        list.sortBy { it.order }
        adapter = LessonAdapter(this,courseImage!!)
        adapter.submitList(list)
        binding.rvLesson.adapter = adapter

        binding.fabLessonAdd.setOnClickListener {
            val name = binding.lessonName.text.toString().trim()
            val info = binding.lessonInfo.text.toString().trim()
            val order = binding.lessonOrder.text.toString().trim()

            if (name.isBlank()){
                binding.lessonName.error = "Name required"
                binding.lessonName.requestFocus()
                return@setOnClickListener
            }

            if (name.isBlank()) {
                binding.lessonInfo.error = "Information required"
                binding.lessonInfo.requestFocus()
                return@setOnClickListener
            }

            if (order.isBlank()){
                binding.lessonOrder.error = "Order required"
                binding.lessonOrder.requestFocus()
                return@setOnClickListener
            }

            val l = Lesson(module.id,name,info,order.toInt())
            dao.insertLesson(l)
            val id = dao.getLessonId(name,order.toInt(),info)
            l.id = id
            list.add(l)
            list.sortBy { it.order }
            adapter.submitList(list)
            binding.rvLesson.adapter = adapter

            binding.lessonName.setText("")
            binding.lessonName.requestFocus()
            binding.lessonInfo.setText("")
            binding.lessonOrder.setText("")
        }

        return binding.root
    }

    override fun delete(lesson: Lesson) {
        val dialog = AlertDialog.Builder(requireContext())
        dialog.setMessage("Dars o’chishiga rozimisiz?")
        dialog.setNegativeButton("Yo'q"){d,i->

        }
        dialog.setPositiveButton("Ha"){d,i->
            list.remove(lesson)
            adapter.submitList(list)
            binding.rvLesson.adapter = adapter
            dao.deleteLesson(lesson)
        }
        dialog.show()
    }

    override fun edit(lesson: Lesson) {
        findNavController().navigate(R.id.lessonEditFragment, bundleOf("lesson" to lesson))
    }

    override fun itemClick(lesson: Lesson) {

    }


}