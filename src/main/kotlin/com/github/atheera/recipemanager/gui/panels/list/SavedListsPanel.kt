package com.github.atheera.recipemanager.gui.panels.list

import com.github.atheera.recipemanager.*
import com.github.atheera.recipemanager.extras.ButtonListCard
import com.github.atheera.recipemanager.gui.exc
import com.github.atheera.recipemanager.gui.frames.list.SavedNList
import com.github.atheera.recipemanager.gui.frames.list.SavedPCList
import com.github.atheera.recipemanager.gui.frames.list.SavedTDList
import com.github.atheera.recipemanager.save.Files
import com.github.atheera.recipemanager.save.read.ReadListPC
import com.github.atheera.recipemanager.save.read.ReadListTD
import com.github.atheera.recipemanager.save.read.ReadNormalList
import com.github.atheera.recipemanager.save.read.ReadSettings
import net.miginfocom.swing.MigLayout
import java.awt.CardLayout
import java.awt.Dimension
import java.awt.event.ItemEvent
import java.io.File
import javax.swing.*

class SavedListsPanel : JPanel() {

    private var jcbCat: JComboBox<String> = JComboBox(listCategories.toTypedArray())
    private var clCat = CardLayout()
    private var jpCat = JPanel(clCat)
    private var jpContent = JPanel(MigLayout())
    private var jspContent = JScrollPane(jpCat)

    private var jpPcCard = JPanel(MigLayout("wrap 1"))
    private var jpTdCard = JPanel(MigLayout("wrap 1"))
    private var jpNCard = JPanel(MigLayout("wrap 1"))

    private lateinit var selectedType: String
    private lateinit var tempButton: JButton

    init {
        // Initialize
        jspContent.verticalScrollBarPolicy = ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS
        jspContent.verticalScrollBar.unitIncrement = 16
        jspContent.minimumSize = Dimension(535, 550)
        jspContent.maximumSize = Dimension(535, 550)

        // Functions
        jcbCat.addItemListener {
            if(it.stateChange == ItemEvent.SELECTED) {
                selectedType = it.item as String
                clCat.show(jpCat, selectedType)
                updateUI()
            }
        }

        // Add to screen
        jpContent.add(jcbCat, "align center, wrap")

        jpCat.add(jpPcCard, listCategories[0])
        jpCat.add(jpTdCard, listCategories[1])
        jpCat.add(jpNCard, listCategories[2])

        jpContent.add(jspContent, "align center")
        add(jpContent)

        clCat.show(jpCat, listCategories[0])
        darkmode()
    }

    fun darkmode() {
        ReadSettings(settingsPath)
        border = createBorder("Select which list type to load buttons for")

        darkMode(this)
        darkModeOut(jpContent)
        darkModeOut(jpCat)
        darkModeIn(jpNCard)
        darkModeIn(jpTdCard)
        darkModeIn(jpPcCard)
    }

    private fun createButton(title: String, type: String, file: String) : ButtonListCard {
        val jb = ButtonListCard(title, type)

        when(type) {
            listCategories[0] -> {
                jb.addActionListener {
                    ReadListPC(file)
                    val titled = listPCTitle
                    val posList = listPCPos
                    val negList = listPCNeg
                    val spcl = SavedPCList(titled, posList, negList)
                    spcl.setLocationRelativeTo(this)
                }
            }
            listCategories[1] -> {
                jb.addActionListener {
                    ReadListTD(file)
                    val titled = listTDTitle
                    val todolist = listTD
                    val checked = listTDChecked
                    val stdl = SavedTDList(titled, todolist, checked)
                    stdl.setLocationRelativeTo(this)
                }
            }
            listCategories[2] -> {
                jb.addActionListener {
                    ReadNormalList(file)
                    val titled = listNTitle
                    val list = listNList
                    val snl = SavedNList(titled, list)
                    snl.setLocationRelativeTo(this)
                }
            }
        }

        return jb
    }

    fun loadLists() {
        try {
            jpPcCard.removeAll()
            jpTdCard.removeAll()
            jpNCard.removeAll()
        } catch (e: Exception) {
            exc(e)
            e.printStackTrace()
        }
        for(type in listCategories) { // Loop through all list types
            val file = File(Files().makeListDir(type))
            val files = file.listFiles()
            for(allFiles in files!!) { // Loop through all files in that type
                // Gets the name for each file and removes .json
                val names = allFiles.name
                val name = removeLast(names, 5)
                println(names)

                when (type) { // Add buttons and function to each list type
                    listCategories[0] -> { // Pros/Cons
                        tempButton = createButton(name, listCategories[0], names)
                        jpPcCard.add(tempButton)
                    }
                    listCategories[1] -> { // To Do List
                        tempButton = createButton(name, listCategories[1], names)
                        jpTdCard.add(tempButton)
                    }
                    listCategories[2] -> { // Plain Item List
                        tempButton = createButton(name, listCategories[2], names)
                        jpNCard.add(tempButton)
                    }
                }

            }
        }
    }

}