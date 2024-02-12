package it.saimao.diario

import android.app.Application
import it.saimao.diario.data.AppContainer
import it.saimao.diario.data.DefaultAppContainer

class DiarioApplication: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }
}