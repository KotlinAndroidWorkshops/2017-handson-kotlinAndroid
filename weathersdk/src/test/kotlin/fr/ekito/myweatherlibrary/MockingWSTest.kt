package fr.ekito.myweatherlibrary

import fr.ekito.myweatherlibrary.json.JavaReader
import fr.ekito.myweatherlibrary.ws.WeatherWS
import fr.ekito.myweatherlibrary.ws.mock.WeatherMockWS
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
class MockingWSTest {

    @Test
    fun unit_test() {
        val javareader = JavaReader()
        val files = javareader.getAllFiles()
        assertTrue(files.isNotEmpty())

        val content = javareader.readJsonFile(files.first())
        assertTrue(content.isNotEmpty())
    }

    @Test
    fun integ_test() {
        val jsonReader = JavaReader()
        val tlse_geocode = jsonReader.getGeocode("toulouse")

        val weatherWS: WeatherWS = WeatherMockWS(jsonReader)
        val testObserver = weatherWS.geocode("Toulouse").test()

        testObserver.awaitTerminalEvent()
        testObserver.assertNoErrors()
        testObserver.assertValue { geocode -> geocode.status == tlse_geocode.status && tlse_geocode.results.size == geocode.results.size }
    }

    @Test
    fun mock_test() {
        val jsonReader = JavaReader()
        val name = "toulouse"
        val tlse_geocode = jsonReader.getGeocode(name)

        val mockReader = mock(JavaReader::class.java)
        `when`(mockReader.getAllFiles()).thenReturn(arrayListOf(name))
        `when`(mockReader.getGeocode(name)).thenReturn(tlse_geocode)

        val weatherWS: WeatherWS = WeatherMockWS(mockReader)
        val rxObserverTest = weatherWS.geocode(name).test()

        rxObserverTest.awaitTerminalEvent()
        rxObserverTest.assertNoErrors()
        rxObserverTest.assertValue { geocode -> geocode.status == tlse_geocode.status && tlse_geocode.results.size == geocode.results.size }
    }
}