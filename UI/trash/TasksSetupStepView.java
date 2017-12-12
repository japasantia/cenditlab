package ve.gob.cendit.cenditlab.ui;

import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import ve.gob.cendit.cenditlab.control.System;
import ve.gob.cendit.cenditlab.control.Task;
import ve.gob.cendit.cenditlab.ui.base.ViewType;

import java.util.Arrays;

public class TasksSetupStepView extends SectionedView
{
    private HeaderContainerView masterContainer;
    private HeaderContainerView detailContainer;
    private HeaderContainerView setupContainer;

    private ComponentListView<Task> tasksListView;

    private SectionedView detailSetupSectionedView;

    private ScrollPane detailScrollView;
    private ScrollPane setupScrollPane;

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
        masterContainer = new HeaderContainerView();
        detailContainer = new HeaderContainerView();
        setupContainer = new HeaderContainerView();

        masterContainer.setCaption("Tasks");
        detailContainer.setCaption("Info");
        setupContainer.setCaption("Setup");

        tasksListView = new ComponentListView<>();
        masterContainer.setCenter(tasksListView);

        detailSetupSectionedView = new SectionedView();

        detailVBox = new VBox();
        setupVBox = new VBox();

        detailScrollView = new ScrollPane(detailVBox);
        setupScrollPane = new ScrollPane(setupVBox);

        detailContainer.setCenter(detailScrollView);
        setupContainer.setCenter(setupScrollPane);

        detailSetupSectionedView = new SectionedView();

        detailSetupSectionedView.setCenterSectionOrientation(Orientation.VERTICAL);
        detailSetupSectionedView.createCenterSection("Detail", detailContainer);
        detailSetupSectionedView.createCenterSection("Setup", setupContainer);

        this.createCenterSection("Master", masterContainer);
        this.createCenterSection("DetailSetup", detailSetupSectionedView);

        tasksListView.setOnComponentSelectionChanged(this::onTaskSelected);
    }

    public void loadSystems(System... systems)
    {
        unloadSystems();

        addSystems(systems);
    }

    public void addSystems(System... systems)
    {
        Arrays.stream(systems)
            .forEach(system -> tasksListView.addComponents(system.getTasks()));

        /*
        for (System system : systems)
        {
            HeaderComponentListView<Task> tasksListView = new HeaderComponentListView<>();

            tasksListView.setOnComponentSelectionChanged(this::onTaskSelected);

            tasksListView.setCollapsible(true);
            VBox.setVgrow(tasksListView, Priority.ALWAYS);

            tasksListView.setIcon(system.getIcon());

            tasksListView.setTitle(system.getName());
            tasksListView.getItems().addAll(system.getTasks());

            tasksListView.getChildren().add(tasksListView);
        }
        */
    }

    public void unloadSystems()
    {
        clearTasksList();

        clearSetup();

        clearDetail();
    }

    public void clearTasksList()
    {
        tasksListView.getItems().clear();
    }

    public void clearDetail()
    {
        detailVBox.getChildren().clear();
    }

    public void clearSetup()
    {
        setupVBox.getChildren().clear();
    }

    private <T extends Task> void onTaskSelected(ObservableValue<? extends Task> observable,
                                                 T oldTask, T newTask)
    {
        if (newTask == null)
            return;

        clearDetail();
        clearSetup();

        String taskName = newTask.getName();

        Node viewNode = newTask.getView(ViewType.DETAILS);

        if (viewNode != null)
        {
            String caption = String.format("%s info", taskName);
            detailContainer.setCaption(caption);
            detailVBox.getChildren().add(viewNode);
        }

        viewNode = newTask.getView(ViewType.SETUP);

        if (viewNode != null)
        {
            String caption = String.format("%s setup", taskName);
            setupContainer.setCaption(caption);
            setupVBox.getChildren().add(viewNode);
        }
    }
}
