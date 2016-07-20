package net.javahispano.jsignalmonitor.demos.demo4;

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

public class Demo4Panel extends JPanel {
  BorderLayout borderLayout1 = new BorderLayout();
  JSignalMonitor monitor;
  Demo4Client client;

  public Demo4Panel(JFrame frame) {
    this.client = new Demo4Client();
    String[] nameSignals = {"Signal 0"};
    boolean[] enablePos = {false};
    //We will show the first signal from the begining
    client.showSignal(0, false);
    monitor = new JSignalMonitor(frame, client, 1, enablePos, nameSignals, true);
    client.setMonitor(monitor);
    monitor.setTotalDuration(10000, true);
    monitor.setBaseDate("10:00:00 01/01/2005");
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

  public Demo4Client getClient() {
    return client;
  }

  public JSignalMonitor getMonitor() {
    return monitor;
  }

}
