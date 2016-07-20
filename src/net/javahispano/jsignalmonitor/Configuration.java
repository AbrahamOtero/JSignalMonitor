package net.javahispano.jsignalmonitor;

import java.util.*;

import java.awt.*;


/**
 * En esta case se almacenan una gran catidad de parametrod de configuración del monitor.
 * Aunque es posible modificar alguno de ellos directamente no se recomienda, siendo
 * la forma adecuada de hacerlo mediante los métodos de la clase Monitor.
 * No esta esta clse diseñada para soportar la configuración de más de 20 canales, número
 * más que debería ser más qeu suficente teniendo en cuenta que todos han de estar dibujados en pantalla.
 *
 * @version 	0.6
 * @author 	Abraham Otero Quintana
 */
public class Configuration {
  private int num_canales;
  private int margenIzquierdo = 100;
  private int margenAbajo = 20;
  private int margenArriba = 10;
  private int margenDerecho = 10;

  /**
   * Tomará valor true cuando el valor de duracion_tatal_registro sea realmente la
   * duración total del registro, y el valor false cuando sea el último dato
   * monitorizado.
   */
  private boolean duracionReal;
  private String nombreSenales[] = {"Signal name", "Signal name", "Signal name",
      "Signal name", "Signal name", "Signal name", "Signal name", "Signal name",
      "Signal name", "Signal name", "Signal name", "Signal name", "Signal name",
      "Signal name", "Signal name", "Signal name", "Signal name", "Signal name",
      "Signal name", "Signal name", "Signal name", "Signal name", "Signal name",
      "Signal name", "Signal name"};
  private String leyendasSenales[][] = { {"label_1", "label_2", "label_3"}
      , {"label_1", "label_2", "label_3"}
      , {"label_1", "label_2", "label_3"}
      , {"label_1", "label_2", "label_3"}
      , {"label_1", "label_2", "label_3"}
      , {"label_1", "label_2", "label_3"}
      , {"label_1", "label_2", "label_3"}
      , {"label_1", "label_2", "label_3"}
      , {"label_1", "label_2", "label_3"}
      , {"label_1", "label_2", "label_3"}
      , {"label_1", "label_2", "label_3"}
      , {"label_1", "label_2", "label_3"}
      , {"label_1", "label_2", "label_3"}
      , {"label_1", "label_2", "label_3"}
      , {"label_1", "label_2", "label_3"}
      , {"label_1", "label_2", "label_3"}
      , {"label_1", "label_2", "label_3"}
      , {"label_1", "label_2", "label_3"}
      , {"label_1", "label_2", "label_3"}
      , {"label_1", "label_2", "label_3"}
  };
  private float rango_senales[][] = { {0, 100}
      , {0, 100}
      , {0, 100}
      , {0, 100}
      , {0, 100}
      , {0, 100}
      , {0, 100}
      , {0, 100}
      , {0, 100}
      , {0, 100}
      , {0, 100}
      , {0, 100}
      , {0, 100}
      , {0, 100}
      , {0, 100}
      , {0, 100}
      , {0, 100}
      , {0, 100}
      , {0, 100}
      , {0, 100}
      , {0, 100}
      , };
  private int tamano_cache = 1500;
  private boolean isCache = true;
  private Fecha fechaInicio;
  private boolean pintar_anotaciones = false;
  private boolean paintMarks = false;
  private boolean pintar_anotaciones_medidas = false;
  private boolean pintando_X_Y = false;

  /**
   * Esta será la duración total del registro. En caso de no estar disponible será
   * la duracción actual monitorizada.
   */
  private int duracionRegistro;
  private boolean pintar;
  private Fecha fechaFin;
  private float[] fs = {1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F,
      1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F};
  private float fsGlobal = 1.0F;
  private int escaladoTemporal = 1;

  private Dimension tamano_canal;
  private Color colorCanal[] = {Color.black, Color.black, Color.black,
      Color.black, Color.black, Color.black, Color.black, Color.black,
      Color.black, Color.black, Color.black, Color.black, Color.black,
      Color.black, Color.black, Color.black, Color.black, Color.black,
      Color.black, Color.black, Color.black, Color.black};
  private boolean enablePosboolean[] = new boolean[20];
  private boolean pintar_rango_como_leyenda = true;
  private float[][] intervaloDivisionesPrincipales = { {5, 5}
      , {5, 5}
      , {5, 5}
      , {5, 5}
      , {5, 5}
      , {5, 5}
      , {5, 5}
      , {5, 5}
      , {5, 5}
      , {5, 5}
      , {5, 5}
      , {5, 5}
      , {5, 5}
      , {5, 5}
      , {5, 5}
      , {5, 5}
      , {5, 5}
      , {5, 5}
      , {5, 5}
      , {5, 5}
  };
  ;
  private float[][] intervaloDivisionesSecundarias = { {3, 3}
      , {3, 3}
      , {3, 3}
      , {3, 3}
      , {3, 3}
      , {3, 3}
      , {3, 3}
      , {3, 3}
      , {3, 3}
      , {3, 3}
      , {3, 3}
      , {3, 3}
      , {3, 3}
      , {3, 3}
      , {3, 3}
      , {3, 3}
      , {3, 3}
      , {3, 3}
      , {3, 3}
      , {3, 3}
  };
  private boolean[][] divisionesSecundarias = { {true, false}
      , {true, false}
      , {true, false}
      , {true, false}
      , {true, false}
      , {true, false}
      , {true, false}
      , {true, false}
      , {true, false}
      , {true, false}
      , {true, false}
      , {true, true}
      , {true, true}
      , {true, true}
      , {true, true}
      , {true, true}
      , {true, true}
      , {true, true}
      , {true, true}
      , {true, true}
  };
  ;

