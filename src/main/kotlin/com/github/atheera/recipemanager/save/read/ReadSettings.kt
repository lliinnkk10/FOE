package com.github.atheera.recipemanager.save.read

import com.github.atheera.recipemanager.gui.exc
import com.github.atheera.recipemanager.isDark
import com.github.atheera.recipemanager.path
import com.github.atheera.recipemanager.save.objects.Settings
import com.github.atheera.recipemanager.settingsPath
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

        val saveLocation = file.get("saveLocation").asString
        path = saveLocation
        settings.saveLocation = saveLocation

        val darkMode = file.get("isDark").asBoolean
        isDark = darkMode
        settings.isDark = darkMode

    }

}