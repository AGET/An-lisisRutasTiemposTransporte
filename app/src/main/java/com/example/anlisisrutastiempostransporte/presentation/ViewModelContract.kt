package com.example.anlisisrutastiempostransporte.presentation

import com.example.anlisisrutastiempostransporte.presentation.events.Event

interface ViewModelContract<T> {
    fun onEvent(event: Event<T>?)
}
