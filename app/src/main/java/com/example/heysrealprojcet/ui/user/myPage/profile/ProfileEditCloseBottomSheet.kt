package com.example.heysrealprojcet.ui.user.myPage.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.heysrealprojcet.databinding.ProfileEditCloseBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ProfileEditCloseBottomSheet(val listener: () -> Unit) : BottomSheetDialogFragment() {
   private lateinit var binding: ProfileEditCloseBottomSheetBinding

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = ProfileEditCloseBottomSheetBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.closeButton.setOnClickListener { dismiss() }
      binding.cancelButton.setOnClickListener { dismiss() }
      binding.okButton.setOnClickListener {
         dismiss()
         // 뒤로가기
         listener.invoke()
      }
   }
}