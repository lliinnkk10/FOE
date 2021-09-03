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

class SavedExtraRecipePanel: JPanel() {

    private var jcbCat = JComboBox(subCatExtras.toTypedArray())
    private var clCat = CardLayout()
    private var jpCat = JPanel(clCat)
    private var jpContent = JPanel(MigLayout())
    private var jspContent = JScrollPane(jpCat)

    private var jpBreadCard = JPanel(MigLayout("wrap 1"))
    private var jpGreenCard = JPanel(MigLayout("wrap 1"))
    private var jpSauceCard = JPanel(MigLayout("wrap 1"))
    private var jpPieCard = JPanel(MigLayout("wrap 1"))
    private var jpSoupCard = JPanel(MigLayout("wrap 1"))

    private lateinit var selectedCat: String
    private lateinit var selectedButton: JButton

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

        jpCat.add(jpBreadCard, subCatExtras[0])
        jpCat.add(jpGreenCard, subCatExtras[1])
        jpCat.add(jpSauceCard, subCatExtras[2])
        jpCat.add(jpPieCard, subCatExtras[3])
        jpCat.add(jpSoupCard, subCatExtras[4])

        jpContent.add(jspContent, "align center")
        add(jpContent)

        clCat.show(jpCat, subCatExtras[0])
        darkmode()
    }

    fun darkmode() {
        ReadSettings()
        border = createBorder("Select which extra category to load buttons for")

        darkMode(this)
        darkModeOut(jpContent)
        darkModeIn(jpBreadCard)
        darkModeIn(jpGreenCard)
        darkModeIn(jpSauceCard)
        darkModeIn(jpPieCard)
        darkModeIn(jpSoupCard)
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

        val jb = ButtonRecipeCard(name, cat, subCat, desc)

        jb.addActionListener {
            val spf = SavedRecipeFrame(title, category, subCategory, instructions, ingredients, desc, temperature, convTemperature, egg, gluten, lactose, vegan, vegetarian)
            spf.setLocationRelativeTo(this)
        }

        jb.addMouseListener(object: MouseListener {
            val pm = JPopupMenu()
            val b = JButton("Delete saved file")
            val file = File(recipePath.plus("$cat/$subCat/$names"))
            override fun mouseClicked(e: MouseEvent) {
                if(e.button == MouseEvent.BUTTON3) {
                    b.addActionListener {
                        val jop = JOptionPane.showConfirmDialog(jb, "Are you sure you want to delete file: ${file.name}?", "Delete selected file", JOptionPane.YES_NO_OPTION)
                        if(jop == 0) {
                            try {
                                if(file.delete()) JOptionPane.showMessageDialog(jb, "Deleted file: ${file.name}")
                                else JOptionPane.showMessageDialog(jb, "Failed to delete file: ${file.name}")
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                        loadRecipes()
                        e.consume()
                        pm.isVisible = false
                        updateUI()
                    }
                }
                pm.border = EmptyBorder(0, 0, 0 ,0)
                pm.add(b)
                pm.preferredSize = b.preferredSize
                pm.show(jb, e.x, e.y)
            }
            override fun mouseExited(e: MouseEvent) {
                if(e.component == b && e.component == jb) {
                    pm.isVisible = false
                    updateUI()
                }
            }
            override fun mousePressed(e: MouseEvent?) { }
            override fun mouseReleased(e: MouseEvent?) { }
            override fun mouseEntered(e: MouseEvent?) { }
        })

        return jb
    }

    fun loadRecipes() {
        try {
            jpBreadCard.removeAll()
            jpGreenCard.removeAll()
            jpPieCard.removeAll()
            jpSauceCard.removeAll()
            jpSoupCard.removeAll()
        } catch (e: IOException) {
            exc(e)
            e.printStackTrace()
        }

        for(type in subCatExtras) {
            val file = File(Files().makeRecipeDir(categories[1], type))
            val files = file.listFiles()
            for(allFiles in files!!) {
                val names = allFiles.name
                val name = removeLast(names, 5)

                when (type) {
                    subCatExtras[0] -> {
                        selectedButton = createButton(categories[1], type, name, names)
                        jpBreadCard.add(selectedButton)
                    }
                    subCatExtras[1] -> {
                        selectedButton = createButton(categories[1], type, name, names)
                        jpGreenCard.add(selectedButton)
                    }
                    subCatExtras[2] -> {
                        selectedButton = createButton(categories[1], type, name, names)
                        jpSauceCard.add(selectedButton)
                    }
                    subCatExtras[3] -> {
                        selectedButton = createButton(categories[1], type, name, names)
                        jpPieCard.add(selectedButton)
                    }
                    subCatExtras[4] -> {
                        selectedButton = createButton(categories[1], type, name, names)
                        jpSoupCard.add(selectedButton)
                    }
                }
            }
        }

    }

}