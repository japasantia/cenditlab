package ve.gob.cendit.cenditlab.ui;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import ve.gob.cendit.cenditlab.control.ComponentDescriptor;
import ve.gob.cendit.cenditlab.control.System;
import ve.gob.cendit.cenditlab.control.Task;
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

    private ComponentListView<System> systemsListView;


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
        systemsListView = new ComponentListView<>();
        systemsListView.enableMultipleSelection(true);
        systemsListView.setViewType(ViewType.LIST_ICON);
        systemsListView.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        systemsContainerView.setCenter(systemsListView);

        systemsListView.setOnComponentSelectionChanged(this::onSystemSelected);
        systemsListView.setOnComponentViewClicked(this::onSystemClicked);
    }

    public ObservableList<System> getSystems()
    {
        return systemsListView.getItems();
    }

    public void loadSystems(System... systems)
    {
        unloadSystems();
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

    private <T extends System> void onSystemSelected(ObservableValue<? extends System> observable,
                                                     T oldSystem, T newSystem)
    {
        if (newSystem == null)
            return;

        ComponentDescriptor[] taskDescriptors = newSystem.getTaskDescriptors();

        if (taskDescriptors == null)
            return;

        detailVBox.getChildren().clear();
        setupVBox.getChildren().clear();

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
}
