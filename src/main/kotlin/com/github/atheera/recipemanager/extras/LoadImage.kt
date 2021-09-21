package com.github.atheera.recipemanager.extras

import com.github.atheera.recipemanager.gui.exc
import java.awt.image.BufferedImage
import java.io.IOException
import javax.imageio.ImageIO
import javax.swing.ImageIcon

class LoadImage {

    fun loadImage(imageName: String) : BufferedImage? {
        val url = (this.javaClass.getResource("/images/$imageName"))
        try {
            return ImageIO.read(url)
        } catch (e: IOException) {
            exc(e)
            e.printStackTrace()
        }
        return null
    }

    fun loadIcon(iconName: String) : ImageIcon? {
        val url = (this.javaClass.getResource("/images/$iconName"))
        try {
            val image = ImageIO.read(url)
            return ImageIcon(image)
        } catch (e: IOException) {
            exc(e)
            e.printStackTrace()
        }
        return null
    }

}