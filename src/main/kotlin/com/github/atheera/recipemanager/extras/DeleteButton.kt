package com.github.atheera.recipemanager.extras

import com.github.atheera.recipemanager.deleteButton
import java.awt.Dimension
import javax.swing.JButton

class DeleteButton : JButton() {

    init {
        this.icon = deleteButton
        this.size = Dimension(20, 20)
        this.minimumSize = Dimension(20, 20)
        this.maximumSize = Dimension(20, 20)
        this.isOpaque = false
        this.border.isBorderOpaque
    }

}