package ve.gob.cendit.cenditlab.measurements.measurements.tests;

import ve.gob.cendit.cenditlab.measurements.ScpiCommand;
import ve.gob.cendit.cenditlab.measurements.Variable;
import ve.gob.cendit.cenditlab.measurements.VariablesBundle;

/**
 * Created by jarias on 20/07/17.
 */
public class ScpiCommandTest
{
    public static void main(String[] args)
    {
        VariablesBundle bundle = new VariablesBundle();

        Variable range = new Variable("Range", 10.0);
        Variable enable = new Variable("Enable", "ON");
        Variable min = new Variable("Min", 1e3);
        Variable max = new Variable("Max", 1e6);
        Variable dcValue = new Variable("DC value", 0.0);
        Variable array = new Variable("Array", null);

        bundle.add("range", range);
        bundle.add("enable", enable);
        bundle.add("min", min);
        bundle.add("max", max);
        bundle.add("array", array);
        bundle.add("dc-value", dcValue);

        printVariables("Start execution variables", bundle);

        ScpiCommand cmd1 = new ScpiCommand(":MEAS:DC: (range), (enable) [dc-value]");
        ScpiCommand cmd2 = new ScpiCommand(":FETCH:ARRAY (min), (max) [array]", bundle);
        ScpiCommand cmd3 = new ScpiCommand(":FETCH:DATA ON", bundle);

        boolean valid = cmd1.isWellFormed();
        String cmd = cmd1.getCommand();
        cmd = cmd1.applyVariables(bundle);

        valid = cmd2.isWellFormed();
        cmd = cmd2.getCommand();

        valid = cmd3.isWellFormed();
        cmd = cmd3.getCommand();
        cmd = cmd3.applyVariables(bundle);

        range.setValue(15.0f);
        enable.setValue("OFF");
        min.setValue(250.0f);
        max.setValue(1750.0f);

        cmd = cmd1.applyVariables(bundle);
        cmd = cmd2.getCommand();

        printVariables("End execution variables", bundle);
    }

    private static void printVariables(String message, VariablesBundle bundle)
    {
        System.out.println(message);

        bundle.variables()
            .stream()
            .forEach(v -> System.out.printf("%s = %s\n", v.getName(), v.getValue()));
    }
}
