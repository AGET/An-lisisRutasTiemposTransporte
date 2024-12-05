package com.example.anlisisrutastiempostransporte.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Station (
    val id: Int = 0,
    val name: String,
    /**
     * Es la distancia con respecto a la terminal, esta debe  ser mayor a 0,
     * ya que si es 0 significa que es la misma terminal, esta sera data en kilometros
     */
    val startTerminalLength: Double) : Parcelable
