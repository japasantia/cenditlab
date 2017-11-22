package ve.gob.cendit.cenditlab.tests;

import ve.gob.cendit.cenditlab.data.*;

/**
 * Created by root on 22/08/17.
 */
public class DataTest
{
    public static void main(String[] args)
    {
        // numericDataTest();
        // listDataTest();
        // booleanDataTest();
        arrayDataTest();
    }

    public static void dataTest()
    {
        Data freqData = new Data("Frequency");
        Data ampData = new Data("Amplitude", 10.5);
        Data maxData = new Data("Maximum", 120.0f);
        Data minData = new Data("Minimum", maxData);

        DataSet dataSet1;
        try
        {
            dataSet1 = new DataSet();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        dataSet1 = new DataSet(freqData, ampData, maxData);
        DataSet dataSet2 = new DataSet(freqData, ampData);
        DataSet dataSet3 = new DataSet(new Data[] {minData, maxData});

        dataSet1.setValue("Frequency", ampData);
        dataSet2.setValue("Amplitude", 15.0f);

        minData.set(ampData);

        float result;

        if (dataSet2.containsData("Amplitude"))
            result =  (float) dataSet2.getData("Amplitude").get();

        dataSet3.setValue("Minimum", (Object) new Float(-10.0f));

        print("Single Values");
        print(freqData);
        print(ampData);

        print("DataSet container");
        print(dataSet1);
        print(dataSet2);
        print(dataSet3);
    }

    public static void numericDataTest()
    {
        NumberData data1 = new NumberData("Number1");
        NumberData data2 = new NumberData("Number1", 12.3);
        NumberData data3 = new NumberData("Number1", "+45.67e3");

        print("Data1", data1);
        print("Data2", data2);
        print("Data3", data3);

        data1.set("#HCAFE");
        data2.set(data1);

        try
        {
            data1.set("Hola");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        print("isValid 12.5", NumberData.isValid("12.5"));
        print("isValid 'Hola'", NumberData.isValid("Hola"));
        print("isValid 3.14", NumberData.isValid(3.14));
        print("isValid true", NumberData.isValid(true));
        print("isValid data1", NumberData.isValid(data1));
    }

    public static void listDataTest()
    {
        ListData list1 = new ListData("List1");

        list1.set(12.5);
        print("List with single double value", list1);

        list1.set(" frequency  , spam  , V5  , 12.34 ");
        print("String list", list1);

        list1.set("freq span, voltage range");
        print("String list with space", list1);

        list1.set("frequency");
        print("List with single value", list1);

        list1.set(new Number[] {new Integer(17), new Float(3.17f)});
        print("List of Number", list1);

        list1.set(new  Object[] {"Cadena", 1.2, new Float(2.72f)});
        print("List of Object", list1);
    }

    public static void booleanDataTest()
    {
        BooleanData data1 = new BooleanData("Boolean data");
        BooleanData data2 = new BooleanData("Boolean data", 1);
        BooleanData data3 = new BooleanData("Boolean data", "Off");

        print("Bolean data 1", data1);
        print("Bolean data 2", data2);
        print("Bolean data 3", data3);

        data1.set("On");
        data2.set(5.0);
        data3.set(0);

        print("Bolean data 1", data1);
        print("Bolean data 2", data2);
        print("Bolean data 3", data3);

        data1.set(false);
        print("Bolean data 1", data1);

        try
        {
            data1.set("valor");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public static void arrayDataTest()
    {
        ArrayData array1 = new ArrayData("Array 1");
        ArrayData array2 = new ArrayData("Array 2",
            new String[][] { { "1", "2" }, { "3", "4", "5"} });
        ArrayData array3 = new ArrayData("Array 2",
            new Number[][] { { new Integer(17) }, { new Float(3.14) } });

        print(array1.getName(), array1);
        print(array2.getName(), array2);
        print(array3.getName(), array3);

        try
        {
            array1.set(new int[][] { {1, 2}, {3, 4} });
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        try
        {
            array2.set(new String[] { "Row 1", "Row 2" });
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        array1.set(new Object[][] { { 1, "a" }, null, { "b", 2 }, { 3, "c", null } });

        print(array1.getName(), array1);

        array1.setItem(3, 2, 10);
        array1.setItem(1, 2, 3.14f);
        array1.setItem(2, 2, "new");
        array1.setItem(2, 10, "unreachable");
        array1.setItem(10, 20, "nothing");

        print(array1.getName(), array1);

        Object item = array1.getItem(1, 6);

        print("Array 1[5, 6]:", item);
    }

    public static void print(Object object)
    {
        java.lang.System.out.println(object.toString());
    }

    public static void print(String message, Object object)
    {
        if (message != null)
        {
            java.lang.System.out.printf("%s: %s\n", message,
                    object != null ? object.toString() : "empty");
        }
    }
}
