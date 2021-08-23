package com.github.atheera.recipemanager.gui.frames.list

import com.github.atheera.recipemanager.gui.panels.list.NewTodoListPanel
import com.github.atheera.recipemanager.imageIcon
import javax.swing.JFrame

class SavedTDList(title: String, list: MutableList<String>, checkList: MutableList<String>) : JFrame() {

    private var cp = NewTodoListPanel()

    init {
        iconImage = imageIcon
        this.title = title
        cp.htfTitle.text = title
        defaultCloseOperation = DISPOSE_ON_CLOSE

        for(l in list.indices) {
            val listItem = cp.createCard(list[l])
            cp.jpList.add(listItem, "wrap")
        }

        for(l in checkList.indices) {
            val cList = cp.createCard(checkList[l])
            cList.font = cp.fontS
            cp.jpList.add(cList, "wrap")
            cList.doClick()
        }

        cp.jbSave.addActionListener {
            dispose()
        }

        add(cp)

        isVisible = true
        pack()
    }

}