package ve.gob.cendit.cenditlab.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import ve.gob.cendit.cenditlab.control.Component;

public class ComponentDescriptionView extends GridPane
{
    private static final String FXML_URL  = "fxml/component-description-view.fxml";

    private static final ViewLoader viewLoader =
            new ViewLoader(FXML_URL);

    @FXML
    private Label componentNameLabel;

    @FXML
    private Label componentDescriptionLabel;

    private Component component;

    public ComponentDescriptionView()
    {
        viewLoader.load(this, this);
    }

    public ComponentDescriptionView(Component component)
    {
        this();

        setComponent(component);
    }


    public void setComponent(Component component)
    {
        // TODO: check for null component (exception?)
        if (component == null)
        {
            throw new IllegalArgumentException("component must not be null");
        }

        this.component = component;

        String text = component.getName();
        componentNameLabel.setText(text != null ? text : "");

        text = component.getDescription();
        componentDescriptionLabel.setText(text != null ? text : "");
    }

    public Component getComponent()
    {
        return component;
    }
}
