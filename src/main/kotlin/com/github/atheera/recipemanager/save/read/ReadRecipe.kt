package com.github.atheera.recipemanager.save.read

import com.github.atheera.recipemanager.*
import com.github.atheera.recipemanager.gui.exc
import com.github.atheera.recipemanager.save.objects.Recipe
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import java.io.FileNotFoundException
import java.io.FileReader
import java.io.IOException
import java.text.ParseException

class ReadRecipe(cat: String, subCat: String, fileName: String) {

    private var parser = JsonParser()
    private var recipe = Recipe()
    private val file = recipePath.plus("$cat/$subCat/$fileName")

    init {
        try {
            val reader = FileReader(file)
            val obj: JsonObject = parser.parse(reader) as JsonObject
            parseRecipeObject(obj)
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

    private fun parseRecipeObject(file: JsonObject) {

        recipeIngredients = mutableListOf()
        recipe.ingredients = mutableListOf()

        val title = file.get("title").asString
        recipe.title = title
        recipeTitle = title

        val category = file.get("category").asString
        recipe.category = category
        recipeCategory = category

        val subCategory = file.get("subCategory").asString
        recipe.subCategory = subCategory
        recipeSubCategory = subCategory

        val instruction = file.get("instructions").asString
        recipe.instructions = instruction
        recipeInstructions = instruction

        val ingredients = file.get("ingredients").asJsonArray
        for(i in 0 until ingredients.size()) {
            recipe.ingredients.add(removeFirstAndLast(ingredients[i].toString()))
            recipeIngredients.add(removeFirstAndLast(ingredients[i].toString()))
        }

        val description = file.get("description").asString
        recipe.description = description
        recipeDescription = description

        val temperature = file.get("temperature").asInt
        recipe.temperature = temperature
        recipeTemperature = temperature

        val convTemperature = file.get("convTemperature").asInt
        recipe.convTemperature = convTemperature
        recipeConvTemperature = convTemperature

        val egg = file.get("egg").asBoolean
        recipe.egg = egg
        recipeEgg = egg

        val gluten = file.get("gluten").asBoolean
        recipe.gluten = gluten
        recipeGluten = gluten

        val lactose = file.get("lactose").asBoolean
        recipe.lactose = lactose
        recipeLactose = lactose

        val vegan = file.get("vegan").asBoolean
        recipe.vegan = vegan
        recipeVegan = vegan

        val vegetarian = file.get("vegetarian").asBoolean
        recipe.vegetarian = vegetarian
        recipeVegetarian = vegetarian

    }

}