package net.javahispano.jsignalmonitor.demos.demo3;

import net.javahispano.jsignalmonitor.*;


/**
 * <p>Title: Herraienta de monitorizacion</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 1999</p>
 * <p>Company: GSI</p>
 * @author Abraham Otero
 * @version 0.2
 */

public class Demo3Client extends ClientAdapter {

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
  float fs = 1;

  private JSignalMonitor monitor;

  public Demo3Client() {
    for (int i = 0; i < isMonitoring.length; i++) {
      isMonitoring[i] = false;
    }
  }

  /**
   * Method invoked by the Monitor to retrieve the data to be represented
   * @param num_canal int
   * @param primero int
   * @param ultimo int
   * @return Object
   */
  public Object getData(int num_canal, int primero, int ultimo) {
    float[] tmp = new float[ultimo - primero + 1];
    for (int i = 0; i < tmp.length; i++) {
      tmp[i] = data[mapChannelToSignal[num_canal]][primero + i];
    }
    return tmp;
  }

  /**
   * Shows the signal, or hides if it already was shown, represented by the argument.
   * @param numSignal int
   */
  public void showSignal(int numSignal) {
    if (!isMonitoring[numSignal]) {
      mapChannelToSignal[numOfSignalsMonitored] = numSignal;
      mapSignalToChannel[numSignal] = numOfSignalsMonitored;
      isMonitoring[numSignal] = true;
      numOfSignalsMonitored++;
      monitor.addChannel("Signal " + numSignal, rangos[numSignal], fs);
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
