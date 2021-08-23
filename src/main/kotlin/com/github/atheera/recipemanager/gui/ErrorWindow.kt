package com.github.atheera.recipemanager.gui

import net.miginfocom.swing.MigLayout
import java.awt.Font
import javax.swing.*
import javax.swing.border.EtchedBorder
import javax.swing.border.TitledBorder

class ErrorWindow(error: String, exception: String) : JFrame() {

    private val panel = JPanel(MigLayout("align center"))
    private val errorLabel = JLabel()
    private val errorMessage = JLabel()

    init {
        title = "Error!"
        defaultCloseOperation = EXIT_ON_CLOSE
        setLocationRelativeTo(null)
        isResizable = false

        // Panel
        panel.border = TitledBorder(EtchedBorder(), "This window has opened instead of crashing.")

        // Content
        errorLabel.font = Font(Font.SANS_SERIF, Font.PLAIN, 20)
        errorLabel.text = error

        errorMessage.font = Font(Font.SANS_SERIF, Font.PLAIN, 20)
        errorMessage.text = "Please contact Atheera on Github with this error:\n$exception"

        // Adding
        this.add(panel)
        panel.add(errorLabel, "wrap")
        panel.add(errorMessage, "wrap")

        pack()
        isVisible = true
    }

}