package ve.gob.cendit.cenditlab.io;

import com.sun.jna.Library;
import com.sun.jna.Native;

/**
 * Created by jsars on 05/06/17.
 */
public class HelloWorld
{
    public interface CTest extends Library {
       void helloFromC();
    }

    static public void main(String[] args) {
        CTest ctest =
                (CTest) Native.loadLibrary("ctestc", CTest.class);
        ctest.helloFromC();

    }
}
