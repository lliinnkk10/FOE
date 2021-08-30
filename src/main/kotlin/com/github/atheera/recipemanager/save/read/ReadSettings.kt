package com.github.atheera.recipemanager.save.read

import com.github.atheera.recipemanager.*
import com.github.atheera.recipemanager.gui.exc
import com.github.atheera.recipemanager.save.objects.Settings
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import java.io.FileNotFoundException
import java.io.FileReader
import java.io.IOException
import java.text.ParseException

class ReadSettings(saveLocation: String = settingsPath) {

    private var parser = JsonParser()
    private var settings = Settings()

    init {

        try {
            val reader = FileReader(saveLocation)
            val obj: JsonObject = parser.parse(reader) as JsonObject
            parseSettingsObjects(obj)
        } catch (e: ParseException) {
            exc(e)
            e.printStackTrace()
        } catch(e: FileNotFoundException) {
            exc(e)
            e.printStackTrace()
        } catch(e: IOException) {
            exc(e)
            e.printStackTrace()
        }

    }

    private fun parseSettingsObjects(file: JsonObject) {

        addedMeasures = mutableListOf()
        settings.addedMeasures = mutableListOf()

        val saveLocation = file.get("saveLocation").asString
        path = saveLocation
        settings.saveLocation = saveLocation

        val darkMode = file.get("isDark").asBoolean
        isDark = darkMode
        settings.isDark = darkMode

        val measure = file.get("addedMeasures").asJsonArray
        for(i in 0 until measure.size()) {
            addedMeasures.add(removeFirstAndLast(measure[i].toString()))
            settings.addedMeasures.add(removeFirstAndLast(measure[i].toString()))
            measures.add(removeFirstAndLast(measure[i].toString()))
        }

    }

}