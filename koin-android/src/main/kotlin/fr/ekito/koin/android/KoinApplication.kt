//package fr.ekito.koin.android
//
//import android.app.Application
//import fr.ekito.koin.Koin
//import fr.ekito.koin.Module
//import kotlin.reflect.KClass
//
///**
// * Created by arnaud on 01/06/2017.
// */
//class KoinApplication<T : KClass<out AndroidModule>> : Application() {
//
//    lateinit var module: Module
//
//    override fun onCreate() {
//        super.onCreate()
//
//        module = Koin().build(T)
//    }
//
//}