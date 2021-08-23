package com.github.atheera.recipemanager.save.write

import com.github.atheera.recipemanager.dw
import com.github.atheera.recipemanager.save.objects.Settings
import com.github.atheera.recipemanager.save.read.ReadSettings
import com.github.atheera.recipemanager.settingsPath
import com.google.gson.GsonBuilder
import java.io.File
import java.io.FileWriter
import java.io.IOException

class WriteSettingsFile(saveLocation: String, darkMode: Boolean) {

    private fun createSettingsObject(saveLocation: String, darkMode: Boolean) : Settings {
        val s = Settings()

        s.saveLocation = saveLocation
        s.isDark = darkMode

        return s
    }

    init {
        ReadSettings(settingsPath)
        val gson = GsonBuilder().setPrettyPrinting().create()
        val file = File(settingsPath)
        val sett = createSettingsObject(saveLocation, darkMode)
        try {
            FileWriter(file.absoluteFile).use { writer -> gson.toJson(sett, writer) }
        } catch (e: IOException) {
            dw.exc(e)
            e.printStackTrace()
        }

    }

}