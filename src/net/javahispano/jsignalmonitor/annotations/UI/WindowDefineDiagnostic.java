package net.javahispano.jsignalmonitor.annotations.UI;

import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import net.javahispano.jsignalmonitor.*;
import net.javahispano.jsignalmonitor.annotations.*;


/**
 *
 * @version 	0.6
 * @author 	Abraham Otero Quintana
 */
public class WindowDefineDiagnostic extends JDialog {
  static ResourceBundle res;
  private Border normal = BorderFactory.createLoweredBevelBorder();
  private Frame frame;
  private Border selecionado = BorderFactory.createRaisedBevelBorder();
  private Diagnostic diagnostico;
  private boolean nuevo;
  private Client cliente;
  private JSignalMonitor monitor;

  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel13 = new JPanel();
  JButton borrar = new JButton();
  JButton cancelar = new JButton();
  JButton aceptar = new JButton();
  FlowLayout flowLayout2 = new FlowLayout();
  TitledBorder titledBorder1;
  private JScrollBar scroll;
  private PanelAnnotation panel_anotacion;
  JPanel jPanel1 = new JPanel();
  BorderLayout borderLayout2 = new BorderLayout();
  JPanel jPanel9 = new JPanel();
  JPanel jPanel8 = new JPanel();
  JPanel jPanel7 = new JPanel();
  JPanel jPanel6 = new JPanel();
  JTextField nombre = new JTextField();
  JPanel jPanel5 = new JPanel();
  JTextField instante_comienzo = new JTextField();
  JPanel jPanel4 = new JPanel();
  JPanel jPanel3 = new JPanel();
  JTextField atributo = new JTextField();
  JPanel jPanel2 = new JPanel();
  JPanel jPanel14 = new JPanel();
  JTextField valor = new JTextField();
  JPanel jPanel12 = new JPanel();
  JPanel jPanel11 = new JPanel();
  JPanel jPanel10 = new JPanel();
  FlowLayout flowLayout1 = new FlowLayout();
  GridLayout gridLayout1 = new GridLayout();
  JLabel jLabel6 = new JLabel();
  JLabel jLabel5 = new JLabel();
  JLabel jLabel4 = new JLabel();
  JLabel jLabel3 = new JLabel();
  JLabel jLabel2 = new JLabel();
  JTextField instante_fin = new JTextField();
  JLabel jLabel1 = new JLabel();
  JPanel jPanel15 = new JPanel();
  JScrollPane jScrollPane1 = new JScrollPane();
  JTextArea comentario = new JTextArea();
  BorderLayout borderLayout3 = new BorderLayout();
  JPanel jPanel16 = new JPanel();
  JLabel jLabel7 = new JLabel();
  private Configuration configuration;

