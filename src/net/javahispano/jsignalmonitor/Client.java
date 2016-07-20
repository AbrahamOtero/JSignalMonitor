package net.javahispano.jsignalmonitor;

import java.util.*;

import net.javahispano.jsignalmonitor.annotations.*;


/**
 * Esta interfaz ha de ser implementada por la calse que desee ser monitorizada.
 * Dicha clase recibir�, v�a los metordos de esta interfaz, peticiones de datos
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
   * M�todo del cliente que devuelve los datos que se deben representar. El n�mero
   * de canal empieza a contar en el cero. El segundo argumento es el primer dato
   * que se te ve de volver, y el tercero y �ltimo dato que tambi�n se debe volver.
   * Si el �ltimo dato no se devolviese jSignalMonitor a�adir�a un 0 al final del
   * registro.
   * El objeto que disiente en debe de volver tiene que ser un array de floats;
   * se han empleado el tipo gen�rico Object para mantener compatibilidad con
   * futuras versiones de la librer�a.
   * @param primero - primer dato que deseamos que nos envien
   * @param ultimo - Ultimo dato que deseamos que nos envien. Este tomar� un valor
   * minimo igual al tama�o del canal, idicando en ese caso que deseamos todos los
   * datos disponibles, en caso de haber menos datos que la longitud del canal en
   * pixeles.
   * @param num_canal - Canal al caul corresponden los datos.
   * @return float[]
   */
  public Object getData(int num_canal, int primero, int ultimo);

  /**
   * Cuando jSignalMonitor funcionen tiempo real este m�todo debe devolver el tama�o
   * total del registro en cada momento. El funcionamiento de no tiempo real no es
   * necesario implementar este m�todo.
   * @return int
   */
  public int getTotaNumberOfData();

  /**
   *Este m�todo permite colorear cada valor de la se�al que, de alg�n modo, se
   * especialmente representativo o importante para la tarde el curso. Para cada
   * canal y para cada dato debe devolver un valor, un byte comprendido entre cero
   * y 100, que indica de alg�n modo cu�n importante es ese dato. Si el valor de
   *  vuelto para un dato es cero se colorear� de negro, si 0 < valor y < = 20
   * corresponder� con verde; si 20 < valor < = 40 amarillo; si 40 < valor < = 60
   * Rosa; si 60 < valor < = 80 naranja; si valor > 80 rojo.
   * El m�todo traduceColor de la clase  Channel es la que se encarga de traducir el bite a un color.
   * @param primero - primer dato que deseamos que nos envien
   * @param ultimo - Ultimo dato que deseamos que nos envien. Este tomar� un valor
   * minimo igual al tama�o del canal, idicando en ese caso que deseamos todos los
   * datos disponibles, en caso de haber menos datos que la longitud del canal en
   * pixeles.
   * @param num_canal - Canal al caul corresponden los datos.
   * @return int[]
   */
  public byte[] getPossibility(int num_canal, int primero, int ultimo);

  /**
   * Devuelve, para cada canal, las marcas que han sido realizadas entre los �ndices
   *  de notados por el segundo y el tercer par�metro. Los objetos que espera recibir
   *  son de tipo Mark. El m�todo de una Mark que permite saber sobre qu� �ndice
   *  temporal se realiz� es getStartTime().
   * @param num_canal
   * @param primero
   * @param ultimo
   * @return java.util.LinkedList
   */
  public LinkedList getMarks(int num_canal, int primero, int ultimo);

  /**
   * Devuelve las anotaciones realizadas sobre el registro entre los �ndices de
   * notados por los argumentos. Actualmente las anotaciones de jSignalMonitor est�n
   * en un formato que se adapta bastante bien con la monitorizaci�n de pacientes;
   * sin embargo para otros entornos puede no ser �tiles. El futuras versiones se
   * desarrollar�n un mecanismo de plugins que permita al usuario definir sus propias
   * anotaciones as� como en modo en el cual desean visualizar las en la parte
   * superior de jSignalMonitor.
   * El m�todo de una Annotation que permite saber sobre qu� �ndice
   * temporal se realiz� es getStartTime()
   * valores pedidos.
   * @param num_canal
   * @param primero
   * @param ultimo
   * @return java.util.LinkedList
   */
  public LinkedList getAnnotations(int primero, int ultimo);

  /**
   * Este m�todo ser� invocado por jSignalMonitor para notificar al cliente que el
   * usuario ha eliminado la anotaci�n que se pasa como argumento. El cliente deber�
   * actualizar su propio modelo de datos y eliminando esa anotaci�n. El cliente
   * tambi�n puede eliminar anotaciones mediante los m�todos de jSignalMonitor.
   * @param anotacion
   */

  public void deleteAnnotation(Annotation anotacion);

  /**
   * Este m�todo es invocado por jSignalMonitor para notificar al cliente que el
   * usuario ha borrado en el canal indicado la marca indicada por el segundo
   * argumento. El cliente puede eliminar marcas a trav�s del API de jSignalMonitor
   * @param marca
   */
  public void deleteMark(int canal, Mark anotacion);

  /**
   * Informa al cliente, con objetivo de que este actualice su modelo de datos,
   * de que el usuario ha a�adido una anotaci�n al registro. El cliente tambi�n
   * puede a�adir anotaciones al registro mediante el API de jSignalMonitor.
   * @param anotacion
   */
  public void addAnnotation(Annotation anotacion);

  /**
   * Informa el cliente, con objetivo de que este actualice su modelo de datos,
   * de que el usuario ha realizado en el canal  indicado una marca. El cliente
   * tambi�n puede a�adir marcas dir�an que el API de jSignalMonitor.
   * @param canal
   * @param anotacion
   */
  public void anadMark(int canal, Mark marca);

  /**
   * Informal cliente de una de una selecci�n realizada por el usuario. Para entrar
   * en modo selecci�n el cliente debe invocar el m�todo * * * de jSignalMonitor.
   * Desde ese momento el usuario podr� seleccionar un fragmento de se�al sobre uno
   * de los canales realizando para ello un par de quites con el rat�n que delimitan
   * el fragmento de se�ales. Durante la selecci�n posible que jSignalMonitor pida
   * datos el cliente que no se est� en representando en el momento en el que se
   * inici� la selecci�n.
   *
   * Al final de la selecci�n jSignalMonitor informa al cliente acerca de y el n�mero
   * de canal sobre el cual el usuario realiz� la selecci�n, y los �ndices de la
   * primera y y la �ltima muestra de se�al que han sido seleccionados. Deber� ser
   * el cliente el que corresponda a esta selecci�n con las acciones pertinentes.
   * @param ditancia en ms
   */

  public void selectionFinished(int num_canal, int t1, int t2);

  /**
   * Este m�todo de vuelve un objeto locale indicando la localizaci�n a emplear.
   * Actualmente jSignalMonitor s�lo soporta ingl�s de Estados Unidos, de Gran Breta�a
   * y espa�ol. Si este m�todo devuelve null (este es el comportamiento implementado
   * en la clase ClientAdapter) se emplear� la localizaci�n por defecto de la m�quina.
   * @return
   */
  public Locale getLocale();
}
