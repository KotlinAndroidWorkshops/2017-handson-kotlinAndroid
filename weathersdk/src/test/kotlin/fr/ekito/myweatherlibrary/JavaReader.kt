package fr.ekito.myweatherlibrary.json

import java.io.File

/**
 * Created by arnaud on 29/05/2017.
 */
class JavaReader : BaseReader() {

    val base_path = "weathersdk/src/main/assets/json"

    override fun getAllFiles(): List<String> = File(base_path).list().toList()

    override fun readJsonFile(jsonFile: String): String = File("$base_path/$jsonFile").readLines().joinToString(separator = "\n")
}