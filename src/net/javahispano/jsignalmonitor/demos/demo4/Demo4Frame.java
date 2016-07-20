package net.javahispano.jsignalmonitor.demos.demo4;

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

public class Demo4Frame extends JFrame {
  JMenuBar jMenuBar1 = new JMenuBar();
  JMenu jMenu1 = new JMenu();
  JCheckBoxMenuItem jCheckBoxMenuItem1 = new JCheckBoxMenuItem();
  JCheckBoxMenuItem jCheckBoxMenuItem2 = new JCheckBoxMenuItem();
  JCheckBoxMenuItem jCheckBoxMenuItem3 = new JCheckBoxMenuItem();
  JCheckBoxMenuItem jCheckBoxMenuItem4 = new JCheckBoxMenuItem();
  JCheckBoxMenuItem jCheckBoxMenuItem5 = new JCheckBoxMenuItem();
  Demo4Client client;
  JToolBar jToolBar1 = new JToolBar();
  JCheckBox jCheckBox1 = new JCheckBox();
  JSignalMonitor monitor;
  JCheckBox jCheckBox2 = new JCheckBox();

  public Demo4Frame() {

    jbInit();
    Demo4Panel demoPanel = new Demo4Panel(this);
    this.getContentPane().add(demoPanel);
    client = demoPanel.getClient();
    monitor = demoPanel.getMonitor();

  }

  public static void main(String[] args) throws HeadlessException {
    Demo4Frame demo4Frame = new Demo4Frame();
    demo4Frame.setSize(800, 600);
    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    demo4Frame.setLocation( (int) (screen.getWidth() / 2 -
        demo4Frame.getWidth() / 2),
        (int) (screen.getHeight() / 2 - demo4Frame.getHeight() / 2));

    demo4Frame.show();
  }

  private void jbInit() {
    this.setJMenuBar(jMenuBar1);
    this.setTitle("JSignalMonitor Demo");
    this.addWindowListener(new Demo4Frame_this_windowAdapter(this));
    jMenu1.setBackground(Color.orange);
    jMenu1.setText("Show");
    jMenuBar1.setBackground(Color.orange);
    jMenuBar1.setAlignmentY( (float) 0.0);
    jCheckBoxMenuItem1.setBackground(Color.orange);
    jCheckBoxMenuItem1.setActionCommand("0");
    jCheckBoxMenuItem1.setSelected(true);
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
    jCheckBox1.setActionCommand("jCheckBox1");
    jCheckBox1.setText("Paint X Y values");
    jCheckBox1.addChangeListener(new Demo3Frame_jCheckBox1_changeAdapter(this));
    jToolBar1.setBackground(Color.orange);
    jCheckBox2.setBackground(Color.orange);
    jCheckBox2.setText("Highlight high values");
    jCheckBox2.addChangeListener(new Demo4Frame_jCheckBox2_changeAdapter(this));
    jMenuBar1.add(jMenu1);
    jMenu1.add(jCheckBoxMenuItem1);
    jMenu1.add(jCheckBoxMenuItem2);
    jMenu1.add(jCheckBoxMenuItem3);
    jMenu1.add(jCheckBoxMenuItem4);
    jMenu1.add(jCheckBoxMenuItem5);
    this.getContentPane().add(jToolBar1, BorderLayout.NORTH);
    jToolBar1.add(jCheckBox1, null);
    jToolBar1.add(jCheckBox2, null);
  }

  void jCheckBoxMenuItem1_actionPerformed(ActionEvent e) {
    client.showSignal(Integer.parseInt(e.getActionCommand()),
        jCheckBox2.isSelected());
  }

  void jCheckBoxMenuItem2_actionPerformed(ActionEvent e) {
    client.showSignal(Integer.parseInt(e.getActionCommand()),
        jCheckBox2.isSelected());
  }

