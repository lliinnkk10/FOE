package com.github.atheera.recipemanager.gui.panels.recipe

import com.github.atheera.recipemanager.*
import com.github.atheera.recipemanager.extras.ButtonRecipeCard
import com.github.atheera.recipemanager.gui.exc
import com.github.atheera.recipemanager.save.read.ReadRecipe
import com.github.atheera.recipemanager.save.read.ReadRecipeFavorite
import com.github.atheera.recipemanager.save.read.ReadSettings
import net.miginfocom.swing.MigLayout
import java.awt.CardLayout
import java.awt.Dimension
import com.github.atheera.recipemanager.save.Files
import java.awt.event.*
import java.io.File
import javax.swing.*
import kotlin.random.Random

class RandomRecipePanel : JPanel(), ItemListener, ActionListener {

    private val txt = "Get Random Recipe In"
    private var selectedCat = categories[0]
    private var selectedSubCat = "Not Selected"
    private val jbRandom = JButton("$txt All Saved Recipes")
    private val jbRandomSub = JButton("$txt Category: $selectedCat And Sub Category: $selectedSubCat")
    private val jpContent = JPanel(MigLayout())
    private val jpRecipe = JPanel(MigLayout())

    private val jcbCat = JComboBox(categories.toTypedArray())
    private var cl = CardLayout()
    private val jpCatCards = JPanel(cl)
    private val jpSubCatD = JPanel()
    private val jpSubCatE = JPanel()
    private val jpSubCatM = JPanel()
    private val bgJRBGroup = ButtonGroup()
    private val jcbFavorite = JCheckBox("Only random favorite recipe")
    private var searchFavorite = false
    lateinit var jrbSelected: JRadioButton

    private var searchedFiles = mutableListOf<ButtonRecipeCard>()
    private var addedButton = mutableListOf<ButtonRecipeCard>()
    private val random = Random

    init {
        darkmode()

        // Functionality
            // Categories
        jcbCat.isEditable = false
        jcbCat.addItemListener(this)
        jcbCat.selectedIndex = 0
        selectedCat = categories[0]

        for (i in subCatDesserts.indices) {
            val jrb = JRadioButton(subCatDesserts[i])
            jpSubCatD.add(jrb)
            bgJRBGroup.add(jrb)
            jrb.actionCommand = i.toString()
            jrb.addActionListener(this)
        }
        for (i in subCatExtras.indices) {
            val jrb = JRadioButton(subCatExtras[i])
            jpSubCatE.add(jrb)
            bgJRBGroup.add(jrb)
            jrb.actionCommand = i.toString()
            jrb.addActionListener(this)
        }
        for (i in subCatMeats.indices) {
            val jrb = JRadioButton(subCatMeats[i])
            jpSubCatM.add(jrb)
            bgJRBGroup.add(jrb)
            jrb.actionCommand = i.toString()
            jrb.addActionListener(this)
        }
        jcbFavorite.addActionListener {
            searchFavorite = jcbFavorite.isSelected
            jbRandom.text = if(searchFavorite) "$txt Favorite Recipes Only" else "$txt All Saved Recipes"
            updateUI()
        }

            // CardLayout for categories
        jpCatCards.add(jpSubCatD, categories[0])
        jpCatCards.add(jpSubCatE, categories[1])
        jpCatCards.add(jpSubCatM, categories[2])

            // Random button
        jbRandom.addActionListener {
            loadRecipes(searchFavorite)
        }
        jbRandomSub.addActionListener {
            loadCategory(selectedCat, selectedSubCat)
        }

            // Recipe panel
        jpRecipe.minimumSize = Dimension(515, 215)
        jpRecipe.maximumSize = Dimension(515, 215)

        // Add to screen
        jpContent.add(jcbCat, "align center, wrap")
        jpContent.add(jpCatCards, "wrap")
        jpContent.add(jcbFavorite, "align center, split 3")
        jpContent.add(jbRandom)
        jpContent.add(jbRandomSub, "wrap")
        jpContent.add(jpRecipe, "align center")
        add(jpContent)
    }

    private fun loadCategory(category: String, subCat: String) {
        try {
            jpRecipe.removeAll()
            searchedFiles.clear()
        } catch (e: Exception) {
            exc(e)
            e.printStackTrace()
        }

        for(i in categories.indices) {
            val file = File(Files().makeRecipeDir(category, subCat))
            val files = file.listFiles()
            for (allFiles in files!!) {
                val names = allFiles.name
                when (category) {
                    categories[i] -> {
                        val jb = createButton(categories[i], subCat, names, false)
                        if (subCat == recipeSubCategory)
                            searchedFiles.add(jb)
                    }
                }
            }
        }

        checkAdded()
    }

