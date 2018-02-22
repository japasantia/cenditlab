package ve.gob.cendit.cenditlab.ui;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import ve.gob.cendit.cenditlab.data.Options;
import ve.gob.cendit.cenditlab.data.ValueData;

public class DataContainerView extends VBox
{
    private static final String FXML_URL = "fxml/fields-view.fxml";

    private int nextRow = -1;

    @FXML
    private GridPane parametersGridPane;

    public DataContainerView()
    {
        ViewLoader.load(FXML_URL, this, this);
    }

    public void addField(String name, ValueData valueData)
    {
        int nextRow = nextRow();

        parametersGridPane.add(new Label(name), 0, nextRow);
        parametersGridPane.add(new ValueView(valueData), 1, nextRow);
    }

    public void addOptions(String name, Options options)
    {
        int nextRow = nextRow();

        ChoiceBox<String> choiceBox = new ChoiceBox();
        choiceBox.getItems().addAll(options.getValues());
        choiceBox.setValue(options.getDefault());

        parametersGridPane.add(new Label(name), 0, nextRow);
        parametersGridPane.add(choiceBox, 1, nextRow);
    }

    public void addSectionLabel(String caption)
    {
        int nextRow = nextRow();

        parametersGridPane.add(new Label(caption), 0, nextRow, 2, 1);
    }

    public int getRows()
    {
        return parametersGridPane.getRowConstraints().size();
    }

    private int nextRow()
    {
        RowConstraints rowConstraints =
                parametersGridPane.getRowConstraints().get(0);
        parametersGridPane.getRowConstraints()
                .add(++nextRow, rowConstraints);

        return nextRow;
    }
}
