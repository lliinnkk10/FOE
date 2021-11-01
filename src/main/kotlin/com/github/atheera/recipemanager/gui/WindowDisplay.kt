package com.github.atheera.recipemanager.gui

import com.github.atheera.recipemanager.*
import com.github.atheera.recipemanager.extras.CJPanel
import com.github.atheera.recipemanager.gui.States.ADDMEASURESTATE
import com.github.atheera.recipemanager.gui.States.CALCULATORSTATE
import com.github.atheera.recipemanager.gui.States.CONVERSIONSTATE
import com.github.atheera.recipemanager.gui.States.FAVORITERECIPESTATE
import com.github.atheera.recipemanager.gui.States.MENUSTATE
import com.github.atheera.recipemanager.gui.States.NEWCATEGORYLISTSTATE
import com.github.atheera.recipemanager.gui.States.NEWNORMALLISTSTATE
import com.github.atheera.recipemanager.gui.States.NEWPCLISTSTATE
import com.github.atheera.recipemanager.gui.States.NEWRECIPESTATE
import com.github.atheera.recipemanager.gui.States.NEWTODOLISTSTATE
import com.github.atheera.recipemanager.gui.States.RANDOMRECIPESTATE
import com.github.atheera.recipemanager.gui.States.SAVEDDESSERTRECIPESTATE
import com.github.atheera.recipemanager.gui.States.SAVEDEXTRARECIPESTATE
import com.github.atheera.recipemanager.gui.States.SAVEDLISTSTATE
import com.github.atheera.recipemanager.gui.States.SAVEDMEATRECIPESTATE
import com.github.atheera.recipemanager.gui.States.SAVEDRECIPESTATE
import com.github.atheera.recipemanager.gui.panels.MenuPanel
import com.github.atheera.recipemanager.gui.panels.list.*
import com.github.atheera.recipemanager.gui.panels.other.CalculatorPanel
import com.github.atheera.recipemanager.gui.panels.other.ConversionTablePanel
import com.github.atheera.recipemanager.gui.panels.other.NewMeasurePanel
import com.github.atheera.recipemanager.gui.panels.recipe.*
import com.github.atheera.recipemanager.save.write.WriteSettingsFile
import java.awt.BorderLayout
import java.awt.CardLayout
import java.awt.Dimension
import java.awt.Toolkit
import javax.swing.*

// Panels
private lateinit var mainPane: JPanel
private lateinit var MenuPane: MenuPanel
    // Recipes
private lateinit var jpNewRecipe: NewRecipePanel
private lateinit var jpSavedRecipe: SavedRecipePanel
private lateinit var jpSavedDessert: SavedDessertRecipePanel
private lateinit var jpSavedExtra: SavedExtraRecipePanel
private lateinit var jpSavedMeat: SavedMeatRecipePanel
private lateinit var jpFavoriteRecipe: FavoriteRecipePanel
private lateinit var jpRandomRecipe: RandomRecipePanel
    // Lists
private lateinit var jpProConList: NewPCListPanel
private lateinit var jpTodoList: NewTodoListPanel
private lateinit var jpPlainList: NewNormalListPanel
private lateinit var jpCategoryList: NewCategoryListPanel
private lateinit var jpSavedLists: SavedListsPanel
    // Extras
private lateinit var jpCalculator: CalculatorPanel
private lateinit var jpMeasurement: NewMeasurePanel
private lateinit var jpConversion: ConversionTablePanel
    // Layout
private lateinit var clMain: CardLayout

// Menu
private lateinit var jmSettings: JMenu
private lateinit var jmRecipes: JMenu
private lateinit var jmLists: JMenu
private lateinit var jmExtras: JMenu
private lateinit var jmDebug: JMenu
private lateinit var jmb: JMenuBar
    // Items
        // Lists
private lateinit var jmSubList: JMenu
private lateinit var jmiPosNegList: JMenuItem
private lateinit var jmiToDoList: JMenuItem
private lateinit var jmiNorList: JMenuItem
private lateinit var jmiSavList: JMenuItem
private lateinit var jmiCatList: JMenuItem
        // Settings
private lateinit var jmiSettings: JMenuItem
private lateinit var jmiDark: JCheckBoxMenuItem
private var jmiDebugMode = JCheckBoxMenuItem("Debug mode")
        // Recipes
