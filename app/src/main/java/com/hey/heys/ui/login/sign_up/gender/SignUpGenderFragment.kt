package com.hey.heys.ui.login.sign_up.gender

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.hey.heys.R
import com.hey.heys.databinding.SignUpGenderFragmentBinding
import com.hey.heys.enums.Gender
import com.hey.heys.ui.main.MainActivity
import com.hey.heys.util.UserPreference

class SignUpGenderFragment : Fragment() {
   private lateinit var binding: SignUpGenderFragmentBinding
   private val viewModel: SignUpGenderViewModel by viewModels()

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      val mainActivity = activity as MainActivity
      mainActivity.hideBottomNavigation(true)
   }

   override fun onResume() {
      super.onResume()
      val mainActivity = activity as MainActivity
      mainActivity.hideBottomNavigation(true)
   }

   override fun onDestroy() {
      super.onDestroy()
      val mainActivity = activity as MainActivity
      mainActivity.hideBottomNavigation(false)
   }

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
   ): View? {
      binding = SignUpGenderFragmentBinding.inflate(inflater, container, false)
      binding.vm = viewModel
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      // default 세팅
      UserPreference.gender = Gender.Male.genderEnglish

      binding.btnBack.setOnClickListener { findNavController().navigateUp() }
      binding.okButton.setOnClickListener {
         findNavController().navigate(R.id.action_signUpGenderFragment_to_signUpAgeFragment)
      }

      viewModel.isMale.observe(viewLifecycleOwner) { isMale ->
         binding.male.isSelected = isMale
         isMale?.let { binding.okButton.isEnabled = true }
      }

      viewModel.isFemale.observe(viewLifecycleOwner) { isFemale ->
         binding.female.isSelected = isFemale
      }

      viewModel.isNonBinary.observe(viewLifecycleOwner) { isNonBinary ->
         binding.nonBinary.isSelected = isNonBinary
      }
   }
}