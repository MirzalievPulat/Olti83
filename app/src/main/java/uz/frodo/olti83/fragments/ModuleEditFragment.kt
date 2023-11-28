package uz.frodo.olti83.fragments

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import uz.frodo.olti83.R
import uz.frodo.olti83.databinding.FragmentModuleEditBinding
import uz.frodo.olti83.room.AppDataBase
import uz.frodo.olti83.room.entities.Course
import uz.frodo.olti83.room.entities.Module


class ModuleEditFragment : Fragment() {
    lateinit var binding: FragmentModuleEditBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentModuleEditBinding.inflate(layoutInflater)

        val dao = AppDataBase.getInstance(requireContext()).dao()

        val module = arguments?.getSerializable("module") as Module

        binding.moduleNameEdit.text = module.name
        binding.etModuleNameEdit.setText(module.name)
        binding.etModuleOrderEdit.setText(module.order.toString())


        binding.fabModuleEdit.setOnClickListener {
            val name = binding.etModuleNameEdit.text.toString().trim()
            val order = binding.etModuleOrderEdit.text.toString().trim()


            if (name.isBlank()){
                binding.etModuleNameEdit.error = "Name required"
                binding.etModuleNameEdit.requestFocus()
                return@setOnClickListener
            }

            if (order.isBlank()) {
                binding.etModuleOrderEdit.error = "Order required"
                binding.etModuleOrderEdit.requestFocus()
                return@setOnClickListener
            }


            val m = Module(module.courseId,name,order.toInt())
            m.id = module.id
            dao.updateModule(m)
            findNavController().popBackStack()

        }

        return binding.root
    }


}