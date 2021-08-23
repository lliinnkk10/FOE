package com.github.atheera.recipemanager.gui.panels.recipe

import com.github.atheera.recipemanager.*
import com.github.atheera.recipemanager.extras.DeleteButton
import com.github.atheera.recipemanager.extras.HintTextField
import com.github.atheera.recipemanager.extras.TextLineNumber
import com.github.atheera.recipemanager.extras.ToolTipLabel
import com.github.atheera.recipemanager.save.read.ReadSettings
import com.github.atheera.recipemanager.save.write.WriteRecipeFavorite
import com.github.atheera.recipemanager.save.write.WriteRecipeSaves
import net.miginfocom.swing.MigLayout
import java.awt.CardLayout
import java.awt.Color
import java.awt.Dimension
import java.awt.Font
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.ItemEvent
import java.awt.event.ItemListener
import javax.swing.*

class NewRecipePanel(new: Boolean) : JPanel(MigLayout()), ItemListener, ActionListener {

    // Local
    private val ml = MigLayout()
    private val fontA = Font("Tahoma", Font.BOLD, 20)
    private val fontB = Font("Tahoma", Font.PLAIN, 16)
    private val fontC = Font("Tahoma", Font.BOLD, 16)
    private val jpInsIng = JPanel(MigLayout())
    private val jpRecipe = JPanel(MigLayout())
    private val jspRecipe = JScrollPane(jpRecipe)

    // Title
    var htfTitle = HintTextField("Start with naming the recipe here")

    // Categories
    var jpCats = JPanel(MigLayout())
    lateinit var jcbCategories: JComboBox<String>
    var cl = CardLayout()
    private val jpCatCards = JPanel(cl)
    private val jpSubCatD = JPanel()
    private val jpSubCatE = JPanel()
    private val jpSubCatM = JPanel()
    var bgJRBGroup = ButtonGroup()
    lateinit var selCategory: String
    lateinit var selSubCat: String
    lateinit var jrbSel: JRadioButton
    lateinit var bmCat: ButtonModel
    var jlCat = JLabel()
    var jlSubCat = JLabel()
    var new = false

    // Intolerance checkboxes
    val jcbEgg = JCheckBox("Egg Free")
    val jcbGluten = JCheckBox("Gluten Free")
    val jcbLactose = JCheckBox("Lactose Free")
    val jcbVegan = JCheckBox("Vegan")
    val jcbVeget = JCheckBox("Vegetarian")
    private val jpIntol = JPanel(MigLayout())

    // Degree converter
    var factor: Int = 0
    var result: Int = 0
    private val jpDegrees = JPanel(MigLayout())
    private val jpConv = JPanel(MigLayout())
    var htfDegrees = HintTextField("0")
    val jlConverted = JLabel("0")
    val jlCF = JLabel("")
    val jrbCFahr = JRadioButton("Convert to Fahrenheit")
    val jrbCCEls = JRadioButton("Convert to Celsius")
    private val bgCF = ButtonGroup()

    // Ingredients list
    val jpIng = JPanel(MigLayout())
    private val jpIngOut = JPanel(MigLayout())
    private val jspIng = JScrollPane(jpIng)
    private val jbIngAdd = JButton("Add to list")
    var htfIngAmount = HintTextField("Amount")
    var jcbIngMeasure = JComboBox(measures.toTypedArray())
    var htfIngItem = HintTextField("Item")
    var alIngredients = mutableListOf<String>()

    var counterIng: Int = 0
    lateinit var selectedMeasure: String
    private val ttlIng = ToolTipLabel("You can also press enter when typing the item to add!")


    // Instructions list
    private val jpIns = JPanel(MigLayout())
    private val jpInsOut = JPanel(MigLayout())
    val jtaIns = JTextArea()
    private val jspIns = JScrollPane(jtaIns)
    private val tlnIns = TextLineNumber(jtaIns)

    // Description list
    private val jpDesc = JPanel(MigLayout())
    val jtaDesc = JTextField()

    // Save buttons
    val jbSave = JButton()
    val jbFavorite = JButton()
    private val jpButtons = JPanel(MigLayout())

    init {

        // Functions
        htfTitle.minimumSize = Dimension(250, 25)
        addJCBList(new)
        addCheckBoxes()
        addDegreeConverter()
        addIngredientList()
        addInstructionList()
        addDescriptionList()
        addSaveButtons()
        jspRecipe.verticalScrollBar.unitIncrement = 16
        jspRecipe.verticalScrollBarPolicy = ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS
        //jspRecipe.minimumSize = Dimension(1025, 975)
        //jpRecipe.minimumSize = Dimension(1025, 975)



        // Add

        jpRecipe.add(htfTitle, "align center, wrap")
        jpRecipe.add(jpCats, "align center, wrap")
        jpRecipe.add(jpIntol, "align center, wrap")
        jpRecipe.add(jpDegrees, "align center, wrap")
        jpRecipe.add(jpInsIng, "align center, wrap")
        jpRecipe.add(jpDesc, "align center, wrap")
        jpRecipe.add(jpButtons, "align center")
        add(jspRecipe)

        darkmode()
    }

