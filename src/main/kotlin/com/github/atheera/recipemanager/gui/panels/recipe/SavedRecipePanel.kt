package com.github.atheera.recipemanager.gui.panels.recipe

import com.github.atheera.recipemanager.*
import net.miginfocom.swing.MigLayout
import java.awt.CardLayout
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.ItemEvent
import java.awt.event.ItemListener
import javax.swing.*

// Categories
private var jpCat = JPanel(MigLayout())
private lateinit var jcbCategories: JComboBox<String>
private var cla = CardLayout()
private val jpCatCard = JPanel(cla)
private val jpSubCatDe = JPanel()
private val jpSubCatEx = JPanel()
private val jpSubCatMe = JPanel()
private var bgJRBGroups = ButtonGroup()
private lateinit var selCategory: String
private lateinit var selSubCats: String
private lateinit var jrbSels: JRadioButton
private lateinit var bmCats: ButtonModel

class SavedRecipePanel : JPanel(MigLayout()), ItemListener, ActionListener {

    init {

        addJCBList()

        add(jpCat, "align center, wrap")
    }

    fun loadRecipes() {

    }

    fun addJCBList() {

        jcbCategories = JComboBox(categories.toTypedArray())
        jcbCategories.isEditable = false
        jcbCategories.addItemListener(this)
        jcbCategories.selectedIndex = 0
        selCategory = categories[0]

        for(i in subCatDesserts.indices) {
            val jrb = JRadioButton(subCatDesserts[i])
            jpSubCatDe.add(jrb)
            bgJRBGroups.add(jrb)
            jrb.actionCommand = i.toString()
            jrb.addActionListener(this)
            if(i == 0)
                jrb.doClick()
        }
        for(i in subCatExtras.indices) {
            val jrb = JRadioButton(subCatExtras[i])
            jpSubCatEx.add(jrb)
            bgJRBGroups.add(jrb)
            jrb.actionCommand = i.toString()
            jrb.addActionListener(this)
        }
        for(i in subCatMeats.indices) {
            val jrb = JRadioButton(subCatMeats[i])
            jpSubCatMe.add(jrb)
            bgJRBGroups.add(jrb)
            jrb.actionCommand = i.toString()
            jrb.addActionListener(this)
        }
        bmCats = bgJRBGroups.selection
        bgJRBGroups.setSelected(bmCats, true)
        jpCatCard.add(jpSubCatDe, categories[0])
        jpCatCard.add(jpSubCatEx, categories[1])
        jpCatCard.add(jpSubCatMe, categories[2])
        jpCat.add(jcbCategories, "align center, wrap")
        jpCat.add(jpCatCard, "wrap")
        jpCat.border = createBorder("Select the category in the drop box, and what kind of food with the buttons")
    }

    override fun itemStateChanged(e: ItemEvent) {
        cla = jpCatCard.layout as CardLayout
        cla.show(jpCatCard, e.item as String)
        if(e.stateChange == ItemEvent.SELECTED)
            selCategory = e.item.toString()
        //updateButtons()
    }

    override fun actionPerformed(e: ActionEvent) {
        jrbSels = e.source as JRadioButton
        selSubCats = jrbSels.text
        //updateButtons()
    }
}