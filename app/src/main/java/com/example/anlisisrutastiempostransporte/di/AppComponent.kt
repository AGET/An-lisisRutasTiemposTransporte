package com.example.anlisisrutastiempostransporte.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import jakarta.inject.Singleton

@Singleton
@Component(modules = [DataBaseModule::class, RepositoryModule::class, UseCaseModule::class])
interface AppComponent {

    fun inject(module: StationModule): StationComponent

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: Application): AppComponent
    }
}