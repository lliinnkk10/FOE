package counter

import net.miginfocom.swing.MigLayout
import java.awt.Color
import java.awt.Dimension
import java.awt.Font
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.time.LocalTime
import javax.swing.*
import javax.swing.border.EmptyBorder
import javax.swing.border.EtchedBorder
import com.github.atheera.recipemanager.*
import com.github.atheera.recipemanager.Icon.minus
import com.github.atheera.recipemanager.Icon.plus

fun main(args: Array<String>) {
    Counter()
}

class Counter : JFrame() {

    private val jpContent = JPanel(MigLayout())
    private val jpButtons = JPanel(MigLayout())
    private val jpList = JPanel(MigLayout())
    private val jspList = JScrollPane(jpList)

    private var amount = 1
    private val jbAdd = JLabel(loadIcon(plus))
    private val jbSub = JLabel(loadIcon(minus))
    private val jlAmount = JLabel("$amount")

    private val list = mutableListOf<JPanel>()
    private val jbDelete = JButton("Clear times")
    private val jbList = JButton("Add to list")

    private val fontPlain = Font("Tahoma", Font.PLAIN, 18)
    private val fontBold = Font("Tahoma", Font.BOLD, 20)

    init {

        jpContent.background = Color.BLACK
        jpList.background = Color.BLACK
        jpButtons.background = Color.GRAY

        title = "Pop Counter"
        defaultCloseOperation = EXIT_ON_CLOSE
        minimumSize = Dimension(300, 570)
        setLocationRelativeTo(null)
        isVisible = true
        pack()
        layout = MigLayout()

        jspList.minimumSize = Dimension(260, 400)
        jspList.verticalScrollBar.unitIncrement = 16
        jspList.verticalScrollBarPolicy = JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED

        jlAmount.font = fontBold

        // Components
            // Increase amount

        jbAdd.addMouseListener(object: MouseListener {
            override fun mouseClicked(e: MouseEvent?) {
                amount += 1
                jlAmount.text = "$amount"
                jpContent.updateUI()
            }

            override fun mouseEntered(e: MouseEvent?) {
                jbAdd.border = EtchedBorder()
            }

            override fun mouseExited(e: MouseEvent?) {
                jbAdd.border = EmptyBorder(0, 0, 0, 0)
            }

            override fun mousePressed(e: MouseEvent?) { }
            override fun mouseReleased(e: MouseEvent?) { }

        })

        jbSub.addMouseListener(object: MouseListener {
            override fun mouseClicked(e: MouseEvent?) {
                amount -= 1
                if(amount <= 0) amount = 0
                jlAmount.text = "$amount"
                jpContent.updateUI()
            }

            override fun mouseEntered(e: MouseEvent?) {
                jbSub.border = EtchedBorder()
            }

            override fun mouseExited(e: MouseEvent?) {
                jbSub.border = EmptyBorder(0, 0, 0, 0)
            }

            override fun mousePressed(e: MouseEvent?) { }
            override fun mouseReleased(e: MouseEvent?) { }

        })

            // Add to list
        jbList.addActionListener {
            val cc = creteCard(amount)
            list.add(cc)
            jpList.add(cc, "wrap")
            jpContent.updateUI()
        }
            // Remove all list
        jbDelete.addActionListener {
            list.clear()
            jpList.removeAll()
            jpContent.updateUI()
        }

            // Add to screen
        jpContent.add(jpButtons, "align center, wrap")
        jpContent.add(jspList, "align center, wrap")
        jpContent.add(jbDelete, "align center")

        jpButtons.add(jbSub, "align center, split 3")
        jpButtons.add(jlAmount)
        jpButtons.add(jbAdd, "wrap")
        jpButtons.add(jbList, "align center, wrap")

        add(jpContent, "align center")


    }

    private fun getTime() : String {
        val localTime = LocalTime.now().toString()
        val sTime = localTime.substring(0, localTime.length-10)
        val splitTime = sTime.split(":")
        val hh = splitTime[0]
        val mm = splitTime[1]
        val ss = splitTime[2]
        return "$hh:$mm:$ss"
    }

    private fun creteCard(amount: Int) : JPanel {
        val jp = JPanel(MigLayout("", "[]30[]", ""))
        val jlAmount = JLabel("$amount X")
        val jlTime = JLabel(getTime())
        jp.minimumSize = Dimension(225, 40)
        jp.border = BorderFactory.createLineBorder(Color.BLACK)
        jlAmount.font = fontBold
        jlTime.font = fontPlain


        jp.add(jlAmount, "align left")
        jp.add(jlTime, "align right")

        jpContent.updateUI()
        return jp
    }

}