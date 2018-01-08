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

    int ibdev(int board_index, int pad, int sad, int timeout,
              int send_eoi, int eos);
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

     /** Constantes para ibln */
     int NO_SAD = 0;
     int ALL_SAD = -1;

     int NULL_END = 0;
     int DAB_END = 1;
     int NL_END = 2;
}
