package com.hey.heys.ui.channel.list.detail.approvedUser

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hey.heys.R
import com.hey.heys.databinding.ApprovedUserImageItemViewBinding
import com.hey.heys.enums.Gender
import com.hey.heys.model.network.ApprovedUserList

class ApprovedUserImageListRecyclerViewAdapter(
   private val user: MutableList<ApprovedUserList>?
) : RecyclerView.Adapter<ApprovedUserImageListRecyclerViewAdapter.ViewHolder>() {
   private lateinit var binding: ApprovedUserImageItemViewBinding

   inner class ViewHolder(private val binding: ApprovedUserImageItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
      fun bind(user: ApprovedUserList) {
         when (user.percentage) {
            in 0..49 -> {
               when (user.gender) {
                  Gender.Male.genderEnglish -> binding.profile.setImageResource(R.drawable.ic_male_0)
                  Gender.Female.genderEnglish -> binding.profile.setImageResource(R.drawable.ic_female_0)
                  else -> binding.profile.setImageResource(R.drawable.ic_none_0)
               }
            }

            in 50..99 -> {
               when (user.gender) {
                  Gender.Male.genderEnglish -> binding.profile.setImageResource(R.drawable.ic_male_50)
                  Gender.Female.genderEnglish -> binding.profile.setImageResource(R.drawable.ic_female_50)
                  else -> binding.profile.setImageResource(R.drawable.ic_none_50)
               }
            }

            100 -> {
               when (user.gender) {
                  Gender.Male.genderEnglish -> binding.profile.setImageResource(R.drawable.ic_male_100)
                  Gender.Female.genderEnglish -> binding.profile.setImageResource(R.drawable.ic_female_100)
                  else -> binding.profile.setImageResource(R.drawable.ic_none_100)
               }
            }
         }
      }
   }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      binding = ApprovedUserImageItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      return ViewHolder(binding)
   }

   override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      user?.get(position)?.let { holder.bind(it) }
   }

   override fun getItemCount(): Int {
      return user?.size ?: 0
   }
}