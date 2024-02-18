package com.hey.heys.model.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hey.heys.api.ContentApi
import com.hey.heys.api.StudyApi
import javax.inject.Inject

class StudyPagingSource @Inject constructor(
   private val studyApi: StudyApi,
   private val token: String,
   private val interest: ArrayList<String>?,
   private val lastRecruitDate: String?,
   private val purposes: ArrayList<String>?,
   private val online: String?,
   private val location: String?,
   private val includeClosed: Boolean?
) : PagingSource<Int, ChannelList>() {
   override fun getRefreshKey(state: PagingState<Int, ChannelList>): Int? {
      return state.anchorPosition?.let { anchorPosition ->
         state.closestPageToPosition(anchorPosition)?.prevKey
      }
   }

   override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ChannelList> {
      val pageNum = params.key ?: 1

      return try {
         val response = studyApi.getStudyList(
            token = token,
            interests = interest,
            lastRecruitDate = lastRecruitDate,
            purposes = purposes,
            online = online,
            location = location,
            includeClosed = includeClosed,
            page = pageNum,
            limit = 30)

         val data = response.body()?.data
         LoadResult.Page(
            data = data!!,
            prevKey = if (pageNum == 1) null else pageNum - 1,
            nextKey = if (data.isEmpty()) null else pageNum + 1)
      } catch (e: Exception) {
         LoadResult.Error(e)
      }
   }
}