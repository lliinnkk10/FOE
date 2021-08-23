package com.github.atheera.recipemanager.save.objects

import java.io.Serializable

class NormalList : Serializable {

    lateinit var title: String
    var list = mutableListOf<String>()

    fun toFormat() {
        println(
            "Title: \n $title \n" +
            "List: \n $list"
        )
    }

}