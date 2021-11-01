package com.github.atheera.recipemanager.gui.panels.list

import com.github.atheera.recipemanager.extras.HintTextField
import com.github.atheera.recipemanager.*
import com.github.atheera.recipemanager.extras.DeleteButton
import com.github.atheera.recipemanager.extras.ToolTipLabel
import com.github.atheera.recipemanager.save.read.ReadSettings
import com.google.gson.JsonArray
import net.miginfocom.swing.MigLayout
import java.awt.Color
import java.awt.Dimension
import java.awt.Font
import javax.swing.*

class NewCategoryListPanel : JPanel(MigLayout()) {

    companion object Text {
        const val TITLEHINT = "Enter the list title here"
        const val CATEGORYHINT = "Add a category"
        const val ITEMHINT = "Name an item for the list here"
    }

    val htfTitle = HintTextField(TITLEHINT)
    private lateinit var htfItem: HintTextField
    private val htfCategory = HintTextField(CATEGORYHINT)
    private val ttlList = ToolTipLabel("Press enter when in the category text field to add to list")

    private val jbList = JButton("Add to list")
    val jbSave = JButton("Press me to save the list to: $listPath/${listCategories[3]}")

    val jpList = JPanel(MigLayout())
    private val jpListOut = JPanel(MigLayout())
    private val jspList = JScrollPane(jpList)

    private val itemList = mutableListOf<MutableList<String>>()
    private val catList = mutableListOf<JPanel>()
    private val catsList = mutableListOf<String>()

    private val jsonCatList = JsonArray()

    private val dim = Dimension(450, 600)
    private val fontA = Font("Tahoma", Font.BOLD, 16)

    init {
        darkmode()

        htfTitle.minimumSize = Dimension(210, 25)
        htfCategory.minimumSize = Dimension(285, 25)

        jpList.minimumSize = dim
        jpListOut.minimumSize = dim
        jspList.minimumSize = dim
        jspList.verticalScrollBar.unitIncrement = 16
        jspList.verticalScrollBarPolicy = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED

        htfCategory.addActionListener { addToList() }
        jbList.addActionListener { addToList() }

        jbSave.addActionListener {
            println(itemList)
            println(catList)
            println(catsList)
        }

        // Add to panel
        add(htfTitle, "align center, wrap")
        add(htfCategory, "align center, split 2")
        add(ttlList, "align center, wrap")
        add(jbList, "align center, wrap")
        jpListOut.add(jspList, "align center, wrap")
        add(jpListOut, "align center, wrap")
        add(jbSave, "align center")

    }

    fun darkmode() {
        ReadSettings()

        darkMode(this)
        darkModeOut(jpListOut)
        darkModeIn(jpList)

        this.border = createBorder("Here you can add lists with categories")
        for(item in catList.indices) {
            darkModeDetail(catList[item])
            for(category in catsList.indices) catList[item].border = createBorder(catsList[category])
        }
        updateUI()
    }

    private fun clearInfo() {
        htfTitle.text = ""
        htfCategory.text = ""

        itemList.clear()
        catList.clear()

        jpList.removeAll()
        updateUI()
    }

    private fun addToList() {
        if (htfCategory.text.isEmpty() || htfCategory.text.equals(CATEGORYHINT)) {
            JOptionPane.showMessageDialog(this, "You need a category to add first!")
        } else {
            val catCard = createCategoryCard(htfCategory.text, jpList)
            jpList.add(catCard, "wrap")
            darkmode()
            htfCategory.text = ""
            updateUI()
        }
    }

    fun createCategoryCard(category: String, removePane: JPanel) : JPanel {
        val item = upperCaseFirstWords(category)

        val jp = JPanel(MigLayout())
        val jbDelete = DeleteButton()

        val htfItem = HintTextField("Add an item to category: $category")
        val jbItem = JButton("Add item")
        val ttlItem = ToolTipLabel("You can also press enter to add item")

        val categoryList = mutableListOf<String>()

        catList.add(jp)
        categoryList.add(category)

        htfItem.minimumSize = Dimension(300, 25)

        htfItem.addActionListener {
            addItemCard(htfItem.text, htfItem, jp, categoryList, category)
        }
        jbItem.addActionListener {
            addItemCard(htfItem.text, htfItem, jp, categoryList, category)
        }
        jbDelete.addActionListener {
            removePane.remove(jp)
            catList.remove(jp)
            catsList.remove(category)
            updateUI()
        }

        itemList.add(categoryList)

        jp.add(htfItem, "align center, split 3")
        jp.add(jbItem, "align center")
        jp.add(ttlItem, "align center, wrap")
        jp.add(jbDelete, "align center, dock south")

        return jp
    }

    fun addItemCard(item: String, htf: HintTextField, jp: JPanel, ml: MutableList<String>, category: String) {
        if(htfItem.text.equals("Add an item to category: $category") || item.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Invalid item to add!")
            return
        }
        val itemPanel = createItemCard(htfItem.text, htfItem, jp, ml)
        jp.add(itemPanel, "wrap")
        darkmode()
        updateUI()
    }

    fun createItemCard(item: String, htf: HintTextField, jpRemove: JPanel, mlCategory: MutableList<String>) : JPanel {
        val finalItem = upperCaseFirstWords(item)
        val jlItem = JLabel(finalItem)

        val jp = JPanel(MigLayout())
        val jbDelete = DeleteButton()
        mlCategory.add(finalItem)

        jbDelete.addActionListener {
            jpRemove.remove(jp)
            updateUI()
        }

        jp.border = BorderFactory.createLineBorder(Color.BLACK)

        jp.add(jbDelete, "align center, split 2")
        jp.add(jlItem, "align center, wrap")

        htf.text = ""
        updateUI()
        return jp
    }

}