private lateinit var jmiDesRec: JMenuItem
private lateinit var jmiExtraRec: JMenuItem
private lateinit var jmiMeatRec: JMenuItem
private lateinit var jmiSavRec: JMenu
private lateinit var jmiNewRec: JMenuItem
private lateinit var jmiFavRec: JMenuItem
private lateinit var jmiRanRec: JMenuItem
        // Extras
private lateinit var jmiCalc: JMenuItem
private lateinit var jmiMeasure: JMenuItem
private lateinit var jmiConvers: JMenuItem
        // Debug
private lateinit var jmiSize: JMenuItem
private lateinit var jmiDebug: JMenuItem

// States of panels and names
object States {
    const val MENUSTATE = 0
    const val NEWRECIPESTATE = 1
    const val SAVEDRECIPESTATE = 2
    const val FAVORITERECIPESTATE = 3
    const val NEWPCLISTSTATE = 4
    const val NEWTODOLISTSTATE = 5
    const val SAVEDLISTSTATE = 6
    const val SAVEDDESSERTRECIPESTATE = 7
    const val SAVEDEXTRARECIPESTATE = 8
    const val SAVEDMEATRECIPESTATE = 9
    const val NEWNORMALLISTSTATE = 10
    const val CALCULATORSTATE = 11
    const val RANDOMRECIPESTATE = 12
    const val ADDMEASURESTATE = 13
    const val CONVERSIONSTATE = 14
    const val NEWCATEGORYLISTSTATE = 15
}
var currentState = States.MENUSTATE
private val panels = listOf(
    "Menu",
    "New Recipe",
    "Saved Recipe",
    "Favorite Recipe",
    "New PC List",
    "New TODO List",
    "Saved List",
    "Saved Desserts",
    "Saved Extras",
    "Saved Meats",
    "New Normal List",
    "Calculator",
    "Random Recipe",
    "Add Measurement",
    "Conversion Table",
    "Category List"
)

// Misc
private const val TITLE = "FOE: Files Organized Easily"

class WindowDisplay : JFrame() {

    init {
        iconImage = loadImage(Images.icon)

        // Initialize
            // Panels
        clMain = CardLayout()
        mainPane = JPanel(clMain)
        MenuPane = MenuPanel()

        jpNewRecipe = NewRecipePanel(true)
        jpSavedRecipe = SavedRecipePanel()
        jpSavedDessert = SavedDessertRecipePanel()
        jpSavedExtra = SavedExtraRecipePanel()
        jpSavedMeat = SavedMeatRecipePanel()
        jpFavoriteRecipe = FavoriteRecipePanel()
        jpRandomRecipe = RandomRecipePanel()

        jpPlainList = NewNormalListPanel()
        jpProConList = NewPCListPanel()
        jpTodoList = NewTodoListPanel()
        jpSavedLists = SavedListsPanel()
        jpCategoryList = NewCategoryListPanel()

        jpCalculator = CalculatorPanel()
        jpMeasurement = NewMeasurePanel()
        jpConversion = ConversionTablePanel()

        // Set data
        buildMenu()

        // Add to screen
            // Panels to main panel
        mainPane.add(MenuPane, panels[0])
        mainPane.add(jpNewRecipe, panels[1])
        mainPane.add(jpSavedRecipe, panels[2])
        mainPane.add(jpFavoriteRecipe, panels[3])
        mainPane.add(jpProConList, panels[4])
        mainPane.add(jpTodoList, panels[5])
        mainPane.add(jpSavedLists, panels[6])
        mainPane.add(jpSavedDessert, panels[7])
        mainPane.add(jpSavedExtra, panels[8])
        mainPane.add(jpSavedMeat, panels[9])
        mainPane.add(jpPlainList, panels[10])
        mainPane.add(jpCalculator, panels[11])
        mainPane.add(jpRandomRecipe, panels[12])
        mainPane.add(jpMeasurement, panels[13])
        mainPane.add(jpConversion, panels[14])
        mainPane.add(jpCategoryList, panels[15])
        add(mainPane, BorderLayout.CENTER)
            // Menu buttons to menu panel
        jmb.add(jmSettings)
        jmb.add(jmRecipes)
        jmb.add(jmLists)
        jmb.add(jmExtras)

        // Add menu buttons to main panel
        add(jmb, BorderLayout.NORTH)

        switchPanels(MENUSTATE)

        defaultCloseOperation = EXIT_ON_CLOSE
        setLocationRelativeTo(null)
        isVisible = true
    }

