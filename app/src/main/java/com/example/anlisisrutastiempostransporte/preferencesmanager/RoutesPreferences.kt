package com.example.anlisisrutastiempostransporte.preferencesmanager

import android.content.Context
import android.content.SharedPreferences

object RoutesPreferences {

    //region Companion Object

    private const val PREFERENCES_NAME = "routes_preferences"

    @Synchronized
    fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
    }
    //endregion
}
