package net.javahispano.jsignalmonitor.annotations.UI;

import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import net.javahispano.jsignalmonitor.*;
import net.javahispano.jsignalmonitor.annotations.*;


/**
 *
 * @version 	0.6
 * @author 	Abraham Otero Quintana
 */

public class WindowDefineMark extends JDialog {

  static ResourceBundle res;
  private Frame frame;
  private Mark marca;
  private boolean nuevo;
  private Channel panel_anotacion;
  private JScrollBar scroll;
  private int num_canal;
  private Client cliente;
  private JSignalMonitor monitor;
  private JOptionPane optionpane;
  private String texto_advertencia_fechas;

  JPanel panel1 = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  JTextField instante_comienzo = new JTextField();
  FlowLayout flowLayout2 = new FlowLayout();
  FlowLayout flowLayout1 = new FlowLayout();
  JButton aceptar = new JButton();
  JTextField nombre = new JTextField();
  JPanel jPanel7 = new JPanel();
  JLabel jLabel7 = new JLabel();
  JPanel jPanel6 = new JPanel();
  JLabel jLabel5 = new JLabel();
  JPanel jPanel3 = new JPanel();
  JPanel jPanel2 = new JPanel();
  JLabel jLabel2 = new JLabel();
  JPanel jPanel1 = new JPanel();
  JLabel jLabel1 = new JLabel();
  JButton cancelar = new JButton();
  BorderLayout borderLayout3 = new BorderLayout();
  BorderLayout borderLayout2 = new BorderLayout();
  JPanel jPanel16 = new JPanel();
  JPanel jPanel15 = new JPanel();
  JPanel jPanel14 = new JPanel();
  JPanel jPanel13 = new JPanel();
  JPanel jPanel12 = new JPanel();
  JButton borrar = new JButton();
  GridLayout gridLayout1 = new GridLayout();
  JScrollPane jScrollPane1 = new JScrollPane();
  JTextArea comentario = new JTextArea();
  private Configuration configuration;