    fun getCurrentState(state: Int) : CJPanel {
        return when (state) {
            NEWPCLISTSTATE -> jpProConList
            NEWTODOLISTSTATE -> jpTodoList
            NEWNORMALLISTSTATE -> jpPlainList
            else -> jpProConList
        }
    }

    private fun setCurrentState(state: Int) {
        currentState = when (state) {
            MENUSTATE -> MENUSTATE
            NEWRECIPESTATE -> NEWRECIPESTATE
            SAVEDRECIPESTATE -> SAVEDRECIPESTATE
            FAVORITERECIPESTATE -> FAVORITERECIPESTATE
            NEWPCLISTSTATE -> NEWPCLISTSTATE
            NEWTODOLISTSTATE -> NEWTODOLISTSTATE
            SAVEDLISTSTATE -> SAVEDLISTSTATE
            SAVEDDESSERTRECIPESTATE -> SAVEDDESSERTRECIPESTATE
            SAVEDEXTRARECIPESTATE -> SAVEDEXTRARECIPESTATE
            SAVEDMEATRECIPESTATE -> SAVEDMEATRECIPESTATE
            NEWNORMALLISTSTATE -> NEWNORMALLISTSTATE
            CALCULATORSTATE -> CALCULATORSTATE
            RANDOMRECIPESTATE -> RANDOMRECIPESTATE
            ADDMEASURESTATE -> ADDMEASURESTATE
            CONVERSIONSTATE -> CONVERSIONSTATE
            NEWCATEGORYLISTSTATE -> NEWCATEGORYLISTSTATE
            else -> MENUSTATE
        }
    }

    private fun setPanelInfo(panel: JPanel, state: Int) {
        //if(panel != getCurrentState(state)) {
        //    JOptionPane.showConfirmDialog(this, "", "", JOptionPane.YES_NO_OPTION)
        //}
        showPanel(panel, state)
        size = changeSize(state)
        title = changeTitle(state)
        setCurrentState(state)
        setLocationRelativeTo(null)
        panel.updateUI()
    }

    private fun switchPanels(state: Int) {
        when (state) {
            MENUSTATE -> { setPanelInfo(MenuPane, state) }
            NEWRECIPESTATE -> { setPanelInfo(jpNewRecipe, state); jpNewRecipe.darkmode() }
            SAVEDRECIPESTATE -> { setPanelInfo(jpSavedRecipe, state) }
            FAVORITERECIPESTATE -> { setPanelInfo(jpFavoriteRecipe, state); jpFavoriteRecipe.darkmode() }
            NEWPCLISTSTATE -> { setPanelInfo(jpProConList, state); jpProConList.darkmode() }
            NEWTODOLISTSTATE -> { setPanelInfo(jpTodoList, state); jpTodoList.darkmode() }
            SAVEDLISTSTATE -> { setPanelInfo(jpSavedLists, state); jpSavedLists.darkmode() }
            SAVEDDESSERTRECIPESTATE -> { setPanelInfo(jpSavedDessert, state); jpSavedDessert.darkmode() }
            SAVEDEXTRARECIPESTATE -> { setPanelInfo(jpSavedExtra, state); jpSavedExtra.darkmode() }
            SAVEDMEATRECIPESTATE -> { setPanelInfo(jpSavedMeat, state); jpSavedMeat.darkmode() }
            NEWNORMALLISTSTATE -> { setPanelInfo(jpPlainList, state); jpPlainList.darkmode() }
            CALCULATORSTATE -> { setPanelInfo(jpCalculator, state); jpCalculator.darkmode() }
            RANDOMRECIPESTATE -> { setPanelInfo(jpRandomRecipe, state); jpRandomRecipe.darkmode() }
            ADDMEASURESTATE -> { setPanelInfo(jpMeasurement, state); jpMeasurement.darkmode() }
            CONVERSIONSTATE -> { setPanelInfo(jpConversion, state); jpConversion.darkmode() }
            NEWCATEGORYLISTSTATE -> { setPanelInfo(jpCategoryList, state); jpCategoryList.darkmode() }
        }
    }

