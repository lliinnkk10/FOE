package com.github.atheera.recipemanager.gui.frames.list

import com.github.atheera.recipemanager.Images
import com.github.atheera.recipemanager.gui.panels.list.NewPCListPanel
import com.github.atheera.recipemanager.loadImage
import java.awt.Dimension
import javax.swing.*

class SavedPCList(title: String, posList: MutableList<String>, negList: MutableList<String>) : JFrame() {

    private var contentPane = NewPCListPanel()

    private var posCount = 0
    private var negCount = 0

    init {
        iconImage = loadImage(Images.icon)
        this.title = title
        contentPane.htfTitle.text = title
        defaultCloseOperation = DISPOSE_ON_CLOSE
        size = Dimension(800, 780)
        isVisible = true

        for(pos in posList.indices) {
            println(posList[pos])
            val argCard = contentPane.createCard(posList[pos], contentPane.posPane, false)
            contentPane.posPane.add(argCard, "wrap")
            posCount++

        }
        for(neg in negList.indices) {
            println(negList[neg])
            val argCard = contentPane.createCard(negList[neg], contentPane.negPane, false)
            contentPane.negPane.add(argCard, "wrap")
            negCount++
        }

        contentPane.saveBtn.addActionListener {
            dispose()
        }

        add(contentPane)

    }

}