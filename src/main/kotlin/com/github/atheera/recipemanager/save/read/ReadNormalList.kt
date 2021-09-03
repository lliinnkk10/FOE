package com.github.atheera.recipemanager.save.read

import com.github.atheera.recipemanager.*
import com.github.atheera.recipemanager.gui.exc
import com.google.gson.JsonParser
import com.github.atheera.recipemanager.save.objects.NormalList
import com.google.gson.JsonObject
import java.io.FileNotFoundException
import java.io.FileReader
import java.io.IOException
import java.text.ParseException

class ReadNormalList(fileName: String) {

    private var parser = JsonParser()
    private var list = NormalList()
    private val file = listPath.plus("${listCategories[2]}/$fileName")

    init {
        try {
            val reader = FileReader(file)
            val obj: JsonObject = parser.parse(reader) as JsonObject
            parseListObject(obj)
            reader.close()
        } catch(e: ParseException) {
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

    private fun parseListObject(file: JsonObject) {

        listNList = mutableListOf()

        val title = file.get("title").asString
        list.title = title
        listNTitle = title

        val listN = file.get("list").asJsonArray
        for(i in 0 until listN.size()) {
            list.list.add(removeFirstAndLast(listN[i].toString()))
            listNList.add(removeFirstAndLast(listN[i].toString()))
        }

    }

}