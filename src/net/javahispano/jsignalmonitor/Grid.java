package net.javahispano.jsignalmonitor;

import java.util.*;

import java.awt.*;


/**
 *
 * <p>Title: </p>
 * <p>Description: </p>
 * @version 	0.6
 * @author 	Abraham Otero Quintana0
 */
class Grid {
  static ResourceBundle res;
  public static final byte HORIZONTAL = 0;
  public static final byte VERTICAL = 1;

  private String fecha_inicio;
  private String fecha_fin;
  private int num_canal;
  private int tamano_fuente;
  private String unidades = null;
  private Configuration configuration;
  private Font smallFont;
  private Font normalFont;
  private Font tinyFont;

  /**
   * @param unidades
   * @param unidades_temporales
   * @param leyendas@param nombre_señal
   */
  Grid(int num_canal, Configuration configuration) {
    this(configuration);
    this.num_canal = num_canal;
  }

  /**
   * @param g
   */
  void paint(Graphics g) {
    String[] leyendas = configuration.getLeyendasSenales(num_canal);
    int numero_leyendas = leyendas.length;
    if (g != null && configuration.isPintar()) {
      g.setColor(Color.black);
      int distancia_leyendas = (int) ( (configuration.getTamanoCanal().height -
          configuration.getMargenAbajo() -
          configuration.getMargenArriba()) / (numero_leyendas /*-1*/));
      //Linea vertical
      g.drawLine(configuration.getMargenIzquierdo(),
          configuration.getMargenArriba(),
          configuration.getMargenIzquierdo(),
          configuration.getTamanoCanal().height -
          configuration.getMargenAbajo());
      //Linea horizontal
      g.drawLine(configuration.getMargenIzquierdo(),
          configuration.getTamanoCanal().height -
          configuration.getMargenAbajo(),
          configuration.getTamanoCanal().width - configuration.getMargenDerecho(),
          configuration.getTamanoCanal().height - configuration.getMargenAbajo());

      if (configuration.isPintarRangoComoLeyenda()) {
        for (int i = 0; i < 2; i++) {
          //Dibujamos la rayita
          distancia_leyendas = (int) ( (configuration.getTamanoCanal().height -
              configuration.getMargenAbajo() -
              configuration.getMargenArriba()));
          g.drawLine(configuration.getMargenIzquierdo() - 4,
              configuration.getTamanoCanal().height -
              configuration.getMargenAbajo() - i * distancia_leyendas,
              configuration.getMargenIzquierdo() + 4,
              configuration.getTamanoCanal().height -
              configuration.getMargenAbajo() - i * distancia_leyendas);
          g.setFont(smallFont);
          //Dibujamos el texto
          g.drawString(Float.toString(configuration.getRangoSenales()[num_canal][
              i])
              , 60, configuration.getTamanoCanal().height -
              configuration.getMargenAbajo() - i * distancia_leyendas + 5);
        }
      }
      else {
        for (int i = 0; i < numero_leyendas; i++) {

          //Dibujamos la rayita
          g.drawLine(configuration.getMargenIzquierdo() - 4,
              configuration.getTamanoCanal().height -
              configuration.getMargenAbajo() - i * distancia_leyendas,
              configuration.getMargenIzquierdo() + 4,
              configuration.getTamanoCanal().height -
              configuration.getMargenAbajo() - i * distancia_leyendas);
          //Dibujamos el texto
          g.drawString(leyendas[i], 4, configuration.getTamanoCanal().height -
              configuration.getMargenAbajo() - i * distancia_leyendas);
        }
      }

      if (configuration.getTipoGrid(num_canal) != Client.WITHOUT_GRID) {

        //Dibujamos el grid horizontal
        int espacio_vertical_total = (configuration.getTamanoCanal().width -
            configuration.getMargenDerecho() - configuration.getMargenIzquierdo());
        float espaciado;
        float espaciadoMenor;
        if (configuration.getTipoGrid(num_canal) != Client.GRID_PERSONALIZED) {
          espaciado = espacio_vertical_total /
              (configuration.getTipoGrid(num_canal));
          espaciadoMenor = espaciado / 4.0F;
        }
        else {

          espaciado = configuration.getIntervaloDivisionesPrincipales()[
              num_canal][0] * configuration.getFs(num_canal);
          espaciado = espaciado / 1000;
          espaciadoMenor = configuration.getIntervaloDivisionesSecundarias()[
              num_canal][0] * configuration.getFs(num_canal);
          espaciadoMenor = espaciadoMenor / 1000;
        }
        this.pintaIndicadorDistanciaTemporal(g, espaciado);

        for (float i = espaciado; (i <= espacio_vertical_total); i += espaciado) {
          g.setColor(Color.lightGray);
          g.drawLine( (int) (configuration.getMargenIzquierdo() + i),
              configuration.getTamanoCanal().height -
              configuration.getMargenAbajo(),
              (int) (configuration.getMargenIzquierdo() + i),
              configuration.getMargenArriba());
          //Pinta la rayita de abajo
          g.setColor(Color.black);
          //Pinta la rayita gorda de abajo
          if (i + espaciado > espacio_vertical_total) {
            g.setColor(Color.black);
            g.fillRect(configuration.getMargenIzquierdo() + (int) i,
                configuration.getTamanoCanal().height -
                configuration.getMargenAbajo() - 4,
                4, 8);
          }
          else if (i == espaciado) {
            g.setColor(Color.black);
            g.fillRect(configuration.getMargenIzquierdo() - 2,
                configuration.getTamanoCanal().height -
                configuration.getMargenAbajo() - 4,
                4, 8);
          }
          g.drawLine( (int) (configuration.getMargenIzquierdo() + i),
              configuration.getTamanoCanal().height -
              configuration.getMargenAbajo() + 4,
              (int) (configuration.getMargenIzquierdo() + i),
              configuration.getTamanoCanal().height -
              configuration.getMargenAbajo() - 4);
          if (configuration.getDivisionesSecundarias()[num_canal][0]) {
            for (float j = i - espaciadoMenor; j > i - espaciado;
                j -= espaciadoMenor) {
              g.drawLine( (int) (configuration.getMargenIzquierdo() + j),
                  configuration.getTamanoCanal().height -
                  configuration.getMargenAbajo() + 2,
                  (int) (configuration.getMargenIzquierdo() + j),
                  configuration.getTamanoCanal().height -
                  configuration.getMargenAbajo() - 2);
            }
          }
        }
        //Dibuja el grid vertical
        float espacio_horizontal_total = (configuration.getTamanoCanal().height
            - configuration.getMargenArriba() - configuration.getMargenAbajo());

        float espaciado_h;
        if (configuration.getTipoGrid(num_canal) != Client.GRID_PERSONALIZED) {
          espaciado_h = 2 * espacio_horizontal_total /
              configuration.getTipoGrid(num_canal);
        }
        else {
          espaciado_h = espacio_horizontal_total *
              configuration.getIntervaloDivisionesPrincipales()[num_canal][1];
          espaciado_h = espaciado_h /
              (configuration.getRangoSenales()[num_canal][1] -
              configuration.getRangoSenales()[num_canal][0]);
          espaciadoMenor = configuration.getIntervaloDivisionesSecundarias()[
              num_canal][1] * espaciado_h /
              configuration.getIntervaloDivisionesPrincipales()[num_canal][1]; //*configuration.getFs(num_canal);
          // espaciadoMenor = espaciadoMenor/1000;
        }
        this.pintaIndicadorDistancia(g, espaciado_h);

        for (float i = 0; (i < espacio_horizontal_total); i += espaciado_h) {
          g.setColor(Color.lightGray);
          g.drawLine(configuration.getMargenIzquierdo(),
              (int) (configuration.getMargenArriba() + i),
              configuration.getTamanoCanal().width -
              configuration.getMargenDerecho(),
              (int) (configuration.getMargenArriba() + i));
          //Pinta la rayita de abajo
          g.setColor(Color.black);
          g.drawLine(configuration.getMargenIzquierdo() - 4,
              (int) (configuration.getMargenArriba() + i),
              configuration.getMargenIzquierdo() + 4,
              (int) (configuration.getMargenArriba() + i));
          if (configuration.getDivisionesSecundarias()[num_canal][1]) {
            for (float j = i + espaciadoMenor; j < i + espaciado_h;
                j += espaciadoMenor) {
              g.drawLine( (int) (configuration.getMargenIzquierdo() - 2),
                  configuration.getTamanoCanal().height -
                  configuration.getMargenAbajo() - (int) j,
                  (int) (configuration.getMargenIzquierdo() + 2),
                  configuration.getTamanoCanal().height -
                  configuration.getMargenAbajo() - (int) j);
            }
          }
        }
      }
      //dibujamos el nombre de la senal
      g.setColor(Color.blue);
      g.setFont(normalFont);
      String nombreCompleto = configuration.getNombreSenales()[this.num_canal];
      int posRetornoCarro = nombreCompleto.indexOf("\n");
      if (posRetornoCarro!=-1) {
        unidades = nombreCompleto.substring(posRetornoCarro,nombreCompleto.length());
        nombreCompleto = nombreCompleto.substring(0, posRetornoCarro);
        g.drawString(nombreCompleto,
          10, configuration.getMargenArriba() + 20);
      g.setFont(smallFont);
      g.drawString(unidades,
        10, configuration.getMargenArriba() + 40);
      }
      else {
        g.drawString(nombreCompleto,
          10, configuration.getMargenArriba() + 20);
       unidades = null;
      }

      //dibujamos las fechas
      g.setColor(Color.black);
      g.drawString(fecha_inicio, configuration.getMargenIzquierdo(),
          configuration.getTamanoCanal().height - 2);
      if (configuration.isUsarFecha()) {
        g.drawString(fecha_fin, configuration.getTamanoCanal().width - 150,
            configuration.getTamanoCanal().height - 2);
      }
      else {
        g.drawString(fecha_fin, configuration.getTamanoCanal().width -
            configuration.getMargenDerecho() - (fecha_fin.length() + 1) * 6,
            configuration.getTamanoCanal().height - 2);
      }

    }

    else {
      System.out.println("No pinto");
    }
  }

