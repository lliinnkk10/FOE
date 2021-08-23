package com.github.atheera.recipemanager.save

import com.github.atheera.recipemanager.listPath
import com.github.atheera.recipemanager.path
import com.github.atheera.recipemanager.recipeFavPath
import com.github.atheera.recipemanager.recipePath
import java.io.File

class Files {

    // Misc
    fun makeDir(dirName: String) : File {
        val dir = File(dirName)
        if(!dir.exists())
            dir.mkdirs()
        return dir
    }

    // Settings
    fun getDirName(location: String, title: String) : File {
        val fileName = "$title.json"
        makeDir(location)
        return File("$location/$fileName")
    }

    fun setPath(location: String) {
        path = location
        makeDir(path)
    }

    // Lists
    fun makeListDir(type: String) : String {
        val dirName = listPath.plus(type)
        makeDir(dirName)
        return dirName
    }

    fun getListFile(type: String, title: String) : File {
        val fileName = "$title.json"
        val dirName = makeListDir(type)
        return File("$dirName/$fileName")
    }

    // Recipes
    fun makeRecipeDir(cat: String, subCat: String) : String {
        val directoryName = recipePath.plus("$cat/$subCat")
        makeDir(directoryName)
        return directoryName
    }

    fun getRecipeFile(cat: String, subCat: String, title: String) : File {
        val fileName = "$title.json"
        val dirName = makeRecipeDir(cat, subCat)
        return File("$dirName/$fileName")
    }

    fun getRecipeFavoriteDir(title: String) : String {
        val fileName = "$recipeFavPath$title.json"
        makeDir(recipeFavPath)
        return fileName
    }

    fun getRecipeFavoriteFile(title: String) : File {
        val fileName = "$title.json"
        val dirName = makeDir(recipeFavPath)
        return File("$dirName/$fileName")
    }

    fun getRecipeFileName(cat: String, subCat: String, title: String) : String {
        val fileName = "$title.json"
        makeRecipeDir(cat, subCat)
        return "c:\\Recipes\\$cat\\$subCat\\$fileName"
    }

}