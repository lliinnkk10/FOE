package com.github.atheera.recipemanager.gui.panels.list

import com.github.atheera.recipemanager.*
import com.github.atheera.recipemanager.extras.DeleteButton
import com.github.atheera.recipemanager.extras.HintTextField
import com.github.atheera.recipemanager.extras.ToolTipLabel
import com.github.atheera.recipemanager.save.read.ReadSettings
import com.github.atheera.recipemanager.save.write.WriteListPC
import net.miginfocom.swing.MigLayout
import java.awt.Color
import java.awt.Dimension
import java.awt.Font
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import javax.swing.*

class NewPCListPanel : JPanel(MigLayout("align center")), KeyListener {

    var posPane = JPanel(MigLayout())
    private var posOutPane = JPanel(MigLayout())
    private var posScroll = JScrollPane(posPane)
    private var posButton = JButton("Add to pros side")

    var negPane = JPanel(MigLayout())
    private var negOutPane = JPanel(MigLayout())
    private var negScroll = JScrollPane(negPane)
    private var negButton = JButton("Add to cons side")

    private var htaPane = JPanel(MigLayout("align center", "[]10[]10[]", ""))
    private val titleText = "Enter the list title here"
    var htfTitle = HintTextField(titleText)
    private val argText = "Enter the argument here"
    private var htfArg = HintTextField(argText)
    var saveBtn = JButton("Press me to save the list to: $listPath/${listCategories[0]}")
    private var jlDesc = ToolTipLabel("NOTE: pressing enter or shift+enter adds to pros or cons respectively")
    private var jlPos = JLabel("Pros:")
    private var jlNeg = JLabel("Cons:")

    private var posList = mutableListOf<String>()
    private var negList = mutableListOf<String>()
    private var alPos = mutableListOf<JPanel>()
    private var alNeg = mutableListOf<JPanel>()
    private var posCounter: Int = 0
    private var negCounter: Int = 0
    private var holdingShift: Boolean = false
    private val fontA = Font("Tahoma", Font.PLAIN, 16)
    private val fontB = Font("Tahoma", Font.BOLD, 20)
    private val dim = Dimension(350, 500)

    init {
        darkmode()
        // Functions
        htfTitle.minimumSize = Dimension(250, 25)
        htfArg.minimumSize = Dimension(350, 25)
        saveBtn.minimumSize = Dimension(350, 40)
            // Positive
        posPane.minimumSize = dim
        posOutPane.minimumSize = dim
        posScroll.minimumSize = dim
        posScroll.verticalScrollBar.unitIncrement = 16
        posScroll.verticalScrollBarPolicy = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
            // Negative
        negPane.minimumSize = dim
        negOutPane.minimumSize = dim
        negScroll.minimumSize = dim
        negScroll.verticalScrollBar.unitIncrement = 16
        negScroll.verticalScrollBarPolicy = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
            // Misc
        //jlDesc.font = fontA
        jlNeg.font = fontB
        jlPos.font = fontB

        htfArg.addKeyListener(this)

        posButton.addActionListener {
            addToList(true)
        }
        negButton.addActionListener {
            addToList(false)
        }
        saveBtn.addActionListener {
            if((htfTitle.text.isEmpty() || htfTitle.text == titleText) || posList.isEmpty() || negList.isEmpty())
                JOptionPane.showMessageDialog(this, "You need to enter information to save first!")
            else {
                WriteListPC(listCategories[0], upperCaseFirstWords(htfTitle.text), posList, negList)
                JOptionPane.showMessageDialog(this, "Successfully saved list to: $listPath/${listCategories[0]}")
                clearInfo()
            }
        }

        // Add to panel
        add(htfTitle, "align center, wrap")
        htaPane.add(posButton, "align center")
        htaPane.add(htfArg, "align center")
        htaPane.add(negButton, "align center, wrap")
        htaPane.add(jlDesc, "align center, span 3")
        add(htaPane, "align center, wrap")
        add(posOutPane, "split 2")
        add(negOutPane, "wrap")
        add(saveBtn, "align center")
            // Positive
        posOutPane.add(jlPos, "align center, wrap")
        posOutPane.add(posScroll)

            // Negative
        negOutPane.add(jlNeg, "align center, wrap")
        negOutPane.add(negScroll)

    }

    fun darkmode() {
        ReadSettings(settingsPath)

        border = createBorder("Here you can make a positive and negative arguments for a subject")

        darkMode(this)
        darkModeOut(htaPane)
        darkModeIn(posPane)
        darkModeOut(negOutPane)
        darkModeOut(posOutPane)
        darkModeIn(negPane)

        for (item in alNeg) darkModeDetail(item)
        for (item in alPos) darkModeDetail(item)

        jlNeg.foreground = if(isDark) Color.WHITE else Color.BLACK
        jlPos.foreground = if(isDark) Color.WHITE else Color.BLACK
    }

    private fun clearInfo() {
        htfArg.text = argText
        htfTitle.text = titleText

        posPane.removeAll()
        negPane.removeAll()

        posCounter = 0
        negCounter = 0

        posList.clear()
        negList.clear()
        updateUI()
    }

    private fun addToList(isPos: Boolean) {

        if(htfArg.text.isEmpty()) {
            JOptionPane.showMessageDialog(this, "You need to enter an argument!")
            return
        }
        if(isPos) {
            val argCard = createCard(htfArg.text, posPane, true)
            posPane.add(argCard, "wrap")
            alPos.add(argCard)
        } else {
            val argCard = createCard(htfArg.text, negPane, true)
            negPane.add(argCard, "wrap")
            alNeg.add(argCard)
        }
        darkmode()
        updateUI()
        htfArg.text = ""

    }

    fun createCard(argument: String, removePane: JPanel, new: Boolean = false) : JPanel {
        val arg = if(new) upperCaseFirstWords(argument) else argument
        val jp = JPanel(MigLayout("", "[]10[]", ""))
        val jlArg = JLabel(arg)
        val jbDelete = DeleteButton()

        when (removePane) {
            posPane -> {
                posList.add(posCounter, arg)
                posCounter++
            }
            negPane -> {
                negList.add(negCounter, arg)
                negCounter++
            }
        }

        jlArg.font = fontA
        jp.border = BorderFactory.createLineBorder(Color.BLACK)
        jp.minimumSize = Dimension(316, 40)
        jp.maximumSize = Dimension( 316, 40)

        jbDelete.addActionListener {
            removePane.remove(jp)
            updateUI()
            when (removePane) {
                posPane -> {
                    posList.remove(arg)
                    posCounter--
                }
                negPane -> {
                    negList.remove(arg)
                    negCounter--
                }
            }
        }

        jp.add(jbDelete)
        jp.add(jlArg)
        updateUI()
        return jp
    }


    override fun keyTyped(e: KeyEvent?) {
    }

    override fun keyPressed(e: KeyEvent?) {
        if(e!!.keyCode == KeyEvent.VK_SHIFT) {
            holdingShift = true
        }
        if(holdingShift && e.keyCode == KeyEvent.VK_ENTER) {
            addToList(false)
        } else if(e.keyCode == KeyEvent.VK_ENTER) {
            addToList(true)
        }

    }

    override fun keyReleased(e: KeyEvent?) {
        if(e!!.keyCode == KeyEvent.VK_SHIFT) {
            holdingShift = false
        }
    }

}