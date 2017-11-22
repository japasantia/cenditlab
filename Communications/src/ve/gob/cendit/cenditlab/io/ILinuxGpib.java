package ve.gob.cendit.cenditlab.io;


import com.sun.jna.Library;
import com.sun.jna.Pointer;

/**
 * Created by jarias on 06/06/17.
 */
/*
    MODELO JAVADOC
    http://www.oracle.com/technetwork/articles/java/index-137868.html


    * Draws as much of the specified image as is currently available
     * with its northwest corner at the specified coordinate (x, y).
     * This method will return immediately in all cases, even if the
     * entire image has not yet been scaled, dithered and converted
     * for the current output device.
     * <p>
     * If the current output representation is not yet complete then
     * the method will return false and the indicated
     * {@link ImageObserver} object will be notified as the
     * conversion process progresses.
     *
     * @param img       the image to be drawn
     * @param x         the x-coordinate of the northwest corner
     *                  of the destination rectangle in pixels
     * @param y         the y-coordinate of the northwest corner
     *                  of the destination rectangle in pixels
     * @param observer  the image observer to be notified as more
     *                  of the image is converted.  May be
     *                  <code>null</code>
     * @return          <code>true</code> if the image is completely
     *                  loaded and was painted successfully;
     *                  <code>false</code> otherwise.
     * @see             Image
     * @see             ImageObserver
     * @since           1.0


    public Image getImage(URL url, String name) {
            try {
            return getImage(new URL(url, name));
            } catch (MalformedURLException e) {
            return null;
            }
            }
*/

/**
 * ILinuxGpib es una interfaz que sirve de envoltorio para las
 * funciones de la libreria <code>linux-gpib</code>
 *
 * @author      Jose Arias [correo@JoseArias.com.ve]
 * @version     %I%, %G%
 * @since       1.0
 */
public interface ILinuxGpib extends Library
{
    /**
     * Contiene el numero de bytes transmitidos, o depués
     * de un error <code>EDVR</code> o <code>EFSO</code>
     * contiene el valor de <code>errno</code>
     * @see ILinuxGpib#ThreadIbcnt()
     */
    int ibcnt  = 0;

    /**
     * Contiene el numero de bytes transmitidos, o depués
     * de un error <code>EDVR</code> o <code>EFSO</code>
     * contiene el valor de <code>errno</code>
     * @see ILinuxGpib#ThreadIbcntl()
     */
    long ibcntl = 0;

    /**
     * Contiene el codigo de error
     * @see ILinuxGpib#ThreadIberr()
     */
    int iberr = 0;
    /**
     * Contiene el estado del bus
     * @see ILinuxGpib#ThreadIbsta();
     */
    int ibsta = 0;

    /**
     * Consulta las configuraciones asociadas con una tarjeta
     * o un descriptor de dispositivo <code>ul</code>.
     * El argumento <code>option</code> especifica la configuracion
     * particular que se desea consultar. El resultado de la consulta
     * es escrito en el lugar especificado por <code>result</code>.
     *
     * @param ud        descritptor de dispositivo
     * @param option    la opción a consultar
     * @param result    lugar donde se almacena el valor consultado
     *
     * @return          el valor de {@link ibsta} es retornado
     */
    int ibask(int ud, int option, Pointer result);
    int ibask(int ud, int option, int[] result);

    int ibdev(int board_index, int pad, int sad, int timeout,
              int send_eoi, int eos);

    /**
     * Permite cerrar o reinicializar un descriptor. Si <code>online</code>
     * es cero, la funcion liberar todos los recurso asociados con la tarjeta
     * o dispositivo, el descriptor <code>ud</code> no puede utilizarse de nuevo.
     * Si <code>online</code> es distinto de cero, toda la configuracion asociada
     * con el descriptor es restablecida a sus valores por defecto, los cuales
     * son los valores cuando se obtiene el descriptor por primera vez.
     * *
     * @param ud        descritptor de dispositivo
     * @param online    es cero para liberar todos los recursos
     *                  distinto de cero para reiniciar recursos
     *
     * @return          el valor de {@link ILinuxGpib#ibsta} es retornado
     */
    int ibonl(int ud, int online);

    int ibbna(int ud, Pointer name);
    int ibbna(int ud, byte[] name);
    int ibbna(int ud, String name);

    int ibcac(int ud, int synchronous);

    int ibconfig(int ui, int option, int setting);

    int ibeos(int ud, int eosmode);
    int ibot(int ud, int send_eoi);
    int ibevent(int ud, /* short */ Pointer event);
    int ibevent(int ud, short[] event);
    int ibfind(/* const char* name */ Pointer name);
    int ibfind(byte[] name);
    int ibfind(String name);


    int iblines(int ud, /* short* */Pointer line_status);
    int iblines(int ud, short[] line_status);

    /**
     * <p>
     *     Consulta la presencia de un dispositivo, intentando
     *     direccionarlo como un escucha (listener). <code>ud</code>
     *     es un descriptor de tarjeta de interfaz que debe ejecutar
     *     la consulta por escuchas. Si <code>ud</code> especifica un
     *     descriptor de dispositivo, se emplea su tarjeta de acceso
     *     para la consulta.
     * </p>
     * <p>
     *     La dirección GPIB a consultar se especifica por <code>pad</code>
     *     para la dirección primaria y <code>sad</code> la dirección
     *     secundaria. <code>pad</code> es un valor de 0 a 30 decimal y
     *     <code>sad</code> es un valor de 0x60 a 0x7e (de 96 a 126 decimal)
     *     o una de las constantes {@link NO_SAD} o {@link ALL_SAD}.
     * </p>
     *
     * @param ud                descritptor de dispositivo
     * @param pad               dirección primaria a consultar
     * @param sad               dirección secundaria a consultar
     * @param found_listener    se establece a distinto de cero si se encuentra
     *                          un dispositivo escucha
     *
     * @return                  el valor de {@link ibsta} es retornado
     */
    int ibln(int ud, int pad, int sad, /* short* */Pointer found_listener);
    int ibln(int ud, int pad, int sad, short[] found_listener);

    /**
     * <p>
     *     Ocasiona que la tarjeta o dispositivo especificad por el
     *     descriptor <code>ud</code> entre en modo local.
     * </p>
     *
     *     @param ud            descriptor de dispositivo
     *
     *     @return              el valor de {@link ibsta} es retornado
     */
    int ibloc(int ud);


    /**
     * <p>
     *     Permite que el dispositivo especificado por el descriptor
     *     <code>ud</code> se convierta en el controlador  a cargo.
     * </p>
     *
     *     @param ud            descriptor de dispositivo
     *
     *     @return              el valor de {@link ibsta} es retornado
     */
    int ibpct(int ud);


    /**
     * <p>
     *     Establece la dirección primaria de la tarjeta o dispositivo
     *     GPIB.
     * </p>
     *
     *     @param ud            descriptor de dispositivo
     *     @param pad           dirección primaria a establecer
     *
     *     @return              el valor de {@link ibsta} es retornado
     */
    int ibpad(int ud, int pad);

    /**
     * <p>
     *     Configura la respuesta a la <i>consulta paralela
     *     (parallel poll)</i> del dispositivo o tarjeta especificada
     *     por <code>ud</code>. El valor de <code>configuration</code>
     *     debe ser establecido en la constante <code>PPD</code> para
     *     desabilitar la consulta paralela, o establecido en el valor
     *     de retorno de la función <i>en linea</i> <code>PPE_byte()</code>
     *     para habilitar y configurar la respuesta a la consulta paralela.
     * </p>
     *
     *     @param ud                 descriptor de dispositivo
     *     @param configuration      respuesta
     *
     *     @return                  el valor de {@link ibsta} es retornado
     */
    int ippc(int ud, int configuration);

    /**
     * <p>
     *      Permite leer bytes de datos de un dispositivo o tarjeta.
     *      Se leen hasta <code>num_bytes</code> los cuales son
     *      depositados en el <code>buffer</code> suministrado por el
     *      usuario.
     * </p>
     * <p>
     *      La lectura se termina en los siguinetes casos: si se agota
     *      el tiempo de respuesta (timeout) ({@link ILinuxGpib#ibtmo}),
     *      si un hablante establece la linea <code>EOI</code>, si la
     *      tarjeta recibe el caracter de final de cadena
     *      ({@link ILinuxGpib#ibeos(int, int)}), cuando se recibe el comando
     *      <i>limpiar dispositivo</i> (device clear) o cuando se recibe
     *      el comando <i>limpiar interfaz</i> (interface clear)
     * </p>
     *     @param ud             descriptor de dispositivo
     *     @param buffer         arreglo donde se depositan los datos leidos
     *     @param num_bytes      cantidad de bytes a leer
     *
     *     @return               el valor de {@link ILinuxGpib#ibsta} es retornado
     */
    int ibrd(int ud, byte[] buffer, long num_bytes);
    int ibrd(int ud, Pointer buffer, long num_bytes);

    /**
     * <p>
     *      Permite leer bytes de datos de un dispositivo de manera asincrona.
     *      Similar a {@link ILinuxGpib#ibrd(int, byte[], long)} excepto que no
     *      espera por la recepción completa de los bytes de datos, sino que
     *      retorna de forma inmediata.
     * </p>
     * <p>
     *     Mientras la operación asincrona esta en progreso, la mayoria de las
     *     funciones fallan con un error EOIP. Para completar una operación
     *     asincrona de manera exitosa, se debe llamar a la función
     *     {@link ILinuxGpib#ibwait(int, int)} hasta que el bit
     *     <code>CMPL</code> del {@link ILinuxGpib#ibsta} sea establecido.
     * </p>
     * <p>
     *     Las operaciones asincronas pueden ser abortadas con llamadas a las
     *     funciones {@link ILinuxGpib#ibstop} o {@link ILinuxGpib#ibonl}.
     * </p>
     *
     *     @param ud             descriptor de dispositivo
     *     @param buffer         arreglo donde se depositan los datos leidos
     *     @param num_bytes      cantidad de bytes a leer
     *
     *     @return               el valor de {@link ILinuxGpib#ibsta}
     *                           es retornado
     */
    int ibrda(int ud, byte[] buffer, long num_bytes);


    /**
     * <p>
     *     Permite leer bytes de datos de un dispositivo y almacenarlos
     *     en un archivo. El archivo se especifica por su ruta completa
     *     <code>file_path</code>. Si el archivo existe, los datos son
     *     agregados al final del archivo.
     * </p>
     *
     *     @param ud             descriptor de dispositivo
     *     @param file_path      ruta completa al archivo donde se almacenan
     *                           los datos leidos del dispositivo
     *
     *     @return               el valor de {@link ILinuxGpib#ibsta}
     *                           es retornado
     */
    int ibrdf(int ud, String file_path);
    int ibrdf(int ud, /* const char* */ Pointer file_path);

    /**
     * <p>
     *      Permite escribir bytes de datos de un dispositivo o tarjeta.
     *      Se escriben hasta <code>num_bytes</code> los cuales son
     *      contenidos en el <code>buffer</code> suministrado por el
     *      usuario.
     * </p>
     * <p>
     *      <code>EOI</code> puede ser establecida cuando se envia el
     *      ultimo byte o cuando el <i>fin de cadena</i> es enviado
     *      ({@link ILinuxGpib#ibeos(int, int)}, {@link ILinuxGpib#ibeot)}).
     * </p>
     * <p>
     *      La escriturq se termina en los siguientes casos: si se agota
     *      el tiempo de respuesta (timeout) ({@link ILinuxGpib#ibtmo}),
     *      cuando se recibe el comando <i>limpiar dispositivo</i>
     *      (device clear) o cuando se recibe el comando
     *      <i>limpiar interfaz</i> (interface clear)
     * </p>
     *     @param ud             descriptor de dispositivo
     *     @param buffer         arreglo con los datos a enviar
     *     @param num_bytes      cantidad de bytes a enviar
     *
     *     @return               el valor de {@link ILinuxGpib#ibsta}
     *                           es retornado
     */
    int ibwrt(int ud, /* void* */ Pointer buffer, long num_bytes);
    int ibwrt(int ud, byte[] buffer, long num_bytes);
    int ibwrt(int ud, String buffer, long num_bytes);


    int ibwrta(int ud, /* void* */ Pointer buffer, long num_bytes);
    int ibwrtf(int ud, /* const char* */ Pointer file_path);

    int ibrsc(int ud, int request_control);
    int ibsic(int ud);
    int ibclr(int ud);

    int ibsre(int ud, int enable);
    int ibstop(int ud);
    int ibwait(int ud, int status_mask);

    int ThreadIbcnt();
    long ThreadIbcntl();

    int ThreadIberr();
    int ThreadIbsta();

    /** Constantes para timeout */

     int TNONE = 0;
     int T10us = 1;
     int T30us = 2;
     int T100us = 3;
     int T300us = 4;
     int T1ms = 5;
     int T3ms = 6;
     int T10ms = 7;
     int T30ms = 8;
     int T100ms = 9;
     int T300ms = 10;
     int T1s = 11;
     int T3s = 12;
     int T10s = 13;
     int T30s = 14;
     int T100s = 15;
     int T300s = 16;
     int T1000 = 15;

     /**
      * Constante para {@link ibln}
      * Indica que no se emplea dirección GPIB secundaria      *
      * @see ibln
      */
     int NO_SAD = 0;

    /**
     * Constante para {@link ibln}
     * Indica qu todas las direcciones secundarias debe ser
     * consultadas
     * @see ibln
     */
     int ALL_SAD = -1;

     int NULL_END = 0;
     int DAB_END = 1;
     int NL_END = 2;
}
