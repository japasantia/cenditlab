package ve.gob.cendit.cenditlab.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import ve.gob.cendit.cenditlab.data.ListData;

public class ListDataView extends TitledPane
{
    private static final ViewLoader viewLoader =
        new ViewLoader("fxml/list-data-view.fxml");

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox containerVBox;

    private ListData listData;

    public ListDataView()
    {
        viewLoader.load(this, this);
    }

    public void setListData(ListData listData)
    {
        clear();

        if (listData == null)
            return;

        this.listData = listData;

        loadListData();
    }

    public ListData getListData()
    {
        return listData;
    }

    private void loadListData()
    {
        listData.getAsStringList()
            .forEach(this::add);
    }

    public void add(String value)
    {
        Node viewNode = getItemDataView(value);

        containerVBox.getChildren().add(viewNode);
    }

    public void clear()
    {
        containerVBox.getChildren().clear();
    }

    public void setVisibleHBar(boolean value)
    {
        scrollPane.setHbarPolicy(value ?
            ScrollPane.ScrollBarPolicy.AS_NEEDED : ScrollPane.ScrollBarPolicy.NEVER);
    }

    public void setVisibleVBar(boolean value)
    {
        scrollPane.setVbarPolicy(value ?
                ScrollPane.ScrollBarPolicy.AS_NEEDED : ScrollPane.ScrollBarPolicy.NEVER);
    }

    private Node getItemDataView(String data)
    {
        Label label = new Label(data);

        return label;
    }

    public void scrollToItem(int index)
    {
        int size = listData.size();

        if (index < 0 || index >= size)
            return;

        scrollPane.setVvalue(((double) index) / (size - 1));

        containerVBox.getChildren().get(index).requestFocus();
    }
}
