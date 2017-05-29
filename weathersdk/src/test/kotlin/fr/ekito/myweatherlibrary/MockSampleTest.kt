package fr.ekito.myweatherlibrary

import fr.ekito.myweatherlibrary.json.JavaReader
import fr.ekito.myweatherlibrary.json.geocode.Geocode
import fr.ekito.myweatherlibrary.ws.WeatherWS
import fr.ekito.myweatherlibrary.ws.mock.WeatherMockWS
import io.reactivex.functions.Consumer
import io.reactivex.subscribers.TestSubscriber
import org.junit.Test

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
class MockSampleTest {
    @Test
    fun get_geocode() {

        val weatherWS: WeatherWS = WeatherMockWS(JavaReader())

        val tester = TestSubscriber<Geocode>()

        weatherWS.geocode("Toulouse")
                .subscribe(tester as Consumer<in Geocode>)

    }
}