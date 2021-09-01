package com.github.atheera.recipemanager.gui.panels

import com.github.atheera.recipemanager.backgroundDarkImage
import com.github.atheera.recipemanager.backgroundImage
import com.github.atheera.recipemanager.isDark
import java.awt.Graphics
import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import javax.swing.JPanel

class MenuPanel : JPanel() {

    init {
        setSize(backgroundImage.width, backgroundImage.height)
    }

    override fun paintComponent(g: Graphics?) {
        super.paintComponent(g)
        g?.drawImage(if(isDark) backgroundDarkImage else backgroundImage, 0, 0, this)
    }

}