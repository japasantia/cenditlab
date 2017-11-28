package ve.gob.cendit.cenditlab.ui;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.VBox;
import ve.gob.cendit.cenditlab.control.System;
import ve.gob.cendit.cenditlab.control.Task;
import ve.gob.cendit.cenditlab.ui.base.ViewType;

import java.util.Arrays;

public class TasksSetupStepViewProto1 extends SplitPane
{
    private static final String FXML_URL =
            "fxml/tasks-setup-step-view.fxml";

    private static final ViewLoader viewLoader =
            new ViewLoader(FXML_URL);

    @FXML
    private HeaderContainerView tasksContainerView;

    @FXML
    private HeaderContainerView detailContainerView;

    @FXML
    private HeaderContainerView setupContainerView;

    @FXML
    private VBox detailVBox;

    @FXML
    private VBox setupVBox;

    private ComponentListView<Task> tasksListView;

    public TasksSetupStepViewProto1()
    {
        viewLoader.load(this, this);

        initialize();
    }

    public TasksSetupStepViewProto1(System... systems)
    {
        this();

        loadSystems(systems);
    }

    private void initialize()
    {
        tasksListView = new ComponentListView<>();

        tasksListView.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        tasksContainerView.setCenter(tasksListView);

        tasksListView.setOnComponentSelectionChanged(this::onTaskSelectionChange);
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

    public void addDetail(Node node)
    {
        detailVBox.getChildren().add(node);
    }

    public void clearDetail()
    {
        detailVBox.getChildren().clear();
    }

    public void addSetup(Node node)
    {
        setupVBox.getChildren().add(node);
    }

    public void clearSetup()
    {
        setupVBox.getChildren().clear();
    }

    private <T extends Task> void onTaskSelectionChange(ObservableValue<? extends Task> observable,
                                                        T oldTask, T newTask)
    {
        if (newTask == null)
            return;

        clearDetail();
        clearSetup();

        String taskName = newTask.getName();

        Node node = newTask.getView(ViewType.DETAILS);

        if (node != null)
        {
            String caption = String.format("%s info", taskName);
            detailContainerView.setCaption(caption);
            addDetail(node);
        }

        node = newTask.getView(ViewType.SETUP);

        if (node != null)
        {
            String caption = String.format("%s setup", taskName);
            setupContainerView.setCaption(caption);
            addSetup(node);
        }
    }
}