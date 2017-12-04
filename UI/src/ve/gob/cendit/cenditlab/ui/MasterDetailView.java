package ve.gob.cendit.cenditlab.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.TilePane;

public class MasterDetailView extends SplitPane
{
    private static final String FXML_URL = "fxml/master-detail-view.fxml";

    @FXML
    ListView<Node> masterListView;

    @FXML
    TilePane detailPane;

    public MasterDetailView()
    {
        ViewLoader.load(FXML_URL, this, this);
    }

    public void addMaster(Node item)
    {
        masterListView.getItems().add(item);
    }

    public void addDetail(Node item)
    {
        detailPane.getChildren().add(item);
    }

    public void clearMaster()
    {
        masterListView.getItems().clear();
    }

    public void clearDetail()
    {
        detailPane.getChildren().clear();
    }
}
