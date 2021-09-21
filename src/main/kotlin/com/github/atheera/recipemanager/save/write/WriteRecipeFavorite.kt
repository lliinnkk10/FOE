package com.github.atheera.recipemanager.save.write

import com.github.atheera.recipemanager.gui.exc
import com.github.atheera.recipemanager.save.Files
import com.github.atheera.recipemanager.save.objects.Recipe
import com.google.gson.GsonBuilder
import java.io.FileWriter
import java.io.IOException

class WriteRecipeFavorite(title: String, cat: String, subCat: String, instr: MutableList<String>, ingr: MutableList<String>, equipment: MutableList<String>, desc: String, link: String, temp: Int, cTemp: Int, egg: Boolean, gluten: Boolean, lactose: Boolean, vegan: Boolean, veget: Boolean) {
    private fun createRecipeObject( title: String, cat: String, subCat: String, instr: MutableList<String>, ingr: MutableList<String>, equipment: MutableList<String>, desc: String, link: String, temp: Int, cTemp: Int, egg: Boolean, gluten: Boolean, lactose: Boolean, vegan: Boolean, veget: Boolean): Recipe {
        val r = Recipe()
        r.title = title
        r.category = cat
        r.subCategory = subCat
        r.instructions = instr
        r.ingredients = ingr
        r.equipment = equipment
        r.description = desc
        r.link = link
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
        val file = Files().getRecipeFavoriteFile(title)
        val rec = createRecipeObject(title, cat, subCat, instr, equipment, ingr, desc, link, temp, cTemp, egg, gluten, lactose, vegan, veget)
        try {
            FileWriter(file.absoluteFile).use { writer -> gson.toJson(rec, writer) }
        } catch (e: IOException) {
            exc(e)
            e.printStackTrace()
        }
    }
}