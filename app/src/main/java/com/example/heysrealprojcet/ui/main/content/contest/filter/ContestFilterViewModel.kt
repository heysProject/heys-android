package com.example.heysrealprojcet.ui.main.content.contest.filter

import android.view.View
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class ContestFilterViewModel : ViewModel() {
   private val interestMax = 3
   private val activityMax = 1
   private val timeMax = 1

   private var interestTotal = MutableStateFlow(0)
   private var activityTotal = MutableStateFlow(0)
   private var timeTotal = MutableStateFlow(0)

   private val interestArray = mutableListOf<String>()
   private val activityArray = mutableListOf<String>()
   private val timeArray = mutableListOf<String>()

   fun onClickInterest(v: View) {
      val item = v.tag.toString()

      if (interestTotal.value < interestMax) {
         if (v.isSelected) {
            v.isSelected = false
            interestTotal.value -= 1
            interestArray.remove(item)
         } else {
            v.isSelected = true
            interestTotal.value += 1
            interestArray.add(item)
         }
      } else {
         if (v.isSelected) {
            v.isSelected = false
            interestTotal.value -= 1
            interestArray.remove(item)
         }
      }
   }

   fun onClickActivity(v: View) {
      val item = v.tag.toString()

      if (activityTotal.value < activityMax) {
         if (v.isSelected) {
            v.isSelected = false
            activityTotal.value -= 1
            activityArray.remove(item)
         } else {
            v.isSelected = true
            activityTotal.value += 1
            activityArray.add(item)
         }
      } else {
         if (v.isSelected) {
            v.isSelected = false
            activityTotal.value -= 1
            activityArray.remove(item)
         }
      }
   }

   fun onClickTime(v: View) {
      val item = v.tag.toString()

      if (timeTotal.value < timeMax) {
         if (v.isSelected) {
            v.isSelected = false
            timeTotal.value -= 1
            timeArray.remove(item)
         } else {
            v.isSelected = true
            timeTotal.value += 1
            timeArray.add(item)
         }
      } else {
         if (v.isSelected) {
            v.isSelected = false
            timeTotal.value -= 1
            timeArray.remove(item)
         }
      }
   }
}