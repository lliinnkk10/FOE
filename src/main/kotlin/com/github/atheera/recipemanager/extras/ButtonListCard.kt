package com.github.atheera.recipemanager.extras

import com.github.atheera.recipemanager.*
import com.github.atheera.recipemanager.gui.exc
import com.github.atheera.recipemanager.gui.frames.list.SavedNList
import com.github.atheera.recipemanager.gui.frames.list.SavedPCList
import com.github.atheera.recipemanager.gui.frames.list.SavedTDList
import com.github.atheera.recipemanager.save.read.ReadListPC
import com.github.atheera.recipemanager.save.read.ReadListTD
import com.github.atheera.recipemanager.save.read.ReadNormalList
import java.awt.Color
import java.awt.Dimension
import java.awt.Font
import java.awt.Graphics
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.io.File
import javax.swing.*
import javax.swing.border.EmptyBorder

class ButtonListCard(private val title: String, private val type: String) : JLabel() {

    private val titleSize = title.length*10
    private val typeSize = type.length*10

    init {
        icon = ImageIcon("button")
        minimumSize = Dimension(icon.iconWidth, icon.iconHeight)
        maximumSize = Dimension(icon.iconWidth, icon.iconHeight)

        this.addMouseListener(object: MouseListener {
            val jpm = JPopupMenu()
            val jb = JButton("Delete saved file")
            val file = File(listPath.plus("$type/$title.json"))
            var pressed = false

            override fun mouseClicked(e: MouseEvent) {
                icon = ImageIcon("button_press")
                addFunction(e, jpm, jb, file, type, pressed)
                icon = ImageIcon("button")
                e.consume()
            }

            override fun mousePressed(e: MouseEvent) {
                icon = ImageIcon("button_press")
            }

            override fun mouseReleased(e: MouseEvent) {
                addFunction(e, jpm, jb, file, type, false)
                icon = ImageIcon("button")
                e.consume()
                pressed = true
            }

            override fun mouseEntered(e: MouseEvent) {
                icon = ImageIcon("button_hovered")
            }

            override fun mouseExited(e: MouseEvent) {
                icon = ImageIcon("button")
                if(!e.component.contains(e.point) && !jb.contains(e.point)) {
                    jpm.isVisible = false
                    updateUI()
                }
            }

        })

    }

    private fun addFunction(e: MouseEvent, jpm: JPopupMenu, jb: JButton, file: File, type: String, pressed: Boolean) {
        jb.font = Font("Tahoma", Font.BOLD, 18)
        jb.background = Color.RED
        when(e.button) {
            MouseEvent.BUTTON1 -> {
                if(pressed) return
                when(type) {
                    listCategories[0] -> {
                        ReadListPC(file.name)
                        val title = listPCTitle
                        val posList = listPCPos
                        val negList = listPCNeg
                        val spcl = SavedPCList(title, posList, negList)
                        spcl.setLocationRelativeTo(this@ButtonListCard)
                    }
                    listCategories[1] -> {
                        ReadListTD(file.name)
                        val title = listTDTitle
                        val todoList = listTD
                        val checked = listTDChecked
                        val stdl = SavedTDList(title, todoList, checked)
                        stdl.setLocationRelativeTo(this@ButtonListCard)
                    }
                    listCategories[2] -> {
                        ReadNormalList(file.name)
                        val title = listNTitle
                        val list = listNList
                        val snl = SavedNList(title, list)
                        snl.setLocationRelativeTo(this@ButtonListCard)
                    }
                }
                e.consume()
            }
            MouseEvent.BUTTON3 -> {
                jb.addActionListener {
                    val jop = JOptionPane.showConfirmDialog(jb, "Are you sure you want to delete file: ${file.name}", "Delete selected file", JOptionPane.YES_NO_OPTION)
                    if(jop == 0) {
                        try {
                            if(file.delete()) JOptionPane.showMessageDialog(jb, "Deleted file: ${file.name}")
                            else JOptionPane.showMessageDialog(jb, "Failed to delete file: ${file.name}")
                        } catch (e: Exception) {
                            exc(e)
                            e.printStackTrace()
                        }
                    }
                    e.consume()
                    jpm.isVisible = false
                    updateUI()
                }
                jpm.border = EmptyBorder(0, 0, 0, 0)
                jpm.add(jb)
                jpm.show(e.component, e.x, e.y)
            }
            else -> {
                e.consume()
            }

        }
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        g.font = Font("Tahoma", Font.BOLD, 20)
        g.drawString(title, (250-(titleSize/2)), (icon.iconHeight/2)-50)
        g.drawString(type, (250-(typeSize/2)), (icon.iconHeight/2)+50)
    }

}