  void jCheckBoxMenuItem3_actionPerformed(ActionEvent e) {
    client.showSignal(Integer.parseInt(e.getActionCommand()),
        jCheckBox2.isSelected());
  }

  void jCheckBoxMenuItem4_actionPerformed(ActionEvent e) {
    client.showSignal(Integer.parseInt(e.getActionCommand()),
        jCheckBox2.isSelected());
  }

  void jCheckBoxMenuItem5_actionPerformed(ActionEvent e) {
    client.showSignal(Integer.parseInt(e.getActionCommand()),
        jCheckBox2.isSelected());
  }

  void jCheckBox1_stateChanged(ChangeEvent e) {
    if (jCheckBox1.isSelected()) {
      monitor.setPaintXY(true);
    }
    else {
      monitor.setPaintXY(false);
    }
  }

  void jCheckBox2_stateChanged(ChangeEvent e) {
    if (jCheckBox2.isSelected()) {
      monitor.setPaintAllPossibilities(true);
    }
    else {
      monitor.setPaintAllPossibilities(false);
    }
  }

  void this_windowClosing(WindowEvent e) {
    System.exit(0);
  }

}


class Demo3Frame_jCheckBoxMenuItem1_actionAdapter implements java.awt.event.
    ActionListener {
  Demo4Frame adaptee;

  Demo3Frame_jCheckBoxMenuItem1_actionAdapter(Demo4Frame adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jCheckBoxMenuItem1_actionPerformed(e);
  }
}


class Demo3Frame_jCheckBoxMenuItem2_actionAdapter implements java.awt.event.
    ActionListener {
  Demo4Frame adaptee;

  Demo3Frame_jCheckBoxMenuItem2_actionAdapter(Demo4Frame adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jCheckBoxMenuItem2_actionPerformed(e);
  }
}


class Demo3Frame_jCheckBoxMenuItem3_actionAdapter implements java.awt.event.
    ActionListener {
  Demo4Frame adaptee;

  Demo3Frame_jCheckBoxMenuItem3_actionAdapter(Demo4Frame adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jCheckBoxMenuItem3_actionPerformed(e);
  }
}


class Demo3Frame_jCheckBoxMenuItem4_actionAdapter implements java.awt.event.
    ActionListener {
  Demo4Frame adaptee;

  Demo3Frame_jCheckBoxMenuItem4_actionAdapter(Demo4Frame adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jCheckBoxMenuItem4_actionPerformed(e);
  }
}


class Demo3Frame_jCheckBoxMenuItem5_actionAdapter implements java.awt.event.
    ActionListener {
  Demo4Frame adaptee;

  Demo3Frame_jCheckBoxMenuItem5_actionAdapter(Demo4Frame adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jCheckBoxMenuItem5_actionPerformed(e);
  }
}


class Demo3Frame_jCheckBox1_changeAdapter implements javax.swing.event.
    ChangeListener {
  Demo4Frame adaptee;

  Demo3Frame_jCheckBox1_changeAdapter(Demo4Frame adaptee) {
    this.adaptee = adaptee;
  }

  public void stateChanged(ChangeEvent e) {
    adaptee.jCheckBox1_stateChanged(e);
  }
}


class Demo4Frame_jCheckBox2_changeAdapter implements javax.swing.event.
    ChangeListener {
  Demo4Frame adaptee;

  Demo4Frame_jCheckBox2_changeAdapter(Demo4Frame adaptee) {
    this.adaptee = adaptee;
  }

  public void stateChanged(ChangeEvent e) {
    adaptee.jCheckBox2_stateChanged(e);
  }
}


class Demo4Frame_this_windowAdapter extends java.awt.event.WindowAdapter {
  Demo4Frame adaptee;

  Demo4Frame_this_windowAdapter(Demo4Frame adaptee) {
    this.adaptee = adaptee;
  }

  public void windowClosing(WindowEvent e) {
    adaptee.this_windowClosing(e);
  }
}
