package com.github.atheera.recipemanager.gui.panels.other

import com.github.atheera.recipemanager.*
import com.github.atheera.recipemanager.Icons.delete
import com.github.atheera.recipemanager.save.read.ReadSettings
import net.miginfocom.swing.MigLayout
import java.awt.Color
import java.awt.Dimension
import java.awt.Font
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.*

class CalculatorPanel : JPanel() {

    // Local Variables
    private var fontA = Font("Tahoma", Font.BOLD, 20)
    private var fontB = Font("Tahoma", Font.PLAIN, 12)
    private var fontC = Font("Tahoma", Font.BOLD, 16)
    private var inputA: Double = 0.0
    private var inputB: Double = 0.0
    private var result: Double = 0.0
    private var operator: Char = '+'
    private var jpContent = JPanel(MigLayout())
    private var shouldReplace = false

    // Calculator Panel
    private var jpCalc = JPanel(MigLayout())
    private var jlNumbers = JLabel("0")
    private var jlMemory = JLabel("0")
    private var jpNumbers = JPanel(MigLayout())
    private var jbClearEntry = createButton("CE")
    private var jbClear = createButton("C")
    private var jbDivide = createButton("÷")
    private var jbMultiply = createButton("x")
    private var jbMinus = createButton("-")
    private var jbAdd = createButton("+")
    private var jbEquals = createButton("=")
    private var jbMinusPlus = createButton("⁺∕₋")
    private var jbComma = createButton(".")
    private var jbDelete = createButton("<-")
    private var jbZero = createNumberButton(0)
    private var jbOne = createNumberButton(1)
    private var jbTwo = createNumberButton(2)
    private var jbThree = createNumberButton(3)
    private var jbFour = createNumberButton(4)
    private var jbFive = createNumberButton(5)
    private var jbSix = createNumberButton(6)
    private var jbSeven = createNumberButton(7)
    private var jbEight = createNumberButton(8)
    private var jbNine = createNumberButton(9)

    // History Panel
    private var jpHistory = JPanel(MigLayout())
    private var jpHistoryOut = JPanel(MigLayout())
    private var jspHistory = JScrollPane(jpHistory)
    private var jbClearHistory = JButton(loadIcon(delete))

    init {

        addHistory()
        addCalc()

        jpContent.add(jpHistoryOut)
        jpContent.add(jpCalc)
        add(jpContent)

        darkmode()
    }

    fun darkmode() {
        ReadSettings(settingsPath)

        darkMode(this)
        darkModeIn(jpCalc)
        darkModeIn(jpHistory)
        darkModeOut(jpHistoryOut)
        darkModeOut(jpContent)
    }

    private fun addMemory(operator: Char) {
        if(jlNumbers.text == "0" && jlMemory.text == "0") {
            JOptionPane.showMessageDialog(this, "A number is needed first!")
            return
        }
        if (jlNumbers.text.last() == '.') {
            return
        }
        if (jlMemory.text.last() == '=') {
            // Get info from the loaded history
            val splitText = jlMemory.text.split(" ")
            inputA = splitText[0].toDouble()
            this.operator = splitText[1][0]
            inputB = splitText[2].toDouble()
            result = calculate(this.operator)
            // Reassign values
            inputA = result
            this.operator = if(operator == '=') splitText[1][0] else operator
            inputB = jlNumbers.text.toDouble()
            result = calculate(this.operator)
            val jp = createHistory(inputA, this.operator, inputB, result)
            jpHistory.add(jp, "wrap")
            jlMemory.text = "$result ${this.operator}"
            jlNumbers.text = "0"
            updateUI()
        } else if (jlNumbers.text == "0") {
            jlMemory.text = if (jlMemory.text.last() != operator) "${removeLast(jlMemory.text, 2)} $operator" else jlMemory.text
            this.operator = operator
            updateUI()
        } else if (jlNumbers.text != "0" && jlMemory.text != "0" && jlNumbers.text.last() != '.' && jlMemory.text.isNotEmpty()) {
            inputA = removeLast(jlMemory.text, 2).toDouble()
            inputB = jlNumbers.text.toDouble()
            result = calculate(operator)
            val jp = createHistory(inputA, operator, inputB, result)
            jpHistory.add(jp, "wrap")
            jlMemory.text = "$result $operator"
            jlNumbers.text = "0"
            this.operator = operator
            updateUI()
        } else {
            jlMemory.text = "${jlNumbers.text} $operator"
            jlNumbers.text = "0"
            this.operator = operator
            updateUI()
        }

    }

    private fun createHistory(inputA: Double, operator: Char, inputB: Double, output: Double) : JPanel {
        val jp = JPanel(MigLayout())
        jp.minimumSize = Dimension(248, 50)
        jp.maximumSize = Dimension(248, 50)
        jp.border = BorderFactory.createLineBorder(Color.BLACK)
        jp.background = Color.WHITE

        val jl = JLabel("$inputA $operator $inputB =")
        val jl1 = JLabel("$output")
        jp.addMouseListener(object: MouseAdapter() {
            override fun mouseClicked(e: MouseEvent?) {
                super.mouseClicked(e)
                jlMemory.text = jl.text
                jlNumbers.text = jl1.text
                shouldReplace = true
                updateUI()
            }

            override fun mouseEntered(e: MouseEvent?) {
                super.mouseEntered(e)
                jp.border = BorderFactory.createLineBorder(Color.GREEN)
                jp.background = WHITE
                updateUI()
            }

            override fun mouseExited(e: MouseEvent?) {
                super.mouseExited(e)
                jp.border = BorderFactory.createLineBorder(Color.BLACK)
                jp.background = Color.WHITE
                updateUI()
            }
        })
        jl.font = fontB
        jl1.font = fontC

        jp.add(jl, "wrap")
        jp.add(jl1)
        return jp
    }

