package com.github.atheera.recipemanager.save.write

import com.github.atheera.recipemanager.gui.exc
import com.github.atheera.recipemanager.save.objects.Settings
import com.github.atheera.recipemanager.save.read.ReadSettings
import com.github.atheera.recipemanager.settingsPath
import com.google.gson.GsonBuilder
import java.io.File
import java.io.FileWriter
import java.io.IOException

class WriteSettingsFile(saveLocation: String, darkMode: Boolean, addedMeasures: MutableList<String>, debugMode: Boolean) {

    private fun createSettingsObject(saveLocation: String, darkMode: Boolean, addedMeasures: MutableList<String>, debugMode: Boolean) : Settings {
        val s = Settings()

        s.saveLocation = saveLocation
        s.isDark = darkMode
        s.addedMeasures = addedMeasures
        s.isDebug = debugMode

        return s
    }

    init {
        ReadSettings(settingsPath)
        val gson = GsonBuilder().setPrettyPrinting().create()
        val file = File(settingsPath)
        val sett = createSettingsObject(saveLocation, darkMode, addedMeasures, debugMode)
        try {
            FileWriter(file.absoluteFile).use { writer -> gson.toJson(sett, writer) }
        } catch (e: IOException) {
            exc(e)
            e.printStackTrace()
        }

    }

}