package ve.gob.cendit.cenditlab.io.vxi11;

import com.sun.jna.Library;
import com.sun.jna.Pointer;
import ve.gob.cendit.cenditlab.io.visa.VisaAddress;

/**
 * <p>Interfaz que contiene la definicion de las funciones contenidas
 * en la libreria de C para linux <code>libvxi11.so</code>.</p>
 *
 * <p>La libreria VXI11 fue contruida a partir del codigo fuente, con
 * ligeras modificaciones para facilitar en enlace por medio de JNA.</p>
 *
 * <p>El codigo fuente de la libreria pude descargarse desde los
 * enlaces.</p>
 *
 * @see <a href="http://optics.eee.nottingham.ac.uk/vxi11/">
 *     http://optics.eee.nottingham.ac.uk/vxi11/</a>
 */
public interface IVxi11 extends Library
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
     * Permite abrir un enlace (link) y un canal VXI11 a un
     * instrumento direccionado por su direccion IP de cuatro
     * puntos (IP dotted quad address) y especificando el
     * identificador de dispositivo (device).
     *
     * Se encarga de inicializar el objeto {@link Pointer}
     * <code>clink</code> el cual es el manejador (handler) de
     * la conexión.
     *
     * @param ip        cadena con la dirección ip del instrumento
     * @param clink     manejador (handler) de la conexion
     * @param device    cadena identificado de dispositivo
     *
     * @return          retorna cero en caso de exito
     *                  devuelve un valor negativo en caso de error
     *
     * @see             VisaAddress
     * @see             Pointer
     */
    int	vxi11_open_device(String ip, Pointer clink, String device);

    /**
     * Permite cerrar el enlace y el canal hacia un dispositivo,
     * liberando todos los recursos asociados. Requiere la dirección
     * <code>ip</code> y el objeto <code>clink</code> utilizados en
     * la función {@link IVxi11#vxi11_open_device(String, Pointer, String)}.
     *
     * @param ip        cadena con la dirección ip del instrumento
     * @param clink     manejador (handler) de la conexion
     *
     * @return          retorna cero en caso de exito
     *                  devuelve un valor negativo en caso de error
     *
     * @see             VisaAddress
     * @see             Pointer
     */
    int vxi11_close_device(String ip, Pointer clink);

    /**
     * Permite enviar una cadena de comandos de longitud <code>len</code>
     * a un instrumento, identificado por el manejador (handler)
     * <code>clink</code>.
     *
     * @param clink     manejador (handler) de la conexion
     * @param cmd       cadena con comandos a enviar al instrumento
     *
     * @return          retorna cero en caso de exito
     *                  devuelve un valor negativo en caso de error
     *                  devuelve la constante {@link IVxi11#VXI11_NULL_WRITE_RESP}
     *                  en caso de agotarse el tiempo de espera
     */
    int	vxi11_send(Pointer clink, String cmd, long len);

    // TODO: se pueden sobrecargar las funciones de la libreria
    int vxi11_send(Pointer clink, byte[] cmd, long len);

    /**
     * Permite recibir un conjunto de datos bytes de un instrumento.
     * Los datos son almacenados en un arreglo <code>buffer</code>
     * que dispone de una capacidad no menor de <code>len</code> bytes.
     *
     * @param clink     manejador (handler) de la conexion
     * @param buffer    arreglo donde se depositan los bytes recibidos
     * @param len       cantidad de datos a recibir
     * @param timeout   Tiempo limite de espera para la recepción,
     *                  en milisegundos
     *
     * @return          retorna cero en caso de exito
     *                  devuelve un valor negativo en caso de error
     *                  devuelve la constante {@link IVxi11#VXI11_NULL_READ_RESP}
     *                  en caso de agotarse el tiempo de espera <code>timeout</code>
*    */
    long vxi11_receive(Pointer clink, byte[] buffer, long len, long timeout);

    /**
     * Permite enviar a un dispositivo una cadena de comandos seguida
     * de un bloque <code>len</code> bytes de datos, que la funcion
     * envia al dispositivo en formato IEEE488.
     *
     * Formato de un bloque de datos IEEE488
     *      #800001000<1000 bytes de datos>
     *      ||\______/
     *      ||    |
     *      ||    \---- cantidad de bytes de datos
     *      |\--------- numero de digitos que  siguen (en este caso 8, con ceros adelante)
     *      \---------- siempre comienza con #
     *
     * @param clink     manejador (handler) de la conexion
     * @param cmd       cadena con comandos a enviar al disposotivo
     * @param buffer    arreglo con bytes de datos a enviar al dispositivo
     * @param len       cantidad de datos a enviar.
     *
     * @return          retorna cero en caso de exito
     *                  devuelve un valor negativo en caso de error
     */
    int vxi11_send_data_block(Pointer clink, String cmd,
              byte[] buffer, long len);
    /**
     * Permite recibir de un dispositivo un conjunto de datos binario
     * de longitud <code>len</code> que la funcion extrae de un bloque
     * de datos IEEE488 que el dispositvo envia.
     *
     * Formato de un bloque de datos IEEE488
     *      #800001000<1000 bytes de datos>
     *      ||\______/
     *      ||    |
     *      ||    \---- cantidad de bytes de datos
     *      |\--------- numero de digitos que  siguen (en este caso 8, con ceros adelante)
     *      \---------- siempre comienza con #
     *
     * @param clink     manejador (handler) de la conexion
     * @param buffer    arreglo donde se depositan los bytes recibidos
     * @param len       cantidad de datos a recibir

     * @param timeout   tiempo limite de espera para la recepción,
     *                  en milisegundos
     * @return          retorna cero en caso de exito
     *                  devuelve un valor negativo en caso de error
     */
    long vxi11_receive_data_block(Pointer clink, byte[] buffer,
              long len, long timeout);

    /**
     * Permite enviar a un dispositivo una cadena de comandos seguida
     * de un numero de <code>len</code> de bytes de datos.
     *
     * @param clink     manejador (handler) de la conexion
     * @param cmd       cadena con comandos a enviar al disposotivo
     * @param buffer    cantidad de datos a recibir

     * @param timeout   tiempo limite de espera para la recepción,
     *                  en milisegundos
     * @return          retorna cero en caso de exito
     *                  devuelve un valor negativo en caso de error
     */
    long vxi11_send_and_receive(Pointer clink, String cmd,
                byte[] buffer, long buf_len, long timeout);

    /**
     * Permite enviar una cadena de comandos <code>cmd</code> al
     * dispositivo y recibir su respuesta como un valor de tipo
     * <code>long</code>.
     *
     * @param clink     manejador (handler) de la conexion
     * @param cmd       cadena con comandos a enviar al disposotivo
     * @param timeout   tiempo limite de espera para la recepción,
     *                  en milisegundos
     *
     * @return          retorna cero en caso de exito
     *                  devuelve un valor negativo en caso de error
     */
    long vxi11_obtain_long_value(Pointer clink, String cmd, long timeout);

    /**
     * Permite enviar una cadena de comandos <code>cmd</code> al
     * dispositivo y recibir su respuesta como un valor de tipo
     * <code>double</code>.
     *
     * @param clink     manejador (handler) de la conexion
     * @param cmd       cadena con comandos a enviar al disposotivo
     * @param timeout   tiempo limite de espera para la recepción,
     *                  en milisegundos
     *
     * @return          retorna cero en caso de exito
     *                  devuelve un valor negativo en caso de error
     */
    double vxi11_obtain_double_value(Pointer clink, String cmd, long timeout);
}
