package com.example.heysrealprojcet.ui.join.phone

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.JoinPhoneVerificationFragmentBinding
import com.example.heysrealprojcet.util.UserPreference
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit

class JoinVerificationFragment : Fragment() {
   private lateinit var binding: JoinPhoneVerificationFragmentBinding
   private val viewModel: JoinVerificationViewModel by viewModels()
   private lateinit var firebaseAuth: FirebaseAuth
   private var storedVerificationId = ""
   private var resendToken: PhoneAuthProvider.ForceResendingToken? = null

   private val callbacks by lazy {
      object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
         override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            Toast.makeText(requireContext(), "인증코드가 전송되었습니다. 90초 이내에 입력해주세요", Toast.LENGTH_LONG).show()
            verifyPhoneNumberWithCode(credential)
         }

         override fun onVerificationFailed(e: FirebaseException) {
            Log.w("aaaaaaa", "onVerificationFailed", e)
            Toast.makeText(requireContext(), "aaaaaa 인증 실패", Toast.LENGTH_LONG).show()
         }

         override fun onCodeSent(verficationId: String, token: PhoneAuthProvider.ForceResendingToken) {
            Log.d("bbbbbbb", "onCodeSent: $verficationId")
            storedVerificationId = verficationId
            resendToken = token
         }
      }
   }

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
   ): View? {
      binding = JoinPhoneVerificationFragmentBinding.inflate(inflater, container, false)
      binding.vm = viewModel

      firebaseAuth = Firebase.auth
      initViewModelCallback()
      return binding.root
   }

   private fun initViewModelCallback() {
      with(viewModel) {
         requestPhoneAuth.observe(viewLifecycleOwner) {
            if (it) {
               viewModel.phoneAuthNumber = ""
               val phoneNumber = "+82" + UserPreference.phoneNumber.substring(1)
               startPhoneNumberVerification(phoneNumber)
               Log.w("ccccccccc phoneNum: ", phoneNumber)
            } else {
               Toast.makeText(requireContext(), "전화번호를 입력해주세요", Toast.LENGTH_LONG).show()
            }
         }

         // resend 부분 추가해야함!

         authComplete.observe(viewLifecycleOwner) {
            Log.w("ddddddd", storedVerificationId)
            Log.w("eeeeeeeee", viewModel.verificationNumber.value)
            val phoneCredential = PhoneAuthProvider.getCredential(storedVerificationId, viewModel.verificationNumber.value)
            verifyPhoneNumberWithCode(phoneCredential)
         }
      }
   }

   private fun startPhoneNumberVerification(phoneNumber: String) {
      val options = PhoneAuthOptions.newBuilder(firebaseAuth)
         .setPhoneNumber(phoneNumber)
         .setTimeout(90L, TimeUnit.SECONDS)
         .setActivity(requireActivity())
         .setCallbacks(callbacks)
         .build()
      PhoneAuthProvider.verifyPhoneNumber(options)
   }

   private fun verifyPhoneNumberWithCode(phoneAuthCredential: PhoneAuthCredential) {
      Firebase.auth.signInWithCredential(phoneAuthCredential)
         .addOnCompleteListener(requireActivity()) { task ->
            if (task.isSuccessful) {
               Log.d("HERE", "signInWithCredential:success")
               goToJoinPassword()
            } else {
               Toast.makeText(requireContext(), "!!!!인증 실패!!!!", Toast.LENGTH_LONG).show()
            }
         }
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this
      viewModel.phoneNumber.value = arguments?.getString("phoneNumber")

      if (viewModel.phoneNumber.value!!.isNotEmpty()) {
         viewModel.timerStart()
      }
   }

   private fun goToJoinPassword() {
      findNavController().navigate(R.id.action_phoneVerificationFragment_to_joinPasswordFragment)
   }
}