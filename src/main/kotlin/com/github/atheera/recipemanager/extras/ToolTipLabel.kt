package com.github.atheera.recipemanager.extras

import com.github.atheera.recipemanager.toolTip
import java.awt.Dimension
import javax.swing.ImageIcon
import javax.swing.JLabel

class ToolTipLabel(tip: String) : JLabel() {

    init {
        val ttIcon = ImageIcon(toolTip)
        this.icon = ttIcon
        this.toolTipText = tip
        this.size = Dimension(16, 16)
        this.minimumSize = Dimension(16, 16)
        this.maximumSize = Dimension(16, 16)
    }

}