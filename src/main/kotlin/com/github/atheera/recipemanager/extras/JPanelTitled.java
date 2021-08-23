package com.github.atheera.recipemanager.extras;

import javax.swing.*;
import java.awt.*;

public class JPanelTitled extends JPanel {

    String title;
    int count;

    public JPanelTitled(LayoutManager m, String title) {
        this.title = title;
    }

    public JPanelTitled(LayoutManager m, String title, int count) {
        this.title = title;
        this.count = count;
    }

    public JPanelTitled(String title) {
        this.title = title;
    }

}