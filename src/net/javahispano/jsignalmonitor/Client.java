package net.javahispano.jsignalmonitor;

import java.util.*;

import net.javahispano.jsignalmonitor.annotations.*;


/**
 * Esta interfaz ha de ser implementada por la calse que desee ser monitorizada.
 * Dicha clase recibirá, vía los metordos de esta interfaz, peticiones de datos
 *  y notificaciones de nuevas marcas y anotaciones realizadas por el usuario.
 *
 *
 * @version 	0.6
 * @author 	Abraham Otero Quintana
 */
public interface Client {

  public int GRID_NORMAL = 12;
  public int GRID_LOW_DENSITY = 10;
  public int GRID_VERY_LOW_DENSITY = 6;
  public int GRID_HIGH_DENSITY = 20;
  public int GRID_DENSE = 16;
  public int WITHOUT_GRID = -1;
  public int GRID_PERSONALIZED = 0;

  /**
   * Método del cliente que devuelve los datos que se deben representar. El número
   * de canal empieza a contar en el cero. El segundo argumento es el primer dato
   * que se te ve de volver, y el tercero y último dato que también se debe volver.
   * Si el último dato no se devolviese jSignalMonitor añadiría un 0 al final del
   * registro.
   * El objeto que disiente en debe de volver tiene que ser un array de floats;
   * se han empleado el tipo genérico Object para mantener compatibilidad con
   * futuras versiones de la librería.
   * @param primero - primer dato que deseamos que nos envien
   * @param ultimo - Ultimo dato que deseamos que nos envien. Este tomará un valor
   * minimo igual al tamaño del canal, idicando en ese caso que deseamos todos los
   * datos disponibles, en caso de haber menos datos que la longitud del canal en
   * pixeles.
   * @param num_canal - Canal al caul corresponden los datos.
   * @return float[]
   */
  public Object getData(int num_canal, int primero, int ultimo);

  /**
   * Cuando jSignalMonitor funcionen tiempo real este método debe devolver el tamaño
   * total del registro en cada momento. El funcionamiento de no tiempo real no es
   * necesario implementar este método.
   * @return int
   */
  public int getTotaNumberOfData();

  /**
   *Este método permite colorear cada valor de la señal que, de algún modo, se
   * especialmente representativo o importante para la tarde el curso. Para cada
   * canal y para cada dato debe devolver un valor, un byte comprendido entre cero
   * y 100, que indica de algún modo cuán importante es ese dato. Si el valor de
   *  vuelto para un dato es cero se coloreará de negro, si 0 < valor y < = 20
   * corresponderá con verde; si 20 < valor < = 40 amarillo; si 40 < valor < = 60
   * Rosa; si 60 < valor < = 80 naranja; si valor > 80 rojo.
   * El método traduceColor de la clase  Channel es la que se encarga de traducir el bite a un color.
   * @param primero - primer dato que deseamos que nos envien
   * @param ultimo - Ultimo dato que deseamos que nos envien. Este tomará un valor
   * minimo igual al tamaño del canal, idicando en ese caso que deseamos todos los
   * datos disponibles, en caso de haber menos datos que la longitud del canal en
   * pixeles.
   * @param num_canal - Canal al caul corresponden los datos.
   * @return int[]
   */
  public byte[] getPossibility(int num_canal, int primero, int ultimo);

  /**
   * Devuelve, para cada canal, las marcas que han sido realizadas entre los índices
   *  de notados por el segundo y el tercer parámetro. Los objetos que espera recibir
   *  son de tipo Mark. El método de una Mark que permite saber sobre qué índice
   *  temporal se realizó es getStartTime().
   * @param num_canal
   * @param primero
   * @param ultimo
   * @return java.util.LinkedList
   */
  public LinkedList getMarks(int num_canal, int primero, int ultimo);

  /**
   * Devuelve las anotaciones realizadas sobre el registro entre los índices de
   * notados por los argumentos. Actualmente las anotaciones de jSignalMonitor están
   * en un formato que se adapta bastante bien con la monitorización de pacientes;
   * sin embargo para otros entornos puede no ser útiles. El futuras versiones se
   * desarrollarán un mecanismo de plugins que permita al usuario definir sus propias
   * anotaciones así como en modo en el cual desean visualizar las en la parte
   * superior de jSignalMonitor.
   * El método de una Annotation que permite saber sobre qué índice
   * temporal se realizó es getStartTime()
   * valores pedidos.
   * @param num_canal
   * @param primero
   * @param ultimo
   * @return java.util.LinkedList
   */
  public LinkedList getAnnotations(int primero, int ultimo);

  /**
   * Este método será invocado por jSignalMonitor para notificar al cliente que el
   * usuario ha eliminado la anotación que se pasa como argumento. El cliente deberá
   * actualizar su propio modelo de datos y eliminando esa anotación. El cliente
   * también puede eliminar anotaciones mediante los métodos de jSignalMonitor.
   * @param anotacion
   */

  public void deleteAnnotation(Annotation anotacion);

  /**
   * Este método es invocado por jSignalMonitor para notificar al cliente que el
   * usuario ha borrado en el canal indicado la marca indicada por el segundo
   * argumento. El cliente puede eliminar marcas a través del API de jSignalMonitor
   * @param marca
   */
  public void deleteMark(int canal, Mark anotacion);

  /**
   * Informa al cliente, con objetivo de que este actualice su modelo de datos,
   * de que el usuario ha añadido una anotación al registro. El cliente también
   * puede añadir anotaciones al registro mediante el API de jSignalMonitor.
   * @param anotacion
   */
  public void addAnnotation(Annotation anotacion);

  /**
   * Informa el cliente, con objetivo de que este actualice su modelo de datos,
   * de que el usuario ha realizado en el canal  indicado una marca. El cliente
   * también puede añadir marcas dirían que el API de jSignalMonitor.
   * @param canal
   * @param anotacion
   */
  public void anadMark(int canal, Mark marca);

  /**
   * Informal cliente de una de una selección realizada por el usuario. Para entrar
   * en modo selección el cliente debe invocar el método * * * de jSignalMonitor.
   * Desde ese momento el usuario podrá seleccionar un fragmento de señal sobre uno
   * de los canales realizando para ello un par de quites con el ratón que delimitan
   * el fragmento de señales. Durante la selección posible que jSignalMonitor pida
   * datos el cliente que no se está en representando en el momento en el que se
   * inició la selección.
   *
   * Al final de la selección jSignalMonitor informa al cliente acerca de y el número
   * de canal sobre el cual el usuario realizó la selección, y los índices de la
   * primera y y la última muestra de señal que han sido seleccionados. Deberá ser
   * el cliente el que corresponda a esta selección con las acciones pertinentes.
   * @param ditancia en ms
   */

  public void selectionFinished(int num_canal, int t1, int t2);

  /**
   * Este método de vuelve un objeto locale indicando la localización a emplear.
   * Actualmente jSignalMonitor sólo soporta inglés de Estados Unidos, de Gran Bretaña
   * y español. Si este método devuelve null (este es el comportamiento implementado
   * en la clase ClientAdapter) se empleará la localización por defecto de la máquina.
   * @return
   */
  public Locale getLocale();
}
