package com.github.atheera.recipemanager.gui.panels.list

import com.github.atheera.recipemanager.*
import com.github.atheera.recipemanager.extras.DeleteButton
import com.github.atheera.recipemanager.extras.HintTextField
import com.github.atheera.recipemanager.extras.ToolTipLabel
import com.github.atheera.recipemanager.gui.info
import com.github.atheera.recipemanager.listCategories
import com.github.atheera.recipemanager.listPath
import com.github.atheera.recipemanager.save.write.WriteNormalList
import com.github.atheera.recipemanager.upperCaseFirstWords
import net.miginfocom.swing.MigLayout
import java.awt.Color
import java.awt.Dimension
import java.awt.Font
import javax.swing.*

class NewNormalListPanel : JPanel(MigLayout()) {

    companion object Text {
        const val TITLEHINT = "Enter the list title here"
        const val ITEMHINT = "Name the item for the list here"
    }

    var htfTitle = HintTextField(TITLEHINT)
    private var htfItem = HintTextField(ITEMHINT)
    private var jbList = JButton("Add to list")
    private var ttlList = ToolTipLabel("Press enter when in the item text field to add to list!")
    var jbSave = JButton("Press me to save the list to: $listPath/${listCategories[2]}")

    var jpList = JPanel(MigLayout())
    private var jpListOut = JPanel(MigLayout())
    private var jspList = JScrollPane(jpList)
    private var itemList = mutableListOf<String>()
    private var itemCounter: Int = 0
    private val fontA = Font("Tahoma", Font.BOLD, 16)
    private val dim = Dimension(450, 600)

    init {
        darkmode()

        // Functions
        htfTitle.minimumSize = Dimension(210, 25)
        htfItem.minimumSize = Dimension(285, 25)
            // List
        jpList.minimumSize = dim
        jpListOut.minimumSize = dim
        jspList.minimumSize = dim
        jspList.verticalScrollBar.unitIncrement = 16
        jspList.verticalScrollBarPolicy = ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS
            // Misc
        htfItem.addActionListener { addToList() }
        jbList.addActionListener { addToList() }
        jbSave.addActionListener {
            if((htfTitle.text.isEmpty() || htfTitle.text.equals(TITLEHINT) || itemList.isEmpty()))
                JOptionPane.showMessageDialog(this, "You need to enter some information to save first!")
            else {
                WriteNormalList(listCategories[2], htfTitle.text, itemList)
                info(listPath)
                JOptionPane.showMessageDialog(this, "Successfully saved list to: $listPath/${listCategories[2]}")
                clearInfo()
            }
        }

        // Add to panel
        add(htfTitle, "align center, wrap")
        add(htfItem, "align center, split 2")
        add(ttlList, "align center, wrap")
        add(jbList, "align center, wrap")
        jpListOut.add(jspList, "align center, wrap")
        add(jpListOut, "align center, wrap")
        add(jbSave, "align center")

    }

    fun darkmode() {
        border = createBorder("This is just a plain list")

        darkMode(this)
        darkModeOut(jpListOut)
        darkModeIn(jpList)
    }

    private fun clearInfo() {
        htfItem.text = ""
        htfTitle.text = ""

        itemCounter = 0
        itemList.clear()
        jpList.removeAll()
        updateUI()
    }

    private fun addToList() {
        if(htfItem.text.isEmpty() || htfItem.text.equals(ITEMHINT)) {
            JOptionPane.showMessageDialog(this, "You need to enter an item first!")
        } else {
            val itemCard = createCard(htfItem.text, jpList, false)
            jpList.add(itemCard, "wrap")
            htfItem.text = ""
        }
    }

    fun createCard(item: String, removePane: JPanel, saved: Boolean) : JPanel {
        val finalItem = if(saved) upperCaseFirstWords(item) else item
        val jlItem = JLabel(finalItem)

        val jp = JPanel(MigLayout())
        val jbDelete = DeleteButton()

        itemList.add(itemCounter, finalItem)
        itemCounter++

        jlItem.font = fontA
        jp.border = BorderFactory.createLineBorder(Color.BLACK)
        jp.minimumSize = Dimension(417, 40)
        jp.maximumSize = Dimension(417, 40)

        jbDelete.addActionListener {
            removePane.remove(jp)
            updateUI()
            itemList.remove(finalItem)
            itemCounter--
        }

        jp.add(jbDelete)
        jp.add(jlItem)
        updateUI()
        return jp
    }
}