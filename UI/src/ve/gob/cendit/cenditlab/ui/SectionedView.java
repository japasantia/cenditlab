package ve.gob.cendit.cenditlab.ui;

import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;

public class SectionedView extends VBox
{
    private static final String FXML_URL = "fxml/sectioned-view.fxml";

    @FXML
    private HBox headerHBox;

    @FXML
    private HBox footerHBox;

    @FXML
    private SplitPane centerSplitPane;

    private Map<String, Node> centerSectionsMap;

    public SectionedView()
    {
        ViewLoader.load(FXML_URL, this, this);

        centerSectionsMap = new HashMap<String, Node>();
    }

    public void addHeader(Node node)
    {
        headerHBox.getChildren().add(node);
    }

    public void addFooter(Node node)
    {
        footerHBox.getChildren().add(node);
    }

    public void removeHeader(Node node)
    {
        headerHBox.getChildren().remove(node);
    }

    public void removeFooter(Node node)
    {
        footerHBox.getChildren().remove(node);
    }

    public void clearHeader(Node node)
    {
        headerHBox.getChildren().clear();
    }

    public void clearFooter(Node node)
    {
        footerHBox.getChildren().clear();
    }

    public void createCenterSection(String sectionId, Node sectionRoot)
    {
        if (sectionId == null || sectionRoot == null)
            return;

        //SplitPane.Divider divider = new SplitPane.Divider();
        //divider.setPosition(0.5);

        //centerSplitPane.getDividers().add(divider);
        centerSplitPane.getItems().add(sectionRoot);

        centerSectionsMap.put(sectionId, sectionRoot);
    }

    public Node removeCenterSection(String sectionId)
    {
        Node node = centerSectionsMap.get(sectionId);

        if (node != null)
        {
            centerSplitPane.getItems().remove(node);
            centerSectionsMap.remove(sectionId);
        }

        return node;
    }

    public Node getCenterSection(String sectionId)
    {
        return centerSectionsMap.get(sectionId);
    }

    public void setCenterSectionOrientation(Orientation orientation)
    {
        centerSplitPane.setOrientation(orientation);
    }
}
