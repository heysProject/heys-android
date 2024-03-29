package com.hey.heys.ui.user.channel.waitingChannel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hey.heys.App
import com.hey.heys.R
import com.hey.heys.databinding.WaitingChannelItemViewBinding
import com.hey.heys.model.network.MyChannel
import com.hey.heys.ui.user.channel.MyChannelDiffCallback

class WaitingChannelItemRecyclerViewAdapter(private val onClickListener: (Int) -> Unit) :
   ListAdapter<MyChannel, WaitingChannelItemRecyclerViewAdapter.ViewHolder>(MyChannelDiffCallback) {
   private lateinit var binding: WaitingChannelItemViewBinding

   inner class ViewHolder(private val binding: WaitingChannelItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
      fun bind(channel: MyChannel) {
         binding.tvTitle.text = channel.name
         binding.tvLeader.text = channel.leaderName
         Glide.with(com.hey.heys.App.getInstance().applicationContext)
            .load(channel.thumbnailUri)
            .error(R.drawable.bg_thumbnail_default).into(binding.imgThumbnail)
         binding.btnCancel.setOnClickListener { onClickListener.invoke(channel.id) }
      }
   }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      binding = WaitingChannelItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      return ViewHolder(binding)
   }

   override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      holder.bind(getItem(position))
   }
}