package com.example.heysrealprojcet.ui.user.setting.delete

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.heysrealprojcet.databinding.SettingDeleteAccountFragmentBinding

class SettingDeleteAccountFragment : Fragment() {
   private lateinit var binding : SettingDeleteAccountFragmentBinding
   private val viewModel: SettingDeleteAccountViewModel by viewModels()

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = SettingDeleteAccountFragmentBinding.inflate(inflater, container, false)
      binding.vm = viewModel
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this

      viewModel.isSelected.observe(viewLifecycleOwner) { isSelected ->
         if (isSelected) {
            binding.edtWrite.visibility = View.VISIBLE
            binding.edtWrite.requestFocus()
         } else {
            binding.edtWrite.visibility = View.GONE
         }
      }

      viewModel.isEnabled.observe(viewLifecycleOwner) { isEnabled ->
         binding.btnNext.isEnabled = isEnabled
      }
   }
}