    private fun loadRecipes(favorite: Boolean = jcbFavorite.isSelected) {
        try {
            jpRecipe.removeAll()
            searchedFiles.clear()
        } catch (e: Exception) {
            exc(e)
            e.printStackTrace()
        }

        if(favorite) { // Only loop through the saved favorite recipes

            loopFavorites()

        } else {

            loopCategory(0)
            loopCategory(1)
            loopCategory(2)
            loopFavorites()

        }

        checkAdded()
    }

    private fun checkAdded() {
        if(searchedFiles.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No recipes found!")
            return
        }

        val num = if(searchedFiles.size > 1) random.nextInt(searchedFiles.size) else 0
        val item = searchedFiles[num]

        if(addedButton.isEmpty()) {
            addedButton.add(item)
            jpRecipe.add(item)
            updateUI()
        } else {
            if (searchedFiles.size < 2) {
                addedButton.clear()
                jpRecipe.add(item)
                updateUI()
                JOptionPane.showMessageDialog(this, "Only one recipe found and already added!")
            } else {

                if (addedButton[0].getTitle() != item.getTitle()) {
                    addedButton.clear()
                    jpRecipe.add(item)
                    updateUI()
                } else {
                    while (addedButton[0].getTitle() == item.getTitle()) {
                        val newNum = random.nextInt(searchedFiles.size)
                        val newItem = searchedFiles[newNum]
                        if (item.getTitle() != newItem.getTitle()) {
                            addedButton.clear()
                            jpRecipe.add(newItem)
                            updateUI()
                            break
                        }
                    }
                }
            }
        }
    }

    private fun loopCategory(j: Int, favorite: Boolean = jcbFavorite.isSelected) {
        val category = when (j) {
            0 -> subCatDesserts
            1 -> subCatExtras
            else -> subCatMeats
        }
        for (i in category.indices) {
            val file = File(Files().makeRecipeDir(categories[j], category[i]))
            val files = file.listFiles()
            for (allFiles in files!!) {
                val names = allFiles.name
                when (val subCat = category[i]) {
                    subCat -> {
                        val jb = createButton(categories[j], subCat, names, favorite)
                        if (subCat == recipeSubCategory) searchedFiles.add(jb)
                    }
                }
            }
        }
    }

    private fun loopFavorites() {
        val recFile = File(recipeFavPath)
        val recFiles = recFile.listFiles()
        for(allFiles in recFiles!!) {
            val files = allFiles.name
            for (i in subCatDesserts.indices) { when (val cat = subCatDesserts[i]) {cat -> {
                val jb = createButton(categories[0], cat, files, true)
                if (cat == recipeSubCategory) searchedFiles.add(jb)
            } } }
            for (i in subCatExtras.indices) { when (val cat = subCatExtras[i]) {cat -> {
                val jb = createButton(categories[1], cat, files, true)
                if (cat == recipeSubCategory) searchedFiles.add(jb)
            } } }
            for (i in subCatMeats.indices) { when (val cat = subCatMeats[i]) {cat -> {
                val jb = createButton(categories[2], cat, files, true)
                if (cat == recipeSubCategory) searchedFiles.add(jb)
            } } }
        }
    }

    private fun createButton(
        cat: String,
        subCat: String,
        file: String,
        favorite: Boolean
    ): ButtonRecipeCard {
        if (favorite) ReadRecipeFavorite(file)
        else ReadRecipe(cat, subCat, file)

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

    fun darkmode() {
        ReadSettings(settingsPath)
        border = createBorder("Here you can select what type of recipe to randomly select or just all recipes saved")

        darkMode(this)
        darkModeOut(jpContent)
        darkModeIn(jpRecipe)

    }

    override fun itemStateChanged(e: ItemEvent) {
        if(e.stateChange == ItemEvent.SELECTED) {
            selectedCat = e.item.toString()
            cl = jpCatCards.layout as CardLayout
            cl.show(jpCatCards, e.item as String)
        }
        updateUI()
    }

    override fun actionPerformed(e: ActionEvent) {
        jrbSelected = e.source as JRadioButton
        selectedSubCat = jrbSelected.text
        jbRandomSub.text = "$txt Category: $selectedCat And Sub Category: $selectedSubCat"
        updateUI()
    }

}