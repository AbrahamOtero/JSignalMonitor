package net.javahispano.jsignalmonitor.demos.demo5;

import BITalino.BITalino;
import BITalino.BITalinoException;
import BITalino.Frame;
import java.io.IOException;
import java.util.Vector;
import javax.bluetooth.RemoteDevice;
import net.javahispano.jsignalmonitor.demos.demo2.*;
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
public class Demo5Client extends ClientAdapter {

    public final static int DATA_SIZE= 1000000;
    public final static int NUMBER_OF_SIGNALS = 2;
    private float[][] data = new float[NUMBER_OF_SIGNALS][DATA_SIZE];
    private int currentNumberOfData = -1;

    /**
     * Method invoked by the Monitor to retrieve the data to be represented
     *
     * @param numCanal int
     * @param primero int
     * @param ultimo int
     * @return Object
     */
    @Override
    public Object getData(int numCanal, int primero, int ultimo) {
        if (currentNumberOfData == -1) {
            return null;
        }
        float[] tmp = new float[ultimo - primero + 1];
        for (int i = 0; i < tmp.length; i++) {
            tmp[i] = data[numCanal][primero + i];
        }
        return tmp;
    }

    public int addData() {
        if (currentNumberOfData < data.length - 1) {
            currentNumberOfData++;
            data[currentNumberOfData] = (int) (50 + 50 * Math.cos(currentNumberOfData / 20.0));
        }
        return currentNumberOfData;
    }

    /**
     * Method invoked by the Monitor to find out the total amount of allowable
     * data
     *
     * @return int
     */
    @Override
    public int getTotaNumberOfData() {
        return currentNumberOfData;
    }
    
}