    private fun addHistory() {
        jspHistory.minimumSize = Dimension(282, 300)
        jspHistory.maximumSize = Dimension(282, 300)
        jspHistory.verticalScrollBar.unitIncrement = 16
        jspHistory.verticalScrollBarPolicy = ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS

        jbClearHistory.maximumSize = Dimension(20, 20)
        jbClearHistory.addActionListener {
            jpHistory.removeAll()
            updateUI()
        }
        jbClearHistory.toolTipText = "Clear all calculation history"

        jpHistoryOut.add(jspHistory, "wrap")
        jpHistoryOut.add(jbClearHistory)
    }

    private fun addCalc() {
        // Functions
        jbDelete.addActionListener {
            if(jlNumbers.text.isEmpty() || jlNumbers.text == "0") {
                jlNumbers.text = "0"
            } else {
                jlNumbers.text = removeLast(jlNumbers.text, 1)
            }
            updateUI()
        }
        jbComma.addActionListener {
            if(!jlNumbers.text.contains('.') && jlNumbers.text.isNotEmpty())
                jlNumbers.text = jlNumbers.text.plus(".")
            updateUI()
        }
        jbMinusPlus.addActionListener {
            if(jlNumbers.text.contains('-')) {
                jlNumbers.text = StringBuilder(jlNumbers.text).deleteCharAt(0).toString()
            } else {
                jlNumbers.text = "-${jlNumbers.text}"
            }
            updateUI()
        }
        jbEquals.addActionListener {
            addMemory(this.operator)
            shouldReplace = true
        }
        jbClear.addActionListener {
            jlNumbers.text = "0"
            jlMemory.text = "0"
            shouldReplace = true
        }
        jbClearEntry.addActionListener {
            jlNumbers.text = "0"
            shouldReplace = true
        }
        jbAdd.addActionListener { addMemory('+') }
        jbMinus.addActionListener { addMemory('-') }
        jbDivide.addActionListener { addMemory('/') }
        jbMultiply.addActionListener { addMemory('*') }

        // Data
        jpNumbers.background = Color.WHITE
        jlNumbers.font = fontA
        jlMemory.font = fontB
        jpNumbers.add(jlMemory, "wrap")
        jpNumbers.add(jlNumbers, "align center")
        jpNumbers.border = BorderFactory.createLineBorder(Color.BLACK)
        jpNumbers.minimumSize = Dimension(292, 42)

        jbEquals.minimumSize = Dimension(292, 40)

        // Add to screen
            // Row #1
        jpCalc.add(jpNumbers, "wrap")

            // Row #2
        jpCalc.add(jbEquals, "split 4")
        jpCalc.add(jbClearEntry)
        jpCalc.add(jbClear)
        jpCalc.add(jbDelete, "wrap")

            // Row #3
        jpCalc.add(jbSeven, "split 4")
        jpCalc.add(jbEight)
        jpCalc.add(jbNine)
        jpCalc.add(jbDivide, "wrap")

            // Row #4
        jpCalc.add(jbFour, "split 4")
        jpCalc.add(jbFive)
        jpCalc.add(jbSix)
        jpCalc.add(jbMultiply, "wrap")

            // Row #5
        jpCalc.add(jbOne, "split 4")
        jpCalc.add(jbTwo)
        jpCalc.add(jbThree)
        jpCalc.add(jbAdd, "wrap")

            // Row #6
        jpCalc.add(jbMinusPlus, "split 4")
        jpCalc.add(jbZero)
        jpCalc.add(jbComma)
        jpCalc.add(jbMinus, "wrap")
    }

    private fun calculate(c: Char) : Double {
        return when (c) {
            '+' -> (inputA + inputB)
            '/' -> (inputA / inputB)
            '*' -> (inputA * inputB)
            '-' -> (inputA - inputB)
            else -> 0.0
        }
    }

    private fun createNumberButton(i: Int) : JButton {
        val jb = createButton("$i")
        jb.addActionListener {
            if((jlNumbers.text.contains('.') && jlNumbers.text.length == 16) || (!jlNumbers.text.contains('.') && jlNumbers.text.length == 15)) {
                jlNumbers.text = jlNumbers.text
            } else if(jlNumbers.text == "0" || shouldReplace) {
                jlNumbers.text = "$i"
                shouldReplace = false
            } else {
                jlNumbers.text = jlNumbers.text.plus("$i")
            }
            updateUI()
        }
        return jb
    }

    private fun createButton(type: String) : JButton {
        val jb = JButton(type)
        jb.font = fontA
        jb.foreground = Color.WHITE
        jb.background = Color.BLACK
        jb.maximumSize = Dimension(70, 40)
        jb.minimumSize = Dimension(70, 40)
        return jb
    }

}