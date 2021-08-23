package com.github.atheera.recipemanager.gui.frames.recipe

import com.github.atheera.recipemanager.*
import com.github.atheera.recipemanager.gui.panels.recipe.*
import com.github.atheera.recipemanager.save.write.WriteRecipeFavorite
import com.github.atheera.recipemanager.save.write.WriteRecipeSaves
import java.awt.Dimension
import javax.swing.JFrame
import javax.swing.JOptionPane

class SavedRecipeFrame(title: String, cat: String, subCat: String, instr: String, ingr: MutableList<String>, desc: String, temp: Int, cTemp: Int, egg: Boolean, gluten: Boolean, lactose: Boolean, vegan: Boolean, veget: Boolean) : JFrame(){

    private val cp = NewRecipePanel(false)

    init {
        iconImage = imageIcon
        this.title = title
        cp.htfTitle.text = title
        defaultCloseOperation = DISPOSE_ON_CLOSE
        size = Dimension(1075, 975)
        isVisible = true

        recipeCategory = cat
        cp.jlCat.text = cat
        recipeSubCategory = subCat
        cp.jlSubCat.text = subCat
        cp.updateButtons(false)

        cp.jbSave.addActionListener {
            if(cp.getInformation(false)) {
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
            }
            dispose()
        }

        cp.jbFavorite.addActionListener {
            if(cp.getInformation(false)) {
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
            }
            dispose()
        }

        cp.jtaDesc.text = desc

        for(i in ingr.indices) {
            println(ingr[i])
            val str = ingr[i]
            val splitStr = str.split(" ")
            val amount = splitStr[0].toDouble()
            val measure = splitStr[1]
            val item = splitStr[2]
            val itemCard = cp.createCard(amount, measure, item)
            cp.jpIng.add(itemCard, "wrap")
        }

        cp.jtaIns.text = instr

        cp.factor = temp
        cp.htfDegrees.text = temp.toString()
        cp.result = cTemp
        if(temp > cTemp) {
            cp.jlCF.text = "° F"
            cp.jlConverted.text = "$cTemp° C"
            cp.jrbCCEls.doClick()
        } else {
            cp.jlCF.text = "° C"
            cp.jlConverted.text = "$cTemp° F"
            cp.jrbCFahr.doClick()
        }

        cp.jcbEgg.isSelected = egg
        cp.jcbGluten.isSelected = gluten
        cp.jcbLactose.isSelected = lactose
        cp.jcbVegan.isSelected = vegan
        cp.jcbVeget.isSelected = veget

        cp.jbSave.addActionListener {
            dispose()
        }

        add(cp)

    }

}