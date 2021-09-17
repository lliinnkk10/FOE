package com.github.atheera.recipemanager

import com.github.atheera.recipemanager.extras.LoadImage
import com.github.atheera.recipemanager.gui.DebugWindow
import com.github.atheera.recipemanager.gui.WindowDisplay
import com.github.atheera.recipemanager.gui.isOpened
import com.github.atheera.recipemanager.gui.jtaInfo
import com.github.atheera.recipemanager.save.Files
import com.github.atheera.recipemanager.save.read.ReadSettings
import com.github.atheera.recipemanager.save.write.WriteSettingsFile
import java.awt.Color
import java.io.File
import java.time.LocalTime
import javax.swing.BorderFactory
import javax.swing.JComponent
import javax.swing.border.Border
import javax.swing.border.EtchedBorder
import javax.swing.border.TitledBorder

// Names of all the list types available
val categories = listOf("Desserts", "Extras", "Meats")
val subCatDesserts = listOf("Cake", "Chocolate", "Confection", "Cookie", "Custard", "Deep Fried", "Frozen", "Gelatin", "Pastry", "Pie", "Pudding", "Sweet Bread", "Tart")
val subCatExtras = listOf("Bread", "Greenthing", "Sauce", "Savory Pie", "Soup")
val subCatMeats = listOf("Beef", "Fish", "Other", "Plant Meat", "Poultry", "Pork")
val listCategories = listOf("Pros and Cons", "To Do", "Plain List")
val measures = mutableListOf("L", "DL", "CL", "ML", "KG", "HG", "G", "MG", "TBSP", "TSP", "SPM", "PIECE(S)", "PINCH", "CUP", "WHOLE", "CLOVE", "CAN")

// List types items
    // Pros/Cons
lateinit var listPCTitle: String
lateinit var listPCPos: MutableList<String>
lateinit var listPCNeg: MutableList<String>
    // To Do
lateinit var listTDTitle: String
lateinit var listTD: MutableList<String>
lateinit var listTDChecked: MutableList<String>
    // Normal list
lateinit var listNTitle: String
lateinit var listNList: MutableList<String>
    // Recipes
lateinit var recipeTitle: String
lateinit var recipeCategory: String
lateinit var recipeSubCategory: String
lateinit var recipeInstructions: String
lateinit var recipeIngredients: MutableList<String>
lateinit var recipeDescription: String
var recipeTemperature: Int = 0
var recipeConvTemperature: Int = 0
var recipeEgg: Boolean = false
var recipeGluten: Boolean = false
var recipeLactose: Boolean = false
var recipeVegan: Boolean = false
var recipeVegetarian: Boolean = false

// Paths for saving location
const val defaultPath: String = "C://FOE/"
const val errorPath: String = "${defaultPath}error-reports/"
const val settingsPath: String = "${defaultPath}Settings.json"
lateinit var path: String
lateinit var recipePath: String
lateinit var listPath: String
lateinit var recipeFavPath: String

// Settings
var isDark: Boolean = false
lateinit var addedMeasures: MutableList<String>
var isDebug: Boolean = false

// Loading all images for use across the program
val backgroundImage = LoadImage().loadImage("notepadBG.png")!!
val backgroundDarkImage = LoadImage().loadImage("notepadDMBG.png")!!
val imageIcon = LoadImage().loadImage("icon.png")!!
val toolTip = LoadImage().loadImage("hoverTooltip.png")!!
val buttonCard = LoadImage().loadImage("ButtonCard.png")!!
val buttonCardPressed = LoadImage().loadImage("ButtonCardPressed.png")!!
val buttonCardHovered = LoadImage().loadImage("ButtonCardHovered.png")!!
val deleteButton = LoadImage().loadIcon("deleteButton.png")!!
val searchButton = LoadImage().loadIcon("searchButton.png")!!

lateinit var dw: DebugWindow

// Misc
val GRAY = Color(43, 43, 43)
val LIGHT_GRAY = Color(180, 180, 180)
val DARK_WHITE = Color(200, 200, 200)
val LIGHT_WHITE = Color(225, 225, 225)
val WHITE = Color(238, 238, 238)

fun main(args: Array<String>) {
    // This should always run first!
    onStartUp()
    // Runs on startup if debug is enabled
    if(isDebug) openDebug()

    // Opens the main programs window
    WindowDisplay()

}

