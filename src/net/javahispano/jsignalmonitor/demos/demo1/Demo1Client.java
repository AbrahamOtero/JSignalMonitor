package net.javahispano.jsignalmonitor.demos.demo1;

import net.javahispano.jsignalmonitor.*;

/**
 * <p>
 * Title: Herraienta de monitorizacion</p>
 * <p>
 * Description: </p>
 * <p>
 * Copyright: Copyright (c) 1999</p>
 *
 * * @version 0.6
 * @author Abraham Otero Quintana
 */
//Implement  the interface Client or exted class ClientAdapter
public class Demo1Client extends ClientAdapter {

    static float[][] data;

//We generate some data
    static {
        data = new float[2][1000];
        for (int i = 0; i < 1000; i++) {
            data[0][i] = (int) (350 + 150 * Math.cos(i / 20.0));
            data[1][i] = (int) (50 + 50 * Math.sin(i / 20.0));
        }
    }

    // Method invoked by the Monitor to retrieve the data to be represented
    @Override
    public Object getData(int num_canal, int primero, int ultimo) {
        float[] tmp = new float[ultimo - primero + 1];
        for (int i = 0; i < tmp.length; i++) {
            tmp[i] = Demo1Client.data[num_canal][primero + i];
        }
        return tmp;
    }

}
