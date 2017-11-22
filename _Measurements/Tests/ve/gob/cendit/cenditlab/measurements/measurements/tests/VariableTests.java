package ve.gob.cendit.cenditlab.measurements.measurements.tests;

import ve.gob.cendit.cenditlab.measurements.Variable;
import ve.gob.cendit.cenditlab.measurements.VariableChangeListener;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class VariableTests
{
    public static void main(String[] args)
    {
        //test1();
        gen();
    }

    private static void test1() {
        Variable v1 = new Variable("Var1", 12.5);
        Variable v2 = new Variable("Var2", "Hola");

        VariableChangeListener changeListener =
            new VariableChangeListener()
            {
                /*
                @Override
                public boolean validate(Variable variable, Object oldValue, Object newValue)
                {
                    System.out.printf("Variable: %s validate old: %s new: %s",
                            variable.getName(), oldValue, newValue);

                    return true;
                }
                */

                @Override
                public void changed(Variable variable, Object oldValue, Object newValue)
                {
                    System.out.printf("Variable: %s changed from old: %s to new: %s",
                            variable.getName(), oldValue, newValue);
                }
            };

        v1.addChangeListener(changeListener);
        v2.addChangeListener(changeListener);

        v1.setValue("Adios");
        v2.setValue(3.14);

        v1.removeChangeListener(changeListener);
        v2.removeAllListeners();
    }

    private static boolean validate(Variable variable, Object oldValue, Object newValue)
    {
        System.out.printf("Validating variable %s",
                variable.getName());

        return true;
    }
    private static void onChange(Variable variable, Object oldValue, Object newValue)
    {
        System.out.printf("Variable %s has changed",
                variable.getName());
    }

    private static void gen()
    {
        List<C<? extends Object>> list = new LinkedList<>();

        list.add(new C<Integer>(1));
        list.add(new C<Float>(12.5f));

        Iterator<C<? extends Object>> it = list.iterator();

        while (it.hasNext())
        {
            C<? extends Object> c = it.next();
            System.out.printf("%s", c.toString());

            if (c instanceof C)
            {

            }
        }
    }
}

class C<T>
{
    C(T v)
    {
        value = v;
    }

    @Override
    public String toString()
    {
        return value.toString();
    }

    private T value;

}


