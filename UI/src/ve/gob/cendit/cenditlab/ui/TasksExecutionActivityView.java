package ve.gob.cendit.cenditlab.ui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.VBox;
import ve.gob.cendit.cenditlab.control.DataDirection;
import ve.gob.cendit.cenditlab.control.Task;
import ve.gob.cendit.cenditlab.control.TaskContext;
import ve.gob.cendit.cenditlab.control.TaskExecutor;
import ve.gob.cendit.cenditlab.data.Data;

import java.util.Arrays;

public class TasksExecutionActivityView extends SplitPane
{
    private static final String FXML_URL = "fxml/tasks-execution-activity-view.fxml";

    private static final ViewLoader viewLoader = new ViewLoader(FXML_URL);

    @FXML
    private VBox outputVBox;

    @FXML
    private ItemsListView<Task> tasksItemsListView;

    @FXML
    private ContainerView<Task> resultsContainerView;

    @FXML
    private ExecutionToolbar mainExecutionToolbar;

    private ExecutionToolbar executionToolbar;

    private TaskExecutor taskExecutor;

    public TasksExecutionActivityView()
    {
        viewLoader.load(this, this);

        initialize();
    }

    public TasksExecutionActivityView(Task... tasks)
    {
        this();

        setTasks(tasks);
    }

    private void initialize()
    {
        taskExecutor = new TaskExecutor();
        executionToolbar = new ExecutionToolbar();

        mainExecutionToolbar.setOnStart(this::onStartTaskButtonClicked);
        mainExecutionToolbar.setOnStop(this::onStopTaskButtonClicked);

        executionToolbar.setOnStart(this::onStartSelectedTaskButtonClicked);
        executionToolbar.setOnStop(this::onStopSelectedTaskButtonClicked);

        tasksItemsListView.setOnItemClicked(this::onTaskClicked);

        tasksItemsListView.getItemsList().setViewFactory(this::getTaskView);
        resultsContainerView.getItemsList().setViewFactory(this::getTaskResultsView);

        taskExecutor.setOnSuccessHandler(this::onTasksExecutionSuccess);
        taskExecutor.setOnErrorHandler(this::onTasksExecutionError);
    }

    public void setTasks(Task... tasks)
    {
        clear();

        addTasks(tasks);
    }

    public void addTasks(Task... tasks)
    {
        taskExecutor.addTasks(tasks);

        tasksItemsListView.getItemsList().addAll(tasks);
        resultsContainerView.getItemsList().addAll(tasks);
    }

    public void clearTaskList()
    {
        tasksItemsListView.getItemsList().clear();
    }

    public void clearResults()
    {
        resultsContainerView.getItemsList().clear();
    }

    public void addOutput(Node node)
    {
        outputVBox.getChildren().add(node);
    }

    public void clearOutput()
    {
        outputVBox.getChildren().clear();
    }

    public void clear()
    {
       clearTaskList();

       clearResults();

       clearOutput();
    }

    public void load()
    {
        tasksItemsListView.load();
    }

    public void unload()
    {
        tasksItemsListView.unload();

        clearResults();
        clearOutput();
    }

    private void onStartTaskButtonClicked(ActionEvent event)
    {
        mainExecutionToolbar.setEnableStart(false);
        mainExecutionToolbar.setEnableStop(true);

        // resultsContainerView.unload();

        taskExecutor.execute();
    }

    private void onStopTaskButtonClicked(ActionEvent event)
    {
        mainExecutionToolbar.setEnableStart(true);
        mainExecutionToolbar.setEnableStop(false);
    }

    private void onStartSelectedTaskButtonClicked(ActionEvent event)
    {
        Task selectedTask = tasksItemsListView.getLastClickedItem().getValue();

        if (selectedTask != null)
        {
            executionToolbar.setEnableStart(false);
            executionToolbar.setEnableStop(true);
            executionToolbar.setProgress(-1);
            executionToolbar.setVisibleProgress(true);

            selectedTask.run(TaskContext.RUN);
        }
    }

    private void onStopSelectedTaskButtonClicked(ActionEvent event)
    {
        Task selectedTask = tasksItemsListView.getLastClickedItem().getValue();

        if (selectedTask != null)
        {
            executionToolbar.setEnableStart(true);
            executionToolbar.setEnableStop(false);
            executionToolbar.setVisibleProgress(false);

            selectedTask.stop();
        }
    }

    private void onTaskClicked(Item<Task> item)
    {
        try
        {
            Task selectedTask = item.getValue();

            TaskExecutionView taskExecutionView = (TaskExecutionView)item.getView();
            taskExecutionView.setExecutionToolbar(executionToolbar);
        }
        catch (Exception ex)
        {
            System.err.println(ex.getMessage());
        }
    }

    private Node getTaskView(Item<Task> item)
    {
        Node itemView = item.getView();

        if (itemView == null)
        {
            itemView = new TaskExecutionView(item.getValue());
        }

        return itemView;
    }

    private Node getTaskResultsView(Item<Task> item)
    {
        Task task = item.getValue();

        Data[] outputDataArray = item.getValue().getData(DataDirection.OUTPUT);

        if (outputDataArray == null)
            return null;

        VBox containerVBox = new VBox();

        Arrays.stream(outputDataArray)
            .forEach(data ->
                containerVBox.getChildren().add(ViewFactory.buildDataView(data, DataDirection.OUTPUT)));

        return containerVBox;
    }

    private void onTasksExecutionSuccess()
    {
        Platform.runLater(() ->
            {
                mainExecutionToolbar.setEnableStart(true);
                mainExecutionToolbar.setEnableStop(false);

                outputVBox.getChildren().add(new Label("Execution success"));
            });

    }

    private void onTasksExecutionError()
    {
        Platform.runLater(() ->
        {
            mainExecutionToolbar.setEnableStart(true);
            mainExecutionToolbar.setEnableStop(false);

            outputVBox.getChildren().add(new Label("Execution error"));
        });
    }
}
