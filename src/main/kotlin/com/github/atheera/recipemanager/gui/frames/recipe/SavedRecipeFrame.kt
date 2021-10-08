package com.github.atheera.recipemanager.gui.frames.recipe

import com.github.atheera.recipemanager.*
import com.github.atheera.recipemanager.gui.panels.recipe.*
import com.github.atheera.recipemanager.save.write.WriteRecipeFavorite
import com.github.atheera.recipemanager.save.write.WriteRecipeSaves
import java.awt.Dimension
import javax.swing.JFrame

class SavedRecipeFrame(title: String, cat: String, subCat: String, instr: MutableList<String>, ingr: MutableList<String>, equipment: MutableList<String>, desc: String, link: String, temp: Int, cTemp: Int, egg: Boolean, gluten: Boolean, lactose: Boolean, vegan: Boolean, veget: Boolean) : JFrame(){

    private val cp = NewRecipePanel(false)

    init {
        iconImage = loadImage(Images.icon)
        this.title = title
        cp.htfTitle.text = title
        defaultCloseOperation = DISPOSE_ON_CLOSE
        size = Dimension(682, 975)
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
                    recipeEquipment,
                    recipeDescription,
                    recipeLink,
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
                    recipeEquipment,
                    recipeDescription,
                    recipeLink,
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

        cp.jtfLink.text = link

        for(i in ingr.indices) {
            println(ingr[i])
            val str = ingr[i]
            val splitStr = str.split(" ")
            val amount = splitStr[0].toDouble()
            val measure = splitStr[1]
            var finalItem = ""
            var combined = ""
            if(splitStr.size > 2) {
                for (it in splitStr.indices) {
                    if(it > 1) {
                        val item = splitStr[it]
                        combined = combined.plus("$item ")
                        if (it == splitStr.lastIndex) {
                            finalItem = combined
                        }
                    }
                }
            } else {
                finalItem = splitStr[2]
            }
            val itemCard = cp.createCard(amount, measure, finalItem, cp.jpIng, false)
            cp.jpIng.add(itemCard, "wrap")
        }

        for (i in instr.indices) {
            if(i == instr.size) break
            cp.jtaIns.text += if(i < instr.size-1) "${instr[i]}\n" else instr[i]
        }

        for (i in equipment.indices) {
            if (i == equipment.size) break
            cp.jtaEquip.text += if(i < equipment.size-1) "${equipment[i]}\n" else equipment[i]
        }

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