package com.github.atheera.recipemanager.save.objects

import java.io.Serializable

class ListTD : Serializable {

    lateinit var title: String
    var list = mutableListOf<String>()
    var checked = mutableListOf<String>()

    fun toFormat() {
        println(
            "Title: \n $title \n" +
            "Items to do: \n $list \n" +
            "Checked items: \n $checked"
        )
    }

}