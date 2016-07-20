//Source file: E:\\JAVA2\\MonitorizacionAlgoritmo\\src\\pintar\\CacheDatos.java

package net.javahispano.jsignalmonitor;

/**
 * Esta clase se halla en un estado muy temprano de desarrollo. Pretende ser una
 * cache de datos (y marcas en el futuro), para uqe no sea necesario pedirle los datos
 * al cliente siempre que el usuario obligue a repintar la ventana. Cuando se emplee
 * una conexión de red esta clase aumentará notablemente el performace del entorno.
 *
 * @version 	0.6
 * @author 	Abraham Otero Quintana
 */
class CacheDatos {
  private boolean tiempo_real;
  private float datos[];

  /**
   * Es el primer dato que poseemos cacheado
   */
  private int primer_dato = 0;
  private int primer_dato_pos = 0;

  /**
   * Es el ultimo dato que está cacheado para cada canal.
   */
  private int ultimo_dato;
  private int ultimo_dato_pos;
  private byte pos_datos[];
  private int num_canal;
  private JSignalMonitor monitor;
  private Configuration configuration;

  /**
   * @param tamano_cache - Es el tamaño del array de datos que va a almacenar la
   * cache, independientemente de el tamaño del canal.
   * @param monitor
   * @param enable_pos@param num_canal
   */
  CacheDatos(JSignalMonitor monitor, int tamano_cache, boolean enable_pos,
      int num_canal,
      Configuration configuration) {
    this.num_canal = num_canal;
    this.configuration = configuration;
    this.monitor = monitor;
    datos = new float[tamano_cache];
    if (enable_pos) {
      pos_datos = new byte[tamano_cache];
    }
  }

  /**
   * @param primero - primer dato que deseamos que nos envien
   * @param ultimo - Ultimo dato que deseamos que nos envien. Este tomará un valor
   * minimo igual al tamaño del canal, idicando en ese caso que deseamos todos los
   * datos disponibles, en caso de haber menos datos que la longitud del canal en
   * pixeles.
   * @return float[]
   */
  float[] getDatos(int primero, int ultimo) {

    float[] datos_tmp;
    try {
      //Si los datos no estan en la cache los pedimos
      if ( (primero <= primer_dato || ultimo >= ultimo_dato)) {

        //NOTrellenamos la petición, nos situamos en el dato medio que nos piden y pedimos
        //NOTmedio tamano de la cache hacia atras y medio tamano de la cache acia adelante
        //Pedimos desde el ultimo dato que tenemos hacia atras. Mas facil, menos eficiente
        //Habría que mirar de hacer lo otro
        /* datos = monitor.getData(num_canal,Math.max(ultimo - configuration.getTamano_cache(),0),
            ultimo );
                 primer_dato = ultimo - datos.length;
                 ultimo_dato = ultimo;*/
        //Sin cache
        datos = monitor.getData(num_canal, primero, ultimo);
        primer_dato = primero;
        ultimo_dato = primero + datos.length;

      }

      int datos_pedidos = ultimo - primero;
      datos_tmp = new float[ (int) (datos_pedidos)];
      //Si primero es el primer dato que nos piden y primer_dato es el primer
      //dato contenido en la cache primero . primer_dato es la posicion de ese
      //dato en la cache. Igualmente la posicion del ultimo dato en la cahe es
      //ultimo - primer dato.
      for (int i = primero - primer_dato; i < (ultimo_dato - primer_dato); i++) {
        //primer_dato - primero = donde esta el primer elemento que me piden

        //El array que vamos a devolver, datos_tmp, debe conter en 0 el primer
        //dato pedido, mientras que en la cache este dato esta en i
        datos_tmp[i - primero + primer_dato] = datos[i];

      }
    }
    catch (Exception ex) {
      System.out.println("Erro en peticion a Cache datos");
      datos_tmp = null; // new float[4];/*OJO*/

    }
    return datos_tmp;
  }

