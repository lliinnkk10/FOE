package com.github.atheera.recipemanager.extras

import com.github.atheera.recipemanager.buttonCard
import java.awt.Dimension
import java.awt.Font
import java.awt.Graphics
import javax.swing.ImageIcon
import javax.swing.JButton

class ButtonListCard(tit: String, typ: String) : JButton() {

    var title = tit
    var type = typ

    init {
        icon = ImageIcon(buttonCard)
        minimumSize = Dimension(icon.iconWidth, icon.iconHeight)
        maximumSize = Dimension(icon.iconWidth, icon.iconHeight)
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        g.font = Font("Tahoma", Font.BOLD, 20)
        g.drawString(title, ((500-title.length)/2-(title.length*4)), (icon.iconHeight/2)-50)
        g.drawString(type, ((500-type.length)/2-(type.length*4)), (icon.iconHeight/2)+50)
    }

}