  public WindowDefineMark(Frame frame, JScrollBar scroll,
      Channel panel_anotacion, int num_canal, JSignalMonitor monitor,
      Configuration configuration) {
    super(frame);
    res = ResourceBundle.getBundle("net.javahispano.jsignalmonitor.i18n.Res",
        configuration.getLocalidad());
    this.frame = frame;
    this.configuration = configuration;
    if (frame != null) {
      Point d = frame.getLocation();
      Dimension t = frame.getSize();
      setLocation( (int) (d.x + t.getWidth() / 4),
          (int) ( (d.y) + t.getHeight() / 2 - 50));
    }
    this.setModal(true);
    this.cliente = monitor.getClient();
    this.monitor = monitor;
    this.panel_anotacion = panel_anotacion;
    this.scroll = scroll;
    this.num_canal = num_canal;
    this.optionpane = optionpane;
    this.texto_advertencia_fechas = texto_advertencia_fechas;
    try {
      jbInit();
      pack();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  void jbInit() throws Exception {
    panel1.setLayout(borderLayout1);
    instante_comienzo.setBorder(BorderFactory.createRaisedBevelBorder());
    instante_comienzo.setColumns(18);
    flowLayout2.setHgap(25);
    aceptar.setToolTipText(res.getString("Crear_Modificar_la1"));
    aceptar.setText(res.getString("Aceptar1"));
    aceptar.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        aceptar_actionPerformed(e);
      }
    });
    nombre.setBorder(BorderFactory.createRaisedBevelBorder());
    nombre.setColumns(18);
    jLabel7.setText(res.getString("Comentario_1"));
    jLabel7.setToolTipText("");
    jLabel7.setForeground(Color.blue);
    jLabel7.setFont(new java.awt.Font("Dialog", 0, 14));
    jLabel5.setFont(new java.awt.Font("Dialog", 0, 14));
    jLabel5.setForeground(Color.blue);
    jLabel5.setText(res.getString("Instante_de_comienzo_1"));
    jPanel2.setLayout(flowLayout1);
    jLabel2.setFont(new java.awt.Font("Dialog", 0, 14));
    jLabel2.setForeground(Color.blue);
    jLabel2.setText(res.getString("Nombre_1"));
    jPanel1.setLayout(borderLayout2);
    jLabel1.setBackground(SystemColor.activeCaptionBorder);
    jLabel1.setFont(new java.awt.Font("Dialog", 1, 18));
    jLabel1.setForeground(Color.red);
    jLabel1.setText(res.getString("MARCA"));
    cancelar.setToolTipText(res.getString("Cancelar_la_creaci_n1"));
    cancelar.setText(res.getString("Cancelar1"));
    cancelar.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cancelar_actionPerformed(e);
      }
    });
    jPanel15.setLayout(borderLayout3);
    jPanel15.setPreferredSize(new Dimension(74, 140));
    jPanel14.setLayout(gridLayout1);
    jPanel13.setLayout(flowLayout2);
    borrar.setToolTipText(res.getString("Borrar_la_marca"));
    borrar.setText(res.getString("Borrar1"));
    borrar.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        borrar_actionPerformed(e);
      }
    });
    gridLayout1.setRows(2);
    gridLayout1.setColumns(2);
    this.setTitle("Marca");
    getContentPane().add(panel1);
    jPanel15.add(jPanel16, BorderLayout.NORTH);
    jPanel16.add(jLabel7, null);
    jPanel15.add(jScrollPane1, BorderLayout.CENTER);
    jScrollPane1.getViewport().add(comentario, null);
    jPanel1.add(jPanel2, BorderLayout.NORTH);
    jPanel2.add(jLabel1, null);
    jPanel1.add(jPanel14, BorderLayout.CENTER);
    jPanel1.add(jPanel15, BorderLayout.SOUTH);
    jPanel14.add(jPanel3, null);
    jPanel3.add(jLabel2, null);
    jPanel14.add(jPanel12, null);
    jPanel12.add(nombre, null);
    jPanel14.add(jPanel7, null);
    jPanel7.add(jLabel5, null);
    jPanel14.add(jPanel6, null);
    jPanel6.add(instante_comienzo, null);
    panel1.add(jPanel13, BorderLayout.SOUTH);
    jPanel13.add(aceptar, null);
    jPanel13.add(cancelar, null);
    jPanel13.add(borrar, null);
    panel1.add(jPanel1, BorderLayout.CENTER);
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
      int tiempo_desde_inicio = scroll.getValue() -
          panel_anotacion.getSize().width
          + panel_anotacion.getX_coordenada() + configuration.getMargenDerecho();
      this.instante_comienzo.setText(SamplesToDate.getInstancia().getFecha(
          tiempo_desde_inicio,
          monitor.getFs(num_canal), false, monitor.getFs(num_canal) > 1));
      this.comentario.setText("");

    }
    else if (!nuevo) {
      this.borrar.setVisible(true);
      this.nombre.setText(marca.getTexto());
      this.instante_comienzo.setText(SamplesToDate.getInstancia().getFecha(
          this.marca.getStartTime(), monitor.getFs(num_canal), false,
          monitor.getFs(num_canal) > 1));
      this.comentario.setText(marca.getComentario());
    }
    this.nuevo = nuevo;
    //actualiza el numero de canal
    this.num_canal = panel_anotacion.getNumeroDeCanal();
    if (frame != null) {
      Point d = frame.getLocation();
      Dimension t = frame.getSize();
      setLocation( (int) (d.x + t.getWidth() / 4),
          (int) ( (d.y) + t.getHeight() / 2 - 50));
    }
    this.show();
  }

  void aceptar_actionPerformed(ActionEvent e) {

    String fecha_inicio = this.instante_comienzo.getText();
    SamplesToDate sample_date = SamplesToDate.getInstancia();
    long tiempo_en_muestras_long =
        sample_date.getSamplesTill(fecha_inicio, monitor.getFs(num_canal));

    if (tiempo_en_muestras_long != Long.MIN_VALUE) {
      int tiempo_en_muestras = (int) tiempo_en_muestras_long;
      String nombre = this.nombre.getText();
      Mark marca = new Mark();
      //
      marca.setTiempo(tiempo_en_muestras);
      marca.setComentario(this.comentario.getText());
      marca.setTexto(nombre);
      //Primero eliminados la anotación antigua, en el caso en el que estemos modificando
      // una anotacion, luego anadimos la nueva
      if (!this.nuevo) {
        cliente.deleteMark(this.num_canal, this.marca);
      }
      cliente.anadMark(this.num_canal, marca);
      this.setVisible(false);
      this.marca = marca;
    }
    else {
      SamplesToDate.getOptionpane().showConfirmDialog(this,
          SamplesToDate.getTextoAdevertenciaFecha(),
          res.getString("ERROR_"), JOptionPane.DEFAULT_OPTION,
          JOptionPane.ERROR_MESSAGE);
    }

  }

  void cancelar_actionPerformed(ActionEvent e) {
    this.setVisible(false);
  }

  void borrar_actionPerformed(ActionEvent e) {
    cliente.deleteMark(this.num_canal, marca);
    this.setVisible(false);
    panel_anotacion.repaint();
  }

  public Mark getMarca() {
    return this.marca;
  }

  public void setMarca(Mark marca) {
    this.marca = marca;

  }

  public void setNumeroCanal(int numero_canal) {
    this.num_canal = num_canal;
  }
}
