package net.javahispano.jsignalmonitor.demos.demo3;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import net.javahispano.jsignalmonitor.*;


/**
 * <p>Title: Herraienta de monitorizacion</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 1999</p>
 * <p>Company: GSI</p>
 * @author Abraham Otero
 * @version 0.2
 */

public class Demo3Frame extends JFrame {
  JMenuBar jMenuBar1 = new JMenuBar();
  JMenu jMenu1 = new JMenu();
  JCheckBoxMenuItem jCheckBoxMenuItem1 = new JCheckBoxMenuItem();
  JCheckBoxMenuItem jCheckBoxMenuItem2 = new JCheckBoxMenuItem();
  JCheckBoxMenuItem jCheckBoxMenuItem3 = new JCheckBoxMenuItem();
  JCheckBoxMenuItem jCheckBoxMenuItem4 = new JCheckBoxMenuItem();
  JCheckBoxMenuItem jCheckBoxMenuItem5 = new JCheckBoxMenuItem();
  Demo3Client client;
  JToolBar jToolBar1 = new JToolBar();
  JCheckBox jCheckBox1 = new JCheckBox();
  JSignalMonitor monitor;

  public Demo3Frame() {

    jbInit();
    Demo3Panel demoPanel = new Demo3Panel();
    this.getContentPane().add(demoPanel);
    client = demoPanel.getClient();
    monitor = demoPanel.getMonitor();

  }

  public static void main(String[] args) throws HeadlessException {
    Demo3Frame demo3Frame1 = new Demo3Frame();
    demo3Frame1.setSize(500, 400);
    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    demo3Frame1.setLocation( (int) (screen.getWidth() / 2 -
        demo3Frame1.getWidth() / 2),
        (int) (screen.getHeight() / 2 - demo3Frame1.getHeight() / 2));

    demo3Frame1.show();
  }

  private void jbInit() {
    this.setJMenuBar(jMenuBar1);
    this.setTitle("JSignalMonitor Demo");
    this.addWindowListener(new Demo3Frame_this_windowAdapter(this));
    jMenu1.setBackground(Color.orange);
    jMenu1.setText("Show");
    jMenuBar1.setBackground(Color.orange);
    jMenuBar1.setAlignmentY( (float) 0.0);
    jCheckBoxMenuItem1.setBackground(Color.orange);
    jCheckBoxMenuItem1.setActionCommand("0");
    jCheckBoxMenuItem1.setText("Singnal 0");
    jCheckBoxMenuItem1.addActionListener(new
        Demo3Frame_jCheckBoxMenuItem1_actionAdapter(this));
    jCheckBoxMenuItem2.setBackground(Color.orange);
    jCheckBoxMenuItem2.setActionCommand("1");
    jCheckBoxMenuItem2.setText("Signal 1");
    jCheckBoxMenuItem2.addActionListener(new
        Demo3Frame_jCheckBoxMenuItem2_actionAdapter(this));
    jCheckBoxMenuItem3.setBackground(Color.orange);
    jCheckBoxMenuItem3.setActionCommand("2");
    jCheckBoxMenuItem3.setText("Signal 2");
    jCheckBoxMenuItem3.addActionListener(new
        Demo3Frame_jCheckBoxMenuItem3_actionAdapter(this));
    jCheckBoxMenuItem4.setBackground(Color.orange);
    jCheckBoxMenuItem4.setActionCommand("3");
    jCheckBoxMenuItem4.setText("Signal 3");
    jCheckBoxMenuItem4.addActionListener(new
        Demo3Frame_jCheckBoxMenuItem4_actionAdapter(this));
    jCheckBoxMenuItem5.setBackground(Color.orange);
    jCheckBoxMenuItem5.setActionCommand("4");
    jCheckBoxMenuItem5.setText("Signal 4");
    jCheckBoxMenuItem5.addActionListener(new
        Demo3Frame_jCheckBoxMenuItem5_actionAdapter(this));
    jCheckBox1.setBackground(Color.orange);
    jCheckBox1.setText("Paint X Y values");
    jCheckBox1.addChangeListener(new Demo3Frame_jCheckBox1_changeAdapter(this));
    jToolBar1.setBackground(Color.orange);
    jMenuBar1.add(jMenu1);
    jMenu1.add(jCheckBoxMenuItem1);
    jMenu1.add(jCheckBoxMenuItem2);
    jMenu1.add(jCheckBoxMenuItem3);
    jMenu1.add(jCheckBoxMenuItem4);
    jMenu1.add(jCheckBoxMenuItem5);
    this.getContentPane().add(jToolBar1, BorderLayout.NORTH);
    jToolBar1.add(jCheckBox1, null);
  }

