package fr.ekito.myweatherlibrary.di

import android.app.Application

/**
 * Inject Module
 * Help define instances
 */
abstract class Module {

    /**
     * load module definition
     */
    abstract fun load()

    fun application(): Application? = Inject.applicationContext()

    /**
     * modules to load
     * @param modules
     */
    fun extend(vararg modules: Class<out Module>) {
        for (m in modules) {
            Inject.load(m)
        }
    }

    /**
     * provide one object instance
     * @param instances
     * *
     * @param clazz
     */
    fun provides(vararg instances: Any) {
        instances.forEach { i -> provide(i, i.javaClass) }
    }

    /**
     * provide one object instance
     * @param instance
     * *
     * @param clazz
     */
    fun provide(instance: Any, clazz: Class<*>) {
        Inject.add(instance, clazz)
    }
}