  /**
   * @param primero - primer dato que deseamos que nos envien
   * @param ultimo - Ultimo dato que deseamos que nos envien. Este tomará un valor
   * minimo igual al tamaño del canal, idicando en ese caso que deseamos todos los
   * datos disponibles, en caso de haber menos datos que la longitud del canal en
   * pixeles.
   * @return int[]
   */
  byte[] getPos(int primero, int ultimo) {
    byte[] pos_tmp;
    try {
      //Si los datos no estan en la cache los pedimos
      if (primero <= primer_dato_pos || ultimo >= ultimo_dato_pos) {
        //NOTrellenamos la petición, nos situamos en el dato medio que nos piden y pedimos
        //NOTmedio tamano de la cache hacia atras y medio tamano de la cache acia adelante
        //Pedimos desde el ultimo dato que tenemos hacia atras. Mas facil, menos eficiente
        //Habría que mirar de hacer lo otro
        /* pos_datos = monitor.getPos(num_canal,Math.max(ultimo - configuration.getTamano_cache(),0),
             ultimo);
         primer_dato_pos = ultimo - datos.length;
         ultimo_dato_pos = ultimo;*/
        //Sin cache
        pos_datos = monitor.getPos(num_canal, primero, ultimo);
        primer_dato_pos = primero;
        ultimo_dato = primero + datos.length;

      }

      pos_tmp = new byte[ultimo - primero];
      for (int i = primero - primer_dato_pos;
          i < (ultimo_dato - primer_dato_pos); i++) {
        //primer_dato - primero = donde esta el primer elemento que me piden

        //El array que vamos a devolver, datos_tmp, debe conter en 0 el primer
        //dato pedido, mientras que en la cache este dato esta en i
        pos_tmp[i - primero + primer_dato_pos] = pos_datos[i];
      }

    }
    catch (Exception ex) {
      System.out.println("Erro en peticion a Cache pos");
      pos_tmp = null; // new int[4];/*OJO*/
    }
    return pos_tmp;
  }

  /**
   * @param datos - Será posible que los datos se añadan de uno en uno o de varios
   * en varios, por ese motivo se emplea  un vector para almacenarlos.
   * @param pos
   * @param canal
   */
  void anadeDato(float[] datos, byte[] pos) {
    float datos_tmp[] = new float[configuration.getTamanoCache()];
    byte pos_datos_tmp[] = new byte[configuration.getTamanoCache()];
    //datos.length es el numero de datos nuevos. Tenemos que "librar" las datos.length
    //primeroa posiciones del array, desplazandolas hacia arriba todos los elementos, y
    //en esas primeras posiciones metemos los datos nuevos.
    for (int i = datos.length; i < this.datos.length; i++) {
      datos_tmp[i] = this.datos[i - datos.length];
      pos_datos_tmp[i] = this.pos_datos[i - datos.length];
    }
    //Rellenamos las primeras posiciones del array con datos nuevos
    for (int i = 0; i < datos.length; i++) {
      pos_datos_tmp[i] = pos[i];
    }
    //El vectos tmp pasa a ser el del objeto.
    this.pos_datos = pos_datos_tmp;
  }

  /**
   * @param datos
   * @param pos
   * @param canal
   */
  void actualizaDatos(float[] datos, byte[] pos) {
    this.pos_datos = pos;
    actualizaDatos(datos);
  }

  /**
   * @param datos
   * @param canal
   */
  void actualizaDatos(float[] datos) {
    this.datos = datos;
    pos_datos = null;
  }

  /**
   * @param b
   */
  void setTirmpoReal(boolean b) {
    this.tiempo_real = b;
  }

  /**
   * @return boolean
   */
  boolean isTiempoReal() {
    return this.tiempo_real;
  }

  /**
   * @return int
   */
  int getNumeroCanal() {
    return num_canal;
  }

  /**
   * @param _num_canal
   */
  void setNumeroCanal(int _num_canal) {
    num_canal = _num_canal;
  }

  /**
   * Invalida la cache.
   */
  void invalidaCache() {
    //   this.primer_dato=0;
    //  this.ultimo_dato = 0;
  }
}
