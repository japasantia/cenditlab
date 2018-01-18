package ve.gob.cendit.cenditlab.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import ve.gob.cendit.cenditlab.control.System;
import ve.gob.cendit.cenditlab.ui.base.ViewType;

import java.util.List;

public class SystemsSetupActivityView extends SplitPane
{
    private static final String FXML_URL = "fxml/systems-setup-activity-view.fxml";

    private static final ViewLoader viewLoader = new ViewLoader(FXML_URL);

    private static final Image DEFAULT_ICON =
            new Image(SystemsSetupActivityView.class.getResource("/ve/gob/cendit/cenditlab/ui/images/system-icon.png").toExternalForm());

    @FXML
    private VBox detailVBox;

    @FXML
    private VBox setupVBox;

    @FXML
    private ItemsListView<System> systemItemsListView;

    public SystemsSetupActivityView()
    {
        viewLoader.load(this, this);
        initialize();
    }

    public SystemsSetupActivityView(System... systems)
    {
        initialize();
        setSystems(systems);
    }

    private void initialize()
    {
        systemItemsListView.enableMultipleSelection(true);

        systemItemsListView.getItemsList().setViewFactory(this::getSystemView);

        systemItemsListView.setOnItemClicked(this::onSystemClicked);
    }

    public List<System> getSystems()
    {
        return systemItemsListView.getItemsList().getAll();
    }

    public List<System> getSelectedSystems()
    {
        return systemItemsListView.getItemsList().getAllSelected();
    }

    public void setSystems(System... systems)
    {
        clear();
        addSystems(systems);
    }

    public void addSystems(System... systems)
    {
        systemItemsListView.getItemsList().addAll(systems);
    }

    public void load()
    {
        systemItemsListView.load();
    }

    public void unload()
    {
        systemItemsListView.unload();

        detailVBox.getChildren().clear();
        setupVBox.getChildren().clear();
    }

    private void setSelectedSytem(System system)
    {
        setSetupView(system.getView(ViewType.SETUP));
        setDetailView(system.getView(ViewType.DETAILS));
    }

    private void setSetupView(Node view)
    {
        clearSetupView();
        setupVBox.getChildren().add(view);
    }

    private void setDetailView(Node view)
    {
        clearDetailView();
        detailVBox.getChildren().add(view);
    }

    private void clearListView()
    {
        systemItemsListView.unload();
    }

    private void clearDetailView()
    {
        detailVBox.getChildren().clear();
    }

    private void clearSetupView()
    {
        setupVBox.getChildren().clear();
    }

    private void clear()
    {
        clearListView();
        clearDetailView();
        clearSetupView();
    }

    private Node getSystemView(Item<System> item)
    {
        Node itemView = item.getView();

        if (itemView == null)
        {
            itemView = item.getValue().getView(ViewType.ICON);
            itemView = new ItemView(itemView);
        }

        return itemView;
    }

    private void onSystemClicked(Item<System> item)
    {
        ItemView itemView = (ItemView) item.getView();

        if (itemView != null)
        {
            itemView.setSelected(item.isSelected());

            setSelectedSytem(item.getValue());
        }
    }
}
