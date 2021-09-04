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
    private var titleSize = tit.length*10
    private var typeSize = typ.length*10

    init {
        icon = ImageIcon(buttonCard)
        minimumSize = Dimension(icon.iconWidth, icon.iconHeight)
        maximumSize = Dimension(icon.iconWidth, icon.iconHeight)
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        g.font = Font("Tahoma", Font.BOLD, 20)
        g.drawString(title, (250-(titleSize/2)), (icon.iconHeight/2)-50)
        g.drawString(type, (250-(typeSize/2)), (icon.iconHeight/2)+50)
    }

}