package net.javahispano.jsignalmonitor;

import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import net.javahispano.jsignalmonitor.annotations.*;

/**
 * jSignalMonitor , junto a la interfaz Client, y la Clase ClientAdapter, es la
 * interfaz de la librer�a. El general jSignalMonitor se que a quien gestione
 * las peticiones del del cliente (entendiendo por cliente el software que
 * emplea la librer�a) respecto a la visualizaci�n de las se�ales. La clase y
 * interfaz anteriormente mencionadas ser�n las encargadas de gestionar las
 * peticiones de jSignalMonitor hacia el cliente pidi�ndole los datos que �ste
 * necesita visualizar en cada momento.
 *
 * Mediante esta clase se gestiona la creaci�n del monitor, la eliminaci�n,
 * creaci�n y modificaci�n din�mica de los canales obre los cuales se reprenta
 * la se�al...
 *
 *
 * @version 0.6
 * @author Abraham Otero Quintana
 */
public class JSignalMonitor extends JPanel implements AdjustmentListener {

    static ResourceBundle res;

  //El primero indica si ahora mismo es tiempo real, y el segundo si el cliente
    //Pidi� tiempo real
    private boolean realTimeNow, realTimeDemadedyClient;
    private PanelAnnotation panel_anotacion;
    private int lastTemporalScale = 1;

    //Frecuencia de Muestreo
    private net.javahispano.jsignalmonitor.PanelContainerOfChannels panel;
    private JScrollBar scroll = new JScrollBar();
    private Client client;
    private BorderLayout borderLayout1 = new BorderLayout();
    private JSplitPane jSplitPane1 = new JSplitPane();
    private JScrollPane scrollPAneAddAnnotations = new JScrollPane(); // JPanel ();

    /**
     * @todo ver que hago con esto
     */
    private JFrame frame;
    BorderLayout borderLayout2 = new BorderLayout();
    private Configuration configuration;

    /**
     * @param frame Frame en el cual se ha incluido el monitor. Si es un applet
     * o no se desea emplear las marcas y anotaciones hacer que este par�metro
     * sea null. Este dato es empleado �nicamente por las ventanas de definir
     * marcas y anotaciones para posicionarse en pantalla. Puede hacerse null en
     * cualquier caso.
     * @param cliente Cliente que va a proporcionar los datos a visualizar.
     * @param numeroCanales Indica el numero de canales iniciales que va a
     * tener la interfaz de monitorizacion. Puede ser 0.
     * @param enablePos Indica para cada cadnal si se va a pintar la se�al en
     * ciertos tramos de un color distinto o no. El color simboliza, en
     * principio, una posibilidad de ocurrencia de un determinado evento sobre
     * la senal, la posibilidad se le pasa como un byte entre 0 y 100.
     * @param nombreSenhal Nombre de cada una de las se�ales que se esta
     * monitorizando. Se asume que la primera posicion del array contiene el
     * nombre de la primeraa se�al y asi sucesivamente.
     * @param anotaciones - Indica si se ha de permitir crear antaciones y
     * marcas, y si se va a visualizar el panel de las anotaciones.
     * @param fechaInicio Fecha que se corresponde con el inicio de la
     * monitorizacion. Al indicar una fehc ade inicio se asume que se emplear�n
     * fechas, y no un �nice entero en la visualizaci�n de los datos. Formato: HH:mm:ss dd/MM/yyyy
     */
    public JSignalMonitor(JFrame frame, Client cliente, int numeroCanales,
            boolean[] enablePos, String[] nombreSenhal, boolean anotaciones,
            String fechaInicio) {
        this(frame, cliente, numeroCanales, enablePos, nombreSenhal,
                anotaciones);
        SamplesToDate.getInstancia().setFechaBase(fechaInicio);
        configuration.setUsarFecha(true);
    }

    /**
     * @param frame Frame en el cual se ha incluido el monitor. Si es un applet
     * o no se desea emplear las marcas y anotaciones hacer que este par�metro
     * sea null. Este dato es empleado �nicamente por las ventanas de definir
     * marcas y anotaciones para posicionarse en pantalla. Puede hacerse null en
     * cualquier caso.
     * @param cliente Cliente que va a proporcionar los datos a visualizar.
     * @param numeroCanales Indica el numero de canales iniciales que va a
     * tener la interfaz de monitorizacion. Puede ser 0.
     * @param enablePos Indica para cada cadnal si se va a pintar la se�al en
     * ciertos tramos de un color distinto o no. El color simboliza, en
     * principio, una posibilidad de ocurrencia de un determinado evento sobre
     * la senal, la posibilidad se le pasa como un byte entre 0 y 100.
     * @param nombreSenhal Nombre de cada una de las se�ales que se esta
     * monitorizando. Se asume que la primera posicion del array contiene el
     * nombre de la primeraa se�al y asi sucesivamente.
     * @param anotaciones - Indica si se ha de permitir crear antaciones y
     * marcas, y si se va a visualizar el panel de las anotaciones.
     */
    public JSignalMonitor(JFrame frame, Client cliente, int numeroCanales,
            boolean[] enablePos, String[] nombreSenhal, boolean anotaciones) {
        configuration = new Configuration();
        if (cliente.getLocale() != null) {
            configuration.setLocalidad(cliente.getLocale());
        }
        res = ResourceBundle.getBundle("net.javahispano.jsignalmonitor.i18n.Res",
                configuration.getLocalidad());
        this.frame = frame;
        createMonitor(cliente, numeroCanales, enablePos, nombreSenhal, anotaciones);
        configuration.setFechaInicio(new Fecha(2000, 1, 1, 1, 1, 1));
        SamplesToDate.getInstancia().setFechaBase("01:01:01 01/01/2000");
        String[] nombre_senales = new String[numeroCanales];
        for (int i = 0; i < numeroCanales; i++) {
            nombre_senales[i] = res.getString("Se_al") + i;
        }
        configuration.setNombreSenales(nombre_senales);
    }
    
