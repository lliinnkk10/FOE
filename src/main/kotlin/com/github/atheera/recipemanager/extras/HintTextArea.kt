package com.github.atheera.recipemanager.extras

import java.awt.Color
import java.awt.Font
import java.awt.event.FocusAdapter
import java.awt.event.FocusEvent
import javax.swing.JTextArea
import javax.swing.JTextField

class HintTextArea(hint: String) : JTextArea() {
    var gainFont = Font("Tahoma", Font.PLAIN, 20)
    var lostFont = Font("Tahoma", Font.ITALIC, 20)

    init {
        text = hint
        font = lostFont
        foreground = Color.GRAY
        addFocusListener(object : FocusAdapter() {
            override fun focusGained(e: FocusEvent) {
                text = if (text == hint) "" else text
                font = gainFont
            }

            override fun focusLost(e: FocusEvent) {
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