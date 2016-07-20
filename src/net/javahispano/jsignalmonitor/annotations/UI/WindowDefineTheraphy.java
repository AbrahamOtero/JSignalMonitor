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
public class WindowDefineTheraphy extends JDialog {

  static ResourceBundle res;
  private Therapy terapia;
  private boolean nuevo;
  private Client cliente;
  private JSignalMonitor monitor;
  private Frame frame;

  JButton borrar = new JButton();
  JPanel jPanel13 = new JPanel();
  FlowLayout flowLayout2 = new FlowLayout();
  JButton aceptar = new JButton();
  JButton cancelar = new JButton();
  BorderLayout borderLayout1 = new BorderLayout();
  private JScrollBar scroll;
  private PanelAnnotation panel_anotacion;
  JPanel jPanel1 = new JPanel();
  BorderLayout borderLayout2 = new BorderLayout();
  JPanel jPanel9 = new JPanel();
  JPanel jPanel8 = new JPanel();
  JPanel jPanel7 = new JPanel();
  JPanel jPanel6 = new JPanel();
  JTextField nombre = new JTextField();
  JTextField instante_comienzo = new JTextField();
  JPanel jPanel5 = new JPanel();
  JPanel jPanel4 = new JPanel();
  JTextField tipo = new JTextField();
  JPanel jPanel3 = new JPanel();
  JPanel jPanel2 = new JPanel();
  JPanel jPanel18 = new JPanel();
  JPanel jPanel17 = new JPanel();
  JPanel jPanel16 = new JPanel();
  JPanel jPanel15 = new JPanel();
  JPanel jPanel14 = new JPanel();
  JTextField disificacion = new JTextField();
  JPanel jPanel12 = new JPanel();
  JPanel jPanel11 = new JPanel();
  JPanel jPanel10 = new JPanel();
  FlowLayout flowLayout1 = new FlowLayout();
  JTextField fase_terapeutica = new JTextField();
  JTextField duracion = new JTextField();
  GridLayout gridLayout1 = new GridLayout();
  JLabel jLabel8 = new JLabel();
  JLabel jLabel7 = new JLabel();
  JLabel jLabel6 = new JLabel();
  JLabel jLabel5 = new JLabel();
  JLabel jLabel4 = new JLabel();
  JLabel jLabel3 = new JLabel();
  JLabel jLabel2 = new JLabel();
  JTextField instante_fin = new JTextField();
  JLabel jLabel1 = new JLabel();
  JPanel jPanel19 = new JPanel();
  BorderLayout borderLayout3 = new BorderLayout();
  JPanel jPanel20 = new JPanel();
  JLabel jLabel9 = new JLabel();
  JScrollPane jScrollPane1 = new JScrollPane();
  JTextArea comentario = new JTextArea();
  private Configuration configuration;

  public WindowDefineTheraphy(JFrame frame, JScrollBar scroll,
      PanelAnnotation panel_anotacion, JSignalMonitor monitor,
      Configuration configuration) {
    super(frame);
    this.frame = frame;
    res = ResourceBundle.getBundle("net.javahispano.jsignalmonitor.i18n.Res",
        configuration.getLocalidad());
    this.configuration = configuration;
    if (frame != null) {
      Point d = frame.getLocation();
      Dimension t = frame.getSize();
      setLocation( (int) (d.x + t.getWidth() / 3),
          (int) ( (d.y) + t.getHeight() / 2 - 250));
    }
    this.cliente = monitor.getClient();
    this.monitor = monitor;
    this.setModal(true);
    this.scroll = scroll;
    this.panel_anotacion = panel_anotacion;
    this.setTitle(res.getString("Anotaci_n_tipo1"));
    try {
      setSize(400, 400);
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }

  }

