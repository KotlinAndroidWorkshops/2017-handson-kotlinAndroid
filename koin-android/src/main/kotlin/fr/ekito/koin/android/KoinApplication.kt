package fr.ekito.koin.android

import android.app.Application
import fr.ekito.koin.Context
import fr.ekito.koin.Koin
import kotlin.reflect.KClass

/**
 * Created by arnaud on 01/06/2017.
 */
open class KoinApplication(val clazz: KClass<out AndroidModule>) : Application() {

    lateinit var koinContext: Context

    override fun onCreate() {
        super.onCreate()

        koinContext = Koin()
                .init(this)
                .build(clazz)
    }

}