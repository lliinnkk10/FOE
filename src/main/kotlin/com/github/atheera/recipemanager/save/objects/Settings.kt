package com.github.atheera.recipemanager.save.objects

import java.io.Serializable

class Settings : Serializable {

    lateinit var saveLocation: String
    var isDark: Boolean = false
    lateinit var addedMeasures: MutableList<String>
    var isDebug: Boolean = false

}