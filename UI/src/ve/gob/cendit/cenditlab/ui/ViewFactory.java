package ve.gob.cendit.cenditlab.ui;

import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import ve.gob.cendit.cenditlab.data.EnrSetup;
import ve.gob.cendit.cenditlab.data.FrequencySetup;
import ve.gob.cendit.cenditlab.data.Setup;

import java.util.List;

public class ViewFactory
{
    public static Node buildSetupView(List<Setup> setupList)
    {
        if (setupList == null || setupList.size() == 0)
            return null;

        VBox containerVBox = new VBox();

        ScrollPane scrollPane = new ScrollPane(containerVBox);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        setupList.forEach(setup ->
        {
            Node setupView = buildSetupView(setup);

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
}
