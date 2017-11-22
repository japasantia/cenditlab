package ve.gob.cendit.cenditlab.io;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

/**
 * Created by jarias on 07/07/17.
 */
public interface IVisa extends Library
{

    /** Equivalencias de tipos entre Visa y Java
     * Visa             typedef         Java
     *
     * ViObject         ViUInt32        int
     * ViSession        ViObject        int
     * ViVersion        ViUInt32        int
     * ViStatus         ViInt32         int
     * ViRsrc           ViString        byte[] o String
     * ViString         ViPChar         byte[] o String
     * ViAccessMode     ViUInt32        int
     * ViBusAddress     ViUInt64        long (?)
     * ViFindList       ViObject        int
     * ViBuf            ViPByte         byte[]
    **/

    /**
     * Constantes de uso en libreria Visa
     * definidas en <code>visa.h</code> y <code>visatype.h</code>
     */
    int VI_SUCCESS = 0;
    int VI_NULL = 0;
    int VI_TRUE = 1;
    int VI_FALSE = 0;

    int viOpenDefaultRM(Pointer vi  /*ViPSession = uint32*/);

    int viOpenDefaultRM(int[] vi  /*ViPSession = uint32*/);

    int viOpen(Pointer sesn,        /*ViSession*/
               String name,         /*ViRsrc*/
               int mode,            /*ViAccessMode*/
               int timeout,
               Pointer vi           /*ViPSession*/);

    int viOpen(int sesn,        /*ViSession*/
               String name,         /*ViRsrc*/
               int mode,            /*ViAccessMode*/
               int timeout,
               int[] vi           /*ViPSession*/);

    int viClose(Pointer vi          /*ViObject*/);

    int viClose(int[] vi          /*ViObject*/);

    int viWrite(Pointer vi,         /*ViSession*/
                byte[] buf,         /*ViBuf*/
                int cnt,            /*ViUInt32*/
                int[] retCnt);      /*ViPUInt32*/

    int viWrite(int vi,         /*ViSession*/
                byte[] buf,         /*ViBuf*/
                int cnt,            /*ViUInt32*/
                int[] retCnt);      /*ViPUInt32*/

    int viRead(Pointer vi,         /*ViSession*/
                byte[] buf,         /*ViBuf*/
                int cnt,            /*ViUInt32*/
                int[] retCnt);      /*ViPUInt32*/

    int viRead(int vi,         /*ViSession*/
               byte[] buf,         /*ViBuf*/
               int cnt,            /*ViUInt32*/
               int[] retCnt);      /*ViPUInt32*/

    int viFindRsrc(Pointer sesn,    /*ViSession*/
                   String expr,     /*ViString*/
                   int[] vi,        /*ViPFindList*/
                   int[] retCnt,      /*ViPUint32*/
                   byte[] desc      /*ViChar[]*/);


    int viFindRsrc(int sesn,    /*ViSession*/
                   String expr,     /*ViString*/
                   int[] vi,        /*ViPFindList*/
                   int[] retCnt,      /*ViPUint32*/
                   byte[] desc      /*ViChar[]*/);

    int viFindNext(int vi,          /*ViFindList*/
                   int[] desc      /*ViChar[]*/);
}