  private void jbInit() throws Exception {
    borrar.setToolTipText(res.getString("Borrar_la_terapia"));
    borrar.setText(res.getString("Borrar1"));
    borrar.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        borrar_actionPerformed(e);
      }
    });

    jPanel13.setLayout(flowLayout2);
    flowLayout2.setHgap(25);
    aceptar.setToolTipText(res.getString("Crear_Modificar_la2"));
    aceptar.setText(res.getString("Aceptar1"));
    aceptar.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        aceptar_actionPerformed(e);
      }
    });
    cancelar.setToolTipText(res.getString("Cancelar_la_creaci_n1"));
    cancelar.setText(res.getString("Cancelar1"));
    cancelar.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cancelar_actionPerformed(e);
      }
    });
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
    tipo.setColumns(18);
    tipo.setBorder(BorderFactory.createRaisedBevelBorder());
    jPanel2.setLayout(flowLayout1);
    jPanel18.setLayout(gridLayout1);
    disificacion.setBorder(BorderFactory.createRaisedBevelBorder());
    disificacion.setColumns(18);
    fase_terapeutica.setBorder(BorderFactory.createRaisedBevelBorder());
    fase_terapeutica.setColumns(18);
    duracion.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        duracion_actionPerformed(e);
      }
    });
    duracion.setColumns(18);
    duracion.setBorder(BorderFactory.createRaisedBevelBorder());
    gridLayout1.setRows(7);
    gridLayout1.setColumns(2);
    jLabel8.setText(res.getString("Duraci_n_"));
    jLabel8.setToolTipText(res.getString("Este_campo_es1"));
    jLabel8.setFont(new java.awt.Font("Dialog", 2, 14));
    jLabel8.setForeground(Color.blue);
    jLabel7.setFont(new java.awt.Font("Dialog", 0, 14));
    jLabel7.setForeground(Color.blue);
    jLabel7.setText(res.getString("Tipo_"));
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
    jLabel4.setText(res.getString("Dosificaci_n_"));
    jLabel3.setFont(new java.awt.Font("Dialog", 0, 14));
    jLabel3.setForeground(Color.blue);
    jLabel3.setText(res.getString("Fase_terap_utica_"));
    jLabel2.setFont(new java.awt.Font("Dialog", 0, 14));
    jLabel2.setForeground(Color.blue);
    jLabel2.setText(res.getString("Nombre_f_rmaco_"));
    instante_fin.setBorder(BorderFactory.createRaisedBevelBorder());
    instante_fin.setColumns(18);
    instante_fin.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        instante_fin_actionPerformed(e);
      }
    });
    jLabel1.setFont(new java.awt.Font("Dialog", 1, 18));
    jLabel1.setForeground(Color.red);
    jLabel1.setText(res.getString("TERAPIA"));
    jPanel19.setLayout(borderLayout3);
    jLabel9.setText(res.getString("Comentario_1"));
    jLabel9.setForeground(Color.blue);
    jLabel9.setFont(new java.awt.Font("Dialog", 0, 14));
    jPanel19.setPreferredSize(new Dimension(147, 140));
    comentario.setText("jTextArea1");
    this.getContentPane().add(jPanel13, BorderLayout.SOUTH);
    jPanel13.add(aceptar, null);
    jPanel13.add(cancelar, null);
    jPanel13.add(borrar, null);
    this.getContentPane().add(jPanel1, BorderLayout.CENTER);
    jPanel18.add(jPanel3, null);
    jPanel3.add(jLabel2, null);
    jPanel18.add(jPanel12, null);
    jPanel12.add(nombre, null);
    jPanel18.add(jPanel11, null);
    jPanel11.add(jLabel3, null);
    jPanel18.add(jPanel10, null);
    jPanel10.add(fase_terapeutica, null);
    jPanel18.add(jPanel9, null);
    jPanel9.add(jLabel4, null);
    jPanel18.add(jPanel8, null);
    jPanel8.add(disificacion, null);
    jPanel18.add(jPanel7, null);
    jPanel7.add(jLabel7, null);
    jPanel18.add(jPanel6, null);
    jPanel6.add(tipo, null);
    jPanel18.add(jPanel5, null);
    jPanel5.add(jLabel5, null);
    jPanel18.add(jPanel4, null);
    jPanel4.add(instante_comienzo, null);
    jPanel18.add(jPanel14, null);
    jPanel14.add(jLabel6, null);
    jPanel18.add(jPanel15, null);
    jPanel15.add(instante_fin, null);
    jPanel18.add(jPanel16, null);
    jPanel16.add(jLabel8, null);
    jPanel18.add(jPanel17, null);
    jPanel17.add(duracion, null);
    jPanel1.add(jPanel19, BorderLayout.SOUTH);
    jPanel19.add(jPanel20, BorderLayout.NORTH);
    jPanel20.add(jLabel9, null);
    jPanel19.add(jScrollPane1, BorderLayout.CENTER);
    jScrollPane1.getViewport().add(comentario, null);
    jPanel1.add(jPanel2, BorderLayout.NORTH);
    jPanel2.add(jLabel1, null);
    jPanel1.add(jPanel18, BorderLayout.CENTER);
  }

  /**
   * @todo cambiar el 1 de fs
   * @param e
   */
  void aceptar_actionPerformed(ActionEvent e) {
    String fecha_inicio = this.instante_comienzo.getText();
    SamplesToDate sample_date = SamplesToDate.getInstancia();
    long tiempo_en_muestras_long = sample_date.getSamplesTill(fecha_inicio,
        monitor.getFsGlobal());
    if (tiempo_en_muestras_long != Long.MIN_VALUE) {
      int tiempo_en_muestras = (int) tiempo_en_muestras_long;
      String nombre = this.nombre.getText();
      String fase = this.fase_terapeutica.getText();
      String dosis = this.disificacion.getText();
      String tipo = this.tipo.getText();
      String comentario_txt = comentario.getText();
      String fecha_fin = this.instante_fin.getText();
      Therapy terapia = new Therapy(nombre, fase, dosis, tipo);
      //
      terapia.setTiempo(tiempo_en_muestras);
      //terapia.setTiempo(Integer.parseInt(fecha_inicio));
      terapia.setTipo(net.javahispano.jsignalmonitor.annotations.
          ClinicalEvent.TERAPIA);
      terapia.setComentario(comentario_txt);

      //Primero eliminados la anotación antigua, en el caso en el que estemos modificando
      // una anotacion, luego anadimos la nueva
      if (!this.nuevo) {
        cliente.deleteAnnotation(this.terapia);
      }
      cliente.addAnnotation(terapia);
      this.setVisible(false);
    }
    else {
      SamplesToDate.getOptionpane().showConfirmDialog(this,
          SamplesToDate.getTextoAdevertenciaFecha(),
          res.getString("ERROR_"), JOptionPane.DEFAULT_OPTION,
          JOptionPane.ERROR_MESSAGE);
    }

    //O así o no furrla.
    if (frame != null) {
      frame.validate();
      frame.repaint();
    }
  }

  void cancelar_actionPerformed(ActionEvent e) {
    this.setVisible(false);
  }

  void borrar_actionPerformed(ActionEvent e) {
    cliente.deleteAnnotation(terapia);
    this.setVisible(false);
    panel_anotacion.repaint();
  }

  /**
   * Muestra la ventana y segun el parámetro que se le pasa se activa o no el
   * botón de borrar
   */
  public void mostrar(boolean nuevo) {
    //Si no borrar, que no se vea ese botón y todos los campos en blanco.
    if (nuevo) {
      this.borrar.setVisible(true);
      this.nombre.setText("");
      this.fase_terapeutica.setText("");
      this.disificacion.setText("");
      this.tipo.setText("");
      this.comentario.setText("");
      int tiempo_desde_inicio = scroll.getValue() -
          panel_anotacion.getSize().width
          - monitor.getWithAnnotationScroll()
          + panel_anotacion.getX_coordenada() + configuration.getMargenDerecho();
      //  this.instante_comienzo.setText(Integer.toString(tiempo_desde_inicio));
      this.instante_comienzo.setText(SamplesToDate.getInstancia().getFecha(
          tiempo_desde_inicio,
          monitor.getFsGlobal(), false, monitor.getFsGlobal() > 1));
      this.instante_fin.setText("");
    }
    else if (!nuevo) {

      this.nombre.setText(terapia.getTexto());
      this.fase_terapeutica.setText(terapia.getFaseTerapeutica());
      this.disificacion.setText(terapia.getDosificacion());
      this.tipo.setText(terapia.getTipoTerapia());
      /* int tiempo_desde_inicio = scroll.getValue() - panel_anotacion.getSize().width
        + panel_anotacion.getX_coordenada() + MainFrame.getMonitor().getMargenDerechoCaneles();*/
      //this.instante_comienzo.setText(Integer.toString(this.terapia.getTiempoInicio()));
      this.instante_comienzo.setText(SamplesToDate.getInstancia().getFecha(
          this.terapia.getStartTime(), monitor.getFsGlobal(), false,
          monitor.getFsGlobal() > 1));
      this.instante_fin.setText("");
      this.comentario.setText(terapia.getComentario());
      this.borrar.setVisible(true);
    }
    this.nuevo = nuevo;
    if (frame != null) {
      Point d = frame.getLocation();
      Dimension t = frame.getSize();
      setLocation( (int) (d.x + t.getWidth() / 3),
          (int) ( (d.y) + t.getHeight() / 2 - 250));
    }
    super.show();

  }

  void instante_comienzo_actionPerformed(ActionEvent e) {

  }

  void duracion_actionPerformed(ActionEvent e) {

  }

  void instante_fin_actionPerformed(ActionEvent e) {

  }

  public Therapy getTerapia() {
    return terapia;
  }

  public void setTerapia(Therapy _terapia) {
    terapia = _terapia;
  }

}
