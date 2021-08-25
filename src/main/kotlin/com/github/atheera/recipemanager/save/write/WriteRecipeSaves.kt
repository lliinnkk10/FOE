package com.github.atheera.recipemanager.save.write

import com.github.atheera.recipemanager.gui.exc
import com.github.atheera.recipemanager.save.Files
import com.github.atheera.recipemanager.save.objects.Recipe
import com.google.gson.GsonBuilder
import java.io.FileWriter
import java.io.IOException

class WriteRecipeSaves(title: String, cat: String, subCat: String, instr: String, ingr: MutableList<String>, desc: String, temp: Int, cTemp: Int, egg: Boolean, gluten: Boolean, lactose: Boolean, vegan: Boolean, veget: Boolean) {
    private fun createRecipeObject( title: String, cat: String, subCat: String, instr: String, ingr: MutableList<String>, desc: String, temp: Int, cTemp: Int, egg: Boolean, gluten: Boolean, lactose: Boolean, vegan: Boolean, veget: Boolean): Recipe {
        val r = Recipe()
        r.title = title
        r.category = cat
        r.subCategory = subCat
        r.instructions = instr
        r.ingredients = ingr
        r.description = desc
        r.temperature = temp
        r.convTemperature = cTemp
        r.egg = egg
        r.gluten = gluten
        r.lactose = lactose
        r.vegan = vegan
        r.vegetarian = veget
        r.toFormat()
        return r
    }

    init {
        val gson = GsonBuilder().setPrettyPrinting().create()
        val file = Files().getRecipeFile(cat, subCat, title)
        val rec = createRecipeObject(title, cat, subCat, instr, ingr, desc, temp, cTemp, egg, gluten, lactose, vegan, veget)
        try {
            FileWriter(file.absoluteFile).use { writer -> gson.toJson(rec, writer) }
        } catch (e: IOException) {
            exc(e)
            e.printStackTrace()
        }
    }
}