    private fun buildMenu() {

        // Menu buttons
        jmb = JMenuBar()
        jmSettings = JMenu("Settings")
        jmRecipes = JMenu("Recipes")
        jmLists = JMenu("Lists")
        jmExtras = JMenu("Extras")
        jmDebug = JMenu("Debug")

        // Menu items
            // Lists
        jmSubList = JMenu("Create new list"); jmLists.add(jmSubList)
        jmiToDoList = JMenuItem("Todo list"); jmSubList.add(jmiToDoList); jmiToDoList.addActionListener{ switchPanels(NEWTODOLISTSTATE) }
        jmiPosNegList = JMenuItem("Pros/cons list"); jmSubList.add(jmiPosNegList); jmiPosNegList.addActionListener { switchPanels(NEWPCLISTSTATE) }
        jmiNorList = JMenuItem("New plain list"); jmSubList.add(jmiNorList); jmiNorList.addActionListener{ switchPanels(NEWNORMALLISTSTATE) }
        jmiCatList = JMenuItem("Category list"); jmSubList.add(jmiCatList); jmiCatList.addActionListener { switchPanels(NEWCATEGORYLISTSTATE) }
        jmiSavList = JMenuItem("View all lists"); jmLists.add(jmiSavList); jmiSavList.addActionListener{ switchPanels(SAVEDLISTSTATE); jpSavedLists.loadLists() }
            // Settings
        jmiSettings = JMenuItem("Change save location"); jmSettings.add(jmiSettings); jmiSettings.addActionListener{ val csd = ChangeSaveDirectory(); csd.setLocationRelativeTo(this) }
        jmiSettings = JMenuItem("Go back to main menu"); jmSettings.add(jmiSettings); jmiSettings.addActionListener{ switchPanels(MENUSTATE) }
        jmiDark = JCheckBoxMenuItem("Dark mode"); jmSettings.add(jmiDark); jmiDark.addActionListener { WriteSettingsFile(path, jmiDark.isSelected, addedMeasures, isDebug); switchPanels(currentState) }; if(isDark) jmiDark.doClick()
        jmSettings.add(jmiDebugMode); jmiDebugMode.addActionListener { WriteSettingsFile(path, jmiDark.isSelected, addedMeasures, jmiDebugMode.isSelected); if(jmiDebugMode.isSelected) jmb.add(jmDebug) else jmb.remove(jmDebug); switchPanels(currentState) }; if(isDebug) jmiDebugMode.doClick()
            // Recipes
        jmiNewRec = JMenuItem("Create new recipe"); jmRecipes.add(jmiNewRec); jmiNewRec.addActionListener{ switchPanels(NEWRECIPESTATE) }
        jmiSavRec = JMenu("View all saved recipes"); jmRecipes.add(jmiSavRec)
        jmiDesRec = JMenuItem("Desserts"); jmiSavRec.add(jmiDesRec); jmiDesRec.addActionListener{ switchPanels(SAVEDDESSERTRECIPESTATE); jpSavedDessert.loadRecipes() }
        jmiExtraRec = JMenuItem("Extras"); jmiSavRec.add(jmiExtraRec); jmiExtraRec.addActionListener{ switchPanels(SAVEDEXTRARECIPESTATE); jpSavedExtra.loadRecipes() }
        jmiMeatRec = JMenuItem("Meats"); jmiSavRec.add(jmiMeatRec); jmiMeatRec.addActionListener{ switchPanels(SAVEDMEATRECIPESTATE); jpSavedMeat.loadRecipes() }
        jmiFavRec = JMenuItem("View all favorite recipes"); jmRecipes.add(jmiFavRec); jmiFavRec.addActionListener{ switchPanels(FAVORITERECIPESTATE); jpFavoriteRecipe.loadRecipes(false) }
        jmiRanRec = JMenuItem("Get random saved recipe"); jmRecipes.add(jmiRanRec); jmiRanRec.addActionListener { switchPanels(RANDOMRECIPESTATE);  }
            // Extras
        jmiCalc = JMenuItem("Calculator"); jmExtras.add(jmiCalc); jmiCalc.addActionListener { switchPanels(CALCULATORSTATE) }
        jmiMeasure = JMenuItem("Add another measurement"); jmExtras.add(jmiMeasure); jmiMeasure.addActionListener { switchPanels(ADDMEASURESTATE) }
        //jmiConvers = JMenuItem("Measurement conversion table"); jmExtras.add(jmiConvers); jmiConvers.addActionListener { switchPanels(CONVERSIONSTATE) }
            // Debug
        jmiDebug = JMenuItem("Open debug window"); jmDebug.add(jmiDebug); jmiDebug.addActionListener{ openDebug() }
        jmiSize = JMenuItem("Get current size of window"); jmDebug.add(jmiSize); jmiSize.addActionListener { info("$currentState's current size: " + this.size) }
    }

