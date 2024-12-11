package com.palash.luckylottery.models

data class LotteryHomeResponse(val ticketId: Int, val ticketNumber: String, val selling: Boolean, var isSelected: Boolean = false)
