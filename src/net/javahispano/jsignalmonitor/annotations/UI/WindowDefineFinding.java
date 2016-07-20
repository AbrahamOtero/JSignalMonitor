//Source file: E:\\JAVA2\\MonitorizacionAlgoritmo\\src\\pintar\\MarcasAnotaciones\\UI\\VentanaDefinirManifestacion.java

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
public class WindowDefineFinding extends JDialog {
  static ResourceBundle res;
  private Frame frame;
  private int tipo_manifestacion;
  private Manifestacion manifestacion;
  private boolean nuevo;
  private Client cliente;
  private JSignalMonitor monitor;

  BorderLayout borderLayout1 = new BorderLayout();
  JButton cancelar = new JButton();
  FlowLayout flowLayout4 = new FlowLayout();
  JButton borrar = new JButton();
  JButton aceptar = new JButton();
  JPanel jPanel13 = new JPanel();
  private JScrollBar scroll;
  private PanelAnnotation panel_anotacion;
  JPanel jPanel1 = new JPanel();
  BorderLayout borderLayout2 = new BorderLayout();
  JPanel jPanel7 = new JPanel();
  JPanel jPanel6 = new JPanel();
  JTextField nombre = new JTextField();
  JPanel jPanel5 = new JPanel();
  JTextField instante_comienzo = new JTextField();
  JPanel jPanel4 = new JPanel();
  JPanel jPanel3 = new JPanel();
  JPanel jPanel2 = new JPanel();
  JPanel jPanel14 = new JPanel();
  JPanel jPanel12 = new JPanel();
  JScrollPane jScrollPane1 = new JScrollPane();
  FlowLayout flowLayout3 = new FlowLayout();
  JPanel jPanel11 = new JPanel();
  JPanel jPanel10 = new JPanel();
  GridLayout gridLayout1 = new GridLayout();
  JLabel jLabel6 = new JLabel();
  JLabel jLabel5 = new JLabel();
  JLabel jLabel3 = new JLabel();
  JLabel jLabel2 = new JLabel();
  JLabel jLabel1 = new JLabel();
  JTextField instante_fin = new JTextField();
  BorderLayout borderLayout3 = new BorderLayout();
  JPanel jPanel8 = new JPanel();
  JPanel jPanel9 = new JPanel();
  BorderLayout borderLayout4 = new BorderLayout();
  JPanel jPanel15 = new JPanel();
  JLabel jLabel7 = new JLabel();
  JComboBox categoria;
  JScrollPane jScrollPane2 = new JScrollPane();
  JTextArea comentario = new JTextArea();
  String tmp[] = new String[2];
  JTable table;
  private String[] eleccion = new String[4];
  private Configuration configuration;

  public WindowDefineFinding(JFrame frame, JScrollBar scroll,
      PanelAnnotation panel_anotacion, JSignalMonitor monitor,
      Configuration configuration) {
    super(frame);
    this.frame = frame;
    this.configuration = configuration;
    res = ResourceBundle.getBundle("net.javahispano.jsignalmonitor.i18n.Res",
        configuration.getLocalidad());
    eleccion[0] = res.getString("Sintoma");
    eleccion[1] = res.getString("Signo");
    eleccion[2] = res.getString("Test");
    eleccion[3] = res.getString("Senal");
    tmp[0] = res.getString("Atributos");
    tmp[1] = res.getString("Valores");
    categoria = new JComboBox(eleccion);
    table = new JTable(new String[16][2], tmp);

    this.cliente = monitor.getClient();
    this.monitor = monitor;
    if (frame != null) {
      Point d = frame.getLocation();
      Dimension t = frame.getSize();
      setLocation( (int) (d.x + t.getWidth() / 4),
          (int) ( (d.y) + t.getHeight() / 2 - 250));
    }
    this.setModal(true);
    this.scroll = scroll;
    this.panel_anotacion = panel_anotacion;
    this.setTitle(res.getString("Anotaci_n_tipo"));
    try {
      this.setSize(new Dimension(500, 500));
      table.setAutoscrolls(true);
      table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }

  }

  private void jbInit() throws Exception {
    this.getContentPane().setLayout(borderLayout1);
    cancelar.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cancelar_actionPerformed(e);
      }
    });
    cancelar.setText(res.getString("Cancelar"));
    cancelar.setToolTipText(res.getString("Cancelar_la_creaci_n"));
    flowLayout4.setHgap(25);
    borrar.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        borrar_actionPerformed(e);
      }
    });
    borrar.setText(res.getString("Borrar"));
    borrar.setToolTipText(res.getString("Borrar_la_manifestaci"));
    aceptar.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        aceptar_actionPerformed(e);
      }
    });
    aceptar.setText(res.getString("Aceptar"));
    aceptar.setToolTipText(res.getString("Crear_Modificar_la"));
    jPanel13.setLayout(flowLayout4);
    jPanel1.setLayout(borderLayout2);
    nombre.setBorder(BorderFactory.createRaisedBevelBorder());
    nombre.setColumns(18);
    jPanel5.setLayout(flowLayout3);
    instante_comienzo.setColumns(18);
    instante_comienzo.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        instante_comienzo_actionPerformed(e);
      }
    });
    jPanel2.setLayout(gridLayout1);
    gridLayout1.setRows(4);
    gridLayout1.setColumns(2);
    jLabel6.setFont(new java.awt.Font("Dialog", 2, 14));
    jLabel6.setForeground(Color.blue);
    jLabel6.setToolTipText(res.getString("Este_campo_es"));
    jLabel6.setText(res.getString("Instante_de_Fin_"));
    jLabel5.setFont(new java.awt.Font("Dialog", 0, 14));
    jLabel5.setForeground(Color.blue);
    jLabel5.setText(res.getString("Instante_de_comienzo_"));
    jLabel3.setFont(new java.awt.Font("Dialog", 0, 14));
    jLabel3.setForeground(Color.blue);
    jLabel3.setText(res.getString("Categoria_"));
    jLabel2.setFont(new java.awt.Font("Dialog", 0, 14));
    jLabel2.setForeground(Color.blue);
    jLabel2.setText(res.getString("Nombre_"));
    jLabel1.setBackground(SystemColor.activeCaptionBorder);
    jLabel1.setFont(new java.awt.Font("Dialog", 1, 18));
    jLabel1.setForeground(Color.red);
    jLabel1.setText(res.getString("MANIFESTACI_N"));
    instante_fin.setBorder(BorderFactory.createRaisedBevelBorder());
    instante_fin.setColumns(18);
    instante_fin.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        instante_fin_actionPerformed(e);
      }
    });
    jPanel8.setLayout(borderLayout3);
    jPanel9.setLayout(borderLayout4);
    jLabel7.setText(res.getString("Comentario_"));
    jLabel7.setToolTipText(res.getString("Este_campo_es"));
    jLabel7.setFont(new java.awt.Font(res.getString("Dialog"), 2, 14));
    jLabel7.setForeground(Color.blue);
    comentario.setPreferredSize(new Dimension(70, 40));
    comentario.setText("jTextArea1");
    jPanel9.setPreferredSize(new Dimension(87, 120));
    jPanel8.setMinimumSize(new Dimension(294, 300));
    this.getContentPane().add(jPanel13, BorderLayout.SOUTH);
    jPanel13.add(aceptar, null);
    jPanel13.add(cancelar, null);
    jPanel13.add(borrar, null);
    this.getContentPane().add(jPanel1, BorderLayout.CENTER);
    jPanel8.add(jScrollPane1, BorderLayout.CENTER);
    jScrollPane1.getViewport().add(table, null);
    jPanel8.add(jPanel2, BorderLayout.NORTH);
    jPanel2.add(jPanel3, null);
    jPanel3.add(jLabel2, null);
    jPanel2.add(jPanel12, null);
    jPanel12.add(nombre, null);
    jPanel2.add(jPanel11, null);
    jPanel11.add(jLabel3, null);
    jPanel2.add(jPanel10, null);
    jPanel10.add(categoria, null);
    jPanel2.add(jPanel7, null);
    jPanel7.add(jLabel5, null);
    jPanel2.add(jPanel6, null);
    jPanel6.add(instante_comienzo, null);
    jPanel2.add(jPanel14, null);
    jPanel14.add(jLabel6, null);
    jPanel2.add(jPanel4, null);
    jPanel4.add(instante_fin, null);
    jPanel1.add(jPanel9, BorderLayout.SOUTH);
    jPanel9.add(jPanel15, BorderLayout.NORTH);
    jPanel15.add(jLabel7, null);
    jPanel9.add(jScrollPane2, BorderLayout.CENTER);
    jScrollPane2.getViewport().add(comentario, null);
    jPanel1.add(jPanel5, BorderLayout.NORTH);
    jPanel5.add(jLabel1, null);
    jPanel1.add(jPanel8, BorderLayout.CENTER);
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
      this.instante_comienzo.setText("JODER·");
      int tiempo_desde_inicio = scroll.getValue() -
          panel_anotacion.getSize().width
          - monitor.getWithAnnotationScroll()
          + panel_anotacion.getX_coordenada() + configuration.getMargenDerecho();
      this.instante_comienzo.setText(SamplesToDate.getInstancia().getFecha(
          tiempo_desde_inicio,
          monitor.getFsGlobal(), false, monitor.getFsGlobal() > 1));
      this.instante_fin.setText("");
      this.comentario.setText("");

      //Limpado de la tabla:
      int num_filas = table.getRowCount();
      table.clearSelection();
      table.editCellAt(0, num_filas - 1);
      for (int i = 0; i < num_filas; i++) {
        table.setValueAt("", i, 0);
        table.setValueAt("", i, 1);
      }
    }
    else if (!nuevo) {
      this.borrar.setVisible(true);
      this.nombre.setText(manifestacion.getTexto());
      this.categoria.setSelectedIndex(manifestacion.getTipoManifestacion() - 1);
      this.comentario.setText(manifestacion.getComentario());
      this.instante_comienzo.setText( (SamplesToDate.getInstancia().getFecha(
          this.manifestacion.getStartTime(), monitor.getFsGlobal(), false,
          monitor.getFsGlobal() > 1)));
      this.instante_fin.setText("");
      this.comentario.setText(manifestacion.getComentario());

      //Limpado de la tabla:
      int num_filas = table.getRowCount();
      table.clearSelection();
      table.editCellAt(0, num_filas - 1);
      table.editCellAt(num_filas, 0);

      LinkedList lista_atributos = manifestacion.getAtributos();
      ListIterator it = lista_atributos.listIterator();
      int count = 0;
      while (it.hasNext()) {
        Attribute atributo_tmp = (Attribute) it.next();
        String atributo_text = atributo_tmp.getAtributo();
        String valor = atributo_tmp.getValor();
        table.setValueAt(atributo_text, count, 0);
        table.setValueAt(valor, count, 1);
        count++;
      }
    }

    this.nuevo = nuevo;
    if (frame != null) {
      Point d = frame.getLocation();
      Dimension t = frame.getSize();
      setLocation( (int) (d.x + t.getWidth() / 4),
          (int) ( (d.y) + t.getHeight() / 2 - 250));
    }
    super.show();
  }

  void aceptar_actionPerformed(ActionEvent e) {

    String fecha_inicio = this.instante_comienzo.getText();
    SamplesToDate sample_date = SamplesToDate.getInstancia();
    long tiempo_en_muestras_long = sample_date.getSamplesTill(fecha_inicio,
        monitor.getFsGlobal());
    if (tiempo_en_muestras_long != Long.MIN_VALUE) {
      int tiempo_en_muestras = (int) tiempo_en_muestras_long;
      LinkedList atributos = new LinkedList();
      String nombre = this.nombre.getText();
      String comentario = this.comentario.getText();
      String fecha_fin = this.instante_fin.getText();
      this.tipo_manifestacion = categoria.getSelectedIndex() + 1;

      table.editCellAt(0, table.getRowCount() - 1);
      for (int i = 0; i < table.getRowCount(); i++) {
        Object atributo_tmp = table.getValueAt(i, 0);
        Object valor_tmp = table.getValueAt(i, 1);
        if (atributo_tmp == null) {
          break;
        }
        atributos.add(new Attribute( (String) atributo_tmp, (String) valor_tmp));
      }

      Manifestacion manifestacion = new Manifestacion(nombre, comentario,
          tipo_manifestacion, atributos);
      //
      manifestacion.setTiempo(tiempo_en_muestras);
      manifestacion.setTipo(net.javahispano.jsignalmonitor.annotations.
          ClinicalEvent.MANIFESTACION);
      //Primero eliminados la anotación antigua, en el caso en el que estemos modificando
      // una anotacion, luego anadimos la nueva
      if (!this.nuevo) {
        cliente.deleteAnnotation(this.manifestacion);
      }
      cliente.addAnnotation(manifestacion);
      this.setVisible(false);
    }
    else {
      SamplesToDate.getOptionpane().showConfirmDialog(this,
          SamplesToDate.getTextoAdevertenciaFecha(),
          "ERROR!!!", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
    }
    panel_anotacion.repaint();
  }

  void cancelar_actionPerformed(ActionEvent e) {
    this.setVisible(false);
  }

  void borrar_actionPerformed(ActionEvent e) {
    cliente.deleteAnnotation(manifestacion);
    this.setVisible(false);
    panel_anotacion.repaint();
  }

  void instante_comienzo_actionPerformed(ActionEvent e) {

  }

  void instante_fin_actionPerformed(ActionEvent e) {

  }

  public Manifestacion getManifestacion() {
    return manifestacion;
  }

  public void setManifestacion(Manifestacion _manifestacion) {
    manifestacion = _manifestacion;
  }

}


class MyJTable extends JTable {
  static ResourceBundle res = ResourceBundle.getBundle("pintar.i18n.Res_es_ES");
  private String[] colum_name = {"WW", "DD"};
  public MyJTable(int i, int u) {
    super(i, u);
  }

  public String getColumnName(int colum) {
    return colum_name[colum];
  }
}
