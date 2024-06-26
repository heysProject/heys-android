package com.hey.heys.ui.channel.create.inform

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.chibatching.kotpref.livedata.asLiveData
import com.hey.heys.R
import com.hey.heys.databinding.ChannelInformFragmentBinding
import com.hey.heys.ui.channel.dialog.capacity.ChannelCapacityDialog
import com.hey.heys.ui.channel.dialog.form.ChannelFormDialog
import com.hey.heys.ui.channel.dialog.interest.ChannelInterestDialog
import com.hey.heys.ui.channel.dialog.period.ChannelPeriodDialog
import com.hey.heys.ui.channel.dialog.period.ChannelTimeDialog
import com.hey.heys.ui.channel.dialog.purpose.ChannelPurposeDialog
import com.hey.heys.ui.channel.dialog.recruitmentMethod.ChannelRecruitmentMethodDialog
import com.hey.heys.ui.main.MainActivity
import com.hey.heys.util.ChannelPreference
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ChannelInformFragment : Fragment() {
   private lateinit var binding: ChannelInformFragmentBinding
   private val viewModel: ChannelInformViewModel by viewModels()
   private val isEnabled: MediatorLiveData<Boolean> = MediatorLiveData()

   val isPurposeSelected = MutableLiveData(false)
   val isFormSelected = MutableLiveData(false)
   val isCapacitySelected = MutableLiveData(false)
   val isRecruitmentMethodSelected = MutableLiveData(false)
   val isRecruitEndDaySelected = MutableLiveData(false)
   val isInterestSelected = MutableLiveData(false)

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      val mainActivity = activity as MainActivity
      mainActivity.hideBottomNavigation(true)
   }


   override fun onDestroy() {
      super.onDestroy()
      val mainActivity = activity as MainActivity
      mainActivity.hideBottomNavigation(false)
   }

   override fun onResume() {
      super.onResume()
      val mainActivity = activity as MainActivity
      mainActivity.hideBottomNavigation(true)
   }

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = ChannelInformFragmentBinding.inflate(inflater, container, false)
      binding.vm = viewModel
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this
      binding.btnBack.setOnClickListener { findNavController().navigateUp() }
      binding.purposeContainer.setOnClickListener {
         val purposeDialog = ChannelPurposeDialog()
         purposeDialog.setOnOKClickListener { content ->
            binding.purpose.text = content
            binding.purpose.setTextColor(ContextCompat.getColor(requireContext(), R.color.color_34d676))
         }
         purposeDialog.show(childFragmentManager, null)
      }

      binding.formContainer.setOnClickListener {
         val formDialog = ChannelFormDialog()
         formDialog.setOnOKClickListener { content ->
            binding.form.text = content
            binding.form.setTextColor(ContextCompat.getColor(requireContext(), R.color.color_34d676))
         }
         formDialog.show(childFragmentManager, null)
      }

      binding.capacityContainer.setOnClickListener {
         val capacityDialog = ChannelCapacityDialog()
         capacityDialog.setOnOKClickListener { content ->
            binding.capacity.text = "최대 ${content}명"
            binding.capacity.setTextColor(ContextCompat.getColor(requireContext(), R.color.color_34d676))
         }
         capacityDialog.show(childFragmentManager, null)
      }

      binding.recruitmentMethodContainer.setOnClickListener {
         val recruitmentMethodDialog = ChannelRecruitmentMethodDialog()
         recruitmentMethodDialog.setOnOKClickListener { content ->
            binding.recruitmentMethod.text = content
            binding.recruitmentMethod.setTextColor(ContextCompat.getColor(requireContext(), R.color.color_34d676))
         }
         recruitmentMethodDialog.show(childFragmentManager, null)
      }

      binding.recruitmentPeriodContainer.setOnClickListener {
         val recruitmentPeriodDialog = ChannelPeriodDialog()
         val recruitmentTimeDialog = ChannelTimeDialog()

         recruitmentPeriodDialog.setOnOKClickListener { content ->
            recruitmentTimeDialog.show(childFragmentManager, null)
         }
         recruitmentPeriodDialog.show(childFragmentManager, null)
         recruitmentTimeDialog.setOnOKClickListener { content ->
            val year = ChannelPreference.channelRecruitEndDay.split("-")[0]
            val month = ChannelPreference.channelRecruitEndDay.split("-")[1]
            val day = ChannelPreference.channelRecruitEndDay.split("-")[2]
            val hour = ChannelPreference.channelRecruitEndTime.split(":")[0]
            val min = ChannelPreference.channelRecruitEndTime.split(":")[1]
            val endDateTime = LocalDateTime.of(year.toInt(), month.toInt(), day.toInt(), hour.toInt(), min.toInt())
            val endTimeString = endDateTime.format(DateTimeFormatter.ofPattern("a hh:mm"))

            binding.recruitmentPeriod.text = "${year.substring(2)}/$month/$day ${endTimeString}까지"
            binding.recruitmentPeriod.setTextColor(ContextCompat.getColor(requireContext(), R.color.color_34d676))
            ChannelPreference.channelRecruitEndDateTime = endDateTime.toString()
         }
      }

      binding.interestContainer.setOnClickListener {
         val interestDialog = ChannelInterestDialog()
         interestDialog.setOnOKClickListener { content ->
            binding.interest.text = content
            binding.interest.setTextColor(ContextCompat.getColor(requireContext(), R.color.color_34d676))
         }
         interestDialog.show(childFragmentManager, null)
      }

      checkAllSelected()
      isEnabled.apply {
         addSource(isPurposeSelected) { v -> isEnabled.value = onIsEnabled() }
         addSource(isFormSelected) { v -> isEnabled.value = onIsEnabled() }
         addSource(isCapacitySelected) { v -> isEnabled.value = onIsEnabled() }
         addSource(isRecruitmentMethodSelected) { v -> isEnabled.value = onIsEnabled() }
         addSource(isRecruitEndDaySelected) { v -> isEnabled.value = onIsEnabled() }
         addSource(isInterestSelected) { v -> isEnabled.value = onIsEnabled() }
      }
      isEnabled.observe(viewLifecycleOwner) { binding.btnNext.isEnabled = it == true }
      binding.btnNext.setOnClickListener { goToName() }
   }

   private fun onIsEnabled(): Boolean {
      return (isPurposeSelected.value == true
              && isFormSelected.value == true
              && isCapacitySelected.value == true
              && isRecruitmentMethodSelected.value == true
              && isRecruitEndDaySelected.value == true
              && isInterestSelected.value == true)
   }

   private fun goToName() {
      findNavController().navigate(R.id.action_channelInformFragment_to_channelFreeFragment)
   }

   private fun checkAllSelected() {
      ChannelPreference.asLiveData(ChannelPreference::channelPurposeArray).observe(viewLifecycleOwner) {
         if (it.size > 0) {
            isPurposeSelected.value = true
         }
      }

      ChannelPreference.asLiveData(ChannelPreference::channelForm).observe(viewLifecycleOwner) {
         isFormSelected.value = !it.isNullOrBlank()
      }

      ChannelPreference.asLiveData(ChannelPreference::channelCapacity).observe(viewLifecycleOwner) {
         isCapacitySelected.value = !it.toString().isNullOrBlank()
      }

      ChannelPreference.asLiveData(ChannelPreference::channelRecruitmentMethod).observe(viewLifecycleOwner) {
         isRecruitmentMethodSelected.value = !it.isNullOrBlank()
      }

      ChannelPreference.asLiveData(ChannelPreference::channelRecruitEndDay).observe(viewLifecycleOwner) {
         isRecruitEndDaySelected.value = !it.isNullOrBlank()
      }

      ChannelPreference.asLiveData(ChannelPreference::channelInterestArray).observe(viewLifecycleOwner) {
         if (it.size > 0) {
            isInterestSelected.value = true
         }
      }
   }
}

