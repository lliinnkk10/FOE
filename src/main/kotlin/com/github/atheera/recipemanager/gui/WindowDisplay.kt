package com.github.atheera.recipemanager.gui

import com.github.atheera.recipemanager.*
import com.github.atheera.recipemanager.extras.CJPanel
import com.github.atheera.recipemanager.gui.States.ADDMEASURESTATE
import com.github.atheera.recipemanager.gui.States.CALCULATORSTATE
import com.github.atheera.recipemanager.gui.States.CONVERSIONSTATE
import com.github.atheera.recipemanager.gui.States.FAVORITERECIPESTATE
import com.github.atheera.recipemanager.gui.States.MENUSTATE
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
import com.github.atheera.recipemanager.gui.panels.list.NewNormalListPanel
import com.github.atheera.recipemanager.gui.panels.list.NewPCListPanel
import com.github.atheera.recipemanager.gui.panels.list.NewTodoListPanel
import com.github.atheera.recipemanager.gui.panels.list.SavedListsPanel
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
private lateinit var NewRecPane: NewRecipePanel
private lateinit var SavRecPane: SavedRecipePanel
private lateinit var SavDesRecPan: SavedDessertRecipePanel
private lateinit var SavExtRecPan: SavedExtraRecipePanel
private lateinit var SavMeaRecPan: SavedMeatRecipePanel
private lateinit var FavRecPane: FavoriteRecipePanel
private lateinit var RanRecPan: RandomRecipePanel
    // Lists
private lateinit var NewProConListPane: NewPCListPanel
private lateinit var NewToDoListPane: NewTodoListPanel
private lateinit var SavListPane: SavedListsPanel
private lateinit var NewNorListPane: NewNormalListPanel
    // Extras
private lateinit var CalcPane: CalculatorPanel
private lateinit var MeasPane: NewMeasurePanel
private lateinit var ConvPane: ConversionTablePanel
    // Layout
private lateinit var cl: CardLayout

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
    "Conversion Table"
)

// Misc
private const val TITLE = "FOE: Files Organized Easily"

class WindowDisplay : JFrame() {

