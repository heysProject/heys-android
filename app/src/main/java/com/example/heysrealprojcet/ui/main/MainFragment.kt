package com.example.heysrealprojcet.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.MainFragmentBinding
import com.example.heysrealprojcet.ui.main.category.CategoryRecyclerViewAdapter
import com.example.heysrealprojcet.util.UserPreference

class MainFragment : Fragment() {
   private lateinit var binding: MainFragmentBinding
   private lateinit var categoryRecyclerViewAdapter: CategoryRecyclerViewAdapter
   private lateinit var activityRecyclerViewAdapter: CategoryRecyclerViewAdapter
   private lateinit var typeList: MutableList<String>

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      // 상태바 색 변경
      val mWindow = requireActivity().window
      mWindow.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
      mWindow.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
      mWindow.statusBarColor = ContextCompat.getColor(requireActivity(), R.color.color_ff6e20)
      binding = MainFragmentBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)

      // 아이콘이 태마색으로 변경되는 것 막음
      binding.bottomNavigation.itemIconTintList = null
      var menu = binding.bottomNavigation.menu

      makeList()
      categoryRecyclerViewAdapter = CategoryRecyclerViewAdapter(type = typeList) { goToJoin() }
      activityRecyclerViewAdapter = CategoryRecyclerViewAdapter(type = typeList) { goToJoin() }

      binding.contestList.adapter = categoryRecyclerViewAdapter
      binding.contestList.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
      binding.activityList.adapter = activityRecyclerViewAdapter
      binding.activityList.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)

      // 로그인 안하면 회원가입 팝업 띄우도록
      if (isLogin()) {
         binding.studyContainer.setOnClickListener { goToStudy() }
      } else {
         with(binding) {
            contestAllText.setOnClickListener { goToContest() }
            activityAllText.setOnClickListener { goToActivity() }
            studyAllText.setOnClickListener { goToJoin() }
            studyContainer.setOnClickListener { goToStudy() }
         }
      }
      Log.d("=== accessToken ===", UserPreference.accessToken)

      binding.bottomNavigation.setOnItemSelectedListener { item ->
         when (item.itemId) {
            R.id.page_home -> {
//               menu.findItem(R.id.page_home).setIcon(R.drawable.intent_building)
               false
            }
            R.id.page_channel -> {
//                    findNavController().navigate(R.id.action_mainFragment_to_joinPopupFragment)
               false
            }
            R.id.page_mypage -> {
//                    findNavController().navigate(R.id.action_mainFragment_to_joinPopupFragment)
               false
            }
         }
         true
      }
   }

   private fun makeList() {
      typeList = mutableListOf("관심분야별", "마감 임박", "많이 찾는", "새로 열린")
   }

   private fun goToJoin() {
      findNavController().navigate(R.id.action_mainFragment_to_joinPopupFragment)
   }

   private fun goToStudy() {
      findNavController().navigate(R.id.action_mainFragment_to_studyFragment)
   }

   private fun goToContest() {
      findNavController().navigate(R.id.action_mainFragment_to_contestFragment)
   }

   private fun goToActivity() {
      findNavController().navigate(R.id.action_mainFragment_to_activityListFragment)
   }

   private fun isLogin(): Boolean = UserPreference.accessToken.isNotEmpty()
}