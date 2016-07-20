package net.javahispano.jsignalmonitor.demos.demo2;

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
public class Demo2Client extends ClientAdapter {

    private float[] data = new float[1000];
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
            tmp[i] = data[primero + i];
        }
        return tmp;
    }

    public int generateData() {
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
    public int getTotaNumberOfData() {
        return currentNumberOfData;
    }
}
