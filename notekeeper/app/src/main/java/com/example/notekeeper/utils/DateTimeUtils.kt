package com.example.notekeeper.utils

import java.text.SimpleDateFormat
import java.util.*

object DateTimeUtils {

    private val dateTimeFormat = SimpleDateFormat(Constants.NoteConstants.DATE_FORMAT)

    fun getCurrentDateTime(): String {
        val calendar = Calendar.getInstance()
        val currentDate = calendar.time
        return dateTimeFormat.format(currentDate)
    }

    fun epochTimeToString(epochTime: Long): String {
        val sdf = SimpleDateFormat(Constants.NoteConstants.DATE_FORMAT, Locale.getDefault())
        val date = Date(epochTime)
        return sdf.format(date)
    }

    fun stringToEpochTime(dateString: String): Long {
        val sdf = SimpleDateFormat(Constants.NoteConstants.DATE_FORMAT, Locale.getDefault())
        val date = sdf.parse(dateString)
        return date.time
    }
}
