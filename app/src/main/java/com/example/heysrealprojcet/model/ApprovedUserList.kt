package com.example.heysrealprojcet.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApprovedUserList(
   @SerialName("id") val id: Int,
   @SerialName("percentage") val percentage: Int,
) : java.io.Serializable
