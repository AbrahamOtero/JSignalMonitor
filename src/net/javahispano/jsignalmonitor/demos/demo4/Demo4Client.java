package net.javahispano.jsignalmonitor.demos.demo4;

import java.util.*;

import net.javahispano.jsignalmonitor.*;
import net.javahispano.jsignalmonitor.annotations.*;


/**
 * <p>Title: Herraienta de monitorizacion</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 1999</p>
 * <p>Company: GSI</p>
 * @author Abraham Otero
 * @version 0.2
 */

public class Demo4Client extends ClientAdapter {

//Just some data
  static float[][] data;
  static {
    data = new float[5][10000];
    for (int i = 0; i < 10000; i++) {
      data[0][i] = (int) (50 + 50 * Math.cos(i / 20.0));
      data[1][i] = (int) (50 + 50 * Math.sin(i / 20.0));
      data[2][i] = (float) Math.random();
      data[3][i] = (i / 10);
      data[4][i] = (data[0][i] + data[1][i]);
    }
  }

//This will store marks and annotations
  LinkedList listAnnotations = new LinkedList();
  LinkedList[] listsMarks = new LinkedList[5];
//Indcates which channels are being shown
  boolean[] isMonitoring = new boolean[5];

//Iindcates in which positions is shown each channel
  int[] mapChannelToSignal = new int[5];
  int[] mapSignalToChannel = new int[5];

//Indcate how many signals are being shown
  int numOfSignalsMonitored = 0;

//Ranges of DiferentChannels
  float[][] rangos = { {0, 100}
      , {0, 100}
      , {0, 1}
      , {0, 1000}
      , {0, 200}
  };

//Sampling frecuenci of the signals. Ot does not needs to be the same for all the signals
  float[] fs = {1, 2, 1, 1, 10};

  private JSignalMonitor monitor;

  public Demo4Client() {
    for (int i = 0; i < 5; i++) {
      isMonitoring[i] = false;
      listsMarks[i] = new LinkedList();
    }
  }

  /**
   * Method invoked by the Monitor to retrieve the data to be represented
   */
  public Object getData(int num_canal, int primero, int ultimo) {
    float[] tmp = new float[ultimo - primero + 1];
    for (int i = 0; i < tmp.length; i++) {
      tmp[i] = data[mapChannelToSignal[num_canal]][primero + i];
    }
    return tmp;
  }

  /**
   * Method invoked by the Monitor to retrieve the data to be represented
   */
  public byte[] getPossibility(int num_canal, int primero, int ultimo) {
    byte[] tmp = new byte[ultimo - primero + 1];
    for (int i = 0; i < tmp.length; i++) {
      tmp[i] = (byte) Math.min(Math.max(data[mapChannelToSignal[num_canal]][
          primero + i] - 50, 0), 100);
    }
    return tmp;
  }

  public void addAnnotation(Annotation annotation) {
    this.listAnnotations.add(annotation);
  }

  public void anadMark(int channel, Mark mark) {
    this.listsMarks[channel].add(mark);
  }

  public void deleteAnnotation(Annotation annotation) {
    this.listAnnotations.remove(annotation);
  }

  public void deleteMark(int channel, Mark annotation) {
    this.listsMarks[channel].remove(annotation);
  }

  public LinkedList getMarks(int channel, int first, int last) {
    LinkedList reply = new LinkedList();
    ListIterator iterator = listsMarks[channel].listIterator();
    while (iterator.hasNext()) {
      Mark mark = (Mark) iterator.next();
      int timeAnnotation = mark.getStartTime();
      //Select only the ones which are visuaized
      if (first <= timeAnnotation && timeAnnotation <= last) {
        reply.add(mark);
      }
    }
    return reply;
  }

  public LinkedList getAnnotations(int first, int last) {
    LinkedList reply = new LinkedList();
    ListIterator iterator = listAnnotations.listIterator();
    while (iterator.hasNext()) {
      Annotation annotation = (Annotation) iterator.next();
      int timeAnnotation = annotation.getStartTime();
      //Select only the ones which are visuaized
      if (first <= timeAnnotation && timeAnnotation <= last) {
        reply.add(annotation);
      }
    }
    return reply;
  }

  /**
   * Shows the signal, or hides if it already was shown, represented by the argument.
   * @param numSignal int
   */
  public void showSignal(int numSignal, boolean paintPossibility) {
    if (!isMonitoring[numSignal]) {
      mapChannelToSignal[numOfSignalsMonitored] = numSignal;
      mapSignalToChannel[numSignal] = numOfSignalsMonitored;
      isMonitoring[numSignal] = true;
      numOfSignalsMonitored++;
      if (monitor != null) {
        monitor.addChannel("Signal " + numSignal, rangos[numSignal],
            fs[numSignal]);
        monitor.setPaintAllPossibilities(paintPossibility);
      }

    }
    else {
      numOfSignalsMonitored--;
      monitor.removeChannel(mapSignalToChannel[numSignal]);
      int eliminatedChannel = mapSignalToChannel[numSignal];
      mapSignalToChannel[numSignal] = -1;
      mapSignalToChannel[numSignal] = -1;
      isMonitoring[numSignal] = false;
      for (int i = 0; i < data.length; i++) {
        //If this signal was monitored in a channel over the one eliminated
        if (mapSignalToChannel[i] > eliminatedChannel) {
          mapSignalToChannel[i] = mapSignalToChannel[i] - 1;
          mapChannelToSignal[mapSignalToChannel[i]] = i;
        }
      }
    }
  }

  public void setMonitor(JSignalMonitor monitor) {
    this.monitor = monitor;
  }
}
