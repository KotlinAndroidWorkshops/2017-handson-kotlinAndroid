package android.app

import fr.ekito.koin.android.KoinApplication

/**
 * Created by arnaud on 02/06/2017.
 */

fun Application.getKoin(): fr.ekito.koin.Context {
    if (this is KoinApplication) {
        return this.koin
    } else throw IllegalStateException("Application is not a KoinApplication !")
}

fun Activity.getKoin(): fr.ekito.koin.Context {
    if (this.application is KoinApplication) {
        return this.application.getKoin()
    } else throw IllegalStateException("Application is not a KoinApplication !")
}

fun Fragment.getKoin(): fr.ekito.koin.Context {
    if (this.activity.application is KoinApplication) {
        return this.activity.getKoin()
    } else throw IllegalStateException("Application is not a KoinApplication !")
}