  /**
   * @param fecha
   */
  void setFechaInicio(String fecha) {
    fecha_inicio = fecha;
  }

  /**
   * @param fecha
   */
  void setFechaFin(String fecha) {
    fecha_fin = fecha;
  }

  public void setNumeroCanal(int num) {
    this.num_canal = num /*- 1*/;
  }

  /**
   *
   * @param g
   * @param espaciado
   */
  private void pintaIndicadorDistanciaTemporal(Graphics g, float espaciado) {
    g.setFont(smallFont);
    g.setColor(Color.blue);
    int posicion = (int) (configuration.getTamanoCanal().width * 0.55F);
    g.drawLine(posicion - (int) espaciado,
        configuration.getTamanoCanal().height - 14, posicion - (int) espaciado,
        configuration.getTamanoCanal().height - 4);
    g.drawLine(posicion - (int) espaciado,
        configuration.getTamanoCanal().height - 10, posicion,
        configuration.getTamanoCanal().height - 10);
    g.drawLine(posicion,
        configuration.getTamanoCanal().height - 14, posicion,
        configuration.getTamanoCanal().height - 4);
    //Realizamos un redondeo, siempre que no se pierda mucha informacion,
    //Sobre el resultado a pintar en pantalla para no ofrecerle al usuario
    //una cantidad de numero decimales muy elevada
    if (Math.abs(Math.round(espaciado / configuration.getFs(num_canal)) -
        espaciado / configuration.getFs(num_canal)) < 0.001) {
      g.drawString(Math.round(espaciado / configuration.getFs(num_canal))
          + res.getString("Segundos"), posicion + 5,
          configuration.getTamanoCanal().height - 5);
    }
    else {
      g.drawString( (espaciado / configuration.getFs(num_canal))
          + res.getString("Segundos"), posicion + 5,
          configuration.getTamanoCanal().height - 5);
    }
  }