fun onStartUp() {
    // Checks if Settings.json has been made, if not make it else gets information from it
    if(!File(settingsPath).exists()) {
        addedMeasures = mutableListOf()
        WriteSettingsFile(defaultPath, isDark, addedMeasures, isDebug)
        path = defaultPath
    } else {
        ReadSettings(settingsPath)
    }
    // Add the custom measurements to existing
    for(i in addedMeasures) {
        measures.add(i)
    }

    // Sets the save paths according to the Settings.json
    recipePath = "$path/Recipes/"
    listPath = "$path/Files/"
    recipeFavPath = recipePath + "Favorites/"

    createDirs()
}

// Creating all the categories and sub categories for less chance of error/crashes
fun createDirs() {
    val dir = Files()
    for(cats in categories) {
        when (cats) {
            categories[0] -> for(subCat in subCatDesserts) { dir.makeRecipeDir(cats, subCat) }
            categories[1] -> for(subCat in subCatExtras) { dir.makeRecipeDir(cats, subCat) }
            categories[2] -> for(subCat in subCatMeats) { dir.makeRecipeDir(cats, subCat) }
        }
    }
    for(lists in listCategories) {
        when (lists) {
            listCategories[0] -> dir.makeListDir(lists)
            listCategories[1] -> dir.makeListDir(lists)
            listCategories[2] -> dir.makeListDir(lists)
        }
    }
    dir.makeDir(recipeFavPath)
    dir.makeDir(errorPath)
}

fun removeFirstAndLast(string: String) : String {
    val sb = StringBuilder(string)
    sb.deleteCharAt(string.length - 1)
    sb.deleteCharAt(0)
    return sb.toString()
}

fun removeLast(string: String, amount: Int) : String {
    return string.substring(0, string.length-amount)
}

fun createBorder(title: String) : Border {
    val border = TitledBorder(EtchedBorder(), title)
    ReadSettings(settingsPath)
    border.titleColor = if(isDark) Color.WHITE else Color.BLACK
    return border
}

fun getCurrentTime() : String {
    val localTime = LocalTime.now().toString()
    val sTime = localTime.substring(0, localTime.length-10)
    val splitTime = sTime.split(":")
    val hh = splitTime[0]
    val mm = splitTime[1]
    val ss = splitTime[2]
    return "$hh.$mm.$ss"
}

fun openDebug() {
    var text = ""
    try {
        text = jtaInfo.text
        dw.dispose()
    } catch (e: Exception) { }

    dw = DebugWindow()
    jtaInfo.text = text
    dw.setLocationRelativeTo(null)
    isOpened = true
}

fun upperCaseFirstWords(inString: String) : String {
    val splitItem = inString.split(" ")
    var combinedItem = ""
    var finalItem = ""
    try {
        for (i in splitItem.indices) {
            var uppercaseFirst = splitItem[i].substring(0, 1)
            val remRest = splitItem[i].substring(1)
            uppercaseFirst = uppercaseFirst.uppercase()
            val combinedString = "$uppercaseFirst$remRest"
            combinedItem = combinedItem.plus("$combinedString ")
            if (i == splitItem.lastIndex) {
                finalItem = removeLast(combinedItem, 1)
            }
        }
    } catch (e: Exception) {
        finalItem = inString
    }
    return finalItem
}

fun darkMode(jp: JComponent) {
    ReadSettings()
    if(isDark) {
        jp.foreground = Color.WHITE
        jp.background = GRAY
        jp.border = BorderFactory.createLineBorder(Color.BLACK)
    } else {
        jp.foreground = Color.BLACK
        jp.background = LIGHT_GRAY
        jp.border = BorderFactory.createLineBorder(Color.WHITE)
    }
}

fun darkModeOut(jp: JComponent) {
    ReadSettings()
    if(isDark) {
        jp.foreground = Color.WHITE
        jp.background = Color.DARK_GRAY
        jp.border = BorderFactory.createLineBorder(Color.BLACK)
    } else {
        jp.foreground = Color.BLACK
        jp.background = DARK_WHITE
        jp.border = BorderFactory.createLineBorder(Color.WHITE)
    }
}

fun darkModeIn(jp: JComponent) {
    ReadSettings()
    if(isDark) {
        jp.foreground = Color.WHITE
        jp.background = Color.GRAY
        jp.border = BorderFactory.createLineBorder(Color.BLACK)
    } else {
        jp.foreground = Color.BLACK
        jp.background = LIGHT_WHITE
        jp.border = BorderFactory.createLineBorder(Color.WHITE)
    }
}

fun darkModeDetail(jp: JComponent) {
    ReadSettings()
    if(isDark) {
        jp.foreground = Color.BLACK
        jp.background = LIGHT_GRAY
        jp.border = BorderFactory.createLineBorder(Color.BLACK)
    } else {
        jp.foreground = Color.BLACK
        jp.background = WHITE
        jp.border = BorderFactory.createLineBorder(Color.WHITE)
    }
}