  /**
     * @param frame Frame en el cual se ha incluido el monitor. Si es un applet
     * o no se desea emplear las marcas y anotaciones hacer que este par�metro
     * sea null. Este dato es empleado �nicamente por las ventanas de definir
     * marcas y anotaciones para posicionarse en pantalla. Puede hacerse null en
     * cualquier caso.
     * @param cliente Cliente que va a proporcionar los datos a visualizar.
     * @param numeroCanales Indica el numero de canales iniciales que va a
     * tener la interfaz de monitorizacion. Puede ser 0.
     * @param nombreSenhal Nombre de cada una de las se�ales que se esta
     * monitorizando. Se asume que la primera posicion del array contiene el
     * nombre de la primeraa se�al y asi sucesivamente.
     */
    public JSignalMonitor(JFrame frame, Client cliente, int numeroCanales,
           String[] nombreSenhal) {
       this(frame, cliente,numeroCanales,null, nombreSenhal, false);
    }
    
    /**
     * @param cliente Cliente que va a proporcionar los datos a visualizar.
     * @param numeroCanales Indica el numero de canales iniciales que va a
     * tener la interfaz de monitorizacion. Puede ser 0.
     * @param nombreSenhal Nombre de cada una de las se�ales que se esta
     * monitorizando. Se asume que la primera posicion del array contiene el
     * nombre de la primeraa se�al y asi sucesivamente.
     */
    public JSignalMonitor(Client cliente, int numeroCanales, String[] nombreSenhal) {
       this(null, cliente,numeroCanales,null, nombreSenhal, false);
    }

