package ve.gob.cendit.cenditlab.ui;

import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import ve.gob.cendit.cenditlab.control.DataDirection;
import ve.gob.cendit.cenditlab.data.*;

import java.util.Arrays;
import java.util.List;

public class ViewFactory
{
    public static Node buildSetupView(Setup[] setupArray)
    {
        if (setupArray == null || setupArray.length == 0)
            return null;

        VBox containerVBox = new VBox();

        ScrollPane scrollPane = new ScrollPane(containerVBox);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        Arrays.stream(setupArray)
            .forEach(setup ->
        {
            Node setupView = ViewFactory.buildSetupView(setup);

            if (setupView != null)
                containerVBox.getChildren().add(setupView);
        });

        return scrollPane;
    }

    public static Node buildSetupView(Setup setup)
    {
        Node setupView = null;

        if (setup instanceof FrequencySetup)
        {
            setupView = new FrequencySetupViewProto1((FrequencySetup) setup);
        }
        else if (setup instanceof EnrSetup)
        {
            setupView = new EnrSetupViewProto1((EnrSetup) setup);
        }

        return setupView;
    }

    public static Node buildDataView(Data data, DataDirection direction)
    {
        Node dataView = null;

        if (data instanceof ValueData || data instanceof NumericData ||
                data instanceof EnrData || data instanceof FrequencyData)
        {
            dataView = new ValueView((ValueData) data);
        }
        else if (data instanceof ListData)
        {
            dataView = new ListDataView((ListData) data);
        }
        else if (data instanceof GraphData)
        {
            dataView = new GraphView(data.getName(), (GraphData) data);
        }

        return dataView;
    }
}
