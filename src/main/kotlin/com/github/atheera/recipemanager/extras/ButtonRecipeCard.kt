package com.github.atheera.recipemanager.extras

import com.github.atheera.recipemanager.buttonCard
import java.awt.Dimension
import java.awt.Font
import java.awt.Graphics
import javax.swing.ImageIcon
import javax.swing.JButton

class ButtonRecipeCard(title: String, cat: String, subCat: String, desc: String) : JButton() {

    private var titles = title
    private var cats = cat
    private var subCats = subCat
    private var descs = desc
    private var titleSize = title.length*10

    init {
        icon = ImageIcon(buttonCard)
        minimumSize = Dimension(icon.iconWidth, icon.iconHeight)
        maximumSize = Dimension(icon.iconWidth, icon.iconHeight)
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        g.font = Font("Tahoma", Font.BOLD, 20)
        g.drawString(titles, (250-(titleSize/2)), 55)
        g.drawString(cats, 160, 90)
        g.drawString(subCats, 290, 90)
        g.drawString(descs, 70, 130)
    }

    fun getTitle() : String {
        return titles
    }

}