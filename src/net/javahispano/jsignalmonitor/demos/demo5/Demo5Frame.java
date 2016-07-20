package net.javahispano.jsignalmonitor.demos.demo5;

import net.javahispano.jsignalmonitor.demos.demo2.*;
import java.awt.*;
import javax.swing.*;

/**
 * <p>
 * Title: Herraienta de monitorizacion</p>
 * <p>
 * Description: </p>
 * <p>
 * Copyright: Copyright (c) 1999</p>
 * <p>
 * Company: GSI</p>
 *
 * @author Abraham Otero
 * @version 0.2
 */
public class Demo5Frame extends JFrame {

    public Demo5Frame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("JSignalMonitor Demo 5");
        this.getContentPane().add(new Demo5Panel());
    }

    public static void main(String[] args) {
        Demo5Frame demo2Frame = new Demo5Frame();
        demo2Frame.setSize(750, 550);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        demo2Frame.setLocation((int) (screen.getWidth() / 2 - demo2Frame.getWidth() / 2),
                (int) (screen.getHeight() / 2 - demo2Frame.getHeight() / 2));
        demo2Frame.setVisible(true);
    }
}
