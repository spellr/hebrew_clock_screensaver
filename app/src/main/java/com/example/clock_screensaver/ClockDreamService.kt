package com.example.clock_screensaver

import android.icu.util.Calendar
import android.icu.util.HebrewCalendar
import android.icu.util.ULocale
import android.service.dreams.DreamService
import android.util.Log
import android.widget.TextView
import java.lang.IllegalStateException

class ClockDreamService : DreamService() {
    private val tag: String
        get() {
            val tag = javaClass.simpleName
            return if (tag.length <= 23) tag else tag.substring(0, 23)
        }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        isInteractive = false
        isFullscreen = true
        setContentView(R.layout.clock_saver)

        updateDate()
    }

    private fun updateDate() {
        val now = HebrewCalendar.getInstance(ULocale("en_US@calendar=hebrew"))

        val weekDay = getWeekDay(now)
        requireViewById<TextView>(R.id.text_week_day).text = weekDay

        val date = StringBuilder()
            .append(now.get(HebrewCalendar.HOUR_OF_DAY))
            .append(":")
            .append(now.get(HebrewCalendar.MINUTE))
            .append(":")
            .append(now.get(HebrewCalendar.SECOND))
            .append(" ")
            .append(now.get(HebrewCalendar.DAY_OF_WEEK))
            .append(" ")
            .append(now.get(HebrewCalendar.DAY_OF_MONTH))
            .append("-")
            .append(now.get(HebrewCalendar.MONTH))
            .append("-")
            .append(now.get(HebrewCalendar.YEAR))

        Log.e(tag, date.toString())
    }

    private fun getWeekDay(now: Calendar): String {
        val dayString = when (val weekDay = now.get(HebrewCalendar.DAY_OF_WEEK)) {
            1 -> "ראשון"
            2 -> "שני"
            3 -> "שלישי"
            4 -> "רביעי"
            5 -> "חמישי"
            6 -> "שישי"
            7 -> "שבת"
            else -> throw IllegalStateException("Unknown week day $weekDay")
        }
        return "יום $dayString"

    }
}