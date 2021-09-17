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

    private enum class EVolume {
        USGALLON,
        USQUART,
        USPINT,
        USCUP,
        FLUIDOUNCE,
        USTBSP,
        USTSP,
        CUBICM,
        LITER,
        DECILITER,
        CENTILITER,
        MILLILITER,
        IMPGALLON,
        IMPQUART,
        IMPPINT,
        IMPCUP,
    }

    private val jpContent = JPanel(MigLayout())

    private val jcbFrom = JComboBox<String>()
    private val htfInput = HintTextField("Enter input here")
    private val jcbTo = JComboBox<String>()
    private val jlOutput = JLabel()

    private val jrbVolume = JRadioButton("Volume")
    private val alLiquid = listOf("")
    private val jrbMass = JRadioButton("Mass")
    private val alSolid = listOf("")
    private val bgJRB = ButtonGroup()

    private val fontA = Font("Tahoma", Font.BOLD, 20)

    init {
        darkmode()

        bgJRB.add(jrbVolume)
        bgJRB.add(jrbMass)
        jrbVolume.doClick()
        addJCB()

        htfInput.font = fontA
        jlOutput.font = fontA

        jpContent.add(jrbVolume, "align center, split 2")
        jpContent.add(jrbMass, "wrap")
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

        if (jrbVolume.isSelected) {
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