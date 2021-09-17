package com.github.atheera.recipemanager.gui.panels.recipe

import com.github.atheera.recipemanager.*
import com.github.atheera.recipemanager.extras.ButtonRecipeCard
import com.github.atheera.recipemanager.gui.exc
import com.github.atheera.recipemanager.gui.frames.recipe.SavedRecipeFrame
import com.github.atheera.recipemanager.save.Files
import com.github.atheera.recipemanager.save.read.ReadRecipe
import com.github.atheera.recipemanager.save.read.ReadSettings
import net.miginfocom.swing.MigLayout
import java.awt.CardLayout
import java.awt.Dimension
import java.awt.event.ItemEvent
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.io.File
import java.io.IOException
import javax.swing.*
import javax.swing.border.EmptyBorder

class SavedMeatRecipePanel : JPanel() {

    private var jcbCat = JComboBox(subCatMeats.toTypedArray())
    private var clCat = CardLayout()
    private var jpCat = JPanel(clCat)
    private var jpContent = JPanel(MigLayout())
    private var jspContent = JScrollPane(jpCat)

    private var jpBeefCard = JPanel(MigLayout("wrap 1"))
    private var jpFishCard = JPanel(MigLayout("wrap 1"))
    private var jpOtherCard = JPanel(MigLayout("wrap 1"))
    private var jpPlantCard = JPanel(MigLayout("wrap 1"))
    private var jpPoultryCard = JPanel(MigLayout("wrap 1"))
    private var jpPorkCard = JPanel(MigLayout("wrap 1"))

    private lateinit var selectedCat: String
    private lateinit var selectedButton: ButtonRecipeCard

    init {

        jspContent.verticalScrollBarPolicy = ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS
        jspContent.verticalScrollBar.unitIncrement = 16
        jspContent.minimumSize = Dimension(535, 550)
        jspContent.maximumSize = Dimension(535, 550)

        jcbCat.addItemListener {
            if(it.stateChange == ItemEvent.SELECTED) {
                selectedCat = it.item as String
                clCat.show(jpCat, selectedCat)
                updateUI()
            }
        }

        jpContent.add(jcbCat, "align center, wrap")

        jpCat.add(jpBeefCard, subCatMeats[0])
        jpCat.add(jpFishCard, subCatMeats[1])
        jpCat.add(jpOtherCard, subCatMeats[2])
        jpCat.add(jpPlantCard, subCatMeats[3])
        jpCat.add(jpPoultryCard, subCatMeats[4])
        jpCat.add(jpPorkCard, subCatMeats[5])

        jpContent.add(jspContent, "align center")
        add(jpContent)

        clCat.show(jpCat, subCatMeats[0])
        darkmode()
    }

    fun darkmode() {
        ReadSettings()
        border = createBorder("Select which meat category to load buttons for")

        darkMode(this)
        darkModeOut(jpContent)
        darkModeIn(jpBeefCard)
        darkModeIn(jpFishCard)
        darkModeIn(jpOtherCard)
        darkModeIn(jpPlantCard)
        darkModeIn(jpPoultryCard)
        darkModeIn(jpPorkCard)

    }

    private fun createButton(cat: String, subCat: String, name: String, names: String) : ButtonRecipeCard {
        ReadRecipe(cat, subCat, names)
        val title = recipeTitle
        val category = recipeCategory
        val subCategory = recipeSubCategory
        val instructions = recipeInstructions
        val ingredients = recipeIngredients
        val desc = recipeDescription
        val temperature = recipeTemperature
        val convTemperature = recipeConvTemperature
        val egg = recipeEgg
        val gluten = recipeGluten
        val lactose = recipeLactose
        val vegan = recipeVegan
        val vegetarian = recipeVegetarian

        return ButtonRecipeCard(
            title,
            category,
            subCategory,
            instructions,
            ingredients,
            desc,
            temperature,
            convTemperature,
            egg,
            gluten,
            lactose,
            vegan,
            vegetarian
        )
    }

    fun loadRecipes() {
        try {
            jpBeefCard.removeAll()
            jpFishCard.removeAll()
            jpOtherCard.removeAll()
            jpPlantCard.removeAll()
            jpPorkCard.removeAll()
            jpPoultryCard.removeAll()
        } catch (e: IOException) {
            exc(e)
            e.printStackTrace()
        }
        val cat = categories[2]
        for(type in subCatMeats) {
            val file = File(Files().makeRecipeDir(cat, type))
            val files = file.listFiles()
            for(allFiles in files!!) {
                val names = allFiles.name
                val name = removeLast(names, 5)

                when (type) {
                    subCatMeats[0] -> {
                        selectedButton = createButton(cat, type, name, names)
                        jpBeefCard.add(selectedButton)
                    }
                    subCatMeats[1] -> {
                        selectedButton = createButton(cat, type, name, names)
                        jpFishCard.add(selectedButton)
                    }
                    subCatMeats[2] -> {
                        selectedButton = createButton(cat, type, name, names)
                        jpOtherCard.add(selectedButton)
                    }
                    subCatMeats[3] -> {
                        selectedButton = createButton(cat, type, name, names)
                        jpPlantCard.add(selectedButton)
                    }
                    subCatMeats[4] -> {
                        selectedButton = createButton(cat, type, name, names)
                        jpPoultryCard.add(selectedButton)
                    }
                    subCatMeats[5] -> {
                        selectedButton = createButton(cat, type, name, names)
                        jpPorkCard.add(selectedButton)
                    }
                }
            }
        }
    }

}