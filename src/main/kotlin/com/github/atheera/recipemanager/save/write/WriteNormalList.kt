package com.github.atheera.recipemanager.save.write

import com.github.atheera.recipemanager.gui.exc
import com.github.atheera.recipemanager.save.Files
import com.github.atheera.recipemanager.save.objects.NormalList
import com.google.gson.GsonBuilder
import java.io.FileWriter
import java.io.IOException

class WriteNormalList(type: String, title: String, list: MutableList<String>) {

    private fun createListObject(title: String, list: MutableList<String>) : NormalList {
        val l = NormalList()

        l.title = title
        l.list = list

        return l
    }

    init {
        val gson = GsonBuilder().setPrettyPrinting().create()
        val file = Files().getListFile(type, title)
        val lists = createListObject(title, list)
        try {
            FileWriter(file.absoluteFile).use { writer -> gson.toJson(lists, writer) }
        } catch (e: IOException) {
            exc(e)
            e.printStackTrace()
        }
    }

}