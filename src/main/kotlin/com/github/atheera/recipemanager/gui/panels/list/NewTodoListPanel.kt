package com.github.atheera.recipemanager.gui.panels.list

import com.github.atheera.recipemanager.*
import com.github.atheera.recipemanager.extras.HintTextField
import com.github.atheera.recipemanager.save.read.ReadSettings
import com.github.atheera.recipemanager.save.write.WriteListTD
import net.miginfocom.swing.MigLayout
import java.awt.Dimension
import java.awt.Font
import java.awt.Graphics
import java.awt.event.ItemEvent
import java.awt.font.TextAttribute
import java.util.*
import javax.swing.*

class NewTodoListPanel : JPanel(MigLayout("align center")) {

    var jpList = JPanel(MigLayout())
    private var jspList = JScrollPane(jpList)
    private var jpOuter = JPanel(MigLayout())

    private var jbAdd = JButton("Add to list")
    private var jbRemove = JButton("Remove all checked items")
    var htfTitle = HintTextField("Enter the list title here")
    private var htfItem = HintTextField("Enter the item to be done here")
    var jbSave = JButton("Press me to save the list to: $listPath/${listCategories[1]}")

    private var list = mutableListOf<String>()
    private var checkList = mutableListOf<JCheckBox>()
    private val map = Hashtable<TextAttribute, Any?>()

    private val fontB = Font("Tahoma", Font.PLAIN, 20)
    var fontS = fontB
    private val dim = Dimension(350, 500)

    init {
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
            println(list)
            for(i in checkList.indices) {
                jpList.remove(checkList[i])
                list.remove(checkList[i].text)
            }
            checkList.clear()
            dw.add(list.toString())
            updateUI()
        }
        jbSave.addActionListener {
            if((htfTitle.text.isEmpty() || htfTitle.text == "Enter the list title here") || list.isEmpty())
                JOptionPane.showMessageDialog(this, "You need to enter information to save first!")
            else {
                val checked = mutableListOf<String>()
                for(i in checkList.indices) {
                    list.removeAt(i)
                    checked.add(checkList[i].text)
                }
                WriteListTD(listCategories[1], htfTitle.text, list, checked)
                JOptionPane.showMessageDialog(this, "Successfully saved list to: $listPath/${listCategories[1]}")
                clearInfo()
            }
        }

        add(htfTitle, "align center, wrap")
        add(htfItem, "align center, wrap")
        add(jbAdd, "align center, split 2")
        add(jbRemove, "align center, wrap")
        add(jpOuter, "align center, wrap")
        jpOuter.add(jspList, "align center, wrap")
        add(jbSave, "align center")

    }

    fun darkmode() : Boolean {
        ReadSettings(settingsPath)
        border = createBorder("Here you can make a list of things to do")

        darkMode(this)
        darkModeOut(jpOuter)
        darkModeIn(jpList)

        return isDark
    }

    private fun addToList() {
        if(htfItem.text.isEmpty() || htfItem.text == "Enter the item to be done here") {
            JOptionPane.showMessageDialog(this, "You need to enter an item first!")
        } else {
            val itemCard = createCard(htfItem.text)
            jpList.add(itemCard, "wrap")
            htfItem.text = ""
        }
    }

    fun createCard(item: String) : JCheckBox {
        val jcbChecked = JCheckBox(item)
        jcbChecked.font = fontB
        //jcbChecked.foreground = if(darkmode()) Color.WHITE else Color.BLACK
        //jcbChecked.background = if(darkmode()) GRAY else WHITE

        list.add(item)

        jcbChecked.addItemListener {
            if(it.stateChange == ItemEvent.SELECTED) {
                checkList.add(jcbChecked)
                jcbChecked.font = fontS
            } else if ((it.stateChange == ItemEvent.DESELECTED)) {
                checkList.remove(jcbChecked)
                jcbChecked.font = fontB
            }
        }

        updateUI()
        return jcbChecked
    }

    private fun clearInfo() {
        htfItem.text = ""
        htfTitle.text = ""

        jspList.removeAll()

        list.clear()
        checkList.clear()
        updateUI()
    }

    override fun paint(g: Graphics?) {
        super.paint(g)
        map[TextAttribute.STRIKETHROUGH] = TextAttribute.STRIKETHROUGH_ON
        fontS = fontB.deriveFont(map)
    }

}