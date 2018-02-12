package ve.gob.cendit.cenditlab.io.vxi11;


import com.sun.jna.Callback;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import ve.gob.cendit.cenditlab.io.visa.VisaAddress;

/**
 * <p>Interfaz que contiene la definicion de las funciones contenidas
 * en la libreria de C para linux <code>libvxi11.so</code>.</p>
 *
 * <p>La libreria VXI11 fue contruida tomando como base el codigo fuente.
 *  Se agregan modificadores <code>extenr "C"</code> para facilitar su
 *  acceso por medio de JNA.</p>
 *
 * <p>El codigo fuente original de la libreria puede descargarse desde
 * los siguientes enlaces.</p>
 *
 * @see <a href="http://optics.eee.nottingham.ac.uk/vxi11/">
 *     http://optics.eee.nottingham.ac.uk/vxi11/</a>
 */

public interface IVxi112 extends Library
{
    /**
     * Tiempo de espera <code>timeout</code> por defecto
     */
    static final long VXI11_DEFAULT_TIMEOUT = 10000; /* en ms */

    /**
     * Tiempo de espera <code>timeout</code> por defecto
     * en operaciones de lectura
     */
    static final long VXI11_READ_TIMEOUT = 2000; /* en ms */
    /**
     * Valor de retorno de {@link IVxi11#vxi11_receive(Pointer, byte[], long, long)}
     * en caso de agotarse el tiempo de espera <code>timeout</code>
     */
    static final long VXI11_NULL_READ_RESP = -50;

    /**
     * Valor de retorno de {@link IVxi11#vxi11_receive(Pointer, byte[], long, long)}
     * en caso de agotarse el tiempo de espera <code>timeout</code>
     */
    static final long VXI11_NULL_WRITE_RESP = -51;

    static final long VXI11_NOTHING_RECEIVED = -15;

    /**
     * <code>Vxi11_CreateHandle</code> crea un handle necesario para las llamadas a funcion de
     * en esta libreria. Dentro de la libreria la estructura <code>VxiHandle</code> se define
     * como
     *      typedef struct tagVxiHandle
     *      {
     *          CLIENT* pClient;
     *          Create_LinkResp* pLink;
     *      } VxiHandle;
     * */
    public static Pointer Vxi11_CreateHandle()
    {
        /* La estructura VxiHandle ocupa 16 bytes como maximo en un sitema de 64 bits. */
        long memory = Native.malloc(16);
        return new Pointer(memory);
    }

    /**
     * Permite abrir un enlace (link) y un canal VXI11 a un
     * instrumento direccionado por su direccion IP de cuatro
     * puntos (IP dotted quad address) y especificando el
     * identificador de dispositivo (device).
     *
     * Se encarga de inicializar el objeto {@link Pointer}
     * <code>VxiHandle</code> el cual es el manejador (handler) de
     * la conexión.
     *
     * @param ip        cadena con la dirección ip del instrumento
     * @param handle    manejador (handler) de la conexion
     * @param device    cadena identificado de dispositivo
     *
     * @return          retorna cero en caso de exito
     *                  devuelve un valor negativo en caso de error
     *
     * @see             VisaAddress
     * @see             Pointer
     */
    int	Vxi11_OpenDevice(Pointer handle, String ip, String device);

    /**
     * Permite cerrar el enlace y el canal hacia un dispositivo,
     * liberando todos los recursos asociados. Requiere la dirección
     * <code>ip</code> y el objeto <code>handle</code> utilizados en
     * la función {@link IVxi11#Vxi11_OpenDevice(Pointer, String, String)}.
     *
     * @param handle    manejador (handler) de la conexion
     * @param ip        cadena con la dirección ip del instrumento     *
     *
     * @return          retorna cero en caso de exito
     *                  devuelve un valor negativo en caso de error
     *
     * @see             VisaAddress
     * @see             Pointer
     */
    int Vxi11_CloseDevice(Pointer handle, String ip);

    /**
     * Permite enviar una cadena de comandos de longitud <code>len</code>
     * a un instrumento, identificado por el manejador (handler)
     * <code>handle</code>.
     *
     * @param handle     manejador (handler) de la conexion
     * @param data       cadena con comandos a enviar al instrumento
     *
     * @return          retorna cero en caso de exito
     *                  devuelve un valor negativo en caso de error
     *                  devuelve la constante {@link IVxi11#VXI11_NULL_WRITE_RESP}
     *                  en caso de agotarse el tiempo de espera
     */
    int	Vxi11_Send(Pointer handle, String data, long len);

    // TODO: se pueden sobrecargar las funciones de la libreria
    int Vxi11_Send(Pointer handle, byte[] data, long len);

    /**
     * Permite recibir un conjunto de datos bytes de un instrumento.
     * Los datos son almacenados en un arreglo <code>buffer</code>
     * que dispone de una capacidad no menor de <code>len</code> bytes.
     *
     * @param handle    manejador (handler) de la conexion
     * @param buffer    arreglo donde se depositan los bytes recibidos
     * @param len       cantidad de datos a recibir
     * @param timeout   tiempo limite de espera para la recepción,
     *                  en milisegundos
     *
     * @return          retorna cero en caso de exito
     *                  devuelve un valor negativo en caso de error
     *                  devuelve la constante {@link IVxi11#VXI11_NULL_READ_RESP}
     *                  en caso de agotarse el tiempo de espera <code>timeout</code>
     *    */
    long Vxi11_Receive(Pointer handle, byte[] buffer, long len, long timeout);

    /** <code>Vxi_ReadSTB</code> permite leer el byte de estadp del instrumento.
     * Equivalente al comando SCPI <code>*STB?</code>.
     *
     * @param handle    manejador (handler) de la conexion
     * @param stb       donde se almacena el byte de estado     *
     * @param timeout   tiempo limite de espera para la recepción,
     *                  en milisegundos
     *
     * @return          retorna cero en caso de exito
     *                  devuelve un valor negativo en caso de error
     * */
    int Vxi_ReadSTB(Pointer handle, Pointer stb, long timeout);
    int Vxi_ReadSTB(Pointer handle, byte[] stb, long timeout);

    int Vxi_DeviceTrigger(Pointer handle, long timeout);

    int Vxi11_DeviceClear(Pointer handle, long timeout);

    int Vxi11_DeviceRemote(Pointer handle, long timeout);

    int Vxi11_DeviceLocal(Pointer handle, long timeout);

    int Vxi11_DeviceLock(Pointer handle, long timeout);

    int Vxi11_DeviceUnlock(Pointer handle);

    int Vxi11_DeviceEnableSRQ(Pointer handle, boolean enable);

    int Vxi11_LibraryId(byte[] buffer, int len);

    // JNA Callback
    public interface SRQCallback extends Callback
    {
        int callback(/* char* */ Pointer arg );
    }

    public int Vxi11_RegisterSRQHandler(SRQCallback callback);
}
