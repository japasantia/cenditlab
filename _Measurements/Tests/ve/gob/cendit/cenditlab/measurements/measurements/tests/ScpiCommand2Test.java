package ve.gob.cendit.cenditlab.measurements.measurements.tests;

import ve.gob.cendit.cenditlab.measurements.ScpiCommand2;
import ve.gob.cendit.cenditlab.measurements.Variable;
import ve.gob.cendit.cenditlab.measurements.VariablesBundle;

import java.util.stream.Stream;

public class ScpiCommand2Test
{
    public static void main(String[] arg)
    {
        Variable v1 = new Variable("Data", 12);
        Variable v2 = new Variable("Value", 3.13);
        Variable v3 = new Variable("State", "ON");

        VariablesBundle bundle = new VariablesBundle();
        bundle.add("value", v1);
        bundle.add("data", v2);
        bundle.add("state", v3);

        ScpiCommand2 cmd1 = new ScpiCommand2(":MEAS:READ $value$, $data$", bundle);
        ScpiCommand2 cmd2 = new ScpiCommand2(":INPUT:DC $data$, $state$");
        ScpiCommand2 cmd3 = new ScpiCommand2(":MEAS:WRITE $state$", bundle);

        String scpiCommand = cmd1.getCommand();
        scpiCommand = cmd2.getCommand();

        scpiCommand = cmd3.applyVariables(bundle);

        v1.setValue("start");
        v2.setValue(15);
        v3.setValue(2.81);

        scpiCommand = cmd1.getCommand();
        scpiCommand = cmd2.getCommand();

        scpiCommand = cmd3.applyVariables(bundle);

        printVariables("bundle variables ",
                bundle.variables().stream());

    }

    private static void printVariables(String message, Stream<Variable> variablesStream)
    {
        System.out.println(message);

        variablesStream
            .forEach( v -> System.out.printf("%s = %s\n", v.getName(), v.getValue()) );
    }

}
