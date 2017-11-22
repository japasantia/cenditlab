package ve.gob.cendit.cenditlab.ui;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;

import javafx.geometry.Orientation;
import javafx.scene.Node;

import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import ve.gob.cendit.cenditlab.control.System;
import ve.gob.cendit.cenditlab.control.Task;
import ve.gob.cendit.cenditlab.ui.base.ViewType;

public class SystemsSetupStepView extends SectionedView
{
    private static final Image DEFAULT_ICON =
            new Image(SystemsSetupStepView.class.getResource("/ve/gob/cendit/cenditlab/ui/images/system-icon.png").toExternalForm());

    private HeaderContainerView masterHeaderContainer;
    private ComponentListView<System> masterListView;

    private SectionedView detailSetupSectionedView;

    private VBox detailVBox;
    private VBox setupVBox;

    public SystemsSetupStepView()
    {
        initialize();
    }

    public SystemsSetupStepView(System... systems)
    {
        initialize();
        setSystems(systems);
    }

    private void initialize()
    {
        masterHeaderContainer = new HeaderContainerView();
        masterListView = new ComponentListView<>();
        masterListView.enableMultipleSelection(true);

        masterHeaderContainer.setCenterContent(masterListView);
        masterHeaderContainer.setText("Available Systems");
        masterHeaderContainer.setIcon(DEFAULT_ICON);

        detailVBox = new VBox();
        setupVBox = new VBox();

        detailSetupSectionedView = new SectionedView();
        detailSetupSectionedView.createCenterSection("Detail", detailVBox);
        detailSetupSectionedView.createCenterSection("Setup", setupVBox);
        detailSetupSectionedView.setCenterSectionOrientation(Orientation.VERTICAL);

        this.createCenterSection("Master", masterHeaderContainer);
        this.createCenterSection("SetupDetail", detailSetupSectionedView);

        masterListView.setOnListItemClicked(this::onSystemClicked);
        masterListView.setOnListSelectionChanged(this::onSystemSelected);
    }

    public ObservableList<System> getSystems()
    {
        return masterListView.getItems();
    }

    public void setSystems(System... systems)
    {
        clearSystems();
        addSystems(systems);
    }

    public void addSystems(System... systems)
    {
        getSystems().addAll(systems);
    }

    public void clearSystems()
    {
        getSystems().clear();

        detailVBox.getChildren().clear();
    }

    private <T extends System> void onSystemSelected(ObservableValue<? extends System> observable,
                                  T oldSystem, T newSystem)
    {
        if (newSystem == null)
            return;

        Task[] tasks = newSystem.getTasks();

        if (tasks == null)
            return;

        detailVBox.getChildren().clear();
        setupVBox.getChildren().clear();

        for (Task task : tasks)
        {
            Node viewNode = task.getView(ViewType.DESCRIPTION);

            if (viewNode != null)
            {
                detailVBox.getChildren().add(viewNode);
            }

            viewNode = task.getView(ViewType.SETUP);

            if (viewNode != null)
            {
                setupVBox.getChildren().add(viewNode);
            }
        }
    }

    private void onSystemClicked(MouseEvent mouseEvent)
    {
        ComponentIconView componentIconView = (ComponentIconView) mouseEvent.getSource();

        componentIconView.toggleSelected();
    }
}
