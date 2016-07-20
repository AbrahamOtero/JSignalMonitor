package net.javahispano.jsignalmonitor;

import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import net.javahispano.jsignalmonitor.annotations.UI.*;


/**
 *
 * <p>Title: </p>
 * <p>Description: </p>
 * @version 	0.6
 * @author 	Abraham Otero Quintana
 */
public class Channel extends JPanel implements ComponentListener,
    MouseMotionListener, MouseListener,
    AdjustmentListener {
  static ResourceBundle res;
  private int datosInt[];
  protected float datos[];
  protected Dimension tamano;
  private byte pos[];
  private int num_canal;
  private Grid escala;
  private JScrollBar scroll;
  private int x_coordenada;
  private boolean pintar_marca = false;
  private boolean pintar_anotaciones = false;
  private boolean medirDistancia = false;
  private WindowDefineMark ventana_marca;
  private MouseListener escuchador_raton;
  private JSignalMonitor monitor;
  private Client cliente;
  private int punto1_T;
  private int punto2_T;
  private float datoXY;
  private boolean valido_Punto1_T = false;
  private int tamano_fuente;
  private Configuration configuration;
  private CacheDatos cache;

  /**
   * @param num_canal@param escala
   * @param scroll
   */
  Channel(int num_canal, CacheDatos cache, JScrollBar scroll,
      JSignalMonitor monitor,
      Frame frame, Configuration configuration) {
    this.escala = escala;
    this.configuration = configuration;
    this.cache = cache;
    cliente = monitor.getClient();
    res = ResourceBundle.getBundle("net.javahispano.jsignalmonitor.i18n.Res",
        configuration.getLocalidad());
    this.num_canal = num_canal;
    this.scroll = scroll;
    this.addComponentListener(this);
    this.setBackground(Color.white);
    this.addMouseListener(this);
    this.monitor = monitor;
    this.addMouseMotionListener(this);
    this.cliente = cliente;
    this.setDefaultTooltip();
    this.cache = cache;
    this.setBorder(BorderFactory.createLoweredBevelBorder());
    ventana_marca = new WindowDefineMark(frame, scroll, this, num_canal,
        monitor, configuration);
    escala = new Grid(num_canal, configuration);

    //      ventanaDistancia = new VentanaDistancia();

    String sistema_operativo = System.getProperty("os.name");
    //Si el sistem operativo es algún tipo de windows 16 puntos de fuente
    if (sistema_operativo.toLowerCase().lastIndexOf("windows") != -1) {
      tamano_fuente = 14;
    }
    //Si no lo dejamos en 12 píxeles.
    else {
      tamano_fuente = 12;
    }
    scroll.addAdjustmentListener(this);
  }

  /**
   * Devuelve el tamano inicial de los paneles pintar para hacer que
   * inicialmente el valor minimo del scroll sea este .
   * @return int
   */
  int getTamanoInicialPAnelPintar() {
    return this.getSize().width;
  }

  /**
   * este metodo creo que es un buen lugar para incluir la actualizacion del visible
   * amount del scroll.
   * @return int[]
   */
  private int[] normaliza() {

    synchronized (this) {
      //Rango de variacion de la senal
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

      //Ofset sobre el cual dibujar
//      int offset = (int)(configuration.getTamano_canal().height/2 -
//          Configuracion.margen_arriba/2);
      //Rango en el que vamos a representar los puntos
      int rango = configuration.getTamanoCanal().height -
          configuration.getMargenAbajo()
          - configuration.getMargenArriba();

      int offset = (int) (configuration.getRangoSenales()[num_canal][1] *
          rango /
          variacion);

      //En este array metemos los puntos sobre los cuales dibujar.
      int[] tmp = new int[datos.length];
      for (int i = 0; i < datos.length; i++) {
        tmp[i] = (int) ( (datos[i] * rango / variacion));
        //  tmp[i] = 2*offset - tmp[i];
        if (configuration.getRangoSenales()[num_canal][0] < 0 &&
            configuration.getRangoSenales()[num_canal][1] < 0) {
          tmp[i] = tmp[i] - offset + configuration.getMargenArriba();
        }
        else {
          tmp[i] = offset - tmp[i] + configuration.getMargenArriba();
        }
      }
      return tmp;
    }
  }

  /**
   * @param g
   * @todo puse a 10 el minimo tamano del scroll para solucionar un puntero nulo
   * ante la peticion 0,0
   */
  void myPaint() {
    //lo prevengo.
    if (configuration.isPintar() && datos != null) {
      //Pedimos a la cache los datos
      int ultimo_dato = Math.max(scroll.getValue(), 10);
      int primer_dato = ultimo_dato - configuration.getTamanoCanal().width +
          configuration.getMargenDerecho() +
          configuration.getMargenIzquierdo();
      primer_dato = Math.max(0, primer_dato);
      datos = cache.getDatos(primer_dato, ultimo_dato);
      if (configuration.isPaintPossibility()[num_canal]) {
        pos = cache.getPos(primer_dato, ultimo_dato);
      }
      //Normalizamos los datos
      int[] datos_pintar = normaliza();
      //Se los pasamos al panel para que los pinte
      if (configuration.isPaintPossibility()[num_canal]) {
        myPaint(datos_pintar, pos);
      }
      else {
        myPaint(datos_pintar);
      }
      // repaint();
    }
  }

  /**
   * @param datos
   * @param pos
   * @todo comprobar si sobra
   */
  void myPaint(int[] datos, byte[] pos) {

    this.datosInt = datos;
    this.pos = pos;
//Creo que sobra
    //   repaint();
  }

  /**
   * @param datos
   * @todo comprobar si sobra
   */
  void myPaint(int[] datos) {
    this.datosInt = datos;
//Creo que sobra
    // repaint();
  }

  /**
   * @param f
   * @return java.awt.Color
   */
  private Color traduceColor(byte f) {
    Color c;
    if (f > 80) {
      c = Color.red;
    }
    else if (f > 60) {
      c = Color.orange;
    }
    else if (f > 40) {
      c = Color.pink;
    }
    else if (f > 20) {
      c = Color.yellow;
    }
    else if (f > 0) {
      c = Color.green;
    }
    else {
      c = Color.black;
    }
    return c;
  }

  /**
   * @param g
   * @todo Nuevo chequeo:  datos != null
   */
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    myPaint();
    this.actualizaFecha();
    //Pinto primero la escala para que no se superponga
    escala.paint(g);

    //Si se puede pintar se pinta, si no solo se pinta la escala por defecto.
    if (configuration.isPintar() && datosInt != null) {
      //Si cambia el tamano del panel => el numero minimo de datos a representar
      //sera igual nuevo tamano. Solo es necesario hacerlo una vez.
      if (this.num_canal == 0) {
        /*  scroll.setMinimum(0this.getSize().width - configuration.getMargenDerecho() -
                            configuration.getMargenIzquierdo());*/
      }
      //Si las posibilidades estan activas => dibujamos punto a punto
      if (configuration.isPaintPossibility(num_canal) & pos != null) {
        for (int i = 1; i < datosInt.length; i++) {
          g.setColor(traduceColor(pos[i]));
          //Hay que sumarle (restar) el margen de arriba para que quede bien
          g.drawLine(configuration.getMargenIzquierdo() + i - 1,
              datosInt[i - 1] /*-Configuracion.margen_arriba*/,
              configuration.getMargenIzquierdo() + i,
              datosInt[i] /*-Configuracion.margen_arriba*/);
        }

      }
      //Siu no construimos un vectosr con el eje temporal y dibujamos todo de golpe.
      else {
        if (configuration.getColorCanal() == null ||
            configuration.getColorCanal()[num_canal] == null) {
          g.setColor(Color.black);
        }
        else {
          g.setColor(configuration.getColorCanal()[num_canal]);
        }
        int[] tmp = new int[datosInt.length];
        for (int i = 0; i < datosInt.length; i++) {
          //Hay que restarle el margen izquierdo a los datos para que quede bien
          //  datos[i] = datos[i] -Configuracion.margen_arriba;
          tmp[i] = configuration.getMargenIzquierdo() + i;
        }
        g.drawPolyline(tmp, datosInt, datosInt.length);
      }
    }

    //Este código dibuja rayita roja de realizar anotaciones
    if (this.pintar_anotaciones) {
      g.setColor(Color.red);
      g.drawLine(this.x_coordenada, 0, this.x_coordenada, this.getSize().height);
    }

    //Este código dibuja la rayita azul de realizar marcas
    else if (this.pintar_marca) {
      g.setColor(Color.blue);
      g.drawLine(this.x_coordenada, 0, this.x_coordenada, this.getSize().height);
    }
    //Este código dibuja rayita de realizar medidas
    if (this.pintar_anotaciones) {
      this.pintaXY(g);
      g.setColor(configuration.getColorDeFondo());
      if (this.valido_Punto1_T) {
        //Se queda con el Xor mode "Puesto", no dibujar nada después de la selección.
        g.setXORMode(Color.white);
        int min = Math.min(this.punto1_T + this.getWidth() - scroll.getValue() -
            configuration.getMargenDerecho(), this.x_coordenada);
        int max = Math.max(this.punto1_T + this.getWidth() - scroll.getValue() -
            configuration.getMargenDerecho(), this.x_coordenada);
        g.fillRect(min, 0, max - min, this.getSize().height);
      }
      else {
        g.drawLine(this.x_coordenada, 0, this.x_coordenada,
            this.getSize().height);
      }

    }
    //Dibujo la medida xy
    if (configuration.isPintandoXY()) {
      g.setColor(Color.magenta);
      g.drawLine(this.x_coordenada, 0, this.x_coordenada, this.getSize().height);
      this.pintaXY(g);

    }

    //Aqui se dibujan las anotaciones
    this.pintarMarcas();
  }

  /**
   * Metodo que dibuja los valores XY
   */
  private void pintaXY(Graphics g) {
    int punto = scroll.getValue() - getSize().width + getX_coordenada()
        + configuration.getMargenDerecho();
    g.setColor(Color.black);
    g.setFont(new Font("Serif", Font.PLAIN, tamano_fuente));
    g.drawString("v(t): " + this.datoXY, 5, 50);
    if (configuration.isUsarFecha()) {
      g.drawString("t: " + SamplesToDate.getInstancia().getFecha(punto,
          configuration.getFs(this.num_canal), true,
          configuration.getFs(this.num_canal) > 1), 5, 70);
    }
    else {
      g.drawString("t: " + punto, 5, 70);

    }
  }

  /**
   * Metodo privado para la actualizacion de la fecha de la escala
   */
  private void actualizaFecha() {
    //FECHA
    //Actualizamos la fecha de configuracion
    int ultimo_tiempo_monitorizado = scroll.getValue();
    //A veces el valor de Scroll es negativo
    if (ultimo_tiempo_monitorizado < 0) {
      ultimo_tiempo_monitorizado = 0;
    }
    if (configuration.isUsarFecha()) {
      escala.setFechaFin(SamplesToDate.getInstancia().getFecha(
          ultimo_tiempo_monitorizado,
          configuration.getFs(num_canal), false,
          configuration.getFs(num_canal) > 1));
    }
    else {
      escala.setFechaFin(ultimo_tiempo_monitorizado + "");
    }

    //Luego la fecha de inicio
    ultimo_tiempo_monitorizado = ultimo_tiempo_monitorizado -
        configuration.getTamanoCanal().width +
        configuration.getMargenDerecho() + configuration.getMargenIzquierdo();
    if (ultimo_tiempo_monitorizado < 0) {
      ultimo_tiempo_monitorizado = 0;
    }
    if (configuration.isUsarFecha()) {
      escala.setFechaInicio(SamplesToDate.getInstancia().getFecha(
          ultimo_tiempo_monitorizado, configuration.getFs(num_canal), false,
          configuration.getFs(num_canal) > 1));
    }
    else {
      escala.setFechaInicio("" + ultimo_tiempo_monitorizado);
    }

    //FIN FECHA
  }

  /**
   * @param e
   * @todo ¿Que hace l bucle for?. No lo ves eh?. Pues yo tam,poco, pero así funciona....
   */
  public void componentResized(ComponentEvent e) {
    if (monitor.isRealTime()) {
      return;
    }

    configuration.setTamanoCanal(this.getSize());
    //¿a donde apuntaba antes el scrooll?
    int volor_scroll = scroll.getValue();

    /**
     *     int duracionRegistro = configuration.getDuracionRegistro();
        configuration.setDuracionRegistro(duracionRegistro);
        int anchoCanal = configuration.getTamano_canal().width -
       configuration.getMargenDerecho() -
       configuration.getMargenIzquierdo();
        if (anchoCanal > duracionRegistro) {
     scroll.setValues(duracionRegistro, 0,duracionRegistro ,duracionRegistro);
        }
        else {

     scroll.setValues(nuevoMaximo, 0, anchoCanal, nuevoMaximo);
        }

     */

    int visibleAmount = configuration.getTamanoCanal().width -
        configuration.getMargenDerecho() -
        configuration.getMargenIzquierdo();
    //   scroll.setVisibleAmount(visibleAmount);
    int minimum = configuration.getTamanoCanal().width -
        configuration.getMargenDerecho() -
        configuration.getMargenIzquierdo();
    //   scroll.setMinimum(minimum);
    int maximum = configuration.getDuracionRegistro() +
        configuration.getTamanoCanal().width -
        configuration.getMargenDerecho() -
        configuration.getMargenIzquierdo();
    //   scroll.setMaximum(maximum);
    //¿A donde tiene que apuntar ahora el scroll?  a donde apuntaba antes
    /* if (monitor.isRealTime()) {
       visibleAmount =0;
       scroll.setValues(volor_scroll, 0,
                 minimum, maximum);
     }
     else{*/
    scroll.setValues(Math.max(volor_scroll,
        minimum), visibleAmount,
        minimum, maximum);
//}



//            canal.myPaint();

    //  }

  }

  /**
   * @param e
   */
  public void componentMoved(ComponentEvent e) {

  }

  /**
   * @param e
   */
  public void componentShown(ComponentEvent e) {

  }

  /**
   * @param e
   */
  public void componentHidden(ComponentEvent e) {

  }

  /**
   * @param fecha
   */
  void setFechaInicio(Fecha fecha) {

  }

  /**
   * @param fecha
   */
  void setFechaFin(Fecha fecha) {

  }

  public void mouseDragged(MouseEvent e) {
    /**
     *
     * @todo se repinta dos veces, por lo menos
     */
  }

  public void mouseMoved(MouseEvent e) {
    if (datos == null) {
      return;
    }
    this.x_coordenada = e.getX();

    if (x_coordenada - configuration.getMargenIzquierdo() < datos.length &&
        x_coordenada - configuration.getMargenIzquierdo() > 0) {
      this.datoXY = datos[x_coordenada - configuration.getMargenIzquierdo()];
    }
    //Chequeo si está dentro del area que se dibuja
    if (x_coordenada > configuration.getMargenIzquierdo() &&
        x_coordenada < this.getSize().width - configuration.getMargenDerecho()) {
      if (this.pintar_anotaciones || this.pintar_marca) {
        this.repaint();
        //Depuracion
        /*System.out.println(""+(this.x_coordenada - configuration.getMargenIzquierdo()) + " " + this.datos[
         this.x_coordenada-
          configuration.getMargenIzquierdo() ]);*/
      }
      if (configuration.isPintandoXY() ||
          configuration.isPintarAnotacionesMedidas()) {
        //this.datoXY = canal.getDatos()[x_coordenada-configuration.getMargenIzquierdo()];
        if (configuration.isPaintMarks()) {
          this.setToolTipText(
              "<html>" +
              "<body bgcolor=\"#A9FF8C\" text=\"#000000\"><p>" +
              res.getString("Vuelva_a_pulsar_el") +
              "</font></p></body>" +
              "</html>"
              );
        }
        this.repaint();
      }
      else if (!this.pintar_anotaciones) {
        this.setDefaultTooltip();
      }
    }
    else {
      if (this.pintar_marca) {
        this.pintar_marca = false;
        repaint();
      }
    }

    //Esto es para que se vuelva a pintar la rayita inmediatamente sobre este canal
    //cuando se invoca dos veces seguidas a setHacerMedida y la medida primera se había
    //realizado sobre este mimsmo canal
    if (configuration.isPintarAnotacionesMedidas()) {
      this.pintar_anotaciones = true;
    }

  }

  public void mousePressed(MouseEvent e) {
    //Si no se permiten marcas no hay nada que hacer
    if (!configuration.isPaintMarks()) {
      return;
    }
    if (e.getClickCount() >= 1 && !this.pintar_marca && !pintar_anotaciones) {
      this.pintar_marca = true;
      this.pintar_anotaciones = false;
      this.repaint();
      return;
    }
    else if (this.pintar_marca) {
      this.pintar_marca = false;
      ventana_marca.mostrar(true);
      repaint();
      return;
    }
    else if (this.pintar_anotaciones) {
      this.setToolTipText(
          "<html>" +
          "<body bgcolor=\"#A9FF8C\" text=\"#000000\"><p>" +
          res.getString("Hecho_el_primer_click") +
          "</font></p></body>" +
          "</html>"
          );
      medirDistancia = true;
      punto1_T = scroll.getValue() - getSize().width + getX_coordenada() +
          configuration.getMargenDerecho();
      valido_Punto1_T = true;
      configuration.setPintarAnotacionesMedidas(false);
      this.removeMouseListener(this);

      escuchador_raton = (new MouseAdapter() {

        public void mousePressed(MouseEvent e) {
          Channel.this.setDefaultTooltip();
          Channel.this.removeMouseListener(this);
          punto2_T = scroll.getValue() - getSize().width + getX_coordenada() +
              configuration.getMargenDerecho();
          valido_Punto1_T = false;
          float tiempo = (punto2_T - punto1_T) / configuration.getFs(num_canal);
          pintar_anotaciones = false;
          medirDistancia = false;
          repaint();
          //Corregimos por si el usuario ha hecho clic fuera de la señal
          punto1_T = Math.max(punto1_T, 0);
          punto2_T = Math.max(punto2_T, 0);
          //Al devover los tiempos tenemos en cuenta el escalado temporal
          cliente.selectionFinished(num_canal,
              punto1_T / configuration.getEscaladoTemporal(),
              punto2_T /
              configuration.getEscaladoTemporal());
          Channel.this.addMouseListener(Channel.this);
        }

        public void mouseEntered(MouseEvent e) {
          pintar_anotaciones = true;
          repaint();
        }

        public void mouseExited(MouseEvent e) {
          pintar_anotaciones = false;
          repaint();
        }
      });
      this.addMouseListener(escuchador_raton);
    }
  }

  public void mouseClicked(MouseEvent e) {

  }

  public void mouseReleased(MouseEvent e) {

  }

  public void mouseEntered(MouseEvent e) {
    if (configuration.isPintarAnotacionesMedidas()) {
      this.pintar_anotaciones = true;
      repaint();
    }
  }

  public void mouseExited(MouseEvent e) {
    if (this.pintar_marca || this.pintar_anotaciones) {
      this.pintar_marca = false;
      this.pintar_anotaciones = false;
      repaint();
    }
  }

  void cancelaMedida() {
    if (medirDistancia) {
      removeMouseListener(escuchador_raton);
      pintar_anotaciones = false;
      medirDistancia = false;
      repaint();
      addMouseListener(Channel.this);
    }
  }

  public int getX_coordenada() {
    return x_coordenada;
  }

  /**
   * @todo: La primera vez que se pide que se pinte el panel es antes de que este tenga
   * su tamano, cuando este viene teniendo unos 10 o 20 pixeles. Como se piden las marcas
   * en funcion del scroll => se pide las que realmente se necesiarías si tuviesemos
   * toda la ventana (800 pixeles o asi). Como no disponemos de un vector de datos de
   * más de 10 datos => arrayOutOfBounds. ¿Merece la pena meter un chequeo para ello
   * o simplemente paso.
   */
  private void pintarMarcas() {
    //Retiramos todas las anotaciones anteriores
    this.removeAll();

    Client cliente = monitor.getClient();
    int tiempo_fin = scroll.getValue();
    int tiempo_desde_inicio = scroll.getValue() - this.getSize().width
        + monitor.getRightMargin() + monitor.getLeftMargin();

    LinkedList marcas = cliente.getMarks(num_canal, tiempo_desde_inicio,
        tiempo_fin);
    //Si no hay marcas
    if (marcas == null) {
      return;
    }

    ListIterator it = marcas.listIterator();
    while (it.hasNext()) {
      net.javahispano.jsignalmonitor.annotations.ClinicalEvent evt = (net.
          javahispano.jsignalmonitor.annotations.ClinicalEvent) it.next();
      String texto = (evt.getTexto());
      try {
        this.add(new GraphicEvent(evt, evt.getStartTime() -
            tiempo_fin + this.getSize().width -
            monitor.getRightMargin(), this.ventana_marca,
            this.datosInt[evt.getStartTime() -
            tiempo_fin + this.getSize().width -
            configuration.getMargenIzquierdo() -
            configuration.getMargenDerecho()] - 10));
      }
      catch (Exception ex) {
        System.out.println("Out of Bounds Exception.");
      }
    }
  }

  /**
   * @param int@param num
   */
  void setNumeroDeCanal(int num) {
    num_canal = num;
    this.escala.setNumeroCanal(num);
    this.num_canal = num;
    this.cache.setNumeroCanal(num);
  }

  /**
   * @param int@param num
   */
  public int getNumeroDeCanal() {
    return this.num_canal;
  }

  public void actulizaLookAndFeel(String nuevo_look) {
    SwingUtilities.updateComponentTreeUI(ventana_marca.getRootPane());
  }

  private void setDefaultTooltip() {
    if (!configuration.isPaintMarks()) {
      return;
    }
    this.setToolTipText(
        "<html><body bgcolor=\"#A9FF8C\" text=\"#000000\"><p>" +
        res.getString("Para_realizar_una") +
        "</font></p>" +
        "</body>" +
        "</html>"
        );
  }

  /**
   * Invoked when the value of the adjustable has changed.
   *
   * @param e AdjustmentEvent
   * @todo Implement this java.awt.event.AdjustmentListener method
   */
  public void adjustmentValueChanged(AdjustmentEvent e) {
    if (configuration.isPintar()) {
      pideDatosDelCanal();
      this.repaint();
    }
  }

  void pideDatosDelCanal() {
    //¿Cual es el ultimo dato a representar?
    int ultimo = scroll.getValue();
    int primero = Math.max(0, ultimo - configuration.getTamanoCanal().width +
        configuration.getMargenDerecho() +
        configuration.getMargenIzquierdo());
    //Pido los datos a mi cache si tienen sentido
    if (ultimo - primero > 0) {
      this.datos = cache.getDatos(primero, ultimo);
      //Si la posibilidad esta activa, la pido
      if (configuration.isPaintPossibility()[num_canal]) {
        this.pos = cache.getPos(primero, ultimo);
      }

      // myPaint();
    }
  }

}