    fun darkmode() {
        ReadSettings(settingsPath)

        darkMode(this)
        darkModeOut(jpRecipe)
        darkModeIn(jpCats)
        darkModeIn(jpIntol)
        darkModeOut(jpInsIng)
        darkModeOut(jpButtons)
        darkModeIn(jpDegrees)
        darkModeIn(jpDesc)
        darkModeIn(jpInsOut)
        darkModeIn(jpIngOut)

        this.border = createBorder("Here you can create a new recipe!")
        jpInsOut.border = createBorder("Here you can enter instructions for the recipe")
        jpIngOut.border = createBorder("Here you can enter the amount of an item in the recipe")
        jpDegrees.border = createBorder("Here you can convert temperature F/C")
        jpIntol.border = createBorder("Check any of these to show intolerances or restrictions")
        val txt = if(new) "Select the category in the drop box, and what kind of food with the buttons" else "The saved recipe is saved in"
        jpCats.border = createBorder(txt)
        jpDesc.border = createBorder("Enter a short description of the recipe")

        jlConverted.foreground = if(isDark) Color.WHITE else Color.BLACK
        jlCat.foreground = if(isDark) Color.WHITE else Color.BLACK
        jlSubCat.foreground = if(isDark) Color.WHITE else Color.BLACK
        jlCF.foreground = if(isDark) Color.WHITE else Color.BLACK
        //jpConv.background = if(isDark) Color.DARK_GRAY else WHITE
    }

    fun addDescriptionList() {

        jtaDesc.minimumSize = Dimension(300, 25)
        jtaDesc.maximumSize = Dimension(300, 25)
        jtaDesc.font = fontB

        jpDesc.add(jtaDesc)
    }

    fun addSaveButtons() {
        updateButtons(true)

        jbSave.addActionListener {
            if(getInformation(true)) {
                WriteRecipeSaves(
                    recipeTitle,
                    recipeCategory,
                    recipeSubCategory,
                    recipeInstructions,
                    recipeIngredients,
                    recipeDescription,
                    recipeTemperature,
                    recipeConvTemperature,
                    recipeEgg,
                    recipeGluten,
                    recipeLactose,
                    recipeVegan,
                    recipeVegetarian
                )
                JOptionPane.showMessageDialog(this, "Successfully saved recipe file to $recipePath$selCategory/$selSubCat")
                clearInformation()
            }
        }

        jbFavorite.addActionListener {
            if(getInformation(true)) {
                WriteRecipeFavorite(
                    recipeTitle,
                    recipeCategory,
                    recipeSubCategory,
                    recipeInstructions,
                    recipeIngredients,
                    recipeDescription,
                    recipeTemperature,
                    recipeConvTemperature,
                    recipeEgg,
                    recipeGluten,
                    recipeLactose,
                    recipeVegan,
                    recipeVegetarian
                )
                JOptionPane.showMessageDialog(this, "Successfully saved recipe file to $recipeFavPath")
                clearInformation()
            }
        }

        jpButtons.add(jbSave, "align center")

        jpButtons.add(jbFavorite, "align center")
    }

    fun addInstructionList() {

        jtaIns.wrapStyleWord = true
        jtaIns.lineWrap = true
        jtaIns.minimumSize = Dimension(442, 508)
        jtaIns.font = fontB

        jspIns.minimumSize = Dimension(475, 508)
        jspIns.maximumSize = Dimension(475, 508)
        jspIns.setRowHeaderView(tlnIns)
        jspIns.verticalScrollBarPolicy = ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS
        jspIns.verticalScrollBar.unitIncrement = 16

        jpIns.add(jspIns)
        jpInsOut.add(jpIns)
        jpInsIng.add(jpInsOut, "wrap")
    }

