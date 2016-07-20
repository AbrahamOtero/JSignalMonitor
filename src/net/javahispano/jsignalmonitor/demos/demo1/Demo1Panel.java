package net.javahispano.jsignalmonitor.demos.demo1;

import java.awt.*;
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
public class Demo1Panel extends JPanel {

    JSignalMonitor monitor;

    public Demo1Panel() {
        Client client = new Demo1Client();
        String[] nomsenal = {"Cosine", "Sine"};
        monitor = new JSignalMonitor(client, 2, nomsenal);
        float[] rango = {0, 500};
        monitor.setYAxisVisualizationRange(0, rango);
        monitor.setTotalDuration(1000, true);
        monitor.setPaint(true);

        this.setLayout(new BorderLayout());
        this.add(monitor, BorderLayout.CENTER);
    }
}
