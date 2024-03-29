package com.hey.heys.ui.user.myPage.bookMark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.hey.heys.CustomSnackBar
import com.hey.heys.R
import com.hey.heys.databinding.BookmarkListFragmentBinding
import com.hey.heys.model.Contest
import com.hey.heys.ui.main.MainActivity
import com.hey.heys.ui.main.content.contestExtracurricular.contest.ContentItemRecyclerViewAdapter

class BookmarkListFragment : Fragment() {
   private lateinit var binding: BookmarkListFragmentBinding
   private lateinit var bookmarkItemRecyclerViewAdapter: ContentItemRecyclerViewAdapter
   private lateinit var bookmarkList: MutableList<Contest>

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      val mainActivity = activity as MainActivity
      mainActivity.hideBottomNavigation(true)
   }

   override fun onResume() {
      super.onResume()

      if (arguments?.getBoolean("isSuccess") == true) {
         com.hey.heys.CustomSnackBar(binding.root, "내 관심에서 삭제했어요!", null).show()
         arguments?.remove("isSuccess")
         // TODO 리스트 API 재호출
      }
   }

   override fun onDestroy() {
      super.onDestroy()
      val mainActivity = activity as MainActivity
      mainActivity.hideBottomNavigation(false)
   }

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = BookmarkListFragmentBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)

      makeList()

//      bookmarkItemRecyclerViewAdapter = ContestItemRecyclerViewAdapter(bookmarkList) { }
//      binding.bookmarkList.adapter = bookmarkItemRecyclerViewAdapter
//      binding.bookmarkList.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

      binding.ivBack.setOnClickListener { findNavController().popBackStack() }
      binding.tvEdit.setOnClickListener { goToBookmarkEdit() }
   }

   private fun goToBookmarkEdit() {
      findNavController().navigate(R.id.action_bookmarkListFragment_to_bookmarkEditFragment)
   }

   private fun makeList() {
      bookmarkList = mutableListOf(
         Contest(3, R.drawable.bg_study_list, "제 3회 방구석 아이디어톤 모집", "므모 MMO\n", 3),
         Contest(10, R.drawable.bg_study_list, "2022 Q4 MPED 국제 아트앤디자..", "MPED / emdash, MyProject", 500),
         Contest(5, R.drawable.bg_study_list, "아산관광 숏폼 공모전 (찰나의 아산)", "아산시", 1000),
         Contest(2, R.drawable.bg_study_list, "2022년 K-이노스 창업아이디어경진..", "건국대학교 캠퍼스타운 사업단", 2)
      )
   }
}