package com.example.clock_screensaver

import android.icu.util.HebrewCalendar
import android.icu.util.ULocale
import android.service.dreams.DreamService
import android.util.Log

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

        val now = HebrewCalendar.getInstance(ULocale("en_US@calendar=hebrew"))

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
}