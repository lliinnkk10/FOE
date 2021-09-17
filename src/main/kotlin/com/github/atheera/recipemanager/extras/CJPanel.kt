package com.github.atheera.recipemanager.extras

import net.miginfocom.swing.MigLayout
import java.awt.LayoutManager
import javax.swing.JPanel

abstract class CJPanel(lm: LayoutManager = MigLayout()) : JPanel() {

    init {
        layout = lm
    }

    abstract fun defaultLoad()

}