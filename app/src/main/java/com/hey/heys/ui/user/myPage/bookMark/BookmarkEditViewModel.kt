package com.hey.heys.ui.user.myPage.bookMark

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hey.heys.model.BookmarkData

class BookmarkEditViewModel : ViewModel() {

   private val bookmarkEditList = mutableListOf<BookmarkData>()

   private val _selectedBookmarkList = MutableLiveData<MutableList<BookmarkData>>()
   val selectedBookmarkList: LiveData<MutableList<BookmarkData>> = _selectedBookmarkList

   val isEnabled = MutableLiveData(false)

   fun addSelectedBookmark(item: BookmarkData) {
      bookmarkEditList.add(item)
      _selectedBookmarkList.value = bookmarkEditList
   }

   fun removeSelectedBookmark(item: BookmarkData) {
      bookmarkEditList.remove(item)
      _selectedBookmarkList.value = bookmarkEditList
   }
}