package net.javahispano.jsignalmonitor.demos.demo5;

import BITalino.BITalino;
import BITalino.BITalinoException;
import BITalino.Frame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import net.javahispano.jsignalmonitor.demos.demo2.*;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.bluetooth.RemoteDevice;
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
public class Demo5Panel extends JPanel implements java.awt.event.ActionListener {

    private JSignalMonitor monitor;
    private JPanel jPanel1 = new JPanel();
    private JButton jButton1 = new JButton();
    private Demo5Client client;
    private boolean running = false;
    private int sample = 0;
    private Thread thread;

    BITalino device = new BITalino();

    public Demo5Panel() {
        client = new Demo5Client();

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
        if (sample >= Demo5Client.DATA_SIZE) {
            return;
        }

        if (running) {
            try {
                // stop acquisition
                device.stop();
                //close bluetooth connection
                device.close();
                running = false;
                jButton1.setText("Start");
            } catch (BITalinoException be) {
                be.printStackTrace();

            }
        } else {
            try {
                running = true;
                jButton1.setText("Stop");

                // connect to BITalino device
                String macAddress = "98:D3:31:B2:C1:32";
                int SamplingRate = 100;
                device.open(macAddress, SamplingRate);

                // start acquisition on analog channels 0 and 4
                int[] aChannels = {2, 4};
                device.start(aChannels);

                thread = new Demo2Panel_jButton1_actionAdapter();
                thread.start();
            } catch (BITalinoException be) {
                be.printStackTrace();

            } catch (Throwable ex) {
                ex.printStackTrace();
            }
        }
    }

    class Demo2Panel_jButton1_actionAdapter extends Thread {

        @Override
        public void run() {
            monitor.setRealTime(true);
            try {
                for (; sample < 100&&running; sample++) {
                    //read 300 samples
                    for (int j = 0; j < 10; j++) {

                        Frame[] Pepe;
                        Pepe = device.read(10);

                        System.out.println("tamaño: " + Pepe.length);

                        for (int i = 0; i < Pepe.length; i++) {
                            System.out.println((j * 10 + i) + " seq: " + Pepe[i].seq + " "
                                    + Pepe[i].analog[0] + " "
                                    + Pepe[i].analog[1] + " "
                                    + Pepe[i].analog[2] + " "
                                    + Pepe[i].analog[3] + " "
                                    + Pepe[i].analog[4] + " "
                                    + Pepe[i].analog[5]);

                        }

                    }
                    monitor.increaseScrollValue(1);
                }

            } catch (BITalinoException ex) {
                Logger.getLogger(Demo5Panel.class.getName()).log(Level.SEVERE, null, ex);
            }
            // System.out.println("saliendo " + sample);
            if (sample >= 1000) {
                jButton1.setText("Done");
            }
            monitor.setRealTime(false);
        }
    }

}