  /**
   *
   * @param g
   * @param espaciado
   */
  private void pintaIndicadorDistancia(Graphics g, float espaciado) {
    g.setFont(smallFont);
    g.setColor(Color.blue);
    int posicionBaseCanal = (int) (configuration.getTamanoCanal().height * 0.4F);
    g.drawLine(configuration.getMargenIzquierdo() - 30,
        posicionBaseCanal + (int) espaciado,
        configuration.getMargenIzquierdo() - 20,
        posicionBaseCanal + (int) espaciado);

    g.drawLine(configuration.getMargenIzquierdo() - 25,
        posicionBaseCanal + (int) espaciado,
        configuration.getMargenIzquierdo() - 25,
        posicionBaseCanal);

    g.drawLine(configuration.getMargenIzquierdo() - 30,
        posicionBaseCanal, configuration.getMargenIzquierdo() - 20,
        posicionBaseCanal);

    float variacion;
    if (configuration.getRangoSenales()[num_canal][0] *
        configuration.getRangoSenales()[num_canal][1] > 0) {
      variacion = Math.abs(configuration.getRangoSenales()[num_canal][1]) -
          Math.abs(configuration.getRangoSenales()[num_canal][0]);
    }
    else {
      variacion = Math.abs(configuration.getRangoSenales()[num_canal][0]) +
          Math.abs(configuration.getRangoSenales()[num_canal][1]);
    }
    float espaciado_real = espaciado * variacion /
        (configuration.getTamanoCanal().height -
        configuration.getMargenAbajo() - configuration.getMargenArriba());
    g.drawString(espaciado_real + "", configuration.getMargenIzquierdo() - 40,
        posicionBaseCanal + 15 + (int) espaciado);
  /*  if (unidades != null) {
      g.setFont(tinyFont);
      g.drawString(unidades, configuration.getMargenIzquierdo() - 40,
          posicionBaseCanal + 25 + (int) espaciado);
      g.setFont(normalFont);

    }*/
  }

  public Grid(Configuration configuration) {
    this.configuration = configuration;
    res = ResourceBundle.getBundle("net.javahispano.jsignalmonitor.i18n.Res",
        configuration.getLocalidad());
    String sistema_operativo = System.getProperty("os.name");
    //Si el sistem operativo es algún tipo de windows 16 puntos de fuente
    if (sistema_operativo.toLowerCase().lastIndexOf("windows") != -1) {
      tamano_fuente = 16;
    }
    //Si no lo dejamos en 13 píxeles.
    else {
      tamano_fuente = 13;
    }

    smallFont = new Font("Dialog", Font.PLAIN,
        tamano_fuente - 5);
    normalFont = new Font("Serif", Font.BOLD, tamano_fuente);
    tinyFont = new Font("Dialog", Font.PLAIN,
        tamano_fuente - 8);
  }

}
