package com.github.atheera.recipemanager.save.write

import com.github.atheera.recipemanager.gui.exc
import com.github.atheera.recipemanager.save.Files
import com.github.atheera.recipemanager.save.objects.ListPC
import com.google.gson.GsonBuilder
import java.io.FileWriter
import java.io.IOException

class WriteListPC(type: String, title: String, posList: MutableList<String>, negList: MutableList<String>) {

    private fun createPCObject(title: String, posList: MutableList<String>, negList: MutableList<String>) : ListPC {
        val l = ListPC()

        l.title = title
        l.posList = posList
        l.negList = negList

        return l
    }

    init {
        val gson = GsonBuilder().setPrettyPrinting().create()
        val file = Files().getListFile(type, title)
        val list = createPCObject(title, posList, negList)
        try {
            FileWriter(file.absoluteFile).use { writer -> gson.toJson(list, writer) }
        } catch (e: IOException) {
            exc(e)
            e.printStackTrace()
        }
    }

}