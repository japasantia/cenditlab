package ve.gob.cendit.cenditlab.ui;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import ve.gob.cendit.cenditlab.control.ComponentDescriptor;
import ve.gob.cendit.cenditlab.control.System;
import ve.gob.cendit.cenditlab.ui.base.ViewType;

import java.util.List;
import java.util.stream.Collectors;

public class SystemsSetupActivityView extends SplitPane
{
    private static final String FXML_URL = "fxml/systems-setup-activity-view.fxml";

    private static final ViewLoader viewLoader = new ViewLoader(FXML_URL);

    private static final Image DEFAULT_ICON =
            new Image(SystemsSetupActivityView.class.getResource("/ve/gob/cendit/cenditlab/ui/images/system-icon.png").toExternalForm());

    @FXML
    private HeaderContainerView systemsContainerView;

    @FXML
    private HeaderContainerView detailContainerView;

    @FXML
    private HeaderContainerView setupContainerView;

    @FXML
    private VBox detailVBox;

    @FXML
    private VBox setupVBox;

    @FXML
    private ToolboxListView<System> systemsToolboxListView;

    public SystemsSetupActivityView()
    {
        viewLoader.load(this, this);
        initialize();
    }

    public SystemsSetupActivityView(System... systems)
    {
        initialize();
        loadSystems(systems);
    }

    private void initialize()
    {
        systemsToolboxListView.enableMultipleSelection(true);

        systemsToolboxListView.setViewFactory(this::getSystemView);

        systemsToolboxListView.setOnItemClicked(this::onSystemClicked);
    }

    // TODO: completar + modificar
    public ObservableList<System> getSystems()
    {
        return null;
    }

    public List<System> getSelectedSystems()
    {
        List<ToolboxListView<System>.Item> selectedItemsList = systemsToolboxListView.getSelectedItems();

        return selectedItemsList.stream()
            .map(item -> item.getValue())
            .collect(Collectors.toList());
    }

    public void loadSystems(System... systems)
    {
        unloadSystems();
        addSystems(systems);
    }

    public void addSystems(System... systems)
    {
        systemsToolboxListView.addItems(systems);
    }

    public void unloadSystems()
    {
        systemsToolboxListView.clearItems();

        detailVBox.getChildren().clear();
    }

    public void clearDetail()
    {
        detailVBox.getChildren().clear();
    }

    public void clearSetup()
    {
        setupVBox.getChildren().clear();
    }

    private void onSystemClicked(MouseEvent mouseEvent)
    {
        ComponentIconView componentIconView = (ComponentIconView) mouseEvent.getSource();

        componentIconView.toggleSelected();
    }

    private Node getSystemView(ToolboxListView<System>.Item item)
    {
        Node view = item.getView();

        if (view == null)
        {
            view = new ComponentIconView(item.getValue());
        }

        return view;
    }

    private void onSystemClicked(ToolboxListView<System>.Item item)
    {
        ComponentIconView iconView = (ComponentIconView) item.getView();

        if (iconView != null)
        {
            iconView.toggleSelected();
        }
    }
}
