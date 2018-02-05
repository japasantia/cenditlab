package ve.gob.cendit.cenditlab.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.VBox;
import ve.gob.cendit.cenditlab.control.System;

import java.util.List;

public class SystemsSetupActivityView extends SplitPane
{
    private static final String FXML_URL = "fxml/systems-setup-activity-view.fxml";

    private static final ViewLoader viewLoader = new ViewLoader(FXML_URL);

    @FXML
    private VBox detailVBox;

    @FXML
    private VBox setupVBox;

    @FXML
    private ItemsListView<System> systemItemsListView;

    private Node setupView;
    private IconView detailView;

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

        setupView = new IconView();
        detailView = new IconView();
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

        clearDetailView();
        clearSetupView();
    }

    private void setSelectedSytem(System system)
    {
        updateDetailView(system);
        updateSetupView(system);
    }

    private void updateSetupView(System system)
    {
        clearSetupView();

        setupView = ViewFactory.buildSetupView(system.getSetup());

        if (setupView != null)
            setupVBox.getChildren().add(setupView);
    }

    private void updateDetailView(System system)
    {
        clearDetailView();

        detailView.setComponentDescriptor(system.getComponentDescriptor());
        detailVBox.getChildren().add(detailView);
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
            itemView = new IconView(item.getValue().getComponentDescriptor());
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
