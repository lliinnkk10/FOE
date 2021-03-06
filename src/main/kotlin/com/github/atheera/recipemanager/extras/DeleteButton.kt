package com.github.atheera.recipemanager.extras

import com.github.atheera.recipemanager.Icons
import com.github.atheera.recipemanager.loadIcon
import java.awt.Dimension
import javax.swing.JButton

class DeleteButton : JButton() {

    init {
        this.icon = loadIcon(Icons.delete)
        this.size = Dimension(20, 20)
        this.minimumSize = Dimension(20, 20)
        this.maximumSize = Dimension(20, 20)
        this.isOpaque = false
        this.border.isBorderOpaque
    }

}