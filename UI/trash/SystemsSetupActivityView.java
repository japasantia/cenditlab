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
    private ListView<System> systemsListView;

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
        systemsListView = new ListView<>();
        systemsListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        systemsListView.setCellFactory(listView -> new SystemListCell());

        systemsListView.getSelectionModel()
                .selectedItemProperty().addListener(this::onSystemSelectionChanged);
    }

    public ObservableList<System> getSystems()
    {
        return systemsListView.getItems();
    }

    public void loadSystems(System... systems)
    {
        unload();
        addSystems(systems);
    }

    public void addSystems(System... systems)
    {
        getSystems().addAll(systems);
    }

    public void unloadSystems()
    {
        getSystems().clear();

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

    private void onSystemSelectionChanged(ObservableValue<? extends System> observable,
                                          System oldSystem, System newSystem)
    {
        if (newSystem == null)
            return;

        clearDetailView();
        clearSetupView();

        ComponentDescriptor[] taskDescriptors = newSystem.getTaskDescriptors();

        if (taskDescriptors == null)
            return;

        setupVBox.getChildren().add(newSystem.getView(ViewType.DESCRIPTION));

        for (ComponentDescriptor descriptor : taskDescriptors)
        {
            Node iconView = new IconView(descriptor.getName(), descriptor.getIcon());

            if (iconView != null)
            {
                detailVBox.getChildren().add(iconView);
            }
        }
    }

    private void onSystemClicked(MouseEvent mouseEvent)
    {
        ComponentIconView componentIconView = (ComponentIconView) mouseEvent.getSource();

        componentIconView.toggleSelected();
    }

    private class SystemListCell extends ListCell<System>
    {
        public SystemListCell()
        { }

        @Override
        protected void updateItem(System system, boolean empty)
        {
            super.updateItem(system, empty);

            if (empty || system == null)
                return;

            Node node = system.getView(ViewType.LIST_ICON);

            if (node != null)
            {
                setGraphic(node);

                node.setOnMouseClicked(event -> onSystemClicked(event));
                // node.addEventFilter(MouseEvent.MOUSE_RELEASED, event -> onComponentListItemClicked(event));
            }
        }
    }
}
