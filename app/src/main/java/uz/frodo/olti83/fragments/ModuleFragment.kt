package uz.frodo.olti83.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import uz.frodo.olti83.R
import uz.frodo.olti83.adapters.ModuleReviewAdapter
import uz.frodo.olti83.databinding.FragmentModuleBinding
import uz.frodo.olti83.listeners.ModuleInterface
import uz.frodo.olti83.room.AppDataBase
import uz.frodo.olti83.room.entities.Course
import uz.frodo.olti83.room.entities.Module


class ModuleFragment : Fragment(),ModuleInterface {
    lateinit var binding: FragmentModuleBinding
    lateinit var adapter:ModuleReviewAdapter
    lateinit var list: ArrayList<Module>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentModuleBinding.inflate(layoutInflater)
        val dao = AppDataBase.getInstance(requireContext()).dao()

        val course = arguments?.getSerializable("course") as  Course
        binding.courseNameReviewModule.text = course.name

        list = ArrayList(dao.getCourseWithModules(course.id).first().modules)
        adapter = ModuleReviewAdapter(this,course)
        adapter.submitList(list)
        binding.rvModuleReview.adapter = adapter

        return binding.root
    }

    override fun itemClick(module: Module) {
        findNavController().navigate(R.id.lessonFragment, bundleOf("module" to module))
    }

    override fun delete(module: Module){}

    override fun edit(module: Module) {}


}