package com.github.atheera.recipemanager.gui.panels.other

import com.github.atheera.recipemanager.*
import com.github.atheera.recipemanager.save.read.ReadSettings
import com.github.atheera.recipemanager.save.write.WriteSettingsFile
import net.miginfocom.swing.MigLayout
import java.awt.Dimension
import javax.swing.*

class NewMeasurePanel : JPanel() {

    private val jpContent = JPanel(MigLayout())
    private val jpAll = JPanel(MigLayout())
    private val jpNew = JPanel(MigLayout())
    private val jtfAdd = JTextField()
    private val jbAdd = JButton("Add measurement")
    private val jbSave = JButton("Save additions")
    private val jbRemove = JButton("Remove selected")

    private val jcbMeasure = JComboBox(measures.toTypedArray())
    private val jcbAdded = JComboBox(addedMeasures.toTypedArray())
    private val alAdded = mutableListOf<String>()

    private val alLoadedMeasures = mutableListOf<String>()

    init {
        darkmode()
        ReadSettings()
        alLoadedMeasures.addAll(measures)

        jtfAdd.minimumSize = Dimension(200, 25)
        jtfAdd.addActionListener { addToList() }
        jbAdd.addActionListener { addToList() }

        jbSave.addActionListener {
            if(check()) {
                for(i in alAdded) {
                    measures.add(i)
                    addedMeasures.add(i)
                }
                WriteSettingsFile(path, isDark, addedMeasures)
                addedMeasures.clear()
                JOptionPane.showMessageDialog(this, "Successfully saved recipe measurements!")
            }
            updateUI()
        }

        jbRemove.addActionListener {
            if(jcbAdded.itemCount == 0) {
                JOptionPane.showMessageDialog(this, "No measurement to remove!")
            } else {
                jcbMeasure.removeItem(jcbAdded.getItemAt(jcbAdded.selectedIndex))
                measures.remove(jcbAdded.getItemAt(jcbAdded.selectedIndex))
                addedMeasures.remove(jcbAdded.getItemAt(jcbAdded.selectedIndex))
                alAdded.remove(jcbAdded.getItemAt(jcbAdded.selectedIndex))
                jcbAdded.removeItem(jcbAdded.getItemAt(jcbAdded.selectedIndex))
            }
        }

        jcbMeasure.isEditable = false
        jcbAdded.isEditable = false

        jpContent.add(jtfAdd, "align center, split 2")
        jpContent.add(jbAdd, "wrap")

        jpAll.add(JLabel("All"), "align center, wrap")
        jpAll.add(jcbMeasure, "align center, wrap")

        jpNew.add(JLabel("New"), "align center, wrap")
        jpNew.add(jcbAdded, "align center, wrap")
        jpNew.add(jbRemove, "align center")

        jpContent.add(jpAll, "align center, split 2")
        jpContent.add(jpNew, "wrap")

        jpContent.add(jbSave, "align center")
        add(jpContent)

    }

    private fun check() : Boolean {
        return if(alLoadedMeasures == measures) {
            JOptionPane.showMessageDialog(this, "Nothing new to save!")
            false
        } else if(alLoadedMeasures == measures && alAdded.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Need something to add first!")
            false
        } else {
            true
        }
    }

    fun darkmode() {
        border = createBorder("Here you can view all recipe measurements and add more")
        darkMode(this)
        darkModeOut(jpContent)
        darkModeIn(jpNew)
        darkModeIn(jpAll)
    }

    private fun addToList() {
        if(jtfAdd.text.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Measurement text to add is empty!")
            return
        } else if(measures.contains(jtfAdd.text.uppercase())) {
            jtfAdd.text = ""
            JOptionPane.showMessageDialog(this, "Measurement already added!")
            return
        }
        jcbAdded.addItem(jtfAdd.text.uppercase())
        alAdded.add(jtfAdd.text.uppercase())
        jtfAdd.text = ""
        updateUI()
    }

}