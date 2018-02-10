package ve.gob.cendit.cenditlab.io.gpib;


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
     * Constantes para <code>timeout</code>
     * @see {@link ibtmo}
     */
    public final static int TNONE    = 0;
    public final static int T10US    = 1;
    public final static int T30US    = 2;
    public final static int T100US   = 3;
    public final static int T300US   = 4;
    public final static int T1MS     = 5;
    public final static int T3MS     = 6;
    public final static int T10MS    = 7;
    public final static int T30MS    = 8;
    public final static int T100MS   = 9;
    public final static int T300MS   = 10;
    public final static int T1S      = 11;
    public final static int T3S      = 12;
    public final static int T10S     = 13;
    public final static int T30S     = 14;
    public final static int T100S    = 15;
    public final static int T300S    = 16;
    public final static int T1000S   = 17;

    /**
     * Constantes para <code>ibln</code>
     * @see {@link ibln}
     */
    public static int NO_SAD = 0;
    public static int ALL_SAD = -1;

    public static int NULL_END = 0;
    public static int DAB_END = 1;
    public static int NL_END = 2;

    /**
     * Constantes para <code>iblines</code>
     */
    public static final int VALID_DAV     = 0x01; // The bus DAV bit is valid
    public static final int VALID_NDAC    = 0x02; // The bus NDAV bit is valid
    public static final int VALID_NRFD    = 0x04; // The bus NRFD bit is valid
    public static final int VALID_IFC     = 0x08; // The bus IFC bit is valid
    public static final int VALID_REN     = 0x10; // The bus REN bit is valid
    public static final int VALID_SRQ     = 0x20; // The bus SRQ bit is valid
    public static final int VALID_ATN     = 0x40; // The bus ATN bit is valid
    public static final int VALID_EOI     = 0x80; // The bus EOI bit is valid
    public static final int BUS_DAV       = 0x100;  // The DAV line value
    public static final int BUS_NDAC      = 0x200;  // The NDAC line value
    public static final int BUS_NRFD      = 0x400;  // The NRFD line value
    public static final int BUS_IFC       = 0x800;  // The IFC line value
    public static final int BUS_REN       = 0x1000; // The REN line value
    public static final int BUS_SRQ       = 0x2000; // The SRQ line value
    public static final int BUS_ATN       = 0x4000; // The ATN line value
    public static final int BUS_EOI       = 0x8000; // The EOI line value

    /**
     * Bits de estado en <code>ibsta</code>
     */
    public static final int DCAS    = 0x01; // Establecida cuando la tarjeta recibe device clear (SDC or DCL) [board]
    public static final int DTAS    = 0x02; // Establecida cuando la tarjeta recibe un comando trigger (GET) [board]
    public static final int LACS    = 0x04; // La tarjeta es direccionada como escucha (listener) [board]
    public static final int TACS    = 0x08; // La tarjeta es direccionada como hablante (talker) [board]
    public static final int ATN     = 0x10; // La linea ATN esta establecida [board]
    public static final int CIC     = 0x20; // La tarjeta es el controlador a cargo [board]
    public static final int REM     = 0x40; // La tarjeta esta en estado remoto [board]
    public static final int LOK     = 0x80; // La tarjeta esta en modo 'lockout' [board]
    public static final int CMPL    = 0x100; // La operacion IO esta completada [board/device]
    public static final int EVENT   = 0x200; // Se ha recibido uno o mas eventos 'clear', 'trigger', 'ifc' [board]
    public static final int SPOLL   = 0x400; // Establecido cuando la tarjeta es encuastada serial [board]
    public static final int RQS     = 0x800; // Indica que el dispositivo requiere servicio [device]
    public static final int SRQI    = 0x1000; // Indica un dispositivo conectado a la tarjeta asertando SRQ [board]
    public static final int END     = 0x2000; // Establecido cuando la ultima operacion culmina con EOI asertada [board/device]
    public static final int TIMO    = 0x4000; // Indica que ocurre un timeout en la ultima operacion [board/device]
    public static final int ERR     = 0x8000; // Inidica que la ultima llamada a funcion falla [board/device]

    /**
     * Opciones para ibconfig
     * Se han seleccionado solo aquellas para las cuales no existe una funcion de configuracion
     */
    public static final int AUTOPOLL = 0x07; // Si es distinto de cero se habilita serial poll automatico [board]
    public static final int SPOLLBIT = 0x16; // Si es distinto de cero se habilita el uso del bit SPOLL en ibsta [board]



    /**
     * Contiene el numero de bytes transmitidos, o depués
     * de un error <code>EDVR</code> o <code>EFSO</code>
     * contiene el valor de <code>errno</code>
     * @see ThreadIbcnt()
     */
    int ibcnt  = 0;

    /**
     * Contiene el numero de bytes transmitidos, o depués
     * de un error <code>EDVR</code> o <code>EFSO</code>
     * contiene el valor de <code>errno</code>
     * @see ThreadIbcntl()
     */
    long ibcntl = 0;

    /**
     * Contiene el codigo de error
     * @see ThreadIberr()
     */
    int iberr = 0;
    /**
     * Contiene el estado del bus
     * @see ThreadIbsta();
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
    int ibask(int ud, int option, Pointer value);
    int ibask(int ud, int option, int[] result);

    int ibconfig(int ui, int option, int setting);

    int ibdev(int board_index, int pad, int sad, int timeout,
              int send_eoi, int eos);
    int ibonl(int ud, int online);

    int ibbna(int ud, Pointer name);
    int ibbna(int ud, byte[] name);
    int ibbna(int ud, String name);

    int ibcac(int ud, int synchronous);


    int ibeos(int ud, int eosmode);
    int ibot(int ud, int send_eoi);
    int ibevent(int ud, /* short */ Pointer event);
    int ibevent(int ud, short[] event);
    int ibfind(/* const char* name */ Pointer name);
    int ibfind(byte[] name);
    int ibfind(String name);
    int ibrpp(int ud, int[] result);
    int ibrpp(int ud, Pointer result);
    int ibrsp(int ud, byte[] result);
    int ibrsp(int ud, Pointer result);

    int iblines(int ud, /* short* */Pointer line_status);
    int iblines(int ud, short[] line_status);
    int ibln(int ud, int pad, int sad, /* short* */Pointer found_listener);
    int ibln(int ud, int pad, int sad, short[] found_listener);
    int ibloc(int ud);
    int ibpct(int ud);

    // ibpad
    // ibpct
    // ibppc

    int ibrd(int ud, /* void* */ Pointer buffer, long num_bytes);
    int ibrd(int ud, byte[] buffer, long num_bytes);
    int ibrda(int ud, /* void* */ Pointer buffer, long num_bytes);
    int ibrdf(int ud, /* const char* */ Pointer file_path);
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

    /**
     * <code>ibwait</code> [board/device] espera por un evento. Detiene la ejecucion
     * hasta que ina de las condiciones especificadas en <code>status_mask</code> sea
     * verdadera. El significado de <code>status_mask</code> es el mismo que los bits de
     * <code>ibsta</code>.
     * Si <code>status_mask</code> es 0, entonces <code>ibwait</code> retornara
     * inmediatamente el valor de <code>ibsta</code>.
     *
     * @param ud            descriptor de dispositivo.
     * @param status_mask   mascara de estado.
     *
     * @return el valor de {@link ibsta} es retornado.
     */
    int ibwait(int ud, int status_mask);

    /**
     * <code>ibtmo</code> [board/device] Establece el timeout para las operaciones IO ejecutadas
     * por una tarjeta o dispositivo con descriptor <code> ud</code>. El tiempo transcurrido antes de
     * un timeout podra mayor que el especificado, pero nunca menor que <code>timeout</code>.
     * <code>timeout</code> se especifica con una de las siguientes constantes
     *
     * Valores para <code>timeout</code>
     * Constante    Valor   Constante   Valor   Constante   Valor
     * TNONE        0       T3ms        6       T3s         12
     * T10us        1       T10ms       7       T10s        13
     * T30us        2       T30ms       8       T30s        14
     * T100us       3       T100ms      9       T100s       15
     * T300us       4       T300ms      10      T300s       16
     * T1ms         5       T1s         11      T1000s      17
     *
     * @param ud        descriptor de dispositivo
     * @param timeout   constante de timeout
     *
     * @return el valor de {@link ibsta} es retornado.
     */
    int ibtmo(int ud, int timeout);

    /**
     *<code>ibtrg</code> [device] trigger device. Envia el comando GET (group execute trigger)
     * al dispositivio especificado por el descriptor <code>ud</code>.
     *
     * @param ud        descriptor de dispositivo.
     * @return ibsta    retorna el valor de <code>ibsta</code>.
     */
    int ibtrg(int ud);

    int ThreadIbcnt();
    long ThreadIbcntl();

    int ThreadIberr();
    int ThreadIbsta();

    /**
     * <code>AllSpoll</code> provoca que la tarjeta de interfaz especificada por
     * <code>board_desc</code> para consulta serial (serial poll) de todas las
     * direcciones especificadas en el arreglo <code>addressList</code>. Los resultados
     * de la encuesta serie son almacenados en <code>resultsList</code>.
     *
     * @param board_desc    descriptor de dispositivo
     * @param addressList   lista de direcciones a consulta serial
     * @param resultsList   lista de resultados
     *
     * @see {@link ReadStatusByte()}, {@link ibrsp()}
     */
    //typedef uint16_t Addr4882_t -> short
    void AllSpoll(int board_desc, short[] addressList, short[] resultsList);


    /**
     * <code>WaitSQR</code> duerme hasta que la linea de bus SRQ sea asertada,
     * o hasta que ocurra un timeout {@link ibtmo()}. Se escribe '1' en la
     * direccion especificada por <code>result</code> si SRQ ha sido asertada,
     * y se escribe '0' si la ocurre un timeout.
     *
     * @param board_desc    descriptor de dispositivo.
     * @param result
     */
    void WaitSRQ(int board_desc, short[] result);
    void WaitSRQ(int board_desc, Pointer result);

    /**
    * <code>TestSRQ</code> consulta el estado de la linea de bus SRQ
    * y escribe su estado en la ubicacion especificad por <code>result</code>
    * Un '1' indica que la linea SRQ esta asertada, y un '0' indica que
    * la linea no esta asertada.
    * Algunas tarjetas carecen de la capacidad para reportar el estado de
    * la linea de bus SRQ. EN este caso, un error <code>ECAP</code> es retornado
    * en <code>iberr</code>.
    *
    * @param board_desc descritptor de dispositivo.
    * @param result     estado de SQR, '1' representa asertado.
    *
    * @return el valor de {@link ibsta} es retornado
    */
    void TestSQR(int board_desc, short[] result);
    void TestSQR(int board_desc, Pointer result);

    /*
	Addr4882_t address;
	address = ( pad & 0xff );
	address |= ( sad << 8 ) & 0xff00;
    */



}