  void jCheckBoxMenuItem1_actionPerformed(ActionEvent e) {
    client.showSignal(Integer.parseInt(e.getActionCommand()));
  }

  void jCheckBoxMenuItem2_actionPerformed(ActionEvent e) {
    client.showSignal(Integer.parseInt(e.getActionCommand()));
  }

  void jCheckBoxMenuItem3_actionPerformed(ActionEvent e) {
    client.showSignal(Integer.parseInt(e.getActionCommand()));
  }

  void jCheckBoxMenuItem4_actionPerformed(ActionEvent e) {
    client.showSignal(Integer.parseInt(e.getActionCommand()));
  }

  void jCheckBoxMenuItem5_actionPerformed(ActionEvent e) {
    client.showSignal(Integer.parseInt(e.getActionCommand()));
  }

  void jCheckBox1_stateChanged(ChangeEvent e) {
    if (jCheckBox1.isSelected()) {
      monitor.setPaintXY(true);
    }
    else {
      monitor.setPaintXY(false);
    }
  }

  void this_windowClosing(WindowEvent e) {
    System.exit(0);
  }

}


class Demo3Frame_jCheckBoxMenuItem1_actionAdapter implements java.awt.event.
    ActionListener {
  Demo3Frame adaptee;

  Demo3Frame_jCheckBoxMenuItem1_actionAdapter(Demo3Frame adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jCheckBoxMenuItem1_actionPerformed(e);
  }
}


class Demo3Frame_jCheckBoxMenuItem2_actionAdapter implements java.awt.event.
    ActionListener {
  Demo3Frame adaptee;

  Demo3Frame_jCheckBoxMenuItem2_actionAdapter(Demo3Frame adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jCheckBoxMenuItem2_actionPerformed(e);
  }
}


class Demo3Frame_jCheckBoxMenuItem3_actionAdapter implements java.awt.event.
    ActionListener {
  Demo3Frame adaptee;

  Demo3Frame_jCheckBoxMenuItem3_actionAdapter(Demo3Frame adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jCheckBoxMenuItem3_actionPerformed(e);
  }
}


class Demo3Frame_jCheckBoxMenuItem4_actionAdapter implements java.awt.event.
    ActionListener {
  Demo3Frame adaptee;

  Demo3Frame_jCheckBoxMenuItem4_actionAdapter(Demo3Frame adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jCheckBoxMenuItem4_actionPerformed(e);
  }
}


class Demo3Frame_jCheckBoxMenuItem5_actionAdapter implements java.awt.event.
    ActionListener {
  Demo3Frame adaptee;

  Demo3Frame_jCheckBoxMenuItem5_actionAdapter(Demo3Frame adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jCheckBoxMenuItem5_actionPerformed(e);
  }
}


class Demo3Frame_jCheckBox1_changeAdapter implements javax.swing.event.
    ChangeListener {
  Demo3Frame adaptee;

  Demo3Frame_jCheckBox1_changeAdapter(Demo3Frame adaptee) {
    this.adaptee = adaptee;
  }

  public void stateChanged(ChangeEvent e) {
    adaptee.jCheckBox1_stateChanged(e);
  }
}


class Demo3Frame_this_windowAdapter extends java.awt.event.WindowAdapter {
  Demo3Frame adaptee;

  Demo3Frame_this_windowAdapter(Demo3Frame adaptee) {
    this.adaptee = adaptee;
  }

  public void windowClosing(WindowEvent e) {
    adaptee.this_windowClosing(e);
  }
}
