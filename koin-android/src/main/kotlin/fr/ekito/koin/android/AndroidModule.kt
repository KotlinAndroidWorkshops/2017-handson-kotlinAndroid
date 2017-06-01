package fr.ekito.koin.android

import android.app.Application
import android.content.res.Resources
import fr.ekito.koin.module.Module

/**
 * Created by arnaud on 01/06/2017.
 */
abstract class AndroidModule : Module() {

    fun applicationContext() = context.get<Application>()

    fun resources(): Resources = applicationContext().resources

}