    /**
     * Emplear este constructor para crear un jSignalMonitor sin ning�n canal,
     * los canales y la configuraci�n relativa a la se�ales se construir�
     * invocando los m�todos Del API de jSignalMonitor.
     */
    public JSignalMonitor(Client client) {
        configuration = new Configuration();
        panel = new PanelContainerOfChannels(this, scroll, frame, configuration);
        createMonitor(client, 0, null, null,
                false);
        this.setPaint(false);
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Se encarga de la construcci�n de la interfaz del monitor.
     *
     * @param numero_canales
     * @param enable_pos
     * @param nombre_se�al
     * @param unidades_temporales
     * @param leyendas
     * @param anotaciones
     * @param cliente
     */
    private void createMonitor(Client cliente, int numero_canales,
            boolean[] enable_pos,
            String[] nombre_senhal, boolean anotaciones) {
        configuration.setPintar(false);
        this.client = cliente;
        configuration.setPintarAnotaciones(anotaciones);
        configuration.setPaintMarks(anotaciones);
        jSplitPane1.setOneTouchExpandable(true);
        jSplitPane1.setDividerSize(10);
        jSplitPane1.setContinuousLayout(true);

        scroll.addAdjustmentListener(this);

        configuration.setNum_canales(numero_canales);
        configuration.setTamanoCanal(this.getSize());
        if (enable_pos != null) {
            for (int i = 0; i < enable_pos.length; i++) {
                configuration.setEnablePosboolean(i, enable_pos[i]);
            }
        }
        if (nombre_senhal != null) {
            configuration.setNombreSenales(nombre_senhal);
        }

        panel = new PanelContainerOfChannels(this, scroll, frame, configuration);

        if (configuration.isPintar()) {
            scroll.setMinimum(panel.getCanal(0).getTamanoInicialPAnelPintar());
        }

        //Incrementos del scroll
        scroll.setBlockIncrement(100);
        scroll.setUnitIncrement(20);

        jbInit();

        scroll.setVisibleAmount(0 /*visibleAmount*/);
    }

    /**
     * Indica a jSignalMonitor sin debe o no representar los datos en tiempo
     * real. Al representar datos en tiempo real jSignalMonitor estar� pidiendo
     * continuamente los datos m�s recientes al cliente, al no ser que el
     * usuario desplace el scroll de jSignalMonitor hacia la izquierda. En ese
     * caso jSignalMonitor dejar� De pedir los datos en tiempo real, pasando a
     * pedir los datos que necesite.
     *
     * No obstante jSignalMonitor seguir� pidiendo informaci�n el cliente a
     * cerca de la cantidad total de datos disponibles para actualizar los
     * valores del scroll. No les el cliente s�lo necesitan invocar una vez este
     * m�todo para avisar jSignalMonitor de que debe funcionar en tiempo real.
     * JSignalMonitor en cada momento las entradas y las salidas de tiempo real
     * sin necesidad de ninguna intervenci�n por parte del cliente; la �nica
     * responsabilidad de �ste es proveer en cada instante tiempo la informaci�n
     * (datos o tama�o total de registros en ese momento) que jSignalMonitor
     * requiere.
     *
     * Si el cliente desee abandonar el modo de tiempo real, por ejemplo porque
     * ya se ha generado todo los datos del registro y �ste no va a crecer m�s,
     * debe advertir mediante este m�todo al jSignalMonitor esta vez pas�ndole
     * como argumento false
     */
    public void setRealTime(boolean b) {
        if (b) {
            scroll.setValue(scroll.getMaximum() - scroll.getVisibleAmount());
        }
        this.realTimeNow = b;
        this.realTimeDemadedyClient = b;
    }

    /**
     * Informa si ahora mismo se esta en tiempo real.
     *
     * @return boolean
     */
    public boolean isRealTime() {
        return realTimeNow;
    }

    /**
     * Para el canal especificado por el primer argumento indica el rango, valor
     * m�nimo y m�ximo, que se visualizar dan en el canal. El primer elemento
     * del segundo argumento sera el valor minimo a visualizar, y el segundo el
     * m�ximo.
     *
     * @param canal int
     * @param rango float[]
     */
    public void setRange(int canal, float[] rango) {
        configuration.setRangoSenales(canal, rango);
    }

    /**
     * Indica con que color se ha de pintar la se�al del canal especificado.
     *
     * @param canal
     * @param color
     */
    public void setChannelColor(int channel, Color color) {
        configuration.setColorCanal(channel, color);
    }

    /**
     * Empleado por la cache para pedir datos.
     *
     * @param primero - primer dato que deseamos que nos envien
     * @param ultimo - Ultimo dato que deseamos que nos envien. Este tomar� un
     * valor minimo igual al tama�o del canal, idicando en ese caso que deseamos
     * todos los datos disponibles, en caso de haber menos datos que la longitud
     * del canal en pixeles.
     * @param canal
     * @return float[]
     * @todo ver si la actualizacion de la fecha de fin vale para algo
     */
    float[] getData(int canal, int primero, int ultimo) {

        float[] tmp;
        try {
            /*  if ( realTime) {
             int maxDuracion = client.getTotaNumberOfData();
             //Si this es l�stener de este evento se produce una serie de respuestas al evento
             //inecesarias que adem� degradan el funcionamiemto de la app por la fusi�n de
             //eventos de Swing
             scroll.removeAdjustmentListener(this);
             scroll.setMaximum(maxDuracion + scroll.getVisibleAmount());
             scroll.setValue(maxDuracion);
             configuration.setNumero_muestras_disponibles(maxDuracion);
             scroll.addAdjustmentListener(this);
             }*/
            //Por la escala temporal
            int num_datos = ultimo - primero;
      // int num_datos_a_pedir = num_datos /Configuracion.escalado_temporal-1 ;
            // ultimo = primero + num_datos_a_pedir;
            int escaladoTemporal = configuration.getEscaladoTemporal();
            ultimo = ultimo / escaladoTemporal - 1;
            primero = primero / escaladoTemporal;
            //Por la escala temporal
            tmp = (float[]) (client.getData(canal, primero, ultimo));

            //Por la escala temporal
            float datos_escalados[];
            if (escaladoTemporal != 1) {
                datos_escalados = new float[num_datos];
                for (int i = 0; i < tmp.length; i++) {
                    for (int j = 0;
                            j < escaladoTemporal
                            && escaladoTemporal * i + j < datos_escalados.length;
                            j++) {
                        datos_escalados[escaladoTemporal * i + j] = tmp[i];
                    }

                }
                tmp = datos_escalados;
            }

      //Por la escala temporal
            //Actualizamos la fecha de configuracion
            Fecha fecha = Fecha.getCopiaFecha(configuration.getFechaInicio());
            fecha.add(GregorianCalendar.SECOND,
                    (int) (ultimo * configuration.getFs(canal)));
            configuration.setFechaFin(fecha);
        } catch (Exception ex) {
            ex.printStackTrace();
            tmp = null;
        }
        return tmp;
    }

    /**
     * Empleado por la cache para pedir posibilidades.
     *
     * @param primero - primer dato que deseamos que nos envien
     * @param ultimo - Ultimo dato que deseamos que nos envien. Este tomar� un
     * valor minimo igual al tama�o del canal, idicando en ese caso que deseamos
     * todos los datos disponibles, en caso de haber menos datos que la longitud
     * del canal en pixeles.
     * @param canal
     * @return int[]
     */
    byte[] getPos(int canal, int primero, int ultimo) {
        byte tmp[];
        try {
            int num_datos = ultimo - primero;
            int escaladoTemporal = configuration.getEscaladoTemporal();
            ultimo = ultimo / escaladoTemporal - 1;
            primero = primero / escaladoTemporal;

            tmp = client.getPossibility(canal, primero, ultimo);

            //Por la escala temporal
            byte datos_escalados[];
            if (escaladoTemporal != 1) {
                datos_escalados = new byte[num_datos];
                for (int i = 0; i < tmp.length; i++) {
                    for (int j = 0; j < escaladoTemporal
                            && escaladoTemporal * i + j < datos_escalados.length;
                            j++) {
                        datos_escalados[escaladoTemporal * i + j] = tmp[i];
                    }

                }
                tmp = datos_escalados;
            }
        } catch (Exception ex) {
            tmp = null;
        }

        if (tmp == null) {
            //tmp = new int[4];
        }
        return tmp;
    }

    /**
     * Este m�todo se emplea para indicar la duraci�n total del registro. Se
     * emplea cuando todo el registro esta disponible desde el primer mometo.
     * Tambi�n se puede emplear si en alg�n momento durante el uso de este
     * objeto jSignalMonitor cambiarla duraci�n del registro.
     *
     * El segundo par�metro indican si se debe, una vez cambiada la longitud del
     * registro, visualizar el principio de es. Habitualmente esta opci�n se
     * emplea cuando pasamos a visualizar un registro distinto en este objeto
     * jSignalMonitor para visualizar su comienzo.
     *
     * @param duracion_total_registro
     * @param reset si true hace que el scroll tener un valor de0
     */
    public void setTotalDuration(int duracion_total_registro, boolean reset) {
        configuration.setDuracionReal(true);
        configuration.setDuracionRegistro(duracion_total_registro);
        scroll.setMaximum(duracion_total_registro + scroll.getVisibleAmount());
        if (reset) {
            scroll.setValue(0);
        }
    }

    /**
     * En modo tiempo real emplear este metodo para actualizar el numero de
     * muestras disponibles. Se debe actualizar periodicamente tanto en
     * funcionamiento off-line para que el monitor sepa cuantas muestras hay
     * disponibles.
     *
     * @param muestras
     */
    public void updateMaximumNumberOfSamples(int numberOfSamples) {
        configuration.setDuracionRegistro(numberOfSamples);
    }

    /**
     * Indica si las marcas esatn permitas o no.
     *
     * @param b
     */
    public void setMarks(boolean b) {
        configuration.setPaintMarks(b);
    }

    /**
     * emplea para especificar un objeto Configuration al completo. No se
     * recomienda a su uso.
     *
     * @param configuration Configuration
     */
    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    /**
     * Informa si las anotaciones estan activadas o no.
     *
     * @return boolean
     */
    public boolean isAnotaciones() {
        return configuration.isPaintMarks();
    }

    /**
     * Informa si las marcas estan activadas o no.
     *
     * @return boolean
     */
    public boolean isMarks() {
        return configuration.isPaintMarks();
    }

    /**
     * Consruye la parte de UI del monitor.
     */
    private void jbInit() {
        borderLayout1.setHgap(2);
        borderLayout1.setVgap(2);
        this.setLayout(borderLayout1);
        jSplitPane1.setOrientation(JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setLeftComponent(null);
        scroll.setOrientation(JScrollBar.HORIZONTAL);
    // panel_anadir_anotaciones.setLayout(borderLayout2);
        //   panel_anadir_anotaciones.setPreferredSize(new Dimension(100,80));
        //  panel_anadir_anotaciones.setForeground(Color.white);
        panel.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentMoved(ComponentEvent e) {
                panel_componentMoved(e);
            }
        });
        this.add(jSplitPane1, BorderLayout.CENTER);
        this.add(scroll, BorderLayout.SOUTH);
        if (configuration.isPaintMarks()) {
            scrollPAneAddAnnotations.setPreferredSize(new Dimension(1600, 184));
            scrollPAneAddAnnotations.setMaximumSize(new Dimension(2600, 180));
            jSplitPane1.add(scrollPAneAddAnnotations, JSplitPane.LEFT);
            panel_anotacion = new PanelAnnotation(frame, this, client, scroll,
                    configuration.getFsGlobal(),
                    configuration.getMargenIzquierdo(),
                    configuration.getMargenDerecho(),
                    jSplitPane1, configuration);
            //  panel_anadir_anotaciones.add( panel_anotacion);
            scrollPAneAddAnnotations.getViewport().add(panel_anotacion, null);
            scrollPAneAddAnnotations.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scrollPAneAddAnnotations.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            panel_anotacion.setBackground(Color.white);
            //Registramos los escuchadores del raton
            Channel[] canal_tmp = panel.getCanales();
            for (int i = 0; i < canal_tmp.length; i++) {
                panel_anotacion.addMouseMotionListener(canal_tmp[i]);
            }
        } else {
            jSplitPane1.setTopComponent(null);
            jSplitPane1.setDividerSize(0);
        }

        jSplitPane1.add(panel, JSplitPane.RIGHT);
    }