    fun addIngredientList() {
        selectedMeasure = measures[0]
        jspIng.minimumSize = Dimension(440, 458)
        jspIng.maximumSize = Dimension(440, 458)
        jspIng.verticalScrollBarPolicy = ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS
        jspIng.verticalScrollBar.unitIncrement = 16
        htfIngItem.minimumSize = Dimension(250, 25)
        htfIngAmount.minimumSize = Dimension(100, 25)

        jcbIngMeasure.selectedIndex = 0
        jcbIngMeasure.isEditable = false
        jcbIngMeasure.addItemListener { if(it.stateChange == ItemEvent.SELECTED) selectedMeasure = it.item.toString() }

        jbIngAdd.addActionListener {
            if(check()) {
                try {
                    val ingCard = createCard()
                    jpIng.add(ingCard, "wrap")
                } catch (e: NumberFormatException) {
                    dw.exc(e)
                    e.printStackTrace()
                    JOptionPane.showMessageDialog(this, "You need to enter some valid information first, numbers only!")
                }
            }
        }

        htfIngItem.addActionListener {
            if(check()) {
                try {
                    val ingCard = createCard()
                    jpIng.add(ingCard, "wrap")
                    htfIngAmount.requestFocus()
                } catch (e: NumberFormatException) {
                    dw.exc(e)
                    e.printStackTrace()
                    JOptionPane.showMessageDialog(this, "You need to enter some valid information first, numbers only!")
                }
            }
        }

        jpIngOut.add(jbIngAdd, "align center, split 2")
        jpIngOut.add(ttlIng, "wrap")
        jpIngOut.add(htfIngAmount, "align center, split 3")
        jpIngOut.add(jcbIngMeasure)
        jpIngOut.add(htfIngItem, "wrap")
        jpIngOut.add(jspIng, "align center")
        jpInsIng.add(jpIngOut, "split 2")
    }

    fun addDegreeConverter() {

        bgCF.add(jrbCCEls)
        bgCF.add(jrbCFahr)
        htfDegrees.minimumSize = Dimension(50, 25)
        jlConverted.font = fontA
        jlCF.font = fontA

        jrbCFahr.addActionListener {
            try {
                factor = htfDegrees.text.toInt()
                result = (factor * 9/5) + 32
                jlConverted.text = "$result° F"
                jlCF.text = "° C"
                updateUI()
            } catch(e: NumberFormatException) {
                dw.exc(e)
                JOptionPane.showMessageDialog(this, "That is not a whole number! Try again!")
            }
        }

        jrbCCEls.addActionListener {
            try {
                factor = htfDegrees.text.toInt()
                result = (factor - 32)*5/9
                jlConverted.text = "$result° C"
                jlCF.text = "° F"
                updateUI()
            } catch(e: NumberFormatException) {
                dw.exc(e)
                JOptionPane.showMessageDialog(this, "That is not a whole number! Try again!")
            }

        }
        //jpDegrees.maximumSize = Dimension(700, 90)

        //jpDegrees.add(jlDesc)
        jpDegrees.add(htfDegrees)
        jpDegrees.add(jlCF)
        jpConv.add(jrbCCEls, "wrap")
        jpConv.add(jrbCFahr)
        jpDegrees.add(jpConv)
        jpDegrees.add(jlConverted)

    }

    fun addCheckBoxes() {
        jpIntol.add(jcbEgg)
        jpIntol.add(jcbGluten)
        jpIntol.add(jcbLactose)
        jpIntol.add(jcbVegan)
        jpIntol.add(jcbVeget)
    }

    private fun addJCBList(new: Boolean) {
        jcbCategories = JComboBox(categories.toTypedArray())
        jcbCategories.isEditable = false
        jcbCategories.addItemListener(this)
        jcbCategories.selectedIndex = 0
        selCategory = categories[0]

        this.new = new

        for (i in subCatDesserts.indices) {
            val jrb = JRadioButton(subCatDesserts[i])
            jpSubCatD.add(jrb)
            bgJRBGroup.add(jrb)
            jrb.actionCommand = i.toString()
            jrb.addActionListener(this)
            if (i == 0)
                jrb.doClick()
        }
        for (i in subCatExtras.indices) {
            val jrb = JRadioButton(subCatExtras[i])
            jpSubCatE.add(jrb)
            bgJRBGroup.add(jrb)
            jrb.actionCommand = i.toString()
            jrb.addActionListener(this)
        }
        for (i in subCatMeats.indices) {
            val jrb = JRadioButton(subCatMeats[i])
            jpSubCatM.add(jrb)
            bgJRBGroup.add(jrb)
            jrb.actionCommand = i.toString()
            jrb.addActionListener(this)
        }
        if(new) {
            bmCat = bgJRBGroup.selection
            bgJRBGroup.setSelected(bmCat, true)
            jpCatCards.add(jpSubCatD, categories[0])
            jpCatCards.add(jpSubCatE, categories[1])
            jpCatCards.add(jpSubCatM, categories[2])
            jpCats.add(jcbCategories, "align center, wrap")
            jpCats.add(jpCatCards, "wrap")
        } else {
            jlCat.font = fontA
            jlSubCat.font = fontB
            jpCats.add(jlCat, "align center, wrap")
            jpCats.add(jlSubCat, "align center")
            jpCats.minimumSize = Dimension(250, 75)
        }
    }

