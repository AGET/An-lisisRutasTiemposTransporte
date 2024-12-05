package com.example.anlisisrutastiempostransporte.data

interface LocalPreferencesDataSource {
    fun setBooleanValue(key: String, value: Boolean)
    fun getBooleanValue(key: String): Boolean
}