    /**
     * A�ade un canal. Se le pasan los mismos parametros que al contructor, pero
     * con informacion sobre un solo canal. Cuando a�ade un canal puede ser
     * necesario ejecutar un validate y a continuacion un repaint sobre el
     * contenedor pesado donde este a�adido (depende el jdk que se use) si no
     * puede que no se repintar� bien.
     *
     * @param nombre_se�al String
     * @param scroll JScrollBar
     * @param rango float[]
     * @param fs float
     */
    public void addChannel(String nombre_senhal,
            JScrollBar scroll, float[] rango, float fs) {
        int nuevoNumCanales = configuration.getNumCanales() + 1;
        this.setPaint(true);
        configuration.setNum_canales(nuevoNumCanales);
        configuration.setRangoSenales(nuevoNumCanales - 1, rango);
        configuration.setFs(nuevoNumCanales - 1,
                fs * configuration.getEscaladoTemporal());
        panel.anadeCanal(scroll);
        //Actualizar nombres de las senales
        String[] tmp = (String[]) configuration.getNombreSenales().clone();
        configuration.setNombreSenales(new String[nuevoNumCanales]);
        for (int i = 0; i < nuevoNumCanales - 1; i++) {
            configuration.setNombreSenales(i, tmp[i]);
        }
        configuration.setNombreSenales(nuevoNumCanales - 1, nombre_senhal);

    //Este codigo es para registrar los escuchadores de rat�n sobre el panel
        //de anotaciones
        if (configuration.isPintarAnotaciones()) {
            panel_anotacion.addMouseMotionListener(panel.getPanelPintar(
                    nuevoNumCanales - 1));
        }
        this.validate();
    }

    /**
     * A�ade un canal. Se le pasan los mismos parametros que al contructor, pero
     * con informacion sobre un solo canal. Cuando a�ade un canal puede ser
     * necesario ejecutar un validate y a continuacion un repaint sobre el
     * contenedor pesado donde este a�adido (depende el jdk que se use) si no
     * puede que no se repintar� bien.
     *
     * @param nombre_se�al String
     * @param rango float[]
     * @param fs float
     */
    public void addChannel(String nombre_senhal, float[] rango, float fs) {

        int nuevoNumCanales = configuration.getNumCanales() + 1;
        this.setPaint(true);
        configuration.setNum_canales(nuevoNumCanales);
        configuration.setRangoSenales(nuevoNumCanales - 1, rango);
        configuration.setFs(nuevoNumCanales - 1,
                fs * configuration.getEscaladoTemporal());
        panel.anadeCanal(scroll);
        //Actualizar nombres de las senales
        String[] tmp = (String[]) configuration.getNombreSenales().clone();
        configuration.setNombreSenales(new String[nuevoNumCanales]);
        for (int i = 0; i < nuevoNumCanales - 1; i++) {
            configuration.setNombreSenales(i, tmp[i]);
        }
        configuration.setNombreSenales(nuevoNumCanales - 1, nombre_senhal);

    //Este codigo es para registrar los escuchadores de rat�n sobre el panel
        //de anotaciones
        if (configuration.isPintarAnotaciones()) {
            panel_anotacion.addMouseMotionListener(panel.getPanelPintar(
                    nuevoNumCanales - 1));
        }
        this.validate();
    }

