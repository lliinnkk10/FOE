package com.github.atheera.recipemanager.gui.panels.other

import com.github.atheera.recipemanager.*
import com.github.atheera.recipemanager.save.read.ReadSettings
import net.miginfocom.swing.MigLayout
import java.awt.Dimension
import javax.swing.*

class NewMeasurePanel : JPanel() {

    private val jpContent = JPanel(MigLayout())
    private val jtfAdd = JTextField()
    private val jbAdd = JButton("Add measurement")
    private val jbSave = JButton("Save additions")

    private val jcbMeasure = JComboBox(measures.toTypedArray())

    init {
        darkmode()
        ReadSettings()

        jtfAdd.minimumSize = Dimension(200, 25)
        jtfAdd.addActionListener {
            jcbMeasure.addItem(jtfAdd.text.uppercase())
            addedMeasures.add(jtfAdd.text.uppercase())
            jtfAdd.text = ""
            jcbMeasure.selectedIndex = jcbMeasure.itemCount
            updateUI()
        }

        jcbMeasure.isEditable = true

        jpContent.add(jtfAdd, "align center, split 2")
        jpContent.add(jbAdd, "wrap")
        jpContent.add(jcbMeasure, "align center, wrap")
        jpContent.add(jbSave, "align center")
        add(jpContent)

    }

    fun darkmode() {
        border = createBorder("Here you can view all recipe measurements and add more")
        darkMode(this)
        darkModeOut(jpContent)

    }

}