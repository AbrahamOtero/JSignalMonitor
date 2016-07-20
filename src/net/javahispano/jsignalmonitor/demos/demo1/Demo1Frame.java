package net.javahispano.jsignalmonitor.demos.demo1;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


/**
 * <p>Title: Herraienta de monitorizacion</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 1999</p>
 * <p>Company: GSI</p>
 * @author Abraham Otero
 * @version 0.2
 */

public class Demo1Frame extends JFrame {
  BorderLayout borderLayout1 = new BorderLayout();
  public Demo1Frame() throws HeadlessException {

    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) throws HeadlessException {
    Demo1Frame demo1Main1 = new Demo1Frame();
    demo1Main1.setSize(300, 300);
    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    demo1Main1.setLocation( (int) (screen.getWidth() / 2 -
        demo1Main1.getWidth() / 2),
        (int) (screen.getHeight() / 2 - demo1Main1.getHeight() / 2));

    demo1Main1.setVisible(true);
  }

  private void jbInit() throws Exception {
    this.getContentPane().setLayout(borderLayout1);
    this.addWindowListener(new Demo1Main_this_windowAdapter(this));
    this.setTitle("JSignalMonitor Demo");
    this.getContentPane().add(new Demo1Panel());
  }

  void this_windowClosing(WindowEvent e) {
    System.exit(0);
  }

}


class Demo1Main_this_windowAdapter extends java.awt.event.WindowAdapter {
  Demo1Frame adaptee;

  Demo1Main_this_windowAdapter(Demo1Frame adaptee) {
    this.adaptee = adaptee;
  }

  public void windowClosing(WindowEvent e) {
    adaptee.this_windowClosing(e);
  }
}