    /**
     * Elimina el canal especificado por el argumento. El canal cero es el
     * primero que se visualiza por arriba.
     *
     * @param num_canal
     */
    public void removeChannel(int num_canal) {
        int nuevoNumCanales = configuration.getNumCanales() - 1;
        configuration.setNum_canales(nuevoNumCanales);
        configuration.setPintar(false);
        panel.eliminaCanal(num_canal, scroll);
        //Actualizar nombres de las senales
        String[] tmp = (String[]) configuration.getNombreSenales().clone();
        float[] tmp2 = (float[]) configuration.getFs().clone();
        float[][] tmp3 = (float[][]) configuration.getRangoSenales().clone();
        float[][] tmp4 = (float[][]) configuration.
                getIntervaloDivisionesPrincipales().
                clone();
        float[][] tmp5 = (float[][]) configuration.
                getIntervaloDivisionesSecundarias().
                clone();
        boolean[][] tmp6 = (boolean[][]) configuration.getDivisionesSecundarias().
                clone();
        int[] tmp7 = (int[]) configuration.getTipoGrid().clone();

        configuration.setNombreSenales(new String[nuevoNumCanales]);
        int contador = 0;
        int van = 0;
        while (van <= nuevoNumCanales) {
            if (van != (num_canal)) {
                configuration.setNombreSenales(contador, tmp[van]);
                configuration.setFs(contador, tmp2[van]);
                configuration.setRangoSenales(contador, tmp3[van]);
                configuration.setIntervaloDivisionPrincipal(contador, tmp4[van]);
                configuration.setIntervaloDivisionSecundaria(contador, tmp5[van]);
                configuration.setDivisionSecundari(contador, tmp6[van]);
                configuration.setTipoGrid(contador, tmp7[van]);
                contador++;
            }
            van++;
        }
        //Para borrar la configuraci�n del grid del ultimo canal:
        configuration.setTipoGrid(nuevoNumCanales, Client.GRID_DENSE);

        if (nuevoNumCanales == 0) {
            if (panel_anotacion != null) {
                panel_anotacion.resetFarmacos();
            }
        } else {
            this.setPaint(true);
        }
        validate();
    }

    /**
     * Elimina todas las anotaciones
     */
    public void deleteAnnotations() {

        /*   this.setPintar(false);
         int tmp = Configuracion.num_canales;
         for (int i = 0; i < tmp ; i++) {
         this.eliminaCanal(1);
         }*/
        this.panel_anotacion.removeAll();
        panel_anotacion.resetFarmacos();
//      this.setPintar(true);
    }

    /**
     * Metodo para idicar cuando se debe pintar y cuando no. Al crear un objeto
     * jSignalMonitor puedeque aun no le hayamos proporcionado toda la
     * informaci�n que necesita para dibujarse correctamente (como los rangos de
     * los canales, los colores en los que queremos dejar, etc) por ello un
     * jSignalMonitor se crea invisible y no se hace visible hasta que cliente
     * lo requiera.
     *
     * No obstante el cliente puede en cualquier caso que a jSignalMonitor que
     * se deje de dibujar mediante este m�todo.
     *
     * @param b
     */
    public void setPaint(boolean b) {
        configuration.setPintar(b);
        //Si hay panel de anotaciones
        if (panel_anotacion != null) {
            this.panel_anotacion.setPintar(b);
        }
    }

    /**
     * Indica si se debe pintar la rayita de realizar anotaciones
     *
     * @param b boolean
     */
    public void setPaintAnnotationLine(boolean b) {
        configuration.setPintarAnotaciones(b);
        configuration.setPintandoXY(b);
    }

    /**
     * Cambia el nombre del canal indicada. El �ndice empieza a contar en 0
     *
     * @param senal int
     * @param nombre String
     */
    public void setSignalName(int channel, String nombre) {
        configuration.setNombreSenales(channel, nombre);
    }

    /**
     * Si el SplitPane se mueve cambia la posici�n del panel. Para que dibuje
     * bien => repinto.
     */
    void panel_componentMoved(ComponentEvent e) {
        repaint();
    }

    /**
     * Devuelve una referencia al objeto cliente al que �ste jSignalMonitor pide
     * datos.
     *
     * @return Client
     */
    public Client getClient() {
        return client;
    }

    /**
     * Devuelve una referencia al Configuration objeto de este jSignalMonitor.
     *
     * @return Configuration
     */
    public Configuration getConfiguration() {
        return configuration;
    }

    /**
     * Deuelve el n�mero de canales que est� pintando este jSignalMonitor
     *
     * @return int
     */
    public int getNumberOfChannels() {
        return configuration.getNumCanales();
    }

    /**
     * Devuelve el valor actual del margen izquierdo.
     *
     * @return margen izquierdo
     */
    public int getLeftMargin() {
        return configuration.getMargenIzquierdo();
    }

    /**
     * Modifica el valor actual del margen izqierdo.
     *
     * @param MargenIzquierdo
     */
    public void setLeftMargin(int _MargenIzquierdo) {
        configuration.setMargenIzquierdo(_MargenIzquierdo);
    }

    /**
     * Devuelve el valor actual del margen inferior.
     *
     * @return
     */
    public int getDownMargin() {
        return configuration.getMargenAbajo();
    }

    /**
     * Modifica el valor actual del margen inferior.
     *
     * @param _MargenAbajo
     */
    public void setDownMargin(int _MargenAbajo) {
        configuration.setMargenAbajo(_MargenAbajo);
    }

    /**
     * Devuelve el valor actual del margen superior.
     *
     * @return
     */
    public int getUpperMargin() {
        return configuration.getMargenArriba();
    }

    /**
     * Modifica el valor actual del margen superior.
     *
     * @param _MargenArriba
     */
    public void setUpperMargin(int _MargenArriba) {
        configuration.setMargenArriba(_MargenArriba);
    }

    /**
     * Devuelve el valor actual del margen derecho.
     *
     * @return
     */
    public int getRightMargin() {
        return configuration.getMargenDerecho();
    }

    /**
     * Modifica el valor actual del margen derecho.
     *
     * @param _MargenDerecho
     */
    public void setRightMargin(int _MargenDerecho) {
        configuration.setMargenDerecho(_MargenDerecho);
    }

    /**
     * Devuelve la frecuencia de muestreo del canal indicado.
     *
     * @param num_canal
     * @return
     */
    public float getFs(int num_canal) {
        return configuration.getFs(num_canal);
    }

