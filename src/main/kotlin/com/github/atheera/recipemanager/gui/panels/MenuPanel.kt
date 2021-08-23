package com.github.atheera.recipemanager.gui.panels

import com.github.atheera.recipemanager.backgroundImage
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
        g?.drawImage(backgroundImage, 0, 0, this)
    }

}