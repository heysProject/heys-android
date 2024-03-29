package com.hey.heys.ui.user.myPage

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.dynamiclinks.DynamicLink
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.google.firebase.dynamiclinks.ktx.androidParameters
import com.google.firebase.dynamiclinks.ktx.dynamicLink
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.ktx.Firebase
import com.hey.heys.R
import com.hey.heys.databinding.MyPageFragmentBinding
import com.hey.heys.enums.Gender
import com.hey.heys.model.network.MyPage
import com.hey.heys.model.network.NetworkResult
import com.hey.heys.util.UserPreference
import com.kakao.sdk.common.util.KakaoCustomTabsClient
import com.kakao.sdk.share.ShareClient
import com.kakao.sdk.share.WebSharerClient
import com.kakao.sdk.template.model.Button
import com.kakao.sdk.template.model.Content
import com.kakao.sdk.template.model.FeedTemplate
import com.kakao.sdk.template.model.Link
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageFragment : Fragment() {
   private lateinit var binding: MyPageFragmentBinding
   private val viewModel: MyPageViewModel by viewModels()

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
   ): View? {
      binding = MyPageFragmentBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this
      getMyInfo()

      with(binding) {
         goToSetting.setOnClickListener { goToSetting() }
         editProfile.setOnClickListener { goToProfileEdit() }
         engagedChannelContainer.setOnClickListener { goToEngagedChannel() }
         waitingChannelContainer.setOnClickListener { goToWaitingChannel() }
         bookmarkChannel.setOnClickListener { goToBookmarkCollection() }
         btnShare.setOnClickListener { kakaoShare() }
      }
   }

   private fun goToSetting() {
      findNavController().navigate(R.id.action_myPageFragment_to_settingFragment)
   }

   private fun goToEngagedChannel() {
      findNavController().navigate(R.id.action_myPageFragment_to_engagedChannelListFragment)
   }

   private fun goToWaitingChannel() {
      findNavController().navigate(R.id.action_myPageFragment_to_waitingChannelListFragment)
   }

   private fun goToProfileEdit() {
      findNavController().navigate(R.id.action_myPageFragment_to_profileEditFragment)
   }

   private fun goToBookmarkCollection() {
      findNavController().navigate(R.id.action_myPageFragment_to_bookmarkListFragment)
   }

   private fun getMyInfo() {
      val token = UserPreference.accessToken
      viewModel.getMyInfo("Bearer $token")
      viewModel.response.observe(viewLifecycleOwner) { response ->
         val alert = AlertDialog.Builder(requireContext())

         when (response) {
            is NetworkResult.Success -> {
               response.data?.user?.let { setMyPageInfo(it) }
            }

            is NetworkResult.Error -> {
               alert.setTitle("마이페이지 로딩 실패")
                  .setMessage("마이페이지 로딩에 실패했습니다.")
                  .create()
                  .show()
            }

            else -> {
               alert.setTitle("마이페이지 로딩 중")
                  .setMessage("마이페이지 로딩이 지연되고 있습니다.")
                  .create()
                  .show()
            }
         }
      }
   }

   private fun setMyPageInfo(myPage: MyPage) {
      with(binding) {
         // 자기소개
         if (myPage.introduce.isNullOrBlank()) {
            introduce.text = "아직 소개할 내용이 없어요."
            introduce.setTextColor(ContextCompat.getColor(requireContext(), R.color.color_828282))
         } else {
            introduce.text = "\"${myPage.introduce}\""
            introduce.setTextColor(ContextCompat.getColor(requireContext(), R.color.color_262626))
         }

         // 이름
         name.text = myPage.name

         // mbti
         if (myPage.userPersonality.isNullOrBlank()) {
            mbti.text = "????"
            mbti.isEnabled = false
         } else {
            mbti.text = myPage.userPersonality
            mbti.isEnabled = true
         }

         // 관심분야/직무
         var interestString = ""
         myPage.interests.forEach { interestString += "#$it " }
         interestContent.text = interestString

         // 직업
         if (myPage.job.isNullOrBlank()) {
            job.apply {
               text = "아직 소개할 직업이 없어요."
               setTextColor(ContextCompat.getColor(requireContext(), R.color.color_4d262626))
            }
         } else {
            job.apply {
               text = myPage.job
               setTextColor(ContextCompat.getColor(requireContext(), R.color.color_262626))
            }
         }

         // 사용가능한 스킬
         if (myPage.capability.isNullOrBlank()) {
            skill.apply {
               text = "아직 소개할 스킬이 없어요."
               setTextColor(ContextCompat.getColor(requireContext(), R.color.color_4d262626))
            }

         } else {
            var skillString = ""
            myPage.capability.split(",").forEach {
               skillString += "#$it "
            }
            skill.apply {
               text = skillString
               setTextColor(ContextCompat.getColor(requireContext(), R.color.color_262626))
            }
         }
         setByPercent(myPage.percentage, myPage.gender)
         setProfileLink(myPage.profileLinks)

         joinChannel.text = "${myPage.joinChannelCount}"
         waitingChannel.text = "${myPage.waitingChannelCount}"
      }
   }

   private fun setProfileLink(links: Array<String>) {
      links.forEach { link ->
         if (link.contains("kakao")) {
            binding.imgKakao.apply {
               setImageResource(R.drawable.ic_kakao_checked)
               setOnClickListener { goToWebView(link) }
            }
         } else if (link.contains("behance")) {
            binding.imgBehance.apply {
               setImageResource(R.drawable.ic_behance_checked)
               setOnClickListener { goToWebView(link) }
            }
         } else if (link.contains("insta")) {
            binding.imgInstagram.apply {
               setImageResource(R.drawable.ic_instagram_checked)
               setOnClickListener { goToWebView(link) }
            }
         } else if (link.contains("github")) {
            binding.imgGithub.apply {
               setImageResource(R.drawable.ic_github_checked)
               setOnClickListener { goToWebView(link) }
            }
         } else {
            if (!link.isNullOrBlank()) {
               binding.imgDefault.apply {
                  setImageResource(R.drawable.ic_clip_checked)
                  setOnClickListener { goToWebView(link) }
               }
            }
         }
      }
   }

   private fun setByPercent(percentage: Int, gender: String) {
      // 프로필 이미지 설정
      when (percentage) {
         in 0..49 -> {
            binding.editText.text = "프로필이 채워지지 않았어요"
            when (gender) {
               Gender.Male.genderEnglish -> binding.profileImage.setImageResource(R.drawable.ic_male_0)
               Gender.Female.genderEnglish -> binding.profileImage.setImageResource(R.drawable.ic_female_0)
               else -> binding.profileImage.setImageResource(R.drawable.ic_none_0)
            }
         }

         in 50..99 -> {
            binding.editText.text = "프로필을 더 채워주세요."
            when (gender) {
               Gender.Male.genderEnglish -> binding.profileImage.setImageResource(R.drawable.ic_male_50)
               Gender.Female.genderEnglish -> binding.profileImage.setImageResource(R.drawable.ic_female_50)
               else -> binding.profileImage.setImageResource(R.drawable.ic_none_50)
            }
         }

         100 -> {
            binding.editText.text = "완성된 프로필 카드를 만들었어요."
            when (gender) {
               Gender.Male.genderEnglish -> binding.profileImage.setImageResource(R.drawable.ic_male_100)
               Gender.Female.genderEnglish -> binding.profileImage.setImageResource(R.drawable.ic_female_100)
               else -> binding.profileImage.setImageResource(R.drawable.ic_none_100)
            }
         }
      }
   }

   private fun goToWebView(url: String) {
      findNavController().navigate(
         R.id.action_myPageFragment_to_webViewFragment,
         bundleOf("url" to url))
   }

   private fun shareProfile() {
      val profileLink = "https://heys.page.link/profile"
      FirebaseDynamicLinks.getInstance()
         .createDynamicLink()
         .setLink(Uri.parse(profileLink))
         .setDomainUriPrefix("https://heys.page.link")
         .setAndroidParameters(
            DynamicLink.AndroidParameters.Builder()
               .build())
         .buildShortDynamicLink()
         .addOnSuccessListener { shortDynamicLink ->
            val intent = Intent()
            intent.apply {
               action = Intent.ACTION_SEND
               putExtra(Intent.EXTRA_TEXT, shortDynamicLink.shortLink.toString())
               type = "text/plain"
            }
            startActivity(intent)
         }
   }

   private fun kakaoShare() {
      val feed = createFeed()

      if (ShareClient.instance.isKakaoTalkSharingAvailable(requireContext())) {
         ShareClient.instance.shareDefault(requireContext(), feed) { sharingResult, error ->
            if (error != null) {
               Log.e("kakaoShare", "카카오톡 공유 실패", error)
            } else if (sharingResult != null) {
               Log.d("kakaoShare", "카카오톡 공유 성공 ${sharingResult.intent}")
               startActivity(sharingResult.intent)

               Log.w("kakaoShare", "Warning Msg: ${sharingResult.warningMsg}")
               Log.w("kakaoShare", "Argument Msg: ${sharingResult.argumentMsg}")
            }
         }
      } else {
         val sharerUrl = WebSharerClient.instance.makeDefaultUrl(feed)
         try {
            KakaoCustomTabsClient.openWithDefault(requireContext(), sharerUrl)
         } catch (e: UnsupportedOperationException) {
            Log.e("sharerUrl", "카카오톡 공유 실패", e.cause)
         }
      }
   }

   private fun createFeed(): FeedTemplate {
      val dynamicLink = Firebase.dynamicLinks.dynamicLink {
         link = Uri.parse("https://heys.page.link/profile?id=${viewModel.response.value?.data?.user?.userId}")
         domainUriPrefix = "https://heys.page.link"
         androidParameters {
            DynamicLink.AndroidParameters.Builder()
               .build()
         }
      }

      return FeedTemplate(
         content = Content(
            title = "함께 성장하는 청춘을 만들어가요!",
            description = "모바일 앱에서 확인해보세요.",
            imageUrl = "https://i.ibb.co/HxMF3Dx/img-heys-logo.png",
            link = Link(
               mobileWebUrl = dynamicLink.uri.toString())),

         buttons = listOf(
            Button(
               "앱으로 이동",
               Link(
                  mobileWebUrl = dynamicLink.uri.toString())
            )))
   }
}