    /**
     * Modifica la frecuencia de muestreo del canal indicado. JSignalMonitor
     * soporta que cada uno de los canales tenga una frecuencia de muestreo
     * distinta; sin embargo el eje temporal sobre el cual se gestiona las
     * anotaciones es �nico. Este es un tema complicado con una soluci�n no
     * triviales que se intentar� abordar en futuras versiones de
     * jSignalMonitor.
     *
     * @param num_canal
     * @param fs
     */
    public void setFs(int num_canal, float fs) {
        configuration.setFs(num_canal, fs);
    }

    /**
     * Devuelve la frecuancia de muestreo global. Esta es la frecuancia de
     * muestreo que emplea el panel en el cual se realizan las anotaciones para
     * los calculos de fechas y posicionamiento de las anotaciones en la
     * pantalla. Ver comentario sobre esta frecuancia en la intoduccion de la
     * clase.
     *
     * @return
     */
    public float getFsGlobal() {
        return configuration.getFsGlobal();
    }

    /**
     * Modifica el valor de la frecuancia de muestreo global. Esta es la
     * frecuencia de muestreo que se emplea en la gesti�n de las anotaciones.
     *
     * @param _fsGlobal
     */
    public void setFsGlobal(float _fsGlobal) {
        configuration.setFsGlobal(_fsGlobal);
    }

    /**
     * Devuelve true si se esta pintando con posibilidad en el canal indicado,
     * false en caso contrario.
     *
     * @param numero_canal
     * @return
     */
    public boolean getPaintPossibiity(int numero_canal) {
        return configuration.isPaintPossibility()[numero_canal];
    }

    /**
     * Si el valor boolean es true har� que en dicho canal se pinte con
     * posibilidad, si es false har� que no.
     *
     * @param numero_canal
     * @param _PintarPosibilidad
     */
    public void setPaintPossibility(int numero_canal, boolean _PintarPosibilidad) {
        configuration.setEnablePosboolean(numero_canal, _PintarPosibilidad);
    }

    /**
     * Hace que todos o ning�n canal se pinten con posibilidad, en funci�n de
     * que se le pase un par�metro true (todos con posibilidad) o false (todos
     * sin posibilidad).
     *
     * @param _PintarPosibilidad
     */
    public void setPaintAllPossibilities(boolean _PintarPosibilidad) {
        boolean[] tmp = configuration.isPaintPossibility();
        Arrays.fill(tmp, _PintarPosibilidad);
        for (int i = 0; i < tmp.length; i++) {
            configuration.setEnablePosboolean(i, tmp[i]);
        }
        this.repaint();
    }

    /**
     * Devuelve un entero representando el tipo de grid del canal
     *
     * @return
     */
    public int getGridType(int num_canal) {
        return configuration.getTipoGrid(num_canal);
    }

    /**
     * Modifica el grid del Monitor. Los posibles valores del entero se hallan
     * definidos en la Interface Cliente.
     *
     * @param _Grid
     */
    public void setGridType(int _Grid) {
        for (int i = 0; i < this.getNumberOfChannels(); i++) {
            configuration.setTipoGrid(i, _Grid);
        }
    }

    /**
     * Modifica el grid del Monitor. Los posibles valores del entero se hallan
     * definidos en la Interface Cliente. La modificacion se aplica a los
     * canales que se indiquen en el array que se le pasa al metodo
     *
     * @param _Grid
     * @param canales
     */
    public void setGridType(int _Grid, int[] canales) {
        for (int i = 0; i < canales.length; i++) {
            configuration.setTipoGrid(canales[i], _Grid);
        }
    }

    /**
     * Indica si esta empleando los rangos como leyendas.
     *
     * @return
     */
    public boolean getPaintRangeAsLabel() {
        return configuration.isPintarRangoComoLeyenda();
    }

    /**
     * Con un valor true fuerza que las leyendas de cada canal sean los rangos
     * de este. Con un valor false las leyendas ser�n las indicadas en el
     * constructor. El valor por defecto es true.
     *
     * @param _pintarRangoComoLeyenda
     */
    public void setPaintRangeAsLegend(boolean _pintarRangoComoLeyenda) {
        configuration.setPintarRangoComoLeyenda(_pintarRangoComoLeyenda);
    }

    /**
     * Actualizar el look and feel de las ventanas de marca y anotaciones
     *
     * @param nuevo_look
     */
    public void updateLookAnfFeel(String nuevo_look) {
        panel_anotacion.actulizaLookAnfFeel(nuevo_look);
        panel.actulizaLookAnfFeel(nuevo_look);
    }

    /**
     * Activa la medici�n de una distancia en el un canal, y el color a emplear
     * en el mood XOR en este canal. Una vez que el usuario de ha seleccionado
     * el fragmento de se�al jSignalMonitor informar� de las selecci�n el
     * cliente mediante el m�todoselectionFinished() de la interfaz Client.
     *
     * @param activar
     * @param color
     */
    public void sizeDistance(boolean activar, Color color) {
        this.setColorOfXorPaintMode(color);
        this.sizeDistance(activar);
    }

    /**
     * Activa la medici�n de una distancia en el un canal. Una vez que el
     * usuario de ha seleccionado el fragmento de se�al jSignalMonitor informar�
     * de las selecci�n el cliente mediante el m�todoselectionFinished() de la
     * interfaz Client.
     *
     */
    public void sizeDistance(boolean activar) {
        configuration.setPintarAnotacionesMedidas(activar);
        if (!activar) {
            Channel[] canales = panel.getCanales();
            for (int i = 0; i < canales.length; i++) {
                canales[i].cancelaMedida();
            }

        }
    }

    /**
     * Metodo que fuerza a que todas las frecuancias sean la misma.
     *
     * @param f float
     */
    public void setAllfrequencies(float f) {
        configuration.setFsGlobal(f);
        for (int i = 0; i < configuration.getFs().length; i++) {
            configuration.setFs(i, f);
        }

    }

