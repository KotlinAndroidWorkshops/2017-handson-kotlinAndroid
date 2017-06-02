package fr.ekito.koin.android

import android.app.Application
import fr.ekito.koin.Koin

/**
 * init android Application - for Koin koinContext
 */
fun Koin.init(application: Application): Koin {
    context.provide { application }
    return this
}