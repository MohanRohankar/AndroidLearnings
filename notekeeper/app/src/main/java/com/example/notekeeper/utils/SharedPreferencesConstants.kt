package com.example.notekeeper.utils

import android.widget.Toast

class SharedPreferencesConstants {

    object Types {
        val STRING = String::class.java
        val INT = Int::class.java
        val BOOLEAN = Boolean::class.java
        val FLOAT = Float::class.java
        val LONG = Long::class.java
        val STRING_SET = Set::class.java
    }

    object PreferenceName {
        val Permissions = "Permissions"
        // add more Preference file name as needed
    }

    // add more Preference file name as needed
    object Permissions {
         val LOCATION_PERMISSION_GRANTED = Pair("LOCATION_PERMISSION_GRANTED", Types.STRING)
         val STORAGE_PERMISSION_GRANTED = Pair("STORAGE_PERMISSION_GRANTED", Types.INT)
         val CAMERA_PERMISSION_GRANTED = Pair("CAMERA_PERMISSION_GRANTED", Types.BOOLEAN)
         // add more variables as needed
     }

}