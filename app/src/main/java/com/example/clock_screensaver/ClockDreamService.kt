package com.example.clock_screensaver

import android.icu.util.Calendar
import android.icu.util.HebrewCalendar
import android.icu.util.ULocale
import android.service.dreams.DreamService
import android.widget.TextView

class ClockDreamService : DreamService() {

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
        val time = getTime(now)
        val date = getDate(now)

        requireViewById<TextView>(R.id.text_week_day).text = weekDay
        requireViewById<TextView>(R.id.text_time).text = time
        requireViewById<TextView>(R.id.text_hebrew_date).text = date
    }

    private fun getDate(now: Calendar): String {
        val day = dayToString(now.get(HebrewCalendar.DAY_OF_MONTH))
        val month = monthToString(now.get(HebrewCalendar.MONTH), isLeapYear(now))
        return "$day $month"
    }

    private fun monthToString(month: Int, leapYear: Boolean): String {
        return when (month) {
            HebrewCalendar.TISHRI -> "תשרי"
            HebrewCalendar.HESHVAN -> "חשוון"
            HebrewCalendar.KISLEV -> "כסלו"
            HebrewCalendar.TEVET -> "טבת"
            HebrewCalendar.SHEVAT -> "שבט"
            HebrewCalendar.ADAR_1 -> "אדר א'"
            HebrewCalendar.ADAR -> if (leapYear) "אדר ב'" else "אדר"
            HebrewCalendar.NISAN -> "ניסן"
            HebrewCalendar.IYAR -> "אייר"
            HebrewCalendar.SIVAN -> "סיוון"
            HebrewCalendar.TAMUZ -> "תמוז"
            HebrewCalendar.AV -> "אב"
            HebrewCalendar.ELUL -> "אלול"
            else -> throw IllegalStateException("Unknown month $month")
        }
    }

    private fun isLeapYear(now: Calendar): Boolean {
        val year = now.get(HebrewCalendar.YEAR)
        val leapRemainders = intArrayOf(0, 3, 6, 8, 11, 14, 17)
        return leapRemainders.contains(year % 19)
    }

    private fun dayToString(monthDay: Int): String {
        val tens = monthDay / 10
        val ones = monthDay % 10

        val tensStr = when (tens) {
            0 -> ""
            1 -> "י"
            2 -> "כ"
            3 -> "ל"
            else -> throw IllegalStateException("Unknown day $monthDay")
        }
        val onesStr = when (ones) {
            0 -> ""
            1 -> "א"
            2 -> "ב"
            3 -> "ג"
            4 -> "ד"
            5 -> "ה"
            6 -> "ו"
            7 -> "ז"
            8 -> "ח"
            9 -> "ט"
            else -> throw IllegalStateException("Unknown day $monthDay")
        }

        return if (monthDay == 15) {
            "ט\"ו"
        } else if (monthDay == 16) {
            "ט\"ז"
        } else if (ones == 0) {
            "$tensStr'"
        } else if (tens == 0) {
            "$onesStr'"
        } else {
            "$tensStr\"$onesStr"
        }
    }

    private fun getTime(now: Calendar): String {
        return "%02d:%02d:%02d".format(
            now.get(HebrewCalendar.HOUR_OF_DAY),
            now.get(HebrewCalendar.MINUTE),
            now.get(HebrewCalendar.SECOND)
        )
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