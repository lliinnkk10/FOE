package com.github.atheera.recipemanager

import com.github.atheera.recipemanager.extras.LoadImage
import com.github.atheera.recipemanager.gui.DebugWindow
import com.github.atheera.recipemanager.gui.WindowDisplay
import com.github.atheera.recipemanager.save.Files
import com.github.atheera.recipemanager.save.read.ReadSettings
import com.github.atheera.recipemanager.save.write.WriteSettingsFile
import java.awt.Color
import java.io.File
import java.time.LocalTime
import javax.swing.JPanel
import javax.swing.border.Border
import javax.swing.border.EtchedBorder
import javax.swing.border.TitledBorder

// Names of all the list types available
val categories = listOf("Desserts", "Extras", "Meats")
val subCatDesserts = listOf("Cake", "Chocolate", "Confection", "Cookie", "Custard", "Deep Fried", "Frozen", "Gelatin", "Pastry", "Pie", "Pudding", "Sweet Bread", "Tart")
val subCatExtras = listOf("Bread", "Greenthing", "Sauce", "Savory Pie", "Soup")
val subCatMeats = listOf("Beef", "Fish", "Other", "Plant Meat", "Poultry", "Pork")
val listCategories = listOf("Pros and Cons", "To Do", "Plain List")
val measures = listOf("L", "DL", "CL", "ML", "KG", "HG", "G", "MG", "TBSP", "TSP", "SPM", "PCS", "PINCH", "CUP")

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

// Loading all images for use across the code
val backgroundImage = LoadImage().loadImage("notepadBG.png")!!
val imageIcon = LoadImage().loadImage(("icon.png"))!!
val logo = LoadImage().loadImage("logo.png")!!
val toolTip = LoadImage().loadImage("hoverTooltip.png")!!
val buttonCard = LoadImage().loadImage("ButtonCard.png")!!
val arrowUp = LoadImage().loadIcon("arrowUp.png")!!
val arrowDown = LoadImage().loadIcon("arrowDown.png")!!
val deleteButton = LoadImage().loadIcon("deleteButton.png")!!
val searchButton = LoadImage().loadIcon("searchButton.png")!!

lateinit var dw: DebugWindow

// Misc
val GRAY = Color(43, 43, 43)
val WHITE = Color(238, 238, 238)

fun main(args: Array<String>) {
    // This should only run first if debug edition!
    openDebug()
    // This should always run first, exc ^!
    onStartUp()

    // Opens the main programs window
    WindowDisplay()

}

fun onStartUp() {
    // Checks if Settings.json has been made, if not make it else gets information from it
    if(!File(settingsPath).exists()) {
        WriteSettingsFile(defaultPath, isDark)
        path = defaultPath
    } else {
        ReadSettings(settingsPath)
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
        text = dw.jtaInfo.text
        dw.dispose()
    } catch (e: Exception) { }

    dw = DebugWindow()
    dw.jtaInfo.text = text
    dw.setLocationRelativeTo(null)
    dw.isOpened = true
}

fun upperCaseFirstWords(inString: String) : String {
    val splitItem = inString.split(" ")
    var combinedItem = ""
    var finalItem = ""
    for(i in splitItem.indices) {
        var uppercaseFirst = splitItem[i].substring(0, 1)
        val remRest = splitItem[i].substring(1)
        uppercaseFirst = uppercaseFirst.uppercase()
        val combinedString = "$uppercaseFirst$remRest"
        combinedItem = combinedItem.plus("$combinedString ")
        if (i == splitItem.lastIndex) {
            finalItem = combinedItem
        }
    }
    return finalItem
}

fun darkMode(jp: JPanel) {
    ReadSettings(settingsPath)
    if(isDark) {
        jp.foreground = WHITE
        jp.background = GRAY
    } else {
        jp.foreground = Color.BLACK
        jp.background = WHITE
    }
}

fun darkModeOut(jp: JPanel) {
    ReadSettings(settingsPath)
    if(isDark) {
        jp.foreground = WHITE
        jp.background = Color.DARK_GRAY
    } else {
        jp.foreground = Color.BLACK
        jp.background = WHITE
    }
}

fun darkModeIn(jp: JPanel) {
    ReadSettings(settingsPath)
    if(isDark) {
        jp.foreground = WHITE
        jp.background = Color.GRAY
    } else {
        jp.foreground = Color.BLACK
        jp.background = WHITE
    }
}