  public WindowDefineDiagnostic(JFrame frame, JScrollBar scroll,
      PanelAnnotation panel_anotacion, JSignalMonitor monitor,
      Configuration configuration) {
    super(frame);
    res = ResourceBundle.getBundle("net.javahispano.jsignalmonitor.i18n.Res",
        configuration.getLocalidad());
    this.frame = frame;
    this.configuration = configuration;
    this.cliente = monitor.getClient();
    this.monitor = monitor;

    this.setModal(true);
    this.panel_anotacion = panel_anotacion;
    this.scroll = scroll;
    this.setTitle(res.getString("Anotaci_n_tipo_diagn"));
    try {
      setSize(400, 400);
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    if (frame != null) {
      Point d = frame.getLocation();
      Dimension t = frame.getSize();
      setLocation( (int) (d.x + t.getWidth() / 3),
          (int) ( (d.y) + t.getHeight() / 2 - 250));
    }
  }

  private void jbInit() throws Exception {
    titledBorder1 = new TitledBorder("");
    this.getContentPane().setLayout(borderLayout1);
    borrar.setToolTipText(res.getString("Borrar_el_diagn_stico"));
    borrar.setText(res.getString("Borrar1"));
    borrar.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        borrar_actionPerformed(e);
      }
    });
    cancelar.setToolTipText(res.getString("Cancelar_la_creaci_n1"));
    cancelar.setText(res.getString("Cancelar1"));
    cancelar.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cancelar_actionPerformed(e);
      }
    });
    aceptar.setToolTipText(res.getString("Crear_Modificar_el"));
    aceptar.setText(res.getString("Aceptar1"));
    aceptar.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        aceptar_actionPerformed(e);
      }
    });

    jPanel13.setLayout(flowLayout2);
    flowLayout2.setHgap(25);
    jPanel1.setLayout(borderLayout2);
    nombre.setBorder(BorderFactory.createRaisedBevelBorder());
    nombre.setColumns(18);
    instante_comienzo.setBorder(BorderFactory.createRaisedBevelBorder());
    instante_comienzo.setColumns(18);
    instante_comienzo.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        instante_comienzo_actionPerformed(e);
      }
    });
    atributo.setBorder(BorderFactory.createRaisedBevelBorder());
    atributo.setColumns(18);
    jPanel2.setLayout(flowLayout1);
    jPanel14.setLayout(gridLayout1);
    valor.setBorder(BorderFactory.createRaisedBevelBorder());
    valor.setColumns(18);
    gridLayout1.setRows(5);
    gridLayout1.setColumns(2);
    jLabel6.setFont(new java.awt.Font("Dialog", 2, 14));
    jLabel6.setForeground(Color.blue);
    jLabel6.setToolTipText(res.getString("Este_campo_es1"));
    jLabel6.setText(res.getString("Instante_de_Fin_1"));
    jLabel5.setFont(new java.awt.Font("Dialog", 0, 14));
    jLabel5.setForeground(Color.blue);
    jLabel5.setText(res.getString("Instante_de_comienzo_1"));
    jLabel4.setFont(new java.awt.Font("Dialog", 0, 14));
    jLabel4.setForeground(Color.blue);
    jLabel4.setToolTipText("");
    jLabel4.setText(res.getString("Valor_"));
    jLabel3.setFont(new java.awt.Font("Dialog", 0, 14));
    jLabel3.setForeground(Color.blue);
    jLabel3.setText(res.getString("Atributo_"));
    jLabel2.setFont(new java.awt.Font("Dialog", 0, 14));
    jLabel2.setForeground(Color.blue);
    jLabel2.setText(res.getString("Nombre_1"));
    instante_fin.setBorder(BorderFactory.createRaisedBevelBorder());
    instante_fin.setColumns(18);
    instante_fin.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        instante_fin_actionPerformed(e);
      }
    });
    jLabel1.setBackground(SystemColor.activeCaptionBorder);
    jLabel1.setFont(new java.awt.Font("Dialog", 1, 18));
    jLabel1.setForeground(Color.red);
    jLabel1.setText(res.getString("DIAGN_STICO"));
    comentario.setText("jTextArea1");
    jPanel15.setLayout(borderLayout3);
    jPanel15.setPreferredSize(new Dimension(74, 140));
    jLabel7.setText(res.getString("Comentario_1"));
    jLabel7.setToolTipText("");
    jLabel7.setForeground(Color.blue);
    jLabel7.setFont(new java.awt.Font(res.getString("Dialog"), 0, 14));
    this.getContentPane().add(jPanel13, BorderLayout.SOUTH);
    jPanel13.add(aceptar, null);
    jPanel13.add(cancelar, null);
    jPanel13.add(borrar, null);
    this.getContentPane().add(jPanel1, BorderLayout.CENTER);
    jPanel14.add(jPanel3, null);
    jPanel3.add(jLabel2, null);
    jPanel14.add(jPanel12, null);
    jPanel12.add(nombre, null);
    jPanel14.add(jPanel11, null);
    jPanel11.add(jLabel3, null);
    jPanel14.add(jPanel10, null);
    jPanel10.add(atributo, null);
    jPanel14.add(jPanel9, null);
    jPanel9.add(jLabel4, null);
    jPanel14.add(jPanel8, null);
    jPanel8.add(valor, null);
    jPanel14.add(jPanel7, null);
    jPanel7.add(jLabel5, null);
    jPanel14.add(jPanel6, null);
    jPanel6.add(instante_comienzo, null);
    jPanel14.add(jPanel5, null);
    jPanel5.add(jLabel6, null);
    jPanel14.add(jPanel4, null);
    jPanel4.add(instante_fin, null);
    jPanel1.add(jPanel15, BorderLayout.SOUTH);
    jPanel15.add(jScrollPane1, BorderLayout.CENTER);
    jPanel15.add(jPanel16, BorderLayout.NORTH);
    jPanel16.add(jLabel7, null);
    jScrollPane1.getViewport().add(comentario, null);
    jPanel1.add(jPanel2, BorderLayout.NORTH);
    jPanel2.add(jLabel1, null);
    jPanel1.add(jPanel14, BorderLayout.CENTER);
  }

  /**
   * Muestra la ventana y segun el parámetro que se le pasa se activa o no el
   * botón de borrar
   */
  public void mostrar(boolean nuevo) {
    //Si no borrar, que no se vea ese botón y todos los campos en blanco.
    if (nuevo) {
      this.borrar.setVisible(false);
      this.nombre.setText("");
      this.atributo.setText("");
      this.valor.setText("");
      this.comentario.setText("");
      int tiempo_desde_inicio = scroll.getValue() -
          panel_anotacion.getSize().width -
          //Esto va por el ancho del scroll
          monitor.getWithAnnotationScroll()
          + panel_anotacion.getX_coordenada() + configuration.getMargenDerecho();
      this.instante_comienzo.setText(SamplesToDate.getInstancia().getFecha(
          tiempo_desde_inicio,
          monitor.getFsGlobal(), false, monitor.getFsGlobal() > 1));
      this.instante_fin.setText("");
    }
    else if (!nuevo) {
      this.borrar.setVisible(true);
      this.nombre.setText(diagnostico.getTexto());
      /*      int tiempo_desde_inicio = scroll.getValue() - panel_anotacion.getSize().width
       + panel_anotacion.getX_coordenada() + MainFrame.getMonitor().getMargenDerechoCaneles();*/
      Attribute atributo_tmp = diagnostico.getAtributo();
      this.atributo.setText(atributo_tmp.getAtributo());
      this.valor.setText(atributo_tmp.getValor());
      this.instante_comienzo.setText(SamplesToDate.getInstancia().getFecha(
          this.diagnostico.getStartTime(), monitor.getFsGlobal(), false,
          monitor.getFsGlobal() > 1));
      this.comentario.setText(diagnostico.getComentario());
      this.instante_fin.setText("");
    }
    this.nuevo = nuevo;

    if (frame != null) {
      Point d = frame.getLocation();
      Dimension t = frame.getSize();
      setLocation( (int) (d.x + t.getWidth() / 3),
          (int) ( (d.y) + t.getHeight() / 2 - 250));
    }
    this.show();
  }

  void aceptar_mouseEntered(MouseEvent e) {
    aceptar.setBorder(selecionado);
  }

  void aceptar_mouseExited(MouseEvent e) {
    aceptar.setBorder(normal);
  }

  void cancelar_mouseEntered(MouseEvent e) {
    cancelar.setBorder(selecionado);
  }

  void cancelar_mouseExited(MouseEvent e) {
    cancelar.setBorder(normal);
  }

  void borrar_mouseEntered(MouseEvent e) {
    borrar.setBorder(selecionado);
  }

  void borrar_mouseExited(MouseEvent e) {
    borrar.setBorder(normal);
  }

  void aceptar_actionPerformed(ActionEvent e) {

    String fecha_inicio = this.instante_comienzo.getText();
    SamplesToDate sample_date = SamplesToDate.getInstancia();
    /* String fecha_base = sample_date.getFechaBase();
     StringTokenizer tk = new StringTokenizer(fecha_base);
     tk.nextToken();
     String dia_mes_ano_base = tk.nextToken();
     fecha_inicio = fecha_inicio + " " +dia_mes_ano_base;*/
    long tiempo_en_muestras_long = sample_date.getSamplesTill(fecha_inicio,
        monitor.getFsGlobal());

    if (tiempo_en_muestras_long != Long.MIN_VALUE) {
      int tiempo_en_muestras = (int) tiempo_en_muestras_long;
      String nuevoNombre = this.nombre.getText();
      String atribut = this.atributo.getText();
      String valor = this.valor.getText();
      String fecha_fin = this.instante_fin.getText();
      Attribute atributo_object = new Attribute(atribut, valor);
      Diagnostic diagnostico = new Diagnostic(nuevoNombre, atributo_object);
      //
      diagnostico.setTiempo(tiempo_en_muestras);
      diagnostico.setComentario(this.comentario.getText());
      diagnostico.setTipo(net.javahispano.jsignalmonitor.annotations.
          ClinicalEvent.DIAGNOSTICO);
      //Primero eliminados la anotación antigua, en el caso en el que estemos modificando
      // una anotacion, luego anadimos la nueva
      if (!this.nuevo) {
        cliente.deleteAnnotation(this.diagnostico);
      }
      cliente.addAnnotation(diagnostico);
      this.setVisible(false);
      this.diagnostico = diagnostico;
    }
    else {
      SamplesToDate.getOptionpane().showConfirmDialog(this,
          SamplesToDate.getTextoAdevertenciaFecha(),
          res.getString("ERROR_"), JOptionPane.DEFAULT_OPTION,
          JOptionPane.ERROR_MESSAGE);
    }
    panel_anotacion.repaint();
  }

  void cancelar_actionPerformed(ActionEvent e) {
    this.setVisible(false);
  }

  void borrar_actionPerformed(ActionEvent e) {
    cliente.deleteAnnotation(diagnostico);
    this.setVisible(false);
    panel_anotacion.repaint();
  }

  void instante_comienzo_actionPerformed(ActionEvent e) {

  }

  void instante_fin_actionPerformed(ActionEvent e) {

  }

  public void setDiagnostico(Diagnostic d) {
    this.diagnostico = d;
  }

  public Diagnostic getDiagnostico() {
    return this.diagnostico;
  }

}