    fun createCard(amount: Double = htfIngAmount.text.toDouble(), measure: String = selectedMeasure, item: String = htfIngItem.text, removePane: JPanel = jpIng) : JPanel {

        val jp = JPanel(MigLayout("","[]10[]","[]20[]20[]20[]"))
        val jbDelete = DeleteButton()
        val jlAmount = JLabel("$amount")
        val jlMeasure = JLabel(measure)
        val jlItem = JLabel(item)

        jlAmount.font = fontC
        jlMeasure.font = fontC
        jlItem.font = fontC

        val combinedIngredient = ("$amount $measure $item")

        jp.border = BorderFactory.createLineBorder(Color.BLACK)
        jp.minimumSize = Dimension(408, 38)
        jp.maximumSize = Dimension(408, 38)
        jp.background = Color.WHITE

        jbDelete.addActionListener {
            removePane.remove(jp)
            updateUI()
            alIngredients.remove(combinedIngredient)
            counterIng--
        }

        htfIngAmount.text = ""
        htfIngItem.text = ""

        alIngredients.add(counterIng, combinedIngredient)
        counterIng++

        jp.add(jbDelete)
        jp.add(jlAmount)
        jp.add(jlMeasure)
        jp.add(jlItem)
        updateUI()
        return jp
    }

    fun getInformation(new: Boolean) : Boolean {
        val c1: Boolean
        val c2: Boolean
        val c3: Boolean
        val c4: Boolean
        var c5 = false
        if((htfTitle.text.isEmpty() || htfTitle.text == "Start with naming the recipe here")) {
            JOptionPane.showMessageDialog(this, "The recipe needs a title first!")
            return false
        } else { c1 = true }
        if(alIngredients.isEmpty()) {
            JOptionPane.showMessageDialog(this, "The ingredient list is empty!")
            return false
        } else { c2 = true }
        if(jtaIns.text.isEmpty()) {
            JOptionPane.showMessageDialog(this, "You should add some instructions!")
            return false
        } else { c3 = true }
        if(jtaDesc.text.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter a short description first!")
            return false
        } else { c4 = true }
        if(c1 && c2 && c3 && c4) {
            recipeTitle = htfTitle.text
            recipeIngredients = alIngredients
            recipeInstructions = jtaIns.text
            recipeDescription = jtaDesc.text
            recipeTemperature = factor
            recipeConvTemperature = result
            recipeEgg = jcbEgg.isSelected
            recipeGluten = jcbGluten.isSelected
            recipeLactose = jcbLactose.isSelected
            recipeVegan = jcbVegan.isSelected
            recipeVegetarian = jcbVeget.isSelected
            c5 = true
            if (!new) {
                recipeCategory = jlCat.text
                recipeSubCategory = jlSubCat.text
            } else {
                recipeCategory = selCategory
                recipeSubCategory = selSubCat
            }
        }
        return c5
    }

    fun clearInformation() {
        htfTitle.text = ""
        selCategory = categories[0]
        jcbCategories.selectedIndex = 0
        selSubCat = subCatDesserts[0]
        alIngredients.clear()
        jpIng.removeAll()
        counterIng = 0
        jtaIns.text = ""
        jtaDesc.text = ""
        factor = 0
        htfDegrees.text = "0"
        result = 0
        jlConverted.text = ""
        jcbEgg.isSelected = false
        jcbGluten.isSelected = false
        jcbLactose.isSelected = false
        jcbVegan.isSelected = false
        jcbVeget.isSelected = false
        updateUI()
    }

    private fun check() : Boolean {
        val check = (htfIngAmount.text.isEmpty() || htfIngAmount.text == "Amount")
        val check2 = (htfIngItem.text.isEmpty() || htfIngItem.text == "Item")
        val info = "You need to enter some valid information first, numbers only!"
        val info2 = "You need to enter an item to add first!"

        if(check) {
            JOptionPane.showMessageDialog(this, info)
            return false
        }
        if(check2) {
            JOptionPane.showMessageDialog(this, info2)
            return false
        }

        return true
    }

    fun updateButtons(new: Boolean) {
        if (new) {
            updateUI()
            jbSave.text = "Click me to save the recipe to: $recipePath$selCategory/$selSubCat"
            jbFavorite.text = recipePath + "Favorites"
            updateUI()
        } else {
            updateUI()
            jbSave.text = "Click me to save the recipe to: $recipePath${jlCat.text}/${jlSubCat.text}"
            jbFavorite.text = recipePath + "Favorites"
            updateUI()
        }
    }

    override fun itemStateChanged(e: ItemEvent) {
        cl = jpCatCards.layout as CardLayout
        cl.show(jpCatCards, e.item as String)
        if(e.stateChange == ItemEvent.SELECTED)
            selCategory = e.item.toString()
        updateButtons(true)
    }

    override fun actionPerformed(e: ActionEvent) {
        jrbSel = e.source as JRadioButton
        selSubCat = jrbSel.text
        updateButtons(true)
    }
}