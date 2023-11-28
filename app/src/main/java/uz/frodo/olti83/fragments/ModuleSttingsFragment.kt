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
import uz.frodo.olti83.adapters.ModuleAdapter
import uz.frodo.olti83.databinding.FragmentModuleSttingsBinding
import uz.frodo.olti83.listeners.ModuleInterface
import uz.frodo.olti83.room.AppDataBase
import uz.frodo.olti83.room.Dao
import uz.frodo.olti83.room.entities.Course
import uz.frodo.olti83.room.entities.Module


class ModuleSttingsFragment : Fragment(),ModuleInterface {
    lateinit var binding: FragmentModuleSttingsBinding
    var list = ArrayList<Module>()
    lateinit var adapter:ModuleAdapter
    lateinit var dao: Dao
    lateinit var course: Course

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentModuleSttingsBinding.inflate(layoutInflater)
        dao = AppDataBase.getInstance(requireContext()).dao()

        course = arguments?.getSerializable("course") as Course

        binding.moduleTitle.text =course.name


        val courseWithModules = dao.getCourseWithModules(course.id)
        println(course)
        println(course.id)
        println(courseWithModules)
        list = ArrayList(courseWithModules.first().modules)
        list.sortBy { it.order }
        adapter = ModuleAdapter(this,course.coursePhoto)
        adapter.submitList(list)
        binding.rvModule.adapter = adapter


        binding.fabModuleAdd.setOnClickListener {
            val name = binding.moduleName.text.toString().trim()
            val order = binding.moduleOrder.text.toString().trim()

            if (name.isBlank()){
                binding.moduleName.error = "Name required"
                binding.moduleName.requestFocus()
                return@setOnClickListener
            }

            if (order.isBlank()){
                binding.moduleOrder.error = "Order required"
                binding.moduleOrder.requestFocus()
                return@setOnClickListener
            }

            val module = Module(course.id,name, order.toInt())
            dao.insertModule(module)
            val id = dao.getModuleId(course.id,name,order.toInt())
            module.id = id
            list.add(module)
            list.sortBy { it.order }
            adapter.submitList(list)
            binding.rvModule.adapter = adapter

            binding.moduleName.setText("")
            binding.moduleOrder.setText("")
            binding.moduleName.requestFocus()
        }

        return binding.root
    }

    override fun delete(module: Module) {
        val dialog = AlertDialog.Builder(requireContext())
        dialog.setMessage("Bu modul ichida darslar kiritilgan. Darslar bilan birgalikda o’chib ketishiga rozimisiz?")
        dialog.setNegativeButton("Yo'q"){d,i->

        }
        dialog.setPositiveButton("Ha"){d,i->
            list.remove(module)
            adapter.submitList(list)
            binding.rvModule.adapter = adapter
            dao.deleteModuleWithLessons(module.id)
        }
        dialog.show()
    }

    override fun edit(module: Module) {
        findNavController().navigate(R.id.moduleEditFragment, bundleOf("module" to module))
    }

    override fun itemClick(module: Module) {
        findNavController().navigate(R.id.lessonSettingsFragment, bundleOf("module" to module,"coursePhoto" to course.coursePhoto))
    }
}