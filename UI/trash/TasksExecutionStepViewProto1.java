package ve.gob.cendit.cenditlab.ui;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.VBox;
import ve.gob.cendit.cenditlab.control.Task;
import ve.gob.cendit.cenditlab.control.TaskContext;
import ve.gob.cendit.cenditlab.ui.base.ViewType;

public class TasksExecutionStepViewProto1 extends SplitPane
{
    private static final String FXML_URL = "fxml/tasks-execution-activity-view.fxml";

    private static final ViewLoader viewLoader = new ViewLoader(FXML_URL);

    @FXML
    private HeaderContainerView tasksContainerView;

    @FXML
    private HeaderContainerView resultsContainerView;

    @FXML
    private HeaderContainerView outputContainerView;

    @FXML
    private VBox resultsVBox;

    @FXML
    private VBox outputVBox;

    @FXML
    private ExecutionToolbar mainExecutionToolbar;

    private ExecutionToolbar executionToolbar;

    private ComponentListView<Task> tasksListView;

    private Task selectedTask;

    public TasksExecutionStepViewProto1()
    {
        viewLoader.load(this, this);

        initialize();
    }

    public TasksExecutionStepViewProto1(Task... tasks)
    {
        this();

        loadTasks(tasks);
    }

    private void initialize()
    {
        tasksListView = new ComponentListView<>();

        tasksListView.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        tasksListView.setViewType(ViewType.EXECUTION);
        tasksListView.setOnComponentSelectionChanged(this::onTaskSelectionChange);

        tasksContainerView.setCenter(tasksListView);

        executionToolbar = new ExecutionToolbar();
        executionToolbar.setOnStart(this::onStartTaskButtonClicked);
        executionToolbar.setOnStop(this::onStopTaskButtonClicked);
    }

    public void loadTasks(Task... tasks)
    {
        unloadTasks();

        addTasks(tasks);
    }

    public void addTasks(Task... tasks)
    {
        tasksListView.addComponents(tasks);
    }

    public void unloadTasks()
    {
        clearTasksList();

        clearResult();

        clearOutput();
    }

    public void clearTasksList()
    {
        tasksListView.getItems().clear();
    }

    public void addResult(Node node)
    {
        resultsVBox.getChildren().add(node);
    }

    public void clearResult()
    {
        resultsVBox.getChildren().clear();
    }

    public void addOutput(Node node)
    {
        outputVBox.getChildren().add(node);
    }

    public void clearOutput()
    {
        outputVBox.getChildren().clear();
    }

    private <T extends Task> void onTaskSelectionChange(ObservableValue<? extends Task> observable,
                                                        T oldTask, T newTask)
    {
        Node view = null;

        if (oldTask != null)
        {
            view = oldTask.getView(ViewType.EXECUTION);

            if (view != null && view instanceof TaskExecutionView)
            {
                TaskExecutionView taskExecutionView = (TaskExecutionView) view;
                taskExecutionView.removeExecutionToolbar();
            }
        }

        if (newTask != null)
        {
            selectedTask = newTask;
            view  = newTask.getView(ViewType.EXECUTION);

            if (view != null && view instanceof TaskExecutionView)
            {
                TaskExecutionView taskExecutionView = (TaskExecutionView) view;
                taskExecutionView.setExecutionToolbar(executionToolbar);
            }
        }
    }


    private void onStartTaskButtonClicked(ActionEvent event)
    {
        if (selectedTask != null)
        {
            executionToolbar.setEnableStart(false);
            executionToolbar.setEnableStop(true);
            executionToolbar.setVisibleProgress(true);
            executionToolbar.setProgress(-1);

            selectedTask.run(TaskContext.RUN);
        }
    }

    private void onStopTaskButtonClicked(ActionEvent event)
    {
        if (selectedTask != null)
        {
            executionToolbar.setEnableStart(true);
            executionToolbar.setEnableStop(false);
            executionToolbar.setVisibleProgress(false);
        }
    }
}
