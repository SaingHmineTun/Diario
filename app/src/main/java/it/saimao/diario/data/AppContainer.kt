package it.saimao.diario.data

import android.content.Context

interface AppContainer {
    val diarioRepository: DiarioRepository
}

class DefaultAppContainer(context: Context) : AppContainer {

    private val database = DiarioDatabase.getDatabase(context)

    override val diarioRepository: DiarioRepository
        get() = DiarioRepository(database.diarioDao())

}