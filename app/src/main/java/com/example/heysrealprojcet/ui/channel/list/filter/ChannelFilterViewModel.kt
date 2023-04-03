package com.example.heysrealprojcet.ui.channel.list.filter

import android.graphics.Typeface
import android.view.View
import android.widget.Button
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kizitonwose.calendarview.CalendarView
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.LocalDate
import java.time.YearMonth

class ChannelFilterViewModel(private val calendarView: CalendarView) : ViewModel() {

   private val _selectedForm = MutableLiveData<String>()
   val selectedForm: LiveData<String> = _selectedForm

   private var choiceInterest = mutableListOf<View>()
   private var choiceActivity: View? = null
   var choiceRegion = mutableListOf<View>()
   private var choicePurpose: View? = null

   private val interestMax = 3
   private val activityMax = 1
   private val regionMax = 1
   private val purposeMax = 1

   private var interestTotal = MutableStateFlow(0)
   private var activityTotal = MutableStateFlow(0)
   var regionTotal = MutableStateFlow(0)
   private var purposeTotal = MutableStateFlow(0)

   private val interestArray = mutableListOf<String>()
   private val activityArray = mutableListOf<String>()
   val regionArray = mutableListOf<String>()
   private val purposeArray = mutableListOf<String>()

   // 달력
   var selectedDate: LocalDate? = null

   private val _calendarPosition = MutableLiveData(YearMonth.now().month.value)
   val calendarPosition: LiveData<Int> = _calendarPosition

   private val _calendarDate = MutableLiveData<LocalDate>()
   val calendarDate: LiveData<LocalDate> = _calendarDate

   fun plusPosition() {
      _calendarPosition.value = _calendarPosition.value!! + 1
   }

   fun minusPosition() {
      _calendarPosition.value = _calendarPosition.value!! - 1
   }

   fun setPosition(value: Int) {
      _calendarPosition.value = value
   }

   fun setCalendarDate() {
      _calendarDate.value = selectedDate
   }

   fun onClickInterest(v: View) {
      val item = v.tag.toString()

      val button = v as Button

      if (interestTotal.value < interestMax) {
         if (v.isSelected) {
            choiceInterest.remove(v)
            v.isSelected = false
            button.setTypeface(null, Typeface.NORMAL)
            interestTotal.value -= 1
            interestArray.remove(item)
         } else {
            choiceInterest.add(v)
            v.isSelected = true
            button.setTypeface(null, Typeface.BOLD)
            interestTotal.value += 1
            interestArray.add(item)
         }
      } else {
         if (v.isSelected) {
            choiceInterest.remove(v)
            v.isSelected = false
            button.setTypeface(null, Typeface.NORMAL)
            interestTotal.value -= 1
            interestArray.remove(item)
         }
      }
   }

   fun onClickActivity(v: View) {
      val item = v.tag.toString()

      val button = v as Button
      _selectedForm.value = button.text.toString()

      if (activityTotal.value < activityMax) {
         if (v.isSelected) {
            v.isSelected = false
            button.setTypeface(null, Typeface.NORMAL)
            activityTotal.value -= 1
            activityArray.remove(item)
         } else {
            choiceActivity = v
            v.isSelected = true
            button.setTypeface(null, Typeface.BOLD)
            activityTotal.value += 1
            activityArray.add(item)
         }
      } else {
         if (v.isSelected) {
            v.isSelected = false
            button.setTypeface(null, Typeface.NORMAL)
            activityTotal.value -= 1
            activityArray.remove(item)
         } else {
            choiceActivity?.isSelected = false
            (choiceActivity as Button).setTypeface(null, Typeface.NORMAL)
            activityArray.remove(activityArray[0])
            v.isSelected = true
            button.setTypeface(null, Typeface.BOLD)
            choiceActivity = v
            activityArray.add(item)
         }

//         if (choiceRegion.isNotEmpty()) {
//            choiceRegion[0].isSelected = false
//            (choiceRegion[0] as Button).setTypeface(null, Typeface.NORMAL)
//            choiceRegion.remove(choiceRegion[0])
//            regionTotal.value -= 1
//            regionArray.remove(regionArray[0])
//         }
      }
   }

   fun onClickRegion(v: View) {
      val item = v.tag.toString()

      val button = v as Button

      if (activityArray.isNotEmpty() && activityArray[0].contains("offline")) {
         if (regionTotal.value < regionMax) {
            if (v.isSelected) {
               choiceRegion.remove(choiceRegion[0])
               v.isSelected = false
               button.setTypeface(null, Typeface.NORMAL)
               regionTotal.value -= 1
               regionArray.remove(item)
            } else {
               choiceRegion.add(v)
               v.isSelected = true
               button.setTypeface(null, Typeface.BOLD)
               regionTotal.value += 1
               regionArray.add(item)
            }
         } else {
            if (v.isSelected) {
               choiceRegion.remove(choiceRegion[0])
               v.isSelected = false
               button.setTypeface(null, Typeface.NORMAL)
               regionTotal.value -= 1
               regionArray.remove(item)
            } else {
               choiceRegion[0].isSelected = false
               (choiceRegion[0] as Button).setTypeface(null, Typeface.NORMAL)
               choiceRegion.remove(choiceRegion[0])
               regionArray.remove(regionArray[0])
               v.isSelected = true
               button.setTypeface(null, Typeface.BOLD)
               choiceRegion.add(v)
               regionArray.add(item)
            }
         }
      }
   }

   fun onClickPurpose(v: View) {
      val item = v.tag.toString()

      var button = v as Button

      if (purposeTotal.value < purposeMax) {
         if (v.isSelected) {
            v.isSelected = false
            button.setTypeface(null, Typeface.NORMAL)
            purposeTotal.value -= 1
            purposeArray.remove(item)
         } else {
            choicePurpose = v
            v.isSelected = true
            button.setTypeface(null, Typeface.BOLD)
            purposeTotal.value += 1
            purposeArray.add(item)
         }
      } else {
         if (v.isSelected) {
            v.isSelected = false
            button.setTypeface(null, Typeface.NORMAL)
            purposeTotal.value -= 1
            purposeArray.remove(item)
         } else {
            choicePurpose?.isSelected = false
            (choicePurpose as Button).setTypeface(null, Typeface.NORMAL)
            purposeArray.remove(purposeArray[0])
            v.isSelected = true
            button.setTypeface(null, Typeface.BOLD)
            choicePurpose = v
            purposeArray.add(item)
         }
      }
   }

   fun onClickInit() {
      for (i in choiceInterest.indices) {
         choiceInterest[i].isSelected = false
         (choiceInterest[i] as Button).setTypeface(null, Typeface.NORMAL)
      }
      interestTotal.value = 0
      choiceInterest.clear()

      choiceActivity?.isSelected = false
      if (choiceActivity != null) {
         (choiceActivity as Button).setTypeface(null, Typeface.NORMAL)
      }
      activityTotal.value = 0
      activityArray.clear()

      choiceRegion[0].isSelected = false
      if (choiceRegion.isNotEmpty()) {
         (choiceRegion[0] as Button).setTypeface(null, Typeface.NORMAL)
         choiceRegion.remove(choiceRegion[0])
      }
      regionTotal.value = 0
      regionArray.clear()

      choicePurpose?.isSelected = false
      if (choicePurpose != null) {
         (choicePurpose as Button).setTypeface(null, Typeface.NORMAL)
      }
      purposeTotal.value = 0
      purposeArray.clear()

      selectedDate = null
      _calendarDate.value = selectedDate
      calendarView.notifyCalendarChanged()
   }
}