  /**
   * Se eempla para determinar el tipo de grid
   */
  private int[] tipoGrid = {Client.GRID_DENSE, Client.GRID_DENSE,
      Client.GRID_DENSE, Client.GRID_DENSE, Client.GRID_DENSE,
      Client.GRID_DENSE, Client.GRID_DENSE, Client.GRID_DENSE,
      Client.GRID_DENSE, Client.GRID_DENSE, Client.GRID_DENSE,
      Client.GRID_DENSE, Client.GRID_DENSE, Client.GRID_DENSE,
      Client.GRID_DENSE, Client.GRID_DENSE, Client.GRID_DENSE,
      Client.GRID_DENSE, Client.GRID_DENSE, Client.GRID_DENSE,
      Client.GRID_DENSE, Client.GRID_DENSE};

  /**
   *Indica de que color se ha de sombrear el canal al hacer una marca, medida, estadístico...
   */
  private Color colorDeFondo = Color.green;

  private Locale localidad;

  //Indica si emplear una fecha o un int para referenciar a cada instante temporal
  private boolean usarFecha = false;

  public Configuration() {

  }

  public Color getColorCanal(int numChannel) {
    return colorCanal[numChannel];
  }

  public void setColorCanal(int numChannel, Color colorCanal) {
    this.colorCanal[numChannel] = colorCanal;
  }

  public void setColorDeFondo(Color colorDeFondo) {
    this.colorDeFondo = colorDeFondo;
  }

  public Color getColorDeFondo() {
    return colorDeFondo;
  }

  public boolean[][] getDivisionesSecundarias() {
    return divisionesSecundarias;
  }

  public void setDivisionesSecundarias(boolean[][] divisionesSecundarias) {
    this.divisionesSecundarias = divisionesSecundarias;
  }

  public void setDivisionSecundari(int canal, boolean[] divisionSecundaria) {
    this.divisionesSecundarias[canal] = divisionSecundaria;
  }

  public void setDuracionRegistro(int duracionRegistro) {
    this.duracionRegistro = duracionRegistro;
  }

  public int getDuracionRegistro() {
    return duracionRegistro;
  }

  public boolean[] isPaintPossibility() {
    return enablePosboolean;
  }

  public boolean isPaintPossibility(int channel) {
    return enablePosboolean[channel];
  }

  public void setPaintPossibility(boolean[] enablePosboolean) {
    this.enablePosboolean = enablePosboolean;
  }

  public void setEnablePosboolean(int canal, boolean enablePosboolean) {
    this.enablePosboolean[canal] = enablePosboolean;
  }

  public void setEscaladoTemporal(int escaladoTemporal) {
    this.escaladoTemporal = escaladoTemporal;
  }

  public int getEscaladoTemporal() {
    return escaladoTemporal;
  }

  public Fecha getFechaFin() {
    return fechaFin;
  }

  public void setFechaFin(Fecha fechaFin) {
    this.fechaFin = fechaFin;
  }

  public void setFechaInicio(Fecha fechaInicio) {
    this.fechaInicio = fechaInicio;
  }

  public Fecha getFechaInicio() {
    return fechaInicio;
  }

  public float getFs(int channelNumber) {
    return fs[channelNumber];
  }

  public void setFs(float[] fs) {
    this.fs = fs;
  }

  public void setFs(int senal, float fs) {
    this.fs[senal] = fs;
  }

  public void setFsGlobal(float fsGlobal) {
    this.fsGlobal = fsGlobal;
  }

  public void setIntervaloDivisionesPrincipales(float[][]
      intervaloDivisionesPrincipales) {
    this.intervaloDivisionesPrincipales = intervaloDivisionesPrincipales;
  }

  public void setIntervaloDivisionPrincipal(int canal,
      float[] intervaloDivisionesPrincipal) {
    this.intervaloDivisionesPrincipales[canal] = intervaloDivisionesPrincipal;
  }

  public void setIntervaloDivisionesSecundarias(float[][]
      intervaloDivisionesSecundarias) {
    this.intervaloDivisionesSecundarias = intervaloDivisionesSecundarias;
  }

  public void setIntervaloDivisionSecundaria(int canal,
      float[] intervaloDivisionesSecundaria) {
    this.intervaloDivisionesSecundarias[canal] = intervaloDivisionesSecundaria;
  }

  public void setIsCache(boolean isCache) {
    this.isCache = isCache;
  }

  public void setDuracionReal(boolean duracionReal) {
    this.duracionReal = duracionReal;
  }

