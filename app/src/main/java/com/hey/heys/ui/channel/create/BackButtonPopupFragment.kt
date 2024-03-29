package com.hey.heys.ui.channel.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.hey.heys.R
import com.hey.heys.databinding.BackButtonPopupFragmentBinding

class BackButtonPopupFragment : Fragment() {
   private lateinit var binding: BackButtonPopupFragmentBinding

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = BackButtonPopupFragmentBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.btnHome.setOnClickListener { goToMain() }
//      binding.btnMake.setOnClickListener { goToDetail() }
   }

   private fun goToMain() {
      findNavController().navigate(R.id.action_backButtonPopupFragment_to_mainFragment)
   }
}