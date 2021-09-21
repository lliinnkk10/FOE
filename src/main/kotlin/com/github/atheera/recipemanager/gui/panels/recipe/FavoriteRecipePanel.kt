package com.github.atheera.recipemanager.gui.panels.recipe

import com.github.atheera.recipemanager.*
import com.github.atheera.recipemanager.extras.ButtonRecipeCard
import com.github.atheera.recipemanager.extras.HintTextField
import com.github.atheera.recipemanager.gui.exc
import com.github.atheera.recipemanager.save.read.ReadRecipeFavorite
import com.github.atheera.recipemanager.save.read.ReadSettings
import net.miginfocom.swing.MigLayout
import java.awt.Dimension
import java.io.File
import javax.swing.*

class FavoriteRecipePanel : JPanel() {

    private var jpContent = JPanel(MigLayout())
    private var jpCat = JPanel(MigLayout("wrap 1"))
    private var jspContent = JScrollPane(jpCat)

    private val searchtext = "Search the titles of favorite recipes"
    private var htfSearch = HintTextField(searchtext)
    private var jbSearch = JButton(searchButton)
    private var jbClear = JButton(deleteButton)
    private var alLoaded = mutableListOf<String>()

    private lateinit var jbTemp: ButtonRecipeCard

    init {
        // Functions
        htfSearch.minimumSize = Dimension(250, 25)
        htfSearch.addActionListener {
            searchSaved()
        }
        jbSearch.maximumSize = Dimension(25, 25)
        jbSearch.addActionListener {
            searchSaved()
        }
        jbClear.maximumSize = Dimension(20, 20)
        jbClear.addActionListener {
            htfSearch.text = ""
            loadRecipes(false)
            updateUI()
        }

        jspContent.verticalScrollBarPolicy = ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS
        jspContent.verticalScrollBar.unitIncrement = 16
        jspContent.minimumSize = Dimension(535, 550)
        jspContent.maximumSize = Dimension(535, 550)

        // Add to screen
        jpContent.add(jbClear, "align center, split 3")
        jpContent.add(htfSearch)
        jpContent.add(jbSearch, "wrap")
        jpContent.add(jspContent, "align center")
        add(jpContent)
    }

    fun darkmode() {
        ReadSettings()
        border = createBorder("All your favorite recipes, easily read")

        darkMode(this)
        darkModeOut(jpContent)
        darkModeIn(jpCat)
    }

    private fun searchSaved() {
        if(htfSearch.text.isEmpty() || htfSearch.text.equals(searchtext)) {
            JOptionPane.showMessageDialog(this, "Nothing entered to search for, try again!")
            loadRecipes(false)
            updateUI()
        } else {
            loadRecipes(true)
            updateUI()
        }
    }

    private fun createButton(cat: String, subCat: String, name: String, names: String) : ButtonRecipeCard {
        ReadRecipeFavorite(names)
        val title = recipeTitle
        val category = recipeCategory
        val subCategory = recipeSubCategory
        val instructions = recipeInstructions
        val ingredients = recipeIngredients
        val equipment = recipeEquipment
        val desc = recipeDescription
        val link = recipeLink
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
            equipment,
            desc,
            link,
            temperature,
            convTemperature,
            egg,
            gluten,
            lactose,
            vegan,
            vegetarian
        )
    }

    private fun loopDesserts(category: String, name: String, names: String, search: Boolean) {
        for(i in 0..12) {
            when (category) {
                subCatDesserts[i] -> {
                    jbTemp = createButton(categories[0], category, name, names)
                    if(category == recipeSubCategory) {
                        jpCat.add(jbTemp)
                        alLoaded.add(name)
                        if(search) {
                            for(j in alLoaded.indices) {
                                if(!alLoaded[j].contains(htfSearch.text)) {
                                    jpCat.remove(jbTemp)
                                    alLoaded.remove(name)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun loopExtras(category: String, name: String, names: String, search: Boolean) {
        for(i in 0..4) {
            when (category) {
                subCatExtras[i] -> {
                    jbTemp = createButton(categories[1], category, name, names)
                    if(category == recipeSubCategory) {
                        jpCat.add(jbTemp)
                        alLoaded.add(name)
                        if(search) {
                            for(j in alLoaded.indices) {
                                if(!alLoaded[j].contains(htfSearch.text)) {
                                    jpCat.remove(jbTemp)
                                    alLoaded.remove(name)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun loopMeat(category: String, name: String, names: String, search: Boolean) {
        for(i in 0..5) {
            when(category) {
                subCatMeats[i] -> {
                    jbTemp = createButton(categories[2], category, name, names)
                    if(category == recipeSubCategory) {
                        jpCat.add(jbTemp)
                        alLoaded.add(name)
                        if(search) {
                            for(j in alLoaded.indices) {
                                if(!alLoaded[j].contains(htfSearch.text)) {
                                    jpCat.remove(jbTemp)
                                    alLoaded.remove(name)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    fun loadRecipes(search: Boolean) {
        try {
            jpCat.removeAll()
            alLoaded.clear()
        } catch (e: Exception) {
            exc(e)
            e.printStackTrace()
        }

        val file = File(recipeFavPath)
        val files = file.listFiles()

        for(allFiles in files!!) {
            val names = allFiles.name
            val name = removeLast(names, 5)

            for (category in subCatDesserts) { loopDesserts(category, name, names, search) }
            for (category in subCatExtras) { loopExtras(category, name, names, search) }
            for (category in subCatMeats) { loopMeat(category, name, names, search) }

        }
        updateUI()
    }
}