    /**
     * Indica si se debe o no que pintar una recitan vertical en la posici�n del
     * rat�n, sobre cada uno de los canales, y los valores X, Y de dicha
     * posici�n.
     *
     * La La raya vertical se desplaza siguiendo la posici�n del rat�n sobre
     * cada canal; si se est� visualizando el panel de las anotaciones y que el
     * rat�n se desplaza dentro de este panel la recitan vertical se desplazara
     * en modo simult�neo por todos los canales motorizados.
     *
     * @param b boolean
     */
    public void setPaintXY(boolean b) {
        configuration.setPintandoXY(b);
        this.repaint();
    }

    /**
     * Este m�todo devuelve el ancho del scrtoll Vertical del panel de las
     * anotaciones.
     *
     * @return int
     */
    public int getWithAnnotationScroll() {
        if (scrollPAneAddAnnotations != null) {
            return scrollPAneAddAnnotations.getVerticalScrollBar().getWidth();
        }
        return 0;
    }

    /**
     * Este metodo realiza un escalado temporal por el factor indicado. Es una
     * soluci�n mediocre a la carencia de un aut�ntico zoom, que hemos dejado
     * para de sesiones posteriores de jSignalMonitor.
     *
     * @param num_canal
     * @param factos
     */
    public void setTemporalScale(int factor) {
        configuration.setEscaladoTemporal(factor);
    //Como todas tienen la misma frecuencia cojo una cualquiera
        // this.setTodasLasFrecuencias(Configuracion.fs[0] * factor / factor_antiguo);
        for (int i = 0; i < configuration.getFs().length; i++) {
            this.setFs(i, configuration.getFs(i) * factor / lastTemporalScale);
        }

        this.setTotalDuration(configuration.getDuracionRegistro() * factor
                / lastTemporalScale, true);
        lastTemporalScale = factor;
    }

    /**
     * Este metodo realiza devuelve el escalado temporalado.
     *
     * @param factos
     */
    public int getTemporalScale() {
        return configuration.getEscaladoTemporal();
    }

    /**
     * De vuelve el tama�o de las divisiones temporales principales para el
     * canal indicado.
     *
     * @param canal
     * @return
     */
    public float getMainTemporalDivisionInterval(int canal) {

        return configuration.getIntervaloDivisionesPrincipales()[canal][0];
    }

    /**
     * Modifica el tama�o de las divisiones temporales principales para el canal
     * indicado
     *
     * @param _intervaloDivisionesPrincipales
     * @param canal
     */
    public void setMainTemporalDivisionInterval(int canal,
            float _intervaloDivisionesPrincipales) {
        configuration.getIntervaloDivisionesPrincipales()[canal][0]
                = _intervaloDivisionesPrincipales;
        configuration.setTipoGrid(canal, Client.GRID_PERSONALIZED);
    }

    /**
     * Devuelve el tama�o de las divisiones de magnitud para el canal y
     * indicado.
     *
     * @param canal
     * @return
     */
    public float getMainMagnitudeDivisionInterval(int canal) {
        return configuration.getIntervaloDivisionesPrincipales()[canal][1];
    }

    /**
     * Modifica el tama�o de las divisiones en magnitud del canal indicado.
     *
     * @param canal
     * @param _intervaloDivisionesPrincipales
     */
    public void setMainMagnitudeDivisionInterval(int canal,
            float _intervaloDivisionesPrincipales) {
        configuration.getIntervaloDivisionesPrincipales()[canal][1]
                = _intervaloDivisionesPrincipales;
    }

    /**
     * Devuelve el tama�o de las divisiones temporales secundarias para el canal
     * indicado.
     *
     * @param canal
     * @return
     */
    public float getSecondaryTemporalDivisionInterval(int canal) {
        return configuration.getIntervaloDivisionesSecundarias()[canal][0];
    }

    /**
     * Modifica El tama�o de las divisiones temporales secundarias para el canal
     * indicado
     *
     * @param canal
     * @param _intervaloDivisionesSecundarias
     */
    public void setSecondaryTemporalDivisionInterval(int canal,
            float _intervaloDivisionesSecundarias) {
        configuration.getIntervaloDivisionesSecundarias()[canal][0]
                = _intervaloDivisionesSecundarias;
    }

    /**
     * Devuelve el tama�o que las divisiones secundarias en magnitud del canal
     * indicado.
     *
     * @param canal
     * @return
     */
    public float getSecondaryMagnitudeDivisionInterval(int canal) {
        return configuration.getIntervaloDivisionesSecundarias()[canal][1];
    }

    /**
     * Modifica El tama�o de las divisiones secundarias de magnitud del canal
     * indicado.
     *
     * @param canal
     * @param _intervaloDivisionesSecundarias
     */
    public void setSecondaryMagnitudeDivisionInterval(int canal,
            float _intervaloDivisionesSecundarias) {
        configuration.getIntervaloDivisionesSecundarias()[canal][1]
                = _intervaloDivisionesSecundarias;
    }

    /**
     * Indica si se est�n dibujando o no las divisiones temporales secundarias
     * del canal correspondiente.
     *
     * @param canal
     * @return
     */
    public boolean isPaintingSecondaryTemporalDivisions(int canal) {
        return configuration.getDivisionesSecundarias()[canal][0];
    }

    /**
     * Indica si se debe o no pintar las divisiones temporales secundarias en el
     * canal indicado.
     *
     * @param canal
     * @param _divisionesSecundarias
     */
    public void setPaintSecondaryTemporalDivisions(int canal,
            boolean _divisionesSecundarias) {
        configuration.getDivisionesSecundarias()[canal][0] = _divisionesSecundarias;
    }

    /**
     * Indica si se est�n dibujando o no las divisiones secundarias del canal
     * correspondiente.
     *
     * @param canal
     * @return
     */
    public boolean isPaintingSecondaryDivisions(int canal) {
        return configuration.getDivisionesSecundarias()[canal][1];
    }

