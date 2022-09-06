package com.potatomeme.newsapp.helper

import java.text.SimpleDateFormat
import java.util.*


class AppHelper {
    companion object {
        fun intervalBetweenDate(beforeDate: String): String {
            val nowFormat = Date(System.currentTimeMillis())
            val beforeFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(beforeDate)
            val diffHor = (nowFormat.time - beforeFormat!!.time) / (60 * 60 * 1000)
            return if (diffHor < 24) {
                "$diffHor hours ago"
            } else {
                "${diffHor / 24} days ago"
            }
        }
    }
}