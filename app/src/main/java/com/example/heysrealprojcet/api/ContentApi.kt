package com.example.heysrealprojcet.api

import com.example.heysrealprojcet.model.network.response.ContentDetailResponse
import com.example.heysrealprojcet.model.network.response.ContentListResponse
import retrofit2.Response
import retrofit2.http.*

interface ContentApi {
   @GET("content/extra")
   suspend fun getContentList(
      @Header("Authorization") token: String,
      @Query("type") type: String,
      @Query("interests") interests: ArrayList<String>?,
      @Query("lastRecruitDate") lastRecruitDate: String?,
      @Query("includeClosed") includeClosed: Boolean?,
      @Query("page") page: Int?,
      @Query("limit") limit: Int?,
   ): Response<ContentListResponse>

   @GET("content/extra/{id}")
   suspend fun getContentDetail(@Header("Authorization") token: String, @Path(value = "id") id: Int): Response<ContentDetailResponse>

   @PUT("content/view-count-up/{contentId}")
   suspend fun contentViewCountUp(@Header("Authorization") token: String, @Path(value = "contentId") id: Int): Response<String>
}