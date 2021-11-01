package com.github.atheera.recipemanager.save.objects

import java.io.Serializable

class ListCategory : Serializable {

    lateinit var title: String
    var categories = mutableListOf<String>()
    var list = mutableListOf<MutableList<String>>()

}