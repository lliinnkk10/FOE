package com.github.atheera.recipemanager.gui.panels.other

import com.github.atheera.recipemanager.createBorder
import com.github.atheera.recipemanager.darkMode
import com.github.atheera.recipemanager.darkModeOut
import com.github.atheera.recipemanager.extras.HintTextField
import com.github.atheera.recipemanager.gui.exc
import net.miginfocom.swing.MigLayout
import java.awt.Font
import javax.swing.*

class ConversionTablePanel : JPanel() {

    private val jpContent = JPanel(MigLayout())

    private val jcbFrom = JComboBox<String>()
    private val htfInput = HintTextField("Enter input here")
    private val jcbTo = JComboBox<String>()
    private val jlOutput = JLabel()

    private val jrbLiquid = JRadioButton("Liquid")
    private val alLiquid = listOf("")
    private val jrbSolid = JRadioButton("Solid")
    private val alSolid = listOf("")
    private val bgJRB = ButtonGroup()

    private val fontA = Font("Tahoma", Font.BOLD, 20)

    init {
        darkmode()

        bgJRB.add(jrbLiquid)
        bgJRB.add(jrbSolid)
        jrbLiquid.doClick()
        addJCB()

        htfInput.font = fontA
        jlOutput.font = fontA

        jpContent.add(jrbLiquid, "align center, split 2")
        jpContent.add(jrbSolid, "wrap")
        jpContent.add(jcbFrom, "align center, wrap")
        jpContent.add(htfInput, "align center, wrap")
        jpContent.add(jcbTo, "align center, wrap")
        jpContent.add(jlOutput, "align center, wrap")
        add(jpContent)

    }

    private fun addJCB() {
        try {
            jcbFrom.removeAllItems()
            jcbTo.removeAllItems()
        } catch (e: Exception) {
            exc(e)
            e.printStackTrace()
        }

        if (jrbLiquid.isSelected) {
            for (item in alLiquid) {
                jcbFrom.addItem(item)
                jcbTo.addItem(item)
            }
        } else {
            for (item in alSolid) {
                jcbFrom.addItem(item)
                jcbTo.addItem(item)
            }
        }

    }

    fun darkmode() {
        border = createBorder("Here you can easily convert most used measurements")

        darkMode(this)
        darkModeOut(jpContent)

    }

}