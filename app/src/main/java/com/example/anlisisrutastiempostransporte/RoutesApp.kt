package com.example.anlisisrutastiempostransporte

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.anlisisrutastiempostransporte.di.AppComponent
import com.example.anlisisrutastiempostransporte.di.DaggerAppComponent

class RoutesApp : Application() {

    //region Fields
    lateinit var component: AppComponent
        private set
    //endregion

    //region Override Methods & Callbacks
    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        component = initAppComponent()
    }
    //endregion

    //region Private Methods
    private fun initAppComponent() = DaggerAppComponent.factory().create(this)
    //endregion
}