  public void setLocalidad(Locale localidad) {
    this.localidad = localidad;
  }

  public void setMargenAbajo(int margenAbajo) {
    this.margenAbajo = margenAbajo;
  }

  public void setMargenArriba(int margenArriba) {
    this.margenArriba = margenArriba;
  }

  public void setMargenDerecho(int margenDerecho) {
    this.margenDerecho = margenDerecho;
  }

  public void setMargenIzquierdo(int margenIzquierdo) {
    this.margenIzquierdo = margenIzquierdo;
  }

  public void setNombreSenales(String[] nombreSenales) {
    this.nombreSenales = nombreSenales;
  }

  public void setNombreSenales(int senal, String nombreSenal) {
    nombreSenales[senal] = nombreSenal;
  }

  public void setNum_canales(int num_canales) {
    this.num_canales = num_canales;
  }

  public int getNumCanales() {
    return num_canales;
  }

  public String[] getNombreSenales() {
    return nombreSenales;
  }

  public int getMargenIzquierdo() {
    return margenIzquierdo;
  }

  public int getMargenDerecho() {
    return margenDerecho;
  }

  public int getMargenArriba() {
    return margenArriba;
  }

  public int getMargenAbajo() {
    return margenAbajo;
  }

  public Locale getLocalidad() {
    if (localidad == null) {
      return Locale.getDefault();
    }
    return localidad;
  }

  public boolean isDuracionReal() {
    return duracionReal;
  }

  public boolean isIsCache() {
    return isCache;
  }

  public float[][] getIntervaloDivisionesSecundarias() {
    return intervaloDivisionesSecundarias;
  }

  public float[][] getIntervaloDivisionesPrincipales() {
    return intervaloDivisionesPrincipales;
  }

  public float getFsGlobal() {
    return fsGlobal;
  }

  public boolean isPintandoXY() {
    return pintando_X_Y;
  }

  public boolean isPintar() {
    return pintar;
  }

  public boolean isPintarAnotaciones() {
    return pintar_anotaciones;
  }

  public boolean isPintarAnotacionesMedidas() {
    return pintar_anotaciones_medidas;
  }

  public boolean isPintarRangoComoLeyenda() {
    return pintar_rango_como_leyenda;
  }

  public float[][] getRangoSenales() {
    return rango_senales;
  }

  public int getTamanoCache() {
    return tamano_cache;
  }

  public Dimension getTamanoCanal() {
    return tamano_canal;
  }

  public int[] getTipoGrid() {
    return tipoGrid;
  }

  public int getTipoGrid(int canal) {
    return tipoGrid[canal];
  }

  public void setTipoGrid(int[] TIPO_GRID) {
    this.tipoGrid = TIPO_GRID;
  }

  public void setTipoGrid(int canal, int TIPO_GRID) {
    this.tipoGrid[canal] = TIPO_GRID;
  }

  public void setTamanoCanal(Dimension tamano_canal) {
    this.tamano_canal = tamano_canal;
  }

  public void setTamanoCache(int tamano_cache) {
    this.tamano_cache = tamano_cache;
  }

  public void setRangoSenales(float[][] rango_senales) {
    this.rango_senales = rango_senales;
  }

  public void setRangoSenales(int senal, float[] rango_senal) {
    this.rango_senales[senal] = rango_senal;
  }

  public void setPintarRangoComoLeyenda(boolean pintar_rango_como_leyenda) {
    this.pintar_rango_como_leyenda = pintar_rango_como_leyenda;
  }

  public void setPintarAnotacionesMedidas(boolean pintar_anotaciones_medidas) {
    this.pintar_anotaciones_medidas = pintar_anotaciones_medidas;
  }

  public void setPintarAnotaciones(boolean pintar_anotaciones) {
    this.pintar_anotaciones = pintar_anotaciones;
  }

  public void setPintar(boolean pintar) {
    this.pintar = pintar;
  }

  public void setPintandoXY(boolean pintando_X_Y) {
    this.pintando_X_Y = pintando_X_Y;
  }

  public String[] getNombreSenalesString() {
    return nombreSenales;
  }

  public float[] getFs() {
    return fs;
  }

  public Color[] getColorCanal() {
    return colorCanal;
  }

  public void setColorCanal(Color[] color) {
    this.colorCanal = color;
  }

  public String[][] getLeyendasSenales() {
    return leyendasSenales;
  }

  public void setLeyendasSenales(String[][] leyendasSenales) {
    this.leyendasSenales = leyendasSenales;
  }

  public String[] getLeyendasSenales(int senal) {
    return leyendasSenales[senal];
  }

  public void setLeyendasSenales(int senal, String[] leyendasSenales) {
    this.leyendasSenales[senal] = leyendasSenales;
  }

  public boolean isUsarFecha() {
    return usarFecha;
  }

  public void setUsarFecha(boolean usarFecha) {
    this.usarFecha = usarFecha;
  }

  public boolean isPaintMarks() {
    return paintMarks;
  }

  public void setPaintMarks(boolean paintMarks) {
    this.paintMarks = paintMarks;
  }

}
