package net.javahispano.jsignalmonitor.demos.demo1;

import java.awt.*;
import javax.swing.*;

import net.javahispano.jsignalmonitor.*;


/**
 * <p>Title: Herraienta de monitorizacion</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 1999</p>
 * <p>Company: GSI</p>
 * @author Abraham Otero
 * @version 0.2
 */

public class Demo1Panel extends JPanel {
  BorderLayout borderLayout1 = new BorderLayout();
  JSignalMonitor monitor;

  public Demo1Panel() {
    Client client = new Demo1Client();
    boolean[] pos = {false, false};
    String[] nomsenal = {"Cos", "Sin"};
    // float[] range = {-1,1};
    monitor = new JSignalMonitor(null, client, 2, pos, nomsenal, false);
    monitor.setTotalDuration(1000, true);
    //   monitor.setRange(0, range);
//    monitor.setRange(1, range);
    //monitor.setDividerThickness(0);
    monitor.setPaint(true);
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    this.setLayout(borderLayout1);
    this.add(monitor, BorderLayout.CENTER);
  }
}
