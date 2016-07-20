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
public class Demo2Panel extends JPanel implements java.awt.event.ActionListener {

    private JSignalMonitor monitor;
    private JPanel jPanel1 = new JPanel();
    private JButton jButton1 = new JButton();
    private Demo2Client client;
    private boolean running = false;
    private int sample = 0;
    private Thread thread;

    public Demo2Panel() {
        client = new Demo2Client();

        boolean[] pos = {false};
        String[] nomsenal = {"Cosine"};
        monitor = new JSignalMonitor(null, client, 1, pos, nomsenal, false);
        monitor.setDividerThickness(0);
        monitor.setPaint(true);

        jButton1.setBackground(Color.orange);
        jButton1.setText("Start");
        jButton1.addActionListener(this);
        this.setLayout(new BorderLayout());
        jPanel1.setBackground(Color.orange);
        this.add(jPanel1, BorderLayout.NORTH);
        jPanel1.add(jButton1, null);
        this.add(monitor, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (sample >= Demo2Client.DATA_SIZE) {
            return;
        }

        if (running) {
            running = false;
            jButton1.setText("Start");
        } else {
            running = true;
            jButton1.setText("Stop");
            thread = new Demo2Panel_jButton1_actionAdapter();
            thread.start();
        }
    }

    class Demo2Panel_jButton1_actionAdapter extends Thread {

        @Override
        public void run() {
            monitor.setRealTime(true);
            for (; sample < Demo2Client.DATA_SIZE && running; sample++) {
                client.generateData();
                monitor.increaseScrollValue(1);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            // System.out.println("saliendo " + sample);
            if (sample >= 1000) {
                jButton1.setText("Done");
            }
            monitor.setRealTime(false);
        }
    }

}
