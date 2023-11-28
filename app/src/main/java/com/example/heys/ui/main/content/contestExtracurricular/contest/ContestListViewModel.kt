package com.example.heys.ui.main.content.contestExtracurricular.contest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.example.heys.model.network.Content
import com.example.heys.repository.ContentRepository
import com.example.heys.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class ContestListViewModel @Inject constructor(
   private val contentRepository: ContentRepository
) : BaseViewModel() {
   private val _contestList = MutableLiveData<List<Content>>()
   val contestList: LiveData<List<Content>> = _contestList

   val isCheked = MutableStateFlow(false)

   fun setContestList(list: List<Content>?) {
      _contestList.value = list ?: listOf()
   }

   fun getContestList(
      token: String,
      type: String,
      order: String? = "Default",
      interest: ArrayList<String>?,
      lastRecruitDate: String?,
      includeClosed: Boolean? = false,
      page: Int? = 1,
      limit: Int? = 30) = contentRepository.getContentList(token, type, order, interest, lastRecruitDate, includeClosed, page, limit).asLiveData()
}