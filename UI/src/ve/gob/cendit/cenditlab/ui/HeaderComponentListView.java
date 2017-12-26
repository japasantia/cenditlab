package ve.gob.cendit.cenditlab.ui;

import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import ve.gob.cendit.cenditlab.control.Component;
import ve.gob.cendit.cenditlab.ui.base.ViewType;

public class HeaderComponentListView<T extends Component> extends TitledPane
{
    private static final String FXML_URL = "fxml/header-toolbox-list-view.fxml";

    private static final String DEFAULT_ICON_URL = "images/task-icon.jpg";
    private static final String DEFAULT_TITLE = "";

    @FXML
    private ImageView iconImageView;

    @FXML
    private ListView<T> containerListView;

    private ViewType viewType = ViewType.LIST_ICON;

    private EventHandler<MouseEvent> onItemClickedHandler;
    private Node selectedNodeView;

    public HeaderComponentListView()
    {
        ViewLoader.load(FXML_URL, this, this);

        setCellFactory(listView -> new ComponentListCell());
    }

    public void setTitle(String value)
    {
        this.setText(value != null ? value : DEFAULT_TITLE);
    }

    public String getTitle()
    {
        return this.getText();
    }

    public void setIcon(Image image)
    {
        if (image != null)
        {
            iconImageView.setVisible(true);
            iconImageView.setImage(image);
        }
        else
        {
            iconImageView.setVisible(false);
        }
    }

    public Image getImage()
    {
        return iconImageView.getImage();
    }

    public void setViewType(ViewType value)
    {
        viewType = value;
    }

    public void enableMultipleSelection(boolean value)
    {
        containerListView.getSelectionModel()
                .setSelectionMode(value ? SelectionMode.MULTIPLE : SelectionMode.SINGLE);
    }

    public void setItems(ObservableList<T> list)
    {
        if (list != null)
        {
            containerListView.setItems(list);
        }
        else
        {
           clearItems();
        }
    }

    public ObservableList<T> getItems()
    {
        return containerListView.getItems();
    }

    public void clearItems()
    {
        containerListView.getItems().clear();
    }

    public Component getSelectedItem()
    {
        return containerListView.getSelectionModel().getSelectedItem();
    }

    public ObservableList<T> getSelectedItems()
    {
        return containerListView.getSelectionModel().getSelectedItems();
    }

    public Node getSelectedNodeView()
    {
        return selectedNodeView;
    }

    public void setCellFactory(Callback<ListView<T>, ListCell<T>> callback)
    {
        containerListView.setCellFactory(callback);
    }

    public void setOnListItemClicked(EventHandler<MouseEvent> eventHandler)
    {
       onItemClickedHandler = eventHandler;
    }

    public void setOnListSelectionChanged(ChangeListener<T> listener)
    {
        containerListView.getSelectionModel()
                .selectedItemProperty()
                .addListener(listener);
    }

    private class ComponentListCell extends ListCell<T>
    {
        public ComponentListCell()
        { }

        @Override
        protected void updateItem(T componentItem, boolean empty)
        {
            super.updateItem(componentItem, empty);

            if (empty || componentItem == null)
                return;

            // TODO: revisar por elemento null
            Node node = componentItem.getView(viewType);

            if (node != null)
            {
                setGraphic(node);
                node.setOnMouseClicked(event ->
                {
                    selectedNodeView = node;
                    onItemClickedHandler.handle(event);
                });
            }
        }
    }
}
