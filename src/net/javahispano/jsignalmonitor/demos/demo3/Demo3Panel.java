package net.javahispano.jsignalmonitor.demos.demo3;

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

public class Demo3Panel extends JPanel {
  BorderLayout borderLayout1 = new BorderLayout();
  JSignalMonitor monitor;
  Demo3Client client;

  public Demo3Panel() {
    this.client = new Demo3Client();
    monitor = new JSignalMonitor(client);
    client.setMonitor(monitor);
    monitor.setTotalDuration(10000, true);
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

  public Demo3Client getClient() {
    return client;
  }

  public JSignalMonitor getMonitor() {
    return monitor;
  }

}
