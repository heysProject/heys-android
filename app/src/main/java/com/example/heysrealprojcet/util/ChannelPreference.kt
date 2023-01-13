package com.example.heysrealprojcet.util

import com.chibatching.kotpref.KotprefModel
import com.chibatching.kotpref.gsonpref.gsonPref

object ChannelPreference : KotprefModel() {
   fun reset() {
      channelPurpose = ""
      channelForm = ""
      channelRegion = ""
      channelCapacity = 0
      channelRecruitmentMethod = ""
      channelRecruitLastDay = ""
      channelRecruitLastTime = ""
   }

   /*
   채널목적
    */
   var channelPurpose by stringPref()

   /*
   활동 형태
    */
   var channelForm by stringPref()

   /*
   활동 지역
    */
   var channelRegion by stringPref()

   /*
   참여 인원
    */
   var channelCapacity by intPref()

   /*
   모집 방식
    */
   var channelRecruitmentMethod by stringPref()

   /*
  모집 마감 날짜
   */
   var channelRecruitLastDay by stringPref()

   /*
  모집 마감 시간
   */
   var channelRecruitLastTime by stringPref()

   /*
  관심 분야
   */
   var channelInterest by gsonPref(arrayListOf<String>())
}
