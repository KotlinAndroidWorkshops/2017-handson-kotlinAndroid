package fr.ekito.myweatherlibrary.di

import android.app.Application
import android.util.Log
import java.util.*


/**
 * Simple Dependency Inject
 * agiuliani
 */
object Inject {

    val TAG = "Inject"
    val instances = HashMap<Class<*>, Any>()

    /**
     * get a component

     / *
     * @param <T>   Type
     * *
     * @return Component if present
    </T> */
    inline fun <reified T> get(): T? {
        val instance = instances.values.filter { it is T }.first() as? T
        Log.v(TAG, "get $instance")
        return instance
    }

    /**
     * Replace an existing or not component

     * @param o
     * *
     * @param clazz
     */
    fun add(o: Any, clazz: Class<*>) {
        val existingInstance = instances[clazz]
        if (existingInstance != null) {
            remove(clazz)
        }
        Log.v(TAG, "Add instance for $o")
        instances.put(clazz, o)
    }

    /**
     * Intialize components from Module definition

     * @param module
     */
    fun load(module: Class<out Module>) {
        try {
            val mod = module.newInstance()
            mod.load()
        } catch (e: Throwable) {
            throw IllegalStateException("Module prepare " + module.simpleName + " error : " + e)
        }

    }

    /**
     * remove an instance
     * @param clazz Class
     */
    fun remove(clazz: Class<*>) {
        Log.v(TAG, "Remove obj : $clazz")
        instances.remove(clazz)
    }

    /**
     * remove an instance
     * @param instance
     */
    fun remove(instance: Any) {
        Log.v(TAG, "Remove obj : $instance")
        remove(instance.javaClass)
    }

    /**
     * clear all instances
     */
    fun clear() {
        Log.v(TAG, "Clear All instances !")
        instances.clear()
    }

    fun applicationContext() = get<Application>()
}
