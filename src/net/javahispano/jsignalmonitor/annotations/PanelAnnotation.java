package net.javahispano.jsignalmonitor.annotations;

import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import net.javahispano.jsignalmonitor.*;
import net.javahispano.jsignalmonitor.annotations.UI.*;


/**
 *
 * @version 	0.6
 * @author 	Abraham Otero Quintana

 */
public class PanelAnnotation extends JPanel implements MouseListener,
    MouseMotionListener, AdjustmentListener, ComponentListener,
    PopupMenuListener {
  static ResourceBundle res;

  private WindowDefineFinding ventana_manifestacion;
  private WindowDefineDiagnostic ventana_diagnostico;
  private WindowDefineTheraphy ventana_terapia;
  private Client cliente;
  private JSignalMonitor monitor;
  private JScrollBar scroll;
  private boolean realizar_anotacion = false;
  private int x_coordenada;
  private int tiempo_fin;
  private LinkedList anotaciones;
  private boolean pintar;
  private JPopupMenu menu_anotaciones = new JPopupMenu();
  private JMenuItem menu_diagnostico = new JMenuItem();
  private JMenuItem menu_manifestacion = new JMenuItem();
  private JMenuItem menu_terapia = new JMenuItem();
  private Font tipo_letra = new Font("Serif", Font.BOLD, 16);
  private HashMap listado_farmacos = new HashMap();
  private int numero_farmacos = 0;
  private String[] nombres_farmacos = new String[20];
  private int alto = 180;
  private int margen_izquierdo, margen_derecho;
  private JSplitPane splitpane;
  private Configuration configuration;

  //Variable que me indica si se est� o no mostrondo el popup menu
  private boolean popup_menu_visible = false;

  public PanelAnnotation(JFrame frame, JSignalMonitor monitor, Client cliente,
      JScrollBar scroll, float fs, int margen_izquierdo, int margen_derecho,
      JSplitPane splitpane, Configuration configuration) {
    this.splitpane = splitpane;
    this.configuration = configuration;
    res = ResourceBundle.getBundle("net.javahispano.jsignalmonitor.i18n.Res",
        configuration.getLocalidad());
    this.margen_derecho = margen_derecho;
    this.margen_izquierdo = margen_izquierdo;
    this.scroll = scroll;
    this.setMaximumSize(new Dimension(10, 180));
    this.setSize(this.getWidth(), 160);
    scroll.addAdjustmentListener(this);
    this.monitor = monitor;
    this.cliente = cliente;
    this.addMouseListener(this);
    this.addMouseMotionListener(this);
    this.ventana_diagnostico = new WindowDefineDiagnostic(frame, scroll, this,
        monitor, configuration);
    this.ventana_manifestacion = new WindowDefineFinding(frame, scroll, this,
        monitor, configuration);

    // UIManager.put("ToolTip.background", new javax.swing.plaf.ColorUIResource(Color.red));

    this.ventana_terapia = new WindowDefineTheraphy(frame, scroll, this,
        monitor, configuration);
    this.setToolTipText(
        "<html>" +
        "<body bgcolor=\"#A9FF8C\" text=\"#000000\">" +
        "<p>Para realizar una anotaci�n" +
        " haga doble " +
        "  click </font></p>" +
        "</body>" +
        "</html>"
        );

    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    menu_anotaciones.addPopupMenuListener(this);
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    //Funciona de 1.3.1 pa bahjo. De 1.4.0_01 pa rriba no, pero java es comaptible hacia atras, asi que no
    //se lo digas a nadie!!!!!!
    //setSize(/*this.getWidth()*/monitor.getWidth()- monitor.getAnchoScrollAnotaciones()-8,this.alto);
    g.setFont(this.tipo_letra);
    if (pintar) {
      if (this.realizar_anotacion) {
        g.setColor(Color.red);
        g.drawLine(this.x_coordenada, 0, this.x_coordenada,
            this.getSize().height);
      }
      //Pone la presentaci�n de las anotaciones
      String tmp[] = {res.getString("Diagn_stico"),
          res.getString("Manifestaci_n"), res.getString("Terapia")};
      int tmp_int[] = {1, 3, 8};
      Dimension tamano = this.getSize();
      for (int i = 0; i < 3; i++) {
        g.setColor(Color.gray);
        g.fillRect(0, (tmp_int[i] - 1) * 20, tamano.width, 20);
        g.setColor(Color.gray);
        g.fillRect(0, (tmp_int[i] - 1) * 20, tamano.width, 2);
        g.fillRect(0, (tmp_int[i] - 1) * 20 + 18, tamano.width, 2);
        g.setColor(Color.white);
        g.drawString(tmp[i], 5, (tmp_int[i]) * 20 - 4);
      }
      String[] tmp2 = {res.getString("Sntoma"), res.getString("Signo"),
          res.getString("Test"), res.getString("Se_al")};
      g.setColor(Color.black);
      g.drawString(tmp2[0], 5, 75);
      for (int i = 0; i < 3; i++) {
        g.drawLine(0, 80 + 20 * i, tamano.width, 80 + 20 * i);
        g.drawString(tmp2[i + 1], 5, 95 + 20 * i);
      }
      for (int i = 0; i < this.numero_farmacos; i++) {
        g.drawLine(0, 160 + 20 * i, tamano.width, 160 + 20 * i);
        g.drawString(this.nombres_farmacos[i], 5, 175 + 20 * i);
      }

      //Pintar la rallita vertical
      g.setColor(Color.black);
      g.drawLine(monitor.getLeftMargin(), 20, monitor.getLeftMargin(), 40);
      g.drawLine(monitor.getLeftMargin(), 60, monitor.getLeftMargin(), 140);
      g.drawLine(monitor.getLeftMargin(), 160, monitor.getLeftMargin(),
          this.getHeight());

      pintarAnotaciones();
    }

  }

  /**
   * Metodo para indicar si se repinta o no el panel
   */
  public void setPintar(boolean b) {
    pintar = b;
  }

  /**
   * @param e
   */
  public void mousePressed(MouseEvent e) {
    if (e.getClickCount() >= 1 && !realizar_anotacion &&
        !monitor.isPintingXYValues()
        && !popup_menu_visible) {
      this.realizar_anotacion = true;
      monitor.setPaintAnnotationLine(true);
      monitor.repaint();
    }
    //Si el men� est� mostrado y el usuario hace un click supongo que lo que
    //Ocurre es que desea pasar del men�
    else if (!popup_menu_visible) {
      if (this.realizar_anotacion) {
        this.realizar_anotacion = false;
        monitor.setPaintAnnotationLine(false);
        monitor.repaint();
        menu_anotaciones.show(this, e.getX(), e.getY());
      }
    }

  }

  /**
   * @param e
   */
  public void mouseClicked(MouseEvent e) {

  }

  /**
   * @param e
   */
  public void mouseReleased(MouseEvent e) {

  }

  /**
   * @param e
   */
  public void mouseEntered(MouseEvent e) {

  }

  /**
   * @param e
   */
  public void mouseExited(MouseEvent e) {

  }

  public void mouseDragged(MouseEvent e) {
  }

  public void mouseMoved(MouseEvent e) {

    this.x_coordenada = e.getX();
    //Chequeo si est� dentro del area que se dibija
    if (x_coordenada > margen_izquierdo &&
        x_coordenada < this.getSize().width - margen_derecho &&
        this.realizar_anotacion) {
      monitor.repaint();
    }
    else {
      if (this.realizar_anotacion) {
        this.realizar_anotacion = false;
        monitor.setPaintAnnotationLine(false); ;
        monitor.repaint();
      }
    }
  }

  public PanelAnnotation() {
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    this.setSize(100, 220);
    menu_diagnostico.setToolTipText(res.getString("Realizar_una_anotaci"));
    menu_diagnostico.setText(res.getString("Diagn_stico"));
    menu_diagnostico.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        menu_diagnostico_actionPerformed(e);
      }
    });
    menu_manifestacion.setToolTipText(res.getString("Realizar_una_anotaci1"));
    menu_manifestacion.setText(res.getString("Manifestaci_n"));
    menu_manifestacion.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        menu_manifestacion_actionPerformed(e);
      }
    });
    menu_terapia.setToolTipText(res.getString("Realizar_una_anotaci2"));
    menu_terapia.setText(res.getString("Terapia"));
    menu_terapia.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        menu_terapia_actionPerformed(e);
      }
    });
    menu_anotaciones.setVisible(true);
    menu_anotaciones.add(menu_diagnostico);
    menu_anotaciones.add(menu_manifestacion);
    menu_anotaciones.add(menu_terapia);
  }

  void menu_diagnostico_actionPerformed(ActionEvent e) {
    this.ventana_diagnostico.mostrar(true);
  }

  void menu_manifestacion_actionPerformed(ActionEvent e) {
    this.ventana_manifestacion.mostrar(true);
  }

  void menu_terapia_actionPerformed(ActionEvent e) {
    this.ventana_terapia.mostrar(true);
  }

  public int getX_coordenada() {
    return x_coordenada;
  }

  public void adjustmentValueChanged(AdjustmentEvent e) {

    repaint();

  }

  private void pintarAnotaciones() {
    //Retiramos todas las anotaciones anteriores
    this.removeAll();
    this.tiempo_fin = scroll.getValue();
    //Sin el 20 no pide bien... A seber que pixeles me deje por ahi.
    int tiempo_desde_inicio = scroll.getValue() - this.getSize().width
        + monitor.getRightMargin() + monitor.getLeftMargin() - 20;
    this.anotaciones = cliente.getAnnotations(tiempo_desde_inicio,
        tiempo_fin);
    ListIterator it = this.anotaciones.listIterator();
    while (it.hasNext()) {
      ClinicalEvent evt = (ClinicalEvent) it.next();
      String texto = (evt.getTexto());

      JDialog ventana;
      int tipo = evt.getTipo();
      if (tipo == ClinicalEvent.DIAGNOSTICO) {
        ventana = ventana_diagnostico;
      }
      else if (tipo == ClinicalEvent.MANIFESTACION) {
        ventana = ventana_manifestacion;
      }
      else {
        ventana = ventana_terapia;
      }

      int off = calculaPosicionAnotacion(evt);
      this.add(new GraphicEvent(evt, evt.getStartTime() -
          this.tiempo_fin + this.getSize().width - monitor.getRightMargin()
          + monitor.getWithAnnotationScroll(), ventana, off));
    }
  }

  /**
   * Este m�todo calcula la coordenada y de la anotacion en funcion de su tipo y,
   * si es un farmaco, de si ya ha sido aplicado o no.
   * @param evt
   * @return
   */
  private int calculaPosicionAnotacion(ClinicalEvent evt) {
    /////Codigo para colocaci�n "bonita"
    int off = 0;
    if (evt.getTipo() == ClinicalEvent.DIAGNOSTICO) {
      off = 20;
    }
    else if (evt.getTipo() == ClinicalEvent.MANIFESTACION) {
      Manifestacion manif = (Manifestacion) evt;
      int tipo_manifestacion = manif.getTipoManifestacion();
      if (tipo_manifestacion == Manifestacion.SINTOMA) {
        off = 61;
      }
      else if (tipo_manifestacion == Manifestacion.SIGNO) {
        off = 81;
      }
      else if (tipo_manifestacion == Manifestacion.TEST) {
        off = 101;
      }
      else {
        off = 121;
      }
    }
    else {
      Therapy terpaia = (Therapy) evt;
      String farmaco = terpaia.getTexto();
      FarmacoIndice actual = new FarmacoIndice(farmaco, 0);
      if (this.listado_farmacos.containsKey(farmaco)) {
        int indice = ( (FarmacoIndice)this.listado_farmacos.get(farmaco)).
            getIndice();
        off = 160 + indice * 20;
      }
      else {
        FarmacoIndice nuevo_farmaco = new FarmacoIndice(farmaco,
            this.numero_farmacos);
        this.numero_farmacos++;
        this.nombres_farmacos[this.numero_farmacos - 1] = farmaco;
        listado_farmacos.put(farmaco, nuevo_farmaco);
        off = 160 + this.numero_farmacos * 20;
        this.alto = off;
        //cada vez que halla un farmaco nuevo: hay que aumentar el tama�o del panel de arriba.
        //Solo hacer en ese caso, si no bucle inficnito.
        this.setPreferredSize(new Dimension(this.getHeight(), alto));
        splitpane.resetToPreferredSizes();
      }

    }
    return off;
  }

  /**
   * Este m�todo resetea la informaci�n de los medicamentos
   */
  public void resetFarmacos() {
    this.numero_farmacos = 0;
    this.removeAll();
    this.alto = 180;
    this.listado_farmacos = new HashMap();
  }

  public void actulizaLookAnfFeel(String nuevo_look) {
    SwingUtilities.updateComponentTreeUI(ventana_diagnostico.getRootPane());
    SwingUtilities.updateComponentTreeUI(ventana_manifestacion.getRootPane());
    SwingUtilities.updateComponentTreeUI(ventana_terapia.getRootPane());
  }

  /**
   * Para que pinte bien cuando empezamos a hacer anotaciones de tipo terapia.
   * @param e
   */
  public void componentResized(ComponentEvent e) {
    this.setPreferredSize(new Dimension(this.getHeight(), alto));
    splitpane.resetToPreferredSizes();
  }

  public void componentMoved(ComponentEvent e) {

  }

  public void componentShown(ComponentEvent e) {

  }

  public void componentHidden(ComponentEvent e) {

  }

  public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
    popup_menu_visible = true;
  }

  public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
    popup_menu_visible = false;
  }

  public void popupMenuCanceled(PopupMenuEvent e) {
    popup_menu_visible = false;
  }

}


class FarmacoIndice {
  private String farmaco;
  private int indice;

  FarmacoIndice(String farmaco, int indice) {
    this.farmaco = farmaco;
    this.indice = indice;
  }

  int getIndice() {
    return indice;
  }

}