    private fun changeTitle(state: Int) : String {
        return "$TITLE - " + when (state) {
            MENUSTATE -> "Main Menu"
            NEWRECIPESTATE -> "Create New Recipe"
            SAVEDRECIPESTATE -> "View Saved Recipes"
            FAVORITERECIPESTATE -> "View Favorite Recipes"
            NEWPCLISTSTATE -> "Create New Pros/Cons List"
            SAVEDLISTSTATE -> "View Saved Lists"
            NEWTODOLISTSTATE -> "Create New ToDo List"
            SAVEDDESSERTRECIPESTATE -> "View Saved Dessert Recipes"
            SAVEDEXTRARECIPESTATE -> "View Saved Extra Recipes"
            SAVEDMEATRECIPESTATE -> "View Saved Meat Recipes"
            NEWNORMALLISTSTATE -> "Create New Plain List"
            CALCULATORSTATE -> "Calculator"
            RANDOMRECIPESTATE -> "Get Random Recipe"
            ADDMEASURESTATE -> "Add Measurement"
            CONVERSIONSTATE -> "Measurement Conversion Table"
            NEWCATEGORYLISTSTATE -> "Categorised List"
            else -> TITLE
        }
    }

    private fun changeSize(state: Int) : Dimension {
        val wh = Toolkit.getDefaultToolkit().screenSize.height
        return when (state) {
            MENUSTATE -> Dimension(693, 645)
            NEWRECIPESTATE -> Dimension(1000, ((wh/2)+(wh/3)))
            SAVEDRECIPESTATE -> Dimension(1020, 600)
            FAVORITERECIPESTATE -> Dimension(600, 684)
            NEWPCLISTSTATE -> Dimension(792, 780)
            SAVEDLISTSTATE -> Dimension(600, 678)
            NEWTODOLISTSTATE -> Dimension(574, 750)
            SAVEDDESSERTRECIPESTATE -> Dimension(600, 680)
            SAVEDEXTRARECIPESTATE -> Dimension(600, 680)
            SAVEDMEATRECIPESTATE -> Dimension(600, 680)
            NEWNORMALLISTSTATE -> Dimension(507, 824)
            CALCULATORSTATE -> Dimension(665, 434)
            RANDOMRECIPESTATE -> Dimension(1030, 410)
            ADDMEASURESTATE -> Dimension(435, 260)
            CONVERSIONSTATE -> Dimension(500, 500)
            NEWCATEGORYLISTSTATE -> Dimension(509, 842)
            else -> Dimension(693, 645)
        }
    }

    private fun showPanel(panel: JPanel, state: Int) {
        MenuPane.isVisible = false
        jpNewRecipe.isVisible = false
        jpSavedRecipe.isVisible = false
        jpFavoriteRecipe.isVisible = false
        jpProConList.isVisible = false
        jpSavedLists.isVisible = false
        jpTodoList.isVisible = false
        jpSavedDessert.isVisible = false
        jpSavedExtra.isVisible = false
        jpSavedMeat.isVisible = false
        jpPlainList.isVisible = false
        jpCalculator.isVisible = false
        jpRandomRecipe.isVisible = false
        jpMeasurement.isVisible = false
        jpConversion.isVisible = false
        jpCategoryList.isVisible = false
        clMain.show(mainPane, panels[state])
        darkMode(panel)
        panel.isVisible = true
        pack()
    }

}