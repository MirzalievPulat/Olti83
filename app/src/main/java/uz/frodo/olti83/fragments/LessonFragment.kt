package uz.frodo.olti83.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import uz.frodo.olti83.R
import uz.frodo.olti83.adapters.LessonReviewAdapter
import uz.frodo.olti83.databinding.FragmentLessonBinding
import uz.frodo.olti83.listeners.LessonInterface
import uz.frodo.olti83.room.AppDataBase
import uz.frodo.olti83.room.entities.Lesson
import uz.frodo.olti83.room.entities.Module


class LessonFragment : Fragment(),LessonInterface {
    lateinit var binding: FragmentLessonBinding
    lateinit var list: ArrayList<Lesson>
    lateinit var adapter:LessonReviewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLessonBinding.inflate(layoutInflater)
        val dao = AppDataBase.getInstance(requireContext()).dao()
        val module = arguments?.getSerializable("module") as Module
        binding.moduleNameReviewLesson.text = module.name

        list = ArrayList(dao.getModuleWithLessons(module.id).first().lessons)
        list.sortBy { it.order }
        adapter = LessonReviewAdapter(this)
        adapter.submitList(list)
        binding.rvLessonReview.adapter = adapter

        return binding.root
    }

    override fun delete(lesson: Lesson) {}

    override fun edit(lesson: Lesson) {}

    override fun itemClick(lesson: Lesson) {
        findNavController().navigate(R.id.infoFragment, bundleOf("lesson" to lesson))
    }


}