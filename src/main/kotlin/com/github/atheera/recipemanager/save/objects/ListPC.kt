package com.github.atheera.recipemanager.save.objects

import java.io.Serializable

class ListPC : Serializable {

    lateinit var title: String
    var posList = mutableListOf<String>()
    var negList = mutableListOf<String>()

    fun toFormat() {
        println(
            "Title: \n $title \n" +
            "Pros List: \n $posList \n" +
            "Cons List: \n $negList \n"
        )
    }
}