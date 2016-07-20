//Source file: E:\\JAVA2\\MonitorizacionAlgoritmo\\src\\pintar\\Fecha.java

package net.javahispano.jsignalmonitor;

/**
 *
 * @version 	0.6
 * @author 	Abraham Otero Quintana
 */
import java.util.*;


public class Fecha extends GregorianCalendar implements Cloneable {
  private int ano;
  private int mes;
  private int dia;
  private int hora;
  private int minutos;
  private int segundos;
  private static Fecha fecha_misma;
  private static boolean ya_hay_fecha = false;

  /**
   * @param ano
   * @param mes
   * @param dia
   * @param hora
   * @param minutos
   * @param segundos
   */
  public Fecha(int ano, int mes, int dia, int hora, int minutos, int segundos) {
    super(ano, mes, dia, hora, minutos, segundos);
    this.ano = ano;
    this.dia = dia;
    this.mes = mes;
    this.hora = hora;
    this.minutos = minutos;
    this.segundos = segundos;
    Fecha.fecha_misma = this;
  }

  /**
   * @return long
   */
  public long getTimeInMillis() {
    return super.getTimeInMillis();
  }

  /**
   * @return Object
   */
  public static Fecha getCopiaFecha(Fecha fecha) {
    if (fecha != null) {
      fecha = new Fecha(fecha.ano, fecha.mes, fecha.dia,
          fecha.hora, fecha.minutos, fecha.segundos);
    }
    else {
      fecha = new Fecha(200, 1, 1, 0, 0, 0);
      /*fecha.dia = Fecha.fecha_misma.dia;
        fecha.ano = Fecha.fecha_misma.ano;
        fecha.mes = Fecha.fecha_misma.mes;
        fecha.hora = Fecha.fecha_misma.hora;
        fecha.minutos = Fecha.fecha_misma.minutos;
        fecha.segundos = Fecha.fecha_misma.segundos;*/
    }
    return fecha;
  }

  public String toString(char sep) {
    /* return Integer.toString(ano)+ sep + Integer.toString(mes)+ sep +
      Integer.toString(dia)+sep + Integer.toString(hora) + sep +
      Integer.toString(minutos)+sep + Integer.toString(segundos)+sep;*/
    return this.getTime().toString();
  }

  /*   public  Object clone()
     {
          Gregor g = new Gregor(ano, mes, dia, hora, minutos, segundos);
          return g;
     }*/
}
