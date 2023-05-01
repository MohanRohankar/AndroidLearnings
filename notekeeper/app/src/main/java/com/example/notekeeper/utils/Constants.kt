package com.example.notekeeper.utils

import android.widget.Toast

class Constants {
    object NoteConstants {
        const val TOAST_SHORT_DURATION = Toast.LENGTH_SHORT
        const val TOAST_LONG_DURATION = Toast.LENGTH_LONG
        const val MAX_RETRY_COUNT = 3
        const val DEFAULT_TIMEOUT = 5000 // milliseconds
        const val DATE_FORMAT = "hh:mm:ss a dd-MM-yyyy"
    }
}
