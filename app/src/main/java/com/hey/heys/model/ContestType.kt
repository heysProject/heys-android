package com.hey.heys.model

data class ContestType(
   val type: String,
   val resId: Int,
   var bool: Boolean,
   var order: String = "Default"
)