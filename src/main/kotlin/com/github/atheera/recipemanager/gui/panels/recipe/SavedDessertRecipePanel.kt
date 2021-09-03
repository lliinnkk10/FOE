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
import javax.swing.*
import javax.swing.border.EmptyBorder

class SavedDessertRecipePanel : JPanel() {

    private var jcbCat = JComboBox(subCatDesserts.toTypedArray())
    private var clCat = CardLayout()
    private var jpCat = JPanel(clCat)
    private var jpContent = JPanel(MigLayout())
    private var jspContent = JScrollPane(jpCat)

    private var jpCakeCard = JPanel(MigLayout("wrap 1"))
    private var jpChocolateCard = JPanel(MigLayout("wrap 1"))
    private var jpConfectionCard = JPanel(MigLayout("wrap 1"))
    private var jpCookieCard = JPanel(MigLayout("wrap 1"))
    private var jpCustardCard = JPanel(MigLayout("wrap 1"))
    private var jpFriedCard = JPanel(MigLayout("wrap 1"))
    private var jpFrozenCard = JPanel(MigLayout("wrap 1"))
    private var jpGelatinCard = JPanel(MigLayout("wrap 1"))
    private var jpPastryCard = JPanel(MigLayout("wrap 1"))
    private var jpPieCard = JPanel(MigLayout("wrap 1"))
    private var jpPuddingCard = JPanel(MigLayout("wrap 1"))
    private var jpBreadCard = JPanel(MigLayout("wrap 1"))
    private var jpTartCard = JPanel(MigLayout("wrap 1"))

    private lateinit var selectedCat: String
    private lateinit var selectedButton: JButton

    init {

        // Initialize
        jspContent.verticalScrollBarPolicy = ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS
        jspContent.verticalScrollBar.unitIncrement = 16
        jspContent.minimumSize = Dimension(533, 550)
        jspContent.maximumSize = Dimension(533, 550)

        jcbCat.addItemListener {
            if(it.stateChange == ItemEvent.SELECTED) {
                selectedCat = it.item as String
                clCat.show(jpCat, selectedCat)
                updateUI()
            }
        }

        jpContent.add(jcbCat, "align center, wrap")

        jpCat.add(jpCakeCard, subCatDesserts[0])
        jpCat.add(jpChocolateCard, subCatDesserts[1])
        jpCat.add(jpConfectionCard, subCatDesserts[2])
        jpCat.add(jpCookieCard, subCatDesserts[3])
        jpCat.add(jpCustardCard, subCatDesserts[4])
        jpCat.add(jpFriedCard, subCatDesserts[5])
        jpCat.add(jpFrozenCard, subCatDesserts[6])
        jpCat.add(jpGelatinCard, subCatDesserts[7])
        jpCat.add(jpPastryCard, subCatDesserts[8])
        jpCat.add(jpPieCard, subCatDesserts[9])
        jpCat.add(jpPuddingCard, subCatDesserts[10])
        jpCat.add(jpBreadCard, subCatDesserts[11])
        jpCat.add(jpTartCard, subCatDesserts[12])

        jpContent.add(jspContent, "align center")
        add(jpContent)

        clCat.show(jpCat, subCatDesserts[0])
    }

    fun darkmode() {
        ReadSettings()
        border = createBorder("Select which dessert category to load buttons for")

        darkMode(this)
        darkModeOut(jpContent)
        darkModeIn(jpCat)
        darkModeIn(jpCakeCard)
        darkModeIn(jpChocolateCard)
        darkModeIn(jpConfectionCard)
        darkModeIn(jpCookieCard)
        darkModeIn(jpCustardCard)
        darkModeIn(jpFriedCard)
        darkModeIn(jpFrozenCard)
        darkModeIn(jpGelatinCard)
        darkModeIn(jpPastryCard)
        darkModeIn(jpPieCard)
        darkModeIn(jpPuddingCard)
        darkModeIn(jpBreadCard)
        darkModeIn(jpTartCard)
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
                                if(file.delete()) JOptionPane.showMessageDialog(jb, "Deleted file at: ${file.name}")
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
            jpCakeCard.removeAll()
            jpChocolateCard.removeAll()
            jpConfectionCard.removeAll()
            jpCookieCard.removeAll()
            jpCustardCard.removeAll()
            jpFriedCard.removeAll()
            jpFrozenCard.removeAll()
            jpGelatinCard.removeAll()
            jpPastryCard.removeAll()
            jpPieCard.removeAll()
            jpPuddingCard.removeAll()
            jpBreadCard.removeAll()
            jpTartCard.removeAll()
        } catch (e: Exception) {
            exc(e)
            e.printStackTrace()
        }

        for(type in subCatDesserts) {
            val file = File(Files().makeRecipeDir(categories[0], type))
            val files = file.listFiles()
            for(allFiles in files!!) {
                val names = allFiles.name
                val name = removeLast(names, 5)

                when (type) {
                    subCatDesserts[0] -> {
                        selectedButton = createButton(categories[0], type, name, names)
                        jpCakeCard.add(selectedButton)
                    }
                    subCatDesserts[1] -> {
                        selectedButton = createButton(categories[0], type, name, names)
                        jpChocolateCard.add(selectedButton)
                    }
                    subCatDesserts[2] -> {
                        selectedButton = createButton(categories[0], type, name, names)
                        jpConfectionCard.add(selectedButton)
                    }
                    subCatDesserts[3] -> {
                        selectedButton = createButton(categories[0], type, name, names)
                        jpCookieCard.add(selectedButton)
                    }
                    subCatDesserts[4] -> {
                        selectedButton = createButton(categories[0], type, name, names)
                        jpCustardCard.add(selectedButton)
                    }
                    subCatDesserts[5] -> {
                        selectedButton = createButton(categories[0], type, name, names)
                        jpFriedCard.add(selectedButton)
                    }
                    subCatDesserts[6] -> {
                        selectedButton = createButton(categories[0], type, name, names)
                        jpFrozenCard.add(selectedButton)
                    }
                    subCatDesserts[7] -> {
                        selectedButton = createButton(categories[0], type, name, names)
                        jpGelatinCard.add(selectedButton)
                    }
                    subCatDesserts[8] -> {
                        selectedButton = createButton(categories[0], type, name, names)
                        jpPastryCard.add(selectedButton)
                    }
                    subCatDesserts[9] -> {
                        selectedButton = createButton(categories[0], type, name, names)
                        jpPieCard.add(selectedButton)
                    }
                    subCatDesserts[10] -> {
                        selectedButton = createButton(categories[0], type, name, names)
                        jpPuddingCard.add(selectedButton)
                    }
                    subCatDesserts[11] -> {
                        selectedButton = createButton(categories[0], type, name, names)
                        jpBreadCard.add(selectedButton)
                    }
                    subCatDesserts[12] -> {
                        selectedButton = createButton(categories[0], type, name, names)
                        jpTartCard.add(selectedButton)
                    }
                }
            }
        }
    }

}