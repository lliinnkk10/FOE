package com.github.atheera.recipemanager.gui.frames.list

import com.github.atheera.recipemanager.Images
import com.github.atheera.recipemanager.gui.panels.list.NewTodoListPanel
import com.github.atheera.recipemanager.loadImage
import javax.swing.JFrame

class SavedTDList(title: String, list: MutableList<String>, checkList: MutableList<String>) : JFrame() {

    private var cp = NewTodoListPanel()

    init {
        iconImage = loadImage(Images.icon)
        this.title = title
        cp.htfTitle.text = title
        defaultCloseOperation = DISPOSE_ON_CLOSE

        for(l in list.indices) {
            val listItem = cp.createCard(list[l], true)
            cp.jpList.add(listItem, "wrap")
        }

        for(l in checkList.indices) {
            val cList = cp.createCard(checkList[l], true)
            cList.font = cp.fontS
            cList.doClick()
            cList.isSelected = true
            cp.jpList.add(cList, "wrap")
        }

        cp.jbSave.addActionListener {
            dispose()
        }

        add(cp)

        isVisible = true
        pack()
    }

}