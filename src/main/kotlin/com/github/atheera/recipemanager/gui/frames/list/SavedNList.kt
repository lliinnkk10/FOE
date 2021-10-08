package com.github.atheera.recipemanager.gui.frames.list

import com.github.atheera.recipemanager.Images
import com.github.atheera.recipemanager.gui.panels.list.NewNormalListPanel
import com.github.atheera.recipemanager.loadImage
import java.awt.Dimension
import javax.swing.JFrame

class SavedNList(title: String, list: MutableList<String>) : JFrame() {

    private val cp = NewNormalListPanel()

    init {
        iconImage = loadImage(Images.icon)
        this.title = title
        cp.htfTitle.text = title
        defaultCloseOperation = DISPOSE_ON_CLOSE
        size = Dimension(500, 835)
        isVisible = true

        for(pos in list.indices) {
            val item = cp.createCard(list[pos], cp.jpList, true)
            cp.jpList.add(item, "wrap")
        }

        cp.jbSave.addActionListener {
            dispose()
        }

        add(cp)

    }

}