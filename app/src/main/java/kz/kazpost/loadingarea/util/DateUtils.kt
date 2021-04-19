package kz.kazpost.loadingarea.util

import kz.kazpost.loadingarea.BuildConfig
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale("RU"))

    fun getYesterdayDate(): String {
        val date = Calendar.getInstance()
        if (BuildConfig.DEBUG) {
            date.add(Calendar.DATE, -30)
        } else {
            date.add(Calendar.DATE, -1)
        }
        return dateFormat.format(date.time)
    }

    fun getTodayDate(): String {
        return dateFormat.format(System.currentTimeMillis())
    }
}