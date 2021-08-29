package com.github.atheera.recipemanager.gui.panels.recipe

import com.github.atheera.recipemanager.*
import com.github.atheera.recipemanager.extras.ButtonRecipeCard
import com.github.atheera.recipemanager.gui.exc
import com.github.atheera.recipemanager.gui.frames.recipe.SavedRecipeFrame
import com.github.atheera.recipemanager.save.read.ReadRecipe
import com.github.atheera.recipemanager.save.read.ReadRecipeFavorite
import com.github.atheera.recipemanager.save.read.ReadSettings
import net.miginfocom.swing.MigLayout
import java.awt.CardLayout
import java.awt.Dimension
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.ItemEvent
import com.github.atheera.recipemanager.save.Files
import java.awt.event.ItemListener
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
            addedButton.clear()
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

        val file = File(Files().makeRecipeDir(category, subCat))
        val files = file.listFiles()
        for(allFiles in files!!) {
            val names = allFiles.name
            val title = removeLast(names, 5)
            when(category) {
                categories[0] -> {
                    val jb = createButton(title, categories[0], subCat, names, false)
                    if(subCat == recipeSubCategory)
                        searchedFiles.add(jb)
                }
                categories[1] -> {
                    val jb = createButton(title, categories[1], subCat, names, false)
                    if(subCat == recipeSubCategory)
                        searchedFiles.add(jb)
                }
                categories[2] -> {
                    val jb = createButton(title, categories[2], subCat, names, false)
                    if(subCat == recipeSubCategory)
                        searchedFiles.add(jb)
                }
            }
        }

        if(searchedFiles.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No Recipes Found!")
            return
        }

        val num = random.nextInt(searchedFiles.size)
        val item = searchedFiles[num]

        jpRecipe.add(item)
        updateUI()
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
            val recFile = File(recipeFavPath)
            val recFiles = recFile.listFiles()

            for(allFiles in recFiles!!) {
                val files = allFiles.name
                val title = removeLast(files, 5)
                loopRecipes(title, files)
            }

        } else {

            for (i in subCatDesserts.indices) {
                val subcat = subCatDesserts[i]
                val file = File(Files().makeRecipeDir(categories[0], subcat))
                val files = file.listFiles()
                for (allFiles in files!!) {
                    val names = allFiles.name
                    val title = removeLast(names, 5)
                    when (subcat) { subcat -> {
                        val jb = createButton(title, categories[0], subcat, names, favorite)
                        if (subcat == recipeSubCategory) searchedFiles.add(jb)
                        }
                    }
                }
            }

            for (i in subCatExtras.indices) {
                val subcat = subCatExtras[i]
                val file = File(Files().makeRecipeDir(categories[1], subcat))
                val files = file.listFiles()
                for (allFiles in files!!) {
                    val names = allFiles.name
                    val title = removeLast(names, 5)
                    when (subcat) { subcat -> {
                        val jb = createButton(title, categories[1], subcat, names, favorite)
                        if (subcat == recipeSubCategory) searchedFiles.add(jb)
                        }
                    }
                }
            }

            for (i in subCatMeats.indices) {
                val subcat = subCatMeats[i]
                val file = File(Files().makeRecipeDir(categories[2], subcat))
                val files = file.listFiles()
                for (allFiles in files!!) {
                    val names = allFiles.name
                    val title = removeLast(names, 5)
                    when (subcat) { subcat -> {
                        val jb = createButton(title, categories[2], subcat, names, favorite)
                        if (subcat == recipeSubCategory) searchedFiles.add(jb)
                        }
                    }
                }
            }

            val file = File(recipeFavPath)
            val files = file.listFiles()
            for (allFiles in files!!) {
                val names = allFiles.name
                val title = removeLast(names, 5)
                loopRecipes(title, names)
            }
        }

        if(searchedFiles.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No Recipes Found!")
            return
        }

        val num = random.nextInt(searchedFiles.size)
        val item = searchedFiles[num]

        addedButton.add(item)
        jpRecipe.add(item)

        try {
            if(addedButton[0].getTitle() == item.getTitle()) {
                println(item.getTitle())
                println(addedButton[0].getTitle())
                loadRecipes()
            } else {
                addedButton.clear()
                updateUI()
            }
        } catch (e: Exception) {

        }

        updateUI()
    }

    private fun loopRecipes(title: String, files: String) {
        for (i in subCatDesserts.indices) {
            when (val cat = subCatDesserts[i]) { cat -> {
            val jb = createButton(title, categories[0], cat, files, true);
            if(cat == recipeSubCategory) searchedFiles.add(jb)
        } } }
        for (i in subCatExtras.indices) { when (val cat = subCatExtras[i]) { cat -> {
            val jb = createButton(title, categories[1], cat, files, true);
            if(cat == recipeSubCategory) searchedFiles.add(jb)
        } } }
        for (i in subCatMeats.indices) { when (val cat = subCatMeats[i]) { cat -> {
            val jb = createButton(title, categories[2], cat, files, true);
            if(cat == recipeSubCategory) searchedFiles.add(jb)
        } } }
    }

    private fun createButton(title: String, cat: String, subCat: String, file: String, favorite: Boolean) : ButtonRecipeCard {
        if(favorite) ReadRecipeFavorite(file)
        else ReadRecipe(cat, subCat, file)

        val rTitle = recipeTitle
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

        val jb = ButtonRecipeCard(title, cat, subCat, desc)

        jb.addActionListener {
            val srf = SavedRecipeFrame(rTitle, category, subCategory, instructions, ingredients, desc, temperature, convTemperature, egg, gluten, lactose, vegan, vegetarian)
            srf.setLocationRelativeTo(this)
        }
        return jb
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