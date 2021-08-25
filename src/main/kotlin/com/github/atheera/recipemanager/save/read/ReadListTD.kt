package com.github.atheera.recipemanager.save.read

import com.github.atheera.recipemanager.*
import com.github.atheera.recipemanager.gui.exc
import com.github.atheera.recipemanager.save.objects.ListTD
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import java.io.FileNotFoundException
import java.io.FileReader
import java.io.IOException
import java.text.ParseException

class ReadListTD(fileName: String) {

    private var parser = JsonParser()
    private var list = ListTD()
    private val file = listPath.plus("${listCategories[1]}/$fileName")

    init {
        try {
            val reader = FileReader(file)
            val obj: JsonObject = parser.parse(reader) as JsonObject
            parseListObject(obj)
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

        listTD = mutableListOf()
        listTDChecked = mutableListOf()

        val title = file.get("title").asString
        list.title = title
        listTDTitle = title

        val listToDo = file.get("list").asJsonArray
        for(i in 0 until listToDo.size()) {
            listTD.add(removeFirstAndLast(listToDo[i].toString()))
            list.list.add(removeFirstAndLast(listToDo[i].toString()))
        }

        val listChecked = file.get("checked").asJsonArray
        for(i in 0 until listChecked.size()) {
            listTDChecked.add(removeFirstAndLast(listChecked[i].toString()))
            list.checked.add(removeFirstAndLast(listChecked[i].toString()))
        }

    }

}