package net.javahispano.jsignalmonitor.demos.demo1;

import java.awt.*;
import java.awt.event.*;
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
public class Demo1Frame extends JFrame {

    public Demo1Frame() throws HeadlessException {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("JSignalMonitor Demo 1");
        this.getContentPane().add(new Demo1Panel());
    }

    public static void main(String[] args) throws HeadlessException {
        Demo1Frame demo1Main1 = new Demo1Frame();
        demo1Main1.setSize(600, 500);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        demo1Main1.setLocation((int) (screen.getWidth() / 2 - demo1Main1.getWidth() / 2),
                (int) (screen.getHeight() / 2 - demo1Main1.getHeight() / 2));

        demo1Main1.setVisible(true);
    }
}
