package com.github.atheera.recipemanager.extras

import com.github.atheera.recipemanager.*
import java.awt.Color
import java.awt.Dimension
import java.awt.Font
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import javax.swing.*

class ToolTipLabel(tip: String) : JLabel() {

    init {
        this.icon = loadIcon(Images.tooltip)
        this.size = Dimension(16, 16)
        this.minimumSize = Dimension(16, 16)
        this.maximumSize = Dimension(16, 16)

        this.addMouseListener(object: MouseListener {
            val pm = JPopupMenu()
            val jl = JLabel(tip)

            override fun mouseEntered(e: MouseEvent) {
                jl.background = LIGHT_WHITE
                jl.font = Font("Tahoma", Font.ITALIC, 16)
                pm.background = LIGHT_WHITE
                pm.border = BorderFactory.createLineBorder(Color.BLACK)
                pm.add(jl)
                //pm.preferredSize = jl.preferredSize
                pm.show(e.component, e.x, e.y)
                updateUI()
            }

            override fun mouseExited(e: MouseEvent) {
                if (!e.component.contains(e.point) && !jl.contains(e.point)) {
                    pm.isVisible = false
                    updateUI()
                }
            }

            override fun mouseClicked(e: MouseEvent?) { }
            override fun mousePressed(e: MouseEvent?) { }
            override fun mouseReleased(e: MouseEvent?) { }
        })

    }

}