package ve.gob.cendit.cenditlab.ui;

import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import ve.gob.cendit.cenditlab.control.Task;
import ve.gob.cendit.cenditlab.control.System;
import ve.gob.cendit.cenditlab.ui.base.ViewType;

public class TasksSetupStepView extends SectionedView
{
    private ScrollPane masterScrollView;
    private SectionedView detailSetupSectionedView;

    private VBox masterVBox;
    private VBox detailVBox;
    private VBox setupVBox;

    public TasksSetupStepView()
    {
        initialize();
    }

    public TasksSetupStepView(System... systems)
    {
        initialize();
        loadSystems(systems);
    }

    private void initialize()
    {
        masterVBox = new VBox();
        masterScrollView = new ScrollPane(masterVBox);

        masterScrollView.setFitToWidth(true);
        masterScrollView.setPannable(true);
        masterVBox.setFillWidth(true);
        masterVBox.setPrefWidth(-1.0);
        masterVBox.setPrefHeight(-1.0);
        masterVBox.setMaxWidth(Double.POSITIVE_INFINITY);

        detailVBox = new VBox();
        setupVBox = new VBox();
        detailSetupSectionedView = new SectionedView();

        detailSetupSectionedView.setCenterSectionOrientation(Orientation.HORIZONTAL);
        detailSetupSectionedView.createCenterSection("Detail", detailVBox);
        detailSetupSectionedView.createCenterSection("Setup", setupVBox);

        this.createCenterSection("Master", masterScrollView);
        this.createCenterSection("DetailSetup", detailSetupSectionedView);
    }

    public void loadSystems(System... systems)
    {
        unloadSystems();

        addSystems(systems);
    }

    public void addSystems(System... systems)
    {
        for (System system : systems)
        {
            HeaderComponentListView<Task> tasksListView = new HeaderComponentListView<>();

            tasksListView.setOnListSelectionChanged(this::onTaskSelected);

            tasksListView.setCollapsible(true);
            VBox.setVgrow(tasksListView, Priority.ALWAYS);

            tasksListView.setIcon(system.getIcon());

            tasksListView.setTitle(system.getName());
            tasksListView.getItems().addAll(system.getTasks());

            masterVBox.getChildren().add(tasksListView);
        }
    }

    public void unloadSystems()
    {
        masterVBox.getChildren().clear();

        detailVBox.getChildren().clear();

        setupVBox.getChildren().clear();
    }

    private <T extends Task> void onTaskSelected(ObservableValue<? extends Task> observable,
                                                 T oldTask, T newTask)
    {
        if (newTask == null)
            return;

        detailVBox.getChildren().clear();
        setupVBox.getChildren().clear();

        Node viewNode = newTask.getView(ViewType.DETAILS);

        if (viewNode != null)
        {
            detailVBox.getChildren().add(viewNode);
        }

        viewNode = newTask.getView(ViewType.SETUP);

        if (viewNode != null)
        {
            setupVBox.getChildren().add(viewNode);
        }
    }
}
