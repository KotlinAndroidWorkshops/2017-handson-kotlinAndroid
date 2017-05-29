package fr.ekito.myweatherlibrary.json

import android.app.Application
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * Created by arnaud on 29/05/2017.
 */
class AndroidJsonReader(val application: Application) : BaseReader() {

    override fun getAllFiles(): Array<String> = application.assets.list("json")

    override fun readJsonFile(jsonFile: String): String {
        val buf = StringBuilder()
        val json = application.assets.open("json/" + jsonFile)
        BufferedReader(InputStreamReader(json, "UTF-8")).use { br ->
            buf.append(br.lineSequence().joinToString(separator = "\n"))
        }
        return buf.toString()
    }
}