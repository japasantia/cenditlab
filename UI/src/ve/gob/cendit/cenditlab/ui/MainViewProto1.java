package ve.gob.cendit.cenditlab.ui;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class MainViewProto1 extends BorderPane
{
    private static final String FXML_URL = "fxml/main-view-proto-1.fxml";

    @FXML
    private VBox topVBox;

    @FXML
    private VBox toolbarVBox;

    public MainViewProto1()
    {
        ViewLoader.load(FXML_URL, this, this);
    }

    public void addToolBar(Node toolbar)
    {
        if (toolbar == null)
        {
            return;
        }

        VBox.setVgrow(toolbar, Priority.SOMETIMES);
        toolbarVBox.getChildren().add(toolbar);
    }

    public void removeToolBar(Node toolbar)
    {
        toolbarVBox.getChildren().remove(toolbar);
    }
}
