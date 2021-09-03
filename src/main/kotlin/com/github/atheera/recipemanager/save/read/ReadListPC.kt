package com.github.atheera.recipemanager.save.read

import com.github.atheera.recipemanager.*
import com.github.atheera.recipemanager.gui.exc
import com.github.atheera.recipemanager.save.objects.ListPC
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import java.io.FileNotFoundException
import java.io.FileReader
import java.io.IOException
import java.text.ParseException

class ReadListPC(fileName: String) {

    private var parser = JsonParser()
    private var list = ListPC()
    private val file = listPath.plus("${listCategories[0]}/$fileName")

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

        listPCNeg = mutableListOf()
        listPCPos = mutableListOf()

        val title = file.get("title").asString
        list.title = title
        listPCTitle = title

        val posList = file.get("posList").asJsonArray
        for(i in 0 until posList.size()) {
            list.posList.add(removeFirstAndLast(posList[i].toString()))
            listPCPos.add(removeFirstAndLast(posList[i].toString()))
        }

        val negList = file.get("negList").asJsonArray
        for(i in 0 until negList.size()) {
            list.negList.add(removeFirstAndLast(negList[i].toString()))
            listPCNeg.add(removeFirstAndLast(negList[i].toString()))
        }
    }



}