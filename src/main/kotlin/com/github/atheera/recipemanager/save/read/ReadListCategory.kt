package com.github.atheera.recipemanager.save.read

import com.github.atheera.recipemanager.*
import com.github.atheera.recipemanager.gui.exc
import com.github.atheera.recipemanager.save.objects.ListCategory
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import java.io.FileNotFoundException
import java.io.FileReader
import java.io.IOException
import java.text.ParseException

class ReadListCategory(fileName: String) {

    private var parser = JsonParser()
    private var list = ListCategory()
    private val file = listPath.plus("${listCategories[3]}/$fileName")

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

        listCatList = mutableListOf()
        val categoryList = mutableListOf<String>()

        val title = file.get("title").asString
        list.title = title
        listCatTitle = title

        val catList = file.get("categories").asJsonArray

        for (i in 0 until catList.size()) {
            val category = removeFirstAndLast(catList[i].toString())
            categoryList.add(category)
            for (j in 0 until categoryList.size) {
                val item = file.get(removeFirstAndLast(categoryList[j])).asString
            }
        }

        for (i in 0 until catList.size()) {
            val cat = removeFirstAndLast(catList[i].toString())
            for (item in cat.indices) {
            }
        }

    }

}