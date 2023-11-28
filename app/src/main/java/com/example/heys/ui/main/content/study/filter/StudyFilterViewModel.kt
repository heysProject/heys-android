package com.example.heys.ui.main.content.study.filter

import android.graphics.Typeface
import android.view.View
import android.widget.Button
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.heys.Event
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.LocalDate
import java.time.YearMonth

class StudyFilterViewModel : ViewModel() {
   private var choiceInterest = mutableListOf<View>()
   private var choiceActivity: View? = null
   private var choiceRegion: View? = null
   private var choicePurpose: View? = null

   private val interestMax = 3
   private val activityMax = 1
   private val regionMax = 1
   private val purposeMax = 1

   private var interestTotal = MutableStateFlow(0)
   private var activityTotal = MutableStateFlow(0)
   private var regionTotal = MutableStateFlow(0)
   private var purposeTotal = MutableStateFlow(0)

   private val interestArray = mutableListOf<String>()
   private val activityArray = mutableListOf<String>()
   private val regionArray = mutableListOf<String>()
   private val purposeArray = mutableListOf<String>()

   // 달력
   var selectedDate: LocalDate? = null

   private val _calendarPosition = MutableLiveData(YearMonth.now().month.value)
   val calendarPosition: LiveData<Int> = _calendarPosition

   private val _calendarDate = MutableLiveData<LocalDate>()
   val calendarDate: LiveData<LocalDate> = _calendarDate

   private val _isCalendarInit = MutableLiveData<Event<Boolean>>()
   val isCalendarInit: LiveData<Event<Boolean>> = _isCalendarInit

   fun onClickInterest(v: View) {
      val item = v.tag.toString()

      var button = v as Button

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

            // 대면·비대면, 대면 클릭 해제시 지역 클릭 해제
            if (choiceRegion != null) {
               choiceRegion!!.isSelected = false
               (choiceRegion as Button).setTypeface(null, Typeface.NORMAL)
               regionTotal.value -= 1
               regionArray.remove(regionArray[0])
               choiceRegion = null
            }
         } else {
            choiceActivity?.isSelected = false
            (choiceActivity as Button).setTypeface(null, Typeface.NORMAL)
            activityArray.remove(activityArray[0])
            v.isSelected = true
            button.setTypeface(null, Typeface.BOLD)
            choiceActivity = v
            activityArray.add(item)

            if (choiceRegion != null) {
               choiceRegion!!.isSelected = false
               (choiceRegion as Button).setTypeface(null, Typeface.NORMAL)
               regionTotal.value -= 1
               regionArray.remove(regionArray[0])
               choiceRegion = null
            }
         }
      }
   }

   fun onClickRegion(v: View) {
      val item = v.tag.toString()

      var button = v as Button

      if (activityArray.isNotEmpty() && activityArray[0].contains("online")) {
         if (regionTotal.value < regionMax) {
            if (v.isSelected) {
               choiceRegion = null
               v.isSelected = false
               button.setTypeface(null, Typeface.NORMAL)
               regionTotal.value -= 1
               regionArray.remove(item)
            } else {
               choiceRegion = v
               v.isSelected = true
               button.setTypeface(null, Typeface.BOLD)
               regionTotal.value += 1
               regionArray.add(item)
            }
         } else {
            if (v.isSelected) {
               choiceRegion = null
               v.isSelected = false
               button.setTypeface(null, Typeface.NORMAL)
               regionTotal.value -= 1
               regionArray.remove(item)
            } else {
               choiceRegion?.isSelected = false
               (choiceRegion as Button).setTypeface(null, Typeface.NORMAL)
               regionArray.remove(regionArray[0])
               v.isSelected = true
               button.setTypeface(null, Typeface.BOLD)
               choiceRegion = v
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

      choiceRegion?.isSelected = false
      if (choiceRegion != null) {
         (choiceRegion as Button).setTypeface(null, Typeface.NORMAL)
      }
      regionTotal.value = 0
      choiceRegion = null
      regionArray.clear()

      choicePurpose?.isSelected = false
      if (choicePurpose != null) {
         (choicePurpose as Button).setTypeface(null, Typeface.NORMAL)
      }
      purposeTotal.value = 0
      purposeArray.clear()

      selectedDate = null
      _calendarDate.value = selectedDate
      _isCalendarInit.value = Event(true)
   }

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
}