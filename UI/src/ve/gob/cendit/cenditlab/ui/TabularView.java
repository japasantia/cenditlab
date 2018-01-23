package ve.gob.cendit.cenditlab.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import ve.gob.cendit.cenditlab.data.Data;


public class TabularView extends Pane
{
    private ViewLoader viewLoader = new ViewLoader("fxml/table-view.fxml");

    @FXML
    private TableView<Data> tableView;

    public TabularView()
    {
        viewLoader.load(this, this);
    }

    private void initialize()
    {
        TableColumn<String, String> tc  = new TableColumn();





    }
}
