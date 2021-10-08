package com.github.atheera.recipemanager.gui.panels

import com.github.atheera.recipemanager.Images.notepad
import com.github.atheera.recipemanager.Images.notepadDM
import com.github.atheera.recipemanager.isDark
import com.github.atheera.recipemanager.loadImage
import java.awt.Graphics
import javax.swing.JPanel

class MenuPanel : JPanel() {

    init {
    }

    override fun paintComponent(g: Graphics?) {
        super.paintComponent(g)
        g?.drawImage(if(isDark) loadImage(notepadDM) else loadImage(notepad), 0, 0, this)
    }

}