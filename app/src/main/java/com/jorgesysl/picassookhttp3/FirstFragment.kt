package com.jorgesysl.picassookhttp3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.jorgesysl.picassookhttp3.databinding.FragmentFirstBinding

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    var imageUrl : String = "https://static01.nyt.com/images/2022/11/26/multimedia/26world-cup-argentina-mexico-gamer-428-1-555a/26world-cup-argentina-mexico-gamer-428-1-555a-jumbo.jpg"

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        _binding!!.btnLoadImage.setOnClickListener { //Load Image
            Utils.loadImage(_binding!!.imageView, imageUrl)
        }
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}