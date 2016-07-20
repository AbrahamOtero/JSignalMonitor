package net.javahispano.jsignalmonitor.annotations.UI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import net.javahispano.jsignalmonitor.annotations.*;


/**
 * Esta clase pretende ser algo editable que se representa durante la
 * monitorización, tantio un marca como un Evento. Al seleccionarse el debe ser
 * capaz de saber en que tipo de ventana debe mostrar la información de la
 *
 * @version 	0.6
 * @author 	Abraham Otero Quintana
 */
public class GraphicEvent extends JRadioButton implements ActionListener {

  private static ImageIcon anotaciong;
  private static ImageIcon terapia;
  private static ImageIcon manifestacion;
  private static ImageIcon diagnostico;
  private JDialog ventana;
  private net.javahispano.jsignalmonitor.annotations.ClinicalEvent evento;
  private int tiempor_inicio, posicion_y;
  private int num_letral;

  static {
    anotaciong = new ImageIcon(net.javahispano.jsignalmonitor.annotations.
        UI.GraphicEvent.class.getResource("marca.gif"));
    terapia = new ImageIcon(net.javahispano.jsignalmonitor.annotations.UI.
        GraphicEvent.class.getResource("terapia.gif"));
    manifestacion = new ImageIcon(net.javahispano.jsignalmonitor.annotations.UI.
        GraphicEvent.class.getResource("manifestacion.gif"));
    diagnostico = new ImageIcon(net.javahispano.jsignalmonitor.annotations.UI.
        GraphicEvent.class.getResource("diagnostico.gif"));
  }

  public GraphicEvent() {

  }

  public GraphicEvent(net.javahispano.jsignalmonitor.annotations.
      ClinicalEvent evt, int tiempor_inicio, JDialog ventana, int posicion_y) {
    evento = evt;
    this.setText(evt.getTexto());
    num_letral = evt.getTexto().length();
    this.setBounds(tiempor_inicio, posicion_y, num_letral * 8 + 25, 20);
    this.setBackground(java.awt.Color.white);
    this.addActionListener(this);
    this.ventana = ventana;
    this.tiempor_inicio = tiempor_inicio;
    this.posicion_y = posicion_y;
    this.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }

  }

  public void GraphicAnotacion() {

  }

  public void actionPerformed(ActionEvent e) {
    switch (evento.getTipo()) {
      case net.javahispano.jsignalmonitor.annotations.ClinicalEvent.
          TERAPIA: {
        WindowDefineTheraphy v_tmp = (WindowDefineTheraphy) ventana;
        v_tmp.setTerapia( (Therapy) evento);
        v_tmp.mostrar(false);
        break;
      }
      case net.javahispano.jsignalmonitor.annotations.ClinicalEvent.
          DIAGNOSTICO: {
        WindowDefineDiagnostic v_tmp = (WindowDefineDiagnostic) ventana;
        v_tmp.setDiagnostico( (Diagnostic) evento);
        v_tmp.mostrar(false /*,true*/);
        break;
      }
      case net.javahispano.jsignalmonitor.annotations.ClinicalEvent.
          MANIFESTACION: {
        WindowDefineFinding v_tmp = (WindowDefineFinding) ventana;
        v_tmp.setManifestacion( (Manifestacion) evento);
        v_tmp.mostrar(false);
        break;
      }
      default: {
        WindowDefineMark v_tmp = (WindowDefineMark) ventana;
        v_tmp.setMarca( (Mark) evento);
        v_tmp.mostrar(false);
      }
    }

  }

  private void jbInit() throws Exception {

    //Dependiendo del jdk los insets son distintos. Como no me interesan:
    this.setMargin(new Insets(0, 0, 0, 0));
    switch (evento.getTipo()) {
      case net.javahispano.jsignalmonitor.annotations.ClinicalEvent.
          TERAPIA: {
        this.setIcon(terapia);
        Therapy ter = (Therapy) evento;
        String nombre = ter.getTexto() + " " + ter.getDosificacion();
        this.setBounds(tiempor_inicio, posicion_y + 2, nombre.length() * 8 + 30,
            16);
        this.setText(nombre);
        break;
      }
      case net.javahispano.jsignalmonitor.annotations.ClinicalEvent.
          DIAGNOSTICO: {
        this.setIcon(manifestacion);
        break;
      }
      case net.javahispano.jsignalmonitor.annotations.ClinicalEvent.
          MANIFESTACION: {
        this.setIcon(diagnostico);
        this.setBounds(tiempor_inicio, posicion_y, num_letral * 8 + 25, 19);
        break;
      }
      default: {
        this.setIcon(anotaciong);
        this.setSize(9, 33);
      }
    }
  }
}
