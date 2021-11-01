package com.github.atheera.recipemanager.extras

import java.awt.Color
import java.awt.Dimension
import java.awt.Font
import java.awt.Graphics
import java.awt.event.FocusAdapter
import java.awt.event.FocusEvent
import javax.swing.JTextField

class HintTextField(private var hint: String) : JTextField() {
    var gainFont = Font("Tahoma", Font.PLAIN, 20)
    var lostFont = Font("Tahoma", Font.ITALIC, 20)
    var focus = false
    var minSize = hint.length*20

    init {
        minimumSize = Dimension(minSize, 24)
        text = hint
        font = lostFont
        foreground = Color.GRAY
        addFocusListener(object : FocusAdapter() {
            override fun focusGained(e: FocusEvent) {
                focus = true

                text = if (text == hint) "" else text
                font = gainFont
            }

            override fun focusLost(e: FocusEvent) {

                focus = false

                if (text == hint || text.isEmpty()) {
                    text = hint
                    font = lostFont
                    foreground = Color.GRAY
                } else {
                    text = text
                    font = gainFont
                    foreground = Color.BLACK
                }
            }
        })
    }


}