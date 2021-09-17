package com.github.atheera.recipemanager.extras

import com.github.atheera.recipemanager.buttonCard
import com.github.atheera.recipemanager.buttonCardHovered
import com.github.atheera.recipemanager.buttonCardPressed
import com.github.atheera.recipemanager.gui.frames.recipe.SavedRecipeFrame
import com.github.atheera.recipemanager.recipePath
import java.awt.Color
import java.awt.Dimension
import java.awt.Font
import java.awt.Graphics
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.awt.event.MouseMotionListener
import java.io.File
import javax.swing.*
import javax.swing.border.EmptyBorder

class ButtonRecipeCard(
   private var title: String,
   private var cat: String,
   private var subCat: String,
   private var instructions: String,
   private var ingredients: MutableList<String>,
   private var desc: String,
   private var temperature: Int,
   private var convTemperature: Int,
   private var egg: Boolean,
   private var gluten: Boolean,
   private var lactose: Boolean,
   private var vegan: Boolean,
   private var vegetarian: Boolean)
   : JLabel() {

    private var titleSize = title.length*10

    init {
        icon = ImageIcon(buttonCard)
        minimumSize = Dimension(icon.iconWidth, icon.iconHeight)
        maximumSize = Dimension(icon.iconWidth, icon.iconHeight)

        this.addMouseListener(object: MouseListener {
            val jpm = JPopupMenu()
            val jb = JButton("Delete saved file")
            val file = File(recipePath.plus("$cat/$subCat/$title.json"))
            var pressed = false

            override fun mouseClicked(e: MouseEvent) {
                icon = ImageIcon(buttonCardPressed)
                addFunction(e, jpm, jb, file, pressed)
                icon = ImageIcon(buttonCard)
                e.consume()
            }

            override fun mousePressed(e: MouseEvent) {
                icon = ImageIcon(buttonCardPressed)
            }

            override fun mouseReleased(e: MouseEvent) {
                addFunction(e, jpm, jb, file, false)
                icon = ImageIcon(buttonCard)
                e.consume()
                pressed = true
            }

            override fun mouseEntered(e: MouseEvent) {
                icon = ImageIcon(buttonCardHovered)
            }

            override fun mouseExited(e: MouseEvent) {
                icon = ImageIcon(buttonCard)
                if (!e.component.contains(e.point) && !jb.contains(e.point)) {
                    jpm.isVisible = false
                    updateUI()
                }
            }

        })

    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        g.font = Font("Tahoma", Font.BOLD, 20)
        g.drawString(title, (250-(titleSize/2)), 55)
        g.drawString(cat, 160, 90)
        g.drawString(subCat, 290, 90)
        g.drawString(desc, 70, 130)
    }

    fun getTitle() : String {
        return title
    }

    private fun addFunction(e: MouseEvent, jpm: JPopupMenu, jb: JButton, file: File, pressed: Boolean) {
        jb.font = Font("Tahoma", Font.BOLD, 18)
        jb.background = Color.RED
        when(e.button) {
            MouseEvent.BUTTON1 -> { // Left click to open selected file
                if(pressed) return
                val srf = SavedRecipeFrame(title, cat, subCat, instructions, ingredients, desc, temperature, convTemperature, egg, gluten, lactose, vegan, vegetarian)
                srf.setLocationRelativeTo(null)
                e.consume()
            }
            MouseEvent.BUTTON3 -> { // Right click to delete selected file
                jb.addActionListener {
                    val jop = JOptionPane.showConfirmDialog(jb, "Are you sure you want to delete file: ${file.name}?", "Delete selected file", JOptionPane.YES_NO_OPTION)
                    if(jop == 0) {
                        try {
                            if(file.delete()) JOptionPane.showMessageDialog(jb, "Deleted file: ${file.name}")
                            else JOptionPane.showMessageDialog(jb, "Failed to delete file: ${file.name}")
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                    e.consume()
                    jpm.isVisible = false
                    updateUI()
                }
                jpm.border = EmptyBorder(0, 0, 0 ,0)
                jpm.add(jb)
                jpm.show(e.component, e.x, e.y)
            }
            else -> { // Ends listener if none above
                e.consume()
            }
        }
    }

}