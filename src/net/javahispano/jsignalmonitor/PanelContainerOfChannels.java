//Source file: E:\\JAVA2\\MonitorizacionAlgoritmo\\src\\pintar\\MyPanel.java

package net.javahispano.jsignalmonitor;

import java.util.*;

import java.awt.*;
import javax.swing.*;


/**
 *
 * @version 	0.6
 * @author 	Abraham Otero Quintana
 */
class PanelContainerOfChannels extends JPanel {
  static ResourceBundle res;
  private Channel canales[];
  private CacheDatos caches[];
  private JSignalMonitor monitor;
  private Frame frame; //rl frame sobre el cual se añadio el monitor. Para lo dialogos modales
  private Configuration configuration;

  /**
   * @param numero_canales
   * @param enable_pos
   * @param nombre_señal
   * @param leyendas
   * @param scroll
   * @param unidades_temporales@param monitor
   * @param num_canales
   * @param nombre_senal
   */
  PanelContainerOfChannels(JSignalMonitor monitor, JScrollBar scroll,
      Frame frame,
      Configuration configuration) {
    this.monitor = monitor;
    this.frame = frame;
    this.configuration = configuration;
    res = ResourceBundle.getBundle("net.javahispano.jsignalmonitor.i18n.Res",
        configuration.getLocalidad());
    int numCanales = configuration.getNumCanales();
    GridLayout layout = new GridLayout(numCanales, 1, 0, 1);
    this.setLayout(layout);
    canales = new Channel[numCanales];
    caches = new CacheDatos[numCanales];
    for (int i = 0; i < numCanales; i++) {
      caches[i] = new CacheDatos(monitor, configuration.getTamanoCache(),
          configuration.isIsCache(), i, configuration);
      canales[i] = new Channel(i, caches[i], scroll, monitor, frame,
          configuration);
      this.add(canales[i], i);
    }
  }

  /**
   * @param enable_pos
   * @param nombre_señal
   * @param leyendas
   * @param scroll
   * @param unidades_temporales
   */
  void anadeCanal(JScrollBar scroll) {
    //OJO remuevo todo ¿Sera necesaio?
    this.removeAll();
    //Creo el nuevo layout
    int numCanales = configuration.getNumCanales();
    GridLayout layout = new GridLayout(numCanales, 1, 0, 1);
    this.setLayout(layout);
    Channel[] canales_tmp = new Channel[numCanales];
    CacheDatos[] caches_tmp = new CacheDatos[numCanales];
    //Almaceno los datos antiguos
    for (int i = 0; i < numCanales - 1; i++) {
      canales_tmp[i] = canales[i];
      caches_tmp[i] = caches[i];
      this.add(canales[i], i);
    }
    //creo y almaceno el dato nuevo
    caches_tmp[numCanales - 1] = new CacheDatos(monitor,
        configuration.getTamanoCache(), configuration.isIsCache(),
        numCanales - 1, configuration);
    canales_tmp[numCanales - 1] = new Channel(numCanales - 1,
        caches_tmp[numCanales - 1], scroll, monitor, frame, configuration);
    //Anado el canal nuevo
    this.add(canales_tmp[numCanales - 1], numCanales - 1);
    canales_tmp[numCanales - 1].pideDatosDelCanal();
    //Los daotos nuevos pasan a ser los que almacenamos
    caches = caches_tmp;
    canales = canales_tmp;
  }

  /**
   * @param num_canal@param scroll
   */
  void eliminaCanal(int num_canal, JScrollBar scroll) {
    //El canal num_canal se halla en la posicion num_canal-- del array de canales
    // num_canal--;
    //OJO remuevo todo ¿Sera necesaio?
    //   System.out.println("My panel elimina a "+ num_canal);
    this.removeAll();
    //Creo el nuevo layout
    int numCanales = configuration.getNumCanales();
    GridLayout layout = new GridLayout(numCanales, 1, 0, 1);
    this.setLayout(layout);
    Channel[] canales_tmp = new Channel[numCanales];
    CacheDatos[] caches_tmp = new CacheDatos[numCanales];
    //Almaceno los canales que siguen, pasando del que se retira
    for (int i = 0; i < num_canal; i++) {
      canales_tmp[i] = canales[i];
      caches_tmp[i] = caches[i];
      this.add(canales_tmp[i]);
    }
    //Cojo todos los canales que se encuantran entre num_canal, antes num_canal
    //+1, y el numero total de canales y decremento su numero de canal asociado
    for (int i = num_canal; i < numCanales; i++) {
      canales_tmp[i] = canales[i + 1];
      caches_tmp[i] = caches[i + 1];
      canales_tmp[i].setNumeroDeCanal(i);
      this.add(canales_tmp[i], i);
    }

    scroll.removeAdjustmentListener(canales[num_canal]);

    caches = caches_tmp;
    canales = canales_tmp;
    monitor.repaint();
  }

  /**
   * @param i
   * @return Logical View::pintar::Canal[]
   */
  Channel getCanal(int i) {
    return canales[i];
  }

  /**
   * @return Logical View::pintar::Canal[]
   */
  Channel[] getCanales() {
    return canales;
  }

  /**
   * @param i
   * @return Logical View::pintar::CacheDatos[]
   */
  CacheDatos getCache(int i) {
    return caches[i];
  }

  /**
   * @param g
   */
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (configuration.isPintar()) {
      for (int i = 0; i < canales.length; i++) {
        canales[i].repaint();
      }
    }
    else {
      g.setColor(Color.white);
      Font f = new Font("Dialog", Font.BOLD, 20);
      g.setFont(f);
      g.fillRect(0, 0, this.getWidth(), this.getHeight());
      g.setColor(Color.blue);
      g.drawString(res.getString("No_se_est"), this.getWidth() / 4,
          this.getHeight() / 2 - 20);
    }

  }

  /**
   * Devuelve el Panel_Pintar de un canal
   */
  Channel getPanelPintar(int num_canl) {
    return this.canales[num_canl];
  }

  /**
   * Look anf feel
   */
  public void actulizaLookAnfFeel(String nuevo_look) {
    for (int i = 0; i < canales.length; i++) {
      canales[i].actulizaLookAndFeel(nuevo_look);
    }

  }

  /**
   * Invalida todas las caches del panel.
   */
  void invalidaCache() {
    for (int i = 0; i < caches.length; i++) {
      caches[i].invalidaCache();
    }

  }
}
