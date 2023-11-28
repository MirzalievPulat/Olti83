package uz.frodo.olti83.fragments

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import uz.frodo.olti83.MainActivity
import uz.frodo.olti83.R
import uz.frodo.olti83.adapters.MainAdapter
import uz.frodo.olti83.databinding.FragmentMainBinding
import uz.frodo.olti83.listeners.InnerInterface
import uz.frodo.olti83.listeners.MainInterface
import uz.frodo.olti83.room.AppDataBase
import uz.frodo.olti83.room.Dao
import uz.frodo.olti83.room.entities.Course
import uz.frodo.olti83.room.entities.Module

class MainFragment : Fragment(),MainInterface,InnerInterface {
    lateinit var binding: FragmentMainBinding
    lateinit var dao: Dao
    lateinit var list: ArrayList<Course>
    lateinit var mainAdapter:MainAdapter
    lateinit var mContext:MainActivity
    var doubleBackToExitPressedOnce = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(layoutInflater)
        dao = AppDataBase.getInstance(requireContext()).dao()

        list = ArrayList(dao.getAllCourses())
        mainAdapter = MainAdapter(requireContext(),this,this)
        mainAdapter.submitList(list)
        binding.rv.adapter = mainAdapter


        binding.fabSettings.setOnClickListener {
            findNavController().navigate(R.id.settingsFragment)
        }



        return binding.root
    }

    override fun allClick(course: Course) {
        findNavController().navigate(R.id.moduleFragment, bundleOf("course" to course))
    }

    override fun itemClick(module: Module) {
        findNavController().navigate(R.id.lessonFragment, bundleOf("module" to module))
    }


    /*override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if (doubleBack) {
                requireActivity().finish()
            } else {
                doubleBack = true
                Toast.makeText(requireContext(), "Press back again to exit", Toast.LENGTH_SHORT).show()
                Handler(Looper.getMainLooper()).postDelayed({
                    doubleBack = false
                }, 2000) // Reset the flag after 2 seconds
            }
        }
    }*/

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context as MainActivity

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (doubleBackToExitPressedOnce) {
                    activity?.finish()
                }
                doubleBackToExitPressedOnce = true
                Toast.makeText(requireContext(), "Press again to exit", Toast.LENGTH_SHORT).show()
                Handler(mContext.mainLooper).postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
                
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }
}