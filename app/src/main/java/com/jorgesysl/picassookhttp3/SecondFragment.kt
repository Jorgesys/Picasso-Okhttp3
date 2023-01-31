package com.jorgesysl.picassookhttp3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.jorgesysl.picassookhttp3.databinding.FragmentFirstBinding
import com.jorgesysl.picassookhttp3.databinding.FragmentSecondBinding

class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    var imageUrl : String = "https://static01.nyt.com/images/2023/02/01/multimedia/31frozen-pizza1-zvgw/30frozen-pizza1-zvgw-superJumbo.jpg"
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        _binding!!.btnLoadImage.setOnClickListener {
            Utils.loadImage(_binding!!.imageView, imageUrl)
        }

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}