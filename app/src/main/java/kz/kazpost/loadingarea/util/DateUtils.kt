package kz.kazpost.loadingarea.util

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale("RU"))

    fun getYesterdayDate(): String {
        val date = Calendar.getInstance()
        date.add(Calendar.DATE, -8)
        return dateFormat.format(date.time)
    }

    fun getTodayDate(): String {
        return dateFormat.format(System.currentTimeMillis())
    }
}