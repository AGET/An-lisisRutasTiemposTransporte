package com.example.anlisisrutastiempostransporte.preferencesmanager

import android.content.SharedPreferences
import com.example.anlisisrutastiempostransporte.data.LocalPreferencesDataSource

class PreferencesDataSources(
    private val preferences: SharedPreferences
) : LocalPreferencesDataSource {

    //region methods
    override fun setBooleanValue(key: String, value: Boolean) {
        val editor = preferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    override fun getBooleanValue(key: String): Boolean {
        return preferences.getBoolean(key, false)
    }


    companion object {
        const val FIRST_LAUNCHED = "FIRST_LAUNCHED"
    }
    //end methods
}