    init {
        iconImage = loadImage(Images.icon)

        // Initialize
            // Panels
        cl = CardLayout()
        mainPane = JPanel(cl)
        MenuPane = MenuPanel()

        NewRecPane = NewRecipePanel(true)
        SavRecPane = SavedRecipePanel()
        SavDesRecPan = SavedDessertRecipePanel()
        SavExtRecPan = SavedExtraRecipePanel()
        SavMeaRecPan = SavedMeatRecipePanel()
        FavRecPane = FavoriteRecipePanel()
        NewNorListPane = NewNormalListPanel()
        RanRecPan = RandomRecipePanel()

        NewProConListPane = NewPCListPanel()
        NewToDoListPane = NewTodoListPanel()
        SavListPane = SavedListsPanel()

        CalcPane = CalculatorPanel()
        MeasPane = NewMeasurePanel()
        ConvPane = ConversionTablePanel()

        // Set data
        buildMenu()

        // Add to screen
            // Panels to main panel
        mainPane.add(MenuPane, panels[0])
        mainPane.add(NewRecPane, panels[1])
        mainPane.add(SavRecPane, panels[2])
        mainPane.add(FavRecPane, panels[3])
        mainPane.add(NewProConListPane, panels[4])
        mainPane.add(NewToDoListPane, panels[5])
        mainPane.add(SavListPane, panels[6])
        mainPane.add(SavDesRecPan, panels[7])
        mainPane.add(SavExtRecPan, panels[8])
        mainPane.add(SavMeaRecPan, panels[9])
        mainPane.add(NewNorListPane, panels[10])
        mainPane.add(CalcPane, panels[11])
        mainPane.add(RanRecPan, panels[12])
        mainPane.add(MeasPane, panels[13])
        mainPane.add(ConvPane, panels[14])
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
            NEWPCLISTSTATE -> NewProConListPane
            NEWTODOLISTSTATE -> NewToDoListPane
            NEWNORMALLISTSTATE -> NewNorListPane
            else -> NewProConListPane
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
            NEWRECIPESTATE -> { setPanelInfo(NewRecPane, state); NewRecPane.darkmode() }
            SAVEDRECIPESTATE -> { setPanelInfo(SavRecPane, state) }
            FAVORITERECIPESTATE -> { setPanelInfo(FavRecPane, state); FavRecPane.darkmode() }
            NEWPCLISTSTATE -> { setPanelInfo(NewProConListPane, state); NewProConListPane.darkmode() }
            NEWTODOLISTSTATE -> { setPanelInfo(NewToDoListPane, state); NewToDoListPane.darkmode() }
            SAVEDLISTSTATE -> { setPanelInfo(SavListPane, state); SavListPane.darkmode() }
            SAVEDDESSERTRECIPESTATE -> { setPanelInfo(SavDesRecPan, state); SavDesRecPan.darkmode() }
            SAVEDEXTRARECIPESTATE -> { setPanelInfo(SavExtRecPan, state); SavExtRecPan.darkmode() }
            SAVEDMEATRECIPESTATE -> { setPanelInfo(SavMeaRecPan, state); SavMeaRecPan.darkmode() }
            NEWNORMALLISTSTATE -> { setPanelInfo(NewNorListPane, state); NewNorListPane.darkmode() }
            CALCULATORSTATE -> { setPanelInfo(CalcPane, state); CalcPane.darkmode() }
            RANDOMRECIPESTATE -> { setPanelInfo(RanRecPan, state); RanRecPan.darkmode() }
            ADDMEASURESTATE -> { setPanelInfo(MeasPane, state); MeasPane.darkmode() }
            CONVERSIONSTATE -> { setPanelInfo(ConvPane, state); ConvPane.darkmode() }
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
        jmiToDoList = JMenuItem("Todo"); jmSubList.add(jmiToDoList); jmiToDoList.addActionListener{ switchPanels(NEWTODOLISTSTATE) }
        jmiPosNegList = JMenuItem("Pros/cons"); jmSubList.add(jmiPosNegList); jmiPosNegList.addActionListener { switchPanels(NEWPCLISTSTATE) }
        jmiNorList = JMenuItem("New plain list"); jmSubList.add(jmiNorList); jmiNorList.addActionListener{ switchPanels(NEWNORMALLISTSTATE) }
        jmiSavList = JMenuItem("View all lists"); jmLists.add(jmiSavList); jmiSavList.addActionListener{ switchPanels(SAVEDLISTSTATE); SavListPane.loadLists() }
            // Settings
        jmiSettings = JMenuItem("Change save location"); jmSettings.add(jmiSettings); jmiSettings.addActionListener{ val csd = ChangeSaveDirectory(); csd.setLocationRelativeTo(this) }
        jmiSettings = JMenuItem("Go back to main menu"); jmSettings.add(jmiSettings); jmiSettings.addActionListener{ switchPanels(MENUSTATE) }
        jmiDark = JCheckBoxMenuItem("Dark mode"); jmSettings.add(jmiDark); jmiDark.addActionListener { WriteSettingsFile(path, jmiDark.isSelected, addedMeasures, isDebug); switchPanels(currentState) }; if(isDark) jmiDark.doClick()
        jmSettings.add(jmiDebugMode); jmiDebugMode.addActionListener { WriteSettingsFile(path, jmiDark.isSelected, addedMeasures, jmiDebugMode.isSelected); if(jmiDebugMode.isSelected) jmb.add(jmDebug) else jmb.remove(jmDebug); switchPanels(currentState) }; if(isDebug) jmiDebugMode.doClick()
            // Recipes
        jmiNewRec = JMenuItem("Create new recipe"); jmRecipes.add(jmiNewRec); jmiNewRec.addActionListener{ switchPanels(NEWRECIPESTATE) }
        jmiSavRec = JMenu("View all saved recipes"); jmRecipes.add(jmiSavRec)
        jmiDesRec = JMenuItem("Desserts"); jmiSavRec.add(jmiDesRec); jmiDesRec.addActionListener{ switchPanels(SAVEDDESSERTRECIPESTATE); SavDesRecPan.loadRecipes() }
        jmiExtraRec = JMenuItem("Extras"); jmiSavRec.add(jmiExtraRec); jmiExtraRec.addActionListener{ switchPanels(SAVEDEXTRARECIPESTATE); SavExtRecPan.loadRecipes() }
        jmiMeatRec = JMenuItem("Meats"); jmiSavRec.add(jmiMeatRec); jmiMeatRec.addActionListener{ switchPanels(SAVEDMEATRECIPESTATE); SavMeaRecPan.loadRecipes() }
        jmiFavRec = JMenuItem("View all favorite recipes"); jmRecipes.add(jmiFavRec); jmiFavRec.addActionListener{ switchPanels(FAVORITERECIPESTATE); FavRecPane.loadRecipes(false) }
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
            else -> Dimension(693, 645)
        }
    }

    private fun showPanel(panel: JPanel, state: Int) {
        MenuPane.isVisible = false
        NewRecPane.isVisible = false
        SavRecPane.isVisible = false
        FavRecPane.isVisible = false
        NewProConListPane.isVisible = false
        SavListPane.isVisible = false
        NewToDoListPane.isVisible = false
        SavDesRecPan.isVisible = false
        SavExtRecPan.isVisible = false
        SavMeaRecPan.isVisible = false
        NewNorListPane.isVisible = false
        CalcPane.isVisible = false
        RanRecPan.isVisible = false
        MeasPane.isVisible = false
        ConvPane.isVisible = false
        cl.show(mainPane, panels[state])
        darkMode(panel)
        panel.isVisible = true
        pack()
    }

}