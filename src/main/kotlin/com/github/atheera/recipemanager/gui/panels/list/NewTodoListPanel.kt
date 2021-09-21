package com.github.atheera.recipemanager.gui.panels.list

import com.github.atheera.recipemanager.*
import com.github.atheera.recipemanager.extras.CJPanel
import com.github.atheera.recipemanager.extras.HintTextField
import com.github.atheera.recipemanager.save.write.WriteListTD
import net.miginfocom.swing.MigLayout
import java.awt.Dimension
import java.awt.Font
import java.awt.event.ItemEvent
import java.awt.font.TextAttribute
import java.util.*
import javax.swing.*

class NewTodoListPanel : CJPanel(MigLayout("align center")) {

    var jpList = JPanel(MigLayout())
    private var jspList = JScrollPane(jpList)
    private var jpOuter = JPanel(MigLayout("align center"))

    private var jbAdd = JButton("Add to list")
    private var jbRemove = JButton("Remove all checked items")
    private val titleText = "Enter the list title here"
    private val itemText = "Enter the item to be done here"
    var htfTitle = HintTextField(titleText)
    private var htfItem = HintTextField(itemText)
    var jbSave = JButton("Press me to save the list to: $listPath/${listCategories[1]}")

    private var list = mutableListOf<String>()
    private var checkedList = mutableListOf<String>()
    private var checkList = mutableListOf<JCheckBox>()
    private val map = Hashtable<TextAttribute, Any?>()

    private var itemCard = JCheckBox()

    private val fontB = Font("Tahoma", Font.PLAIN, 20)
    var fontS: Font
    private val dim = Dimension(500, 500)

    init {
        map[TextAttribute.STRIKETHROUGH] = TextAttribute.STRIKETHROUGH_ON
        fontS = fontB.deriveFont(map)

        darkmode()

        htfTitle.minimumSize = Dimension(250, 25)
        htfItem.minimumSize = Dimension(350, 25)

        jpList.minimumSize = dim
        jspList.minimumSize = dim
        jpOuter.minimumSize = dim
        jspList.verticalScrollBar.unitIncrement = 16
        jspList.verticalScrollBarPolicy = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED

        htfItem.addActionListener {
            addToList()
        }
        jbAdd.addActionListener {
            addToList()
        }
        jbRemove.addActionListener {
            for(i in checkList.indices) {
                jpList.remove(checkList[i])
            }
            checkList.clear()
            checkedList.clear()
            updateUI()
        }
        jbSave.addActionListener {
            if((htfTitle.text.isEmpty() || htfTitle.text == titleText) || list.isEmpty())
                JOptionPane.showMessageDialog(this, "You need to enter information to save first!")
            else {
                WriteListTD(listCategories[1], upperCaseFirstWords(htfTitle.text), list, checkedList)
                JOptionPane.showMessageDialog(this, "Successfully saved list to: $listPath/${listCategories[1]}")
                clearInfo()
            }
        }
        jpOuter.add(htfTitle, "align center, wrap")
        jpOuter.add(htfItem, "align center, wrap")
        jpOuter.add(jbAdd, "align center, split 2")
        jpOuter.add(jbRemove, "align center, wrap")
        jpOuter.add(jspList, "align center, wrap")
        jpOuter.add(jbSave, "align center")
        add(jpOuter, "align center")
    }

    fun darkmode(){
        border = createBorder("Here you can make a list of things to do")

        darkMode(this)
        darkModeOut(jpOuter)
        darkModeIn(jpList)

        darkModeDetail(itemCard)

    }

    private fun addToList() {
        if(htfItem.text.isEmpty() || htfItem.text == itemText) {
            JOptionPane.showMessageDialog(this, "You need to enter an item first!")
        } else {
            itemCard = createCard(htfItem.text, false)
            jpList.add(itemCard, "wrap")
            htfItem.text = ""
            darkmode()
            updateUI()
        }
    }

    fun createCard(item: String, saved: Boolean) : JCheckBox {
        val finalItem = if(!saved) upperCaseFirstWords(item) else item

        val jcbChecked = JCheckBox(finalItem)
        jcbChecked.font = if(jcbChecked.isSelected) fontS else fontB

        list.add(finalItem)

        jcbChecked.addItemListener {
            if (it.stateChange == ItemEvent.SELECTED) {
                checkList.add(jcbChecked)
                checkedList.add(finalItem)
                list.remove(finalItem)
                jcbChecked.font = fontS
            } else if (it.stateChange == ItemEvent.DESELECTED) {
                checkList.remove(jcbChecked)
                checkedList.add(finalItem)
                list.add(finalItem)
                jcbChecked.font = fontB
            }
        }

        updateUI()
        return jcbChecked
    }

    private fun clearInfo() {
        htfItem.text = itemText
        htfTitle.text = titleText

        jpList.removeAll()

        list.clear()
        checkList.clear()
        updateUI()
    }

    override fun defaultLoad() {

    }

}