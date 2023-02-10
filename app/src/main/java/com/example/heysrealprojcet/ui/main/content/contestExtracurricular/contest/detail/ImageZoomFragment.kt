package com.example.heysrealprojcet.ui.main.content.contestExtracurricular.contest.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.ImageZoomFragmentBinding

class ImageZoomFragment : Fragment() {
   private lateinit var binding: ImageZoomFragmentBinding

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = ImageZoomFragmentBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)

      binding.closeButton.setOnClickListener { goToDetail() }
   }

   private fun goToDetail() {
      findNavController().navigate(R.id.action_imageZoomFragment_to_contestExtracurricularDetailFragment)
   }
}