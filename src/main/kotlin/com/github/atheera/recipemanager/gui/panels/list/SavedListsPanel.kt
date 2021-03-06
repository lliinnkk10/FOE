package com.github.atheera.recipemanager.gui.panels.list

import com.github.atheera.recipemanager.*
import com.github.atheera.recipemanager.extras.ButtonListCard
import com.github.atheera.recipemanager.gui.exc
import com.github.atheera.recipemanager.save.Files
import com.github.atheera.recipemanager.save.read.ReadSettings
import net.miginfocom.swing.MigLayout
import java.awt.CardLayout
import java.awt.Dimension
import java.awt.event.ItemEvent
import java.io.File
import javax.swing.JComboBox
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.ScrollPaneConstants

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
    private lateinit var tempButton: ButtonListCard

    init {
        // Initialize
        jspContent.verticalScrollBarPolicy = ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS
        jspContent.verticalScrollBar.unitIncrement = 16
        jspContent.minimumSize = Dimension(537, 550)
        jspContent.maximumSize = Dimension(537, 550)

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
        this.border = createBorder("Select which list type to load buttons for")

        darkMode(this)
        darkModeOut(jpContent)
        darkModeOut(jpCat)
        darkModeIn(jpNCard)
        darkModeIn(jpTdCard)
        darkModeIn(jpPcCard)
    }
/*
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
                mouseListener(jb, file, type)
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
                mouseListener(jb, file, type)
            }
            listCategories[2] -> {
                jb.addActionListener {
                    ReadNormalList(file)
                    val titled = listNTitle
                    val list = listNList
                    val snl = SavedNList(titled, list)
                    snl.setLocationRelativeTo(this)
                }
                mouseListener(jb, file, type)
            }
        }

        return jb
    }

    private fun mouseListener(jb: JButton, files: String, type: String) {
        jb.addMouseListener(object: MouseListener {
            val pm = JPopupMenu()
            val b = JButton("Delete saved file")
            val file = File(listPath.plus("$type/$files"))
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
                        loadLists()
                        e.consume()
                        pm.isVisible = false
                        updateUI()
                    }
                }
                pm.border = EmptyBorder(0, 0, 0, 0)
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


    }*/

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
                        tempButton = ButtonListCard(name, listCategories[0])
                        jpPcCard.add(tempButton)
                    }
                    listCategories[1] -> { // To Do List
                        tempButton = ButtonListCard(name, listCategories[1])
                        jpTdCard.add(tempButton)
                    }
                    listCategories[2] -> { // Plain Item List
                        tempButton = ButtonListCard(name, listCategories[2])
                        jpNCard.add(tempButton)
                    }
                }

            }
        }
    }

}