package net.javahispano.jsignalmonitor.demos.demo2;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import net.javahispano.jsignalmonitor.*;

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
public class Demo2Panel extends JPanel {

    JSignalMonitor monitor;
    JPanel jPanel1 = new JPanel();
    JButton jButton1 = new JButton();
    Demo2Client client;
    BorderLayout borderLayout1 = new BorderLayout();

    public Demo2Panel() {
        client = new Demo2Client();

        boolean[] pos = {false};
        String[] nomsenal = { "Cos"};
        monitor = new JSignalMonitor(null, client, 1, pos, nomsenal, false);
        monitor.setDividerThickness(0);
        monitor.setPaint(true);
   
        jButton1.setBackground(Color.orange);
        jButton1.setText("Start");
        jButton1.addActionListener(new Demo2Panel_jButton1_actionAdapter());
        this.setLayout(borderLayout1);
        jPanel1.setBackground(Color.orange);
        this.add(jPanel1, BorderLayout.NORTH);
        jPanel1.add(jButton1, null);
        this.add(monitor, BorderLayout.CENTER);
    }

    class Demo2Panel_jButton1_actionAdapter extends Thread implements java.awt.event.ActionListener {

        Demo2Panel adaptee;        
        public void run() {
            monitor.setRealTime(true);
            for (int i = 0; i < 1000; i++) {
                client.generateData();
                monitor.increaseScrollValue(1);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            monitor.setRealTime(false);
        }

        public void actionPerformed(ActionEvent e) {
            this.start();
        }
    }
}