    /**
     * Indica si se deben pintar o no las divisiones secundarias del canal
     * indicado.
     *
     * @todo Corregir el bug de las divisioens secundarias.
     * @param canal
     * @param _divisionesSecundarias
     */
    public void setPaintSecondaryMAagnitudeDivisions(int canal,
            boolean _divisionesSecundarias) {
        configuration.getDivisionesSecundarias()[canal][1] = false; // _divisionesSecundarias;
    }

    /**
     * Devuelve la posicion de la barra del splitPane.
     *
     * @return int
     */
    public int getSplitPaneDividerPosition() {
        return jSplitPane1.getDividerLocation();
    }

    /**
     * Pone a un determinado valor la posici�n de la barra del SplitPane.
     *
     * @param int.
     */
    public void setPositionOfSplitPaneDiivider(int i) {
        jSplitPane1.setDividerLocation(i);
    }

    /**
     * Pone a un determinado valor al ancho de la barra del SplitPane.
     *
     * @param int
     */
    public void setDividerThickness(int thick) {
        jSplitPane1.setDividerSize(thick);
    }

    /**
     * Indica el color con el cual se debe sombrear un canal al hacer
     * selecciones.
     *
     * @param color
     */
    public void setColorOfXorPaintMode(Color color) {
        configuration.setColorDeFondo(color);
    }

    /**
     * En la versi�n actual de jSignalMonitor este m�todo no funciona, ya que
     * tampoco funcionan la cache.
     */
    public void invalidateCache() {
        panel.invalidaCache();
        repaint();
    }

    /**
     * Incrementa el valor al que apunta el scroll la cantidad que se le
     * indique. La cantida tiene que ser positiva.
     *
     * @param incremento
     */
    public void increaseScrollValue(int incremento) {
        if (incremento + scroll.getValue() < 0) {
            scroll.setValue(0);
            return;
        }

        if (realTimeNow) {
            int nuevoMaximo = scroll.getMaximum() + incremento;
            int duracionRegistro = client.getTotaNumberOfData();
            configuration.setDuracionRegistro(duracionRegistro);
            int anchoCanal = configuration.getTamanoCanal().width
                    - configuration.getMargenDerecho()
                    - configuration.getMargenIzquierdo();
            if (anchoCanal > duracionRegistro) {
                scroll.setValues(duracionRegistro, 0, duracionRegistro,
                        duracionRegistro);
            } else {

                scroll.setValues(nuevoMaximo, 0, anchoCanal, nuevoMaximo);
            }
        } else {
            int nuevoValor = scroll.getValue() + incremento;
            if (nuevoValor < scroll.getMaximum()) {
                scroll.setValue(scroll.getValue() + incremento);
            } else {
                scroll.setValue(scroll.getMaximum());
            }
        }

    }

    /**
     * Indica si est� o no pintando los valores Y(t), t.
     *
     * @return
     */
    public boolean isPintingXYValues() {
        return configuration.isPintandoXY();
    }

    /**
     * Devuelve las leyendas de todoas las se�ales dibujadas
     *
     * @return String[][]
     */
    public String[][] getAllSignalsLabels() {
        return configuration.getLeyendasSenales();
    }

    /**
     * Modifica las leyendas de todos las se�ales.
     *
     * @param leyendasSenales String[][]
     */
    public void setAllSignalsLabels(String[][] leyendasSenal) {
        configuration.setLeyendasSenales(leyendasSenal);
    }

    /**
     * Devuelve la leyenda de la se�al indicada.
     *
     * @param senal int
     * @return String[]
     */
    public String[] getSignalLabel(int senal) {
        return configuration.getLeyendasSenales(senal);
    }

    /**
     * Modifica la leyenda de la se�al indicada.
     *
     * @param senal int
     * @param leyendasSenales String[]
     */
    public void setSignalLabel(int senal, String[] leyendasSenal) {
        configuration.setLeyendasSenales(senal, leyendasSenal);
    }

    /**
     * Invoked when the value of the adjustable has changed.
     *
     * @param e AdjustmentEvent
     * @todo Implement this java.awt.event.AdjustmentListener method
     */
    public void adjustmentValueChanged(AdjustmentEvent e) {
    //   System.out.println("val " + scroll.getValue() + " min " + scroll.getMinimum() +
        //                      " max " + scroll.getMaximum() + " extet " +
        //                      scroll.getVisibleAmount());
        if (!realTimeDemadedyClient) {
            return;
        }
        if (realTimeNow) {

            if (scroll.getValue() + scroll.getVisibleAmount() < scroll.getMaximum()) {
                this.realTimeNow = false;
            }
        } else {
            if (scroll.getValue() + scroll.getVisibleAmount() == scroll.getMaximum()) {
                this.realTimeNow = true;
            }

        }
    }

    /**
     * Indica si se debe (true) o no (false) pintar la fecha. Si no se pinta
     * fecha se pinten un �ndice que representa la posici�n de la primera y
     * �ltima muestra del registro.
     *
     * @return boolean
     */
    public boolean isPaintDate() {
        return configuration.isUsarFecha();
    }

    /**
     * Indica si se debe (true) o no (false) pintar la fecha. Si no se pinta
     * fecha se pinten un �ndice que representa la posici�n de la primera y
     * �ltima muestra del registro.
     *
     * @param usarFecha boolean
     */
    public void setPaintDate(boolean usarFecha) {
        configuration.setUsarFecha(usarFecha);
    }

    /**
     * Devuelve y la fecha en base de el registro; esto es la fecha
     * correspondiente con la primera muestra. La fecha puede estar en dos
     * formatos: "HH:mm:ss dd/MM/yyyy" o "HH:mm:ss:SSS",
     *
     * @return String
     */
    public String getBaseDate() {
        return SamplesToDate.getInstancia().getFechaBase();
    }

    /**
     * Modifica la fecha base del registro la nueva fecha puede estar en dos
     * formatos: "HH:mm:ss dd/MM/yyyy" o "HH:mm:ss:SSS"
     *
     * @param baseDate String
     */
    public void setBaseDate(String baseDate) {
        configuration.setUsarFecha(true);
        SamplesToDate.getInstancia().setFechaBase(baseDate);
    }
}
