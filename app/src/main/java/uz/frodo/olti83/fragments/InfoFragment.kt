package uz.frodo.olti83.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import uz.frodo.olti83.R
import uz.frodo.olti83.databinding.FragmentInfoBinding
import uz.frodo.olti83.room.entities.Lesson


class InfoFragment : Fragment() {
    lateinit var binding: FragmentInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInfoBinding.inflate(layoutInflater)
        val lesson = arguments?.getSerializable("lesson") as Lesson

        binding.lessonOrderReview.text = lesson.order.toString()+"-dars"
        binding.lessonNameReview.text = lesson.name
        binding.lessonContentReview.text = lesson.content

        binding.imageButton.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }


}