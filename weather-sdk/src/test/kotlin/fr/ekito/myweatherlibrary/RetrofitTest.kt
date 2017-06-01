package fr.ekito.myweatherlibrary

import fr.ekito.koin.Koin
import fr.ekito.myweatherlibrary.module.NetworkModule
import fr.ekito.myweatherlibrary.ws.WeatherWS
import org.junit.Assert.assertNotNull
import org.junit.Test

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
class RetrofitTest {

    @Test
    fun test_weather() {
        val module = Koin()
                .properties(mapOf("server_url" to "https://my-weather-api.herokuapp.com/"))
                .build(NetworkModule::class)

        val serverUrl = module.getProperty<String>("server_url")
        assertNotNull(serverUrl)

        val weatherWS = module.get<WeatherWS>()
        val tester = weatherWS.geocode("Toulouse").test()

        tester.awaitTerminalEvent()
        tester.assertNoErrors()
        tester.assertValue { geocode -> geocode != null && geocode.results.isNotEmpty() }
    }
}