package ve.gob.cendit.cenditlab.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.VBox;
import ve.gob.cendit.cenditlab.control.Task;
import ve.gob.cendit.cenditlab.control.TaskContext;
import ve.gob.cendit.cenditlab.ui.base.ViewType;

public class TasksExecutionActivityView extends SplitPane
{
    private static final String FXML_URL = "fxml/tasks-execution-activity-view.fxml";

    private static final ViewLoader viewLoader = new ViewLoader(FXML_URL);

    @FXML
    private VBox resultsVBox;

    @FXML
    private VBox outputVBox;

    @FXML
    private ItemsListView<Task> tasksItemsListView;

    @FXML
    private ExecutionToolbar mainExecutionToolbar;

    private ExecutionToolbar executionToolbar;

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
        executionToolbar = new ExecutionToolbar();
        executionToolbar.setOnStart(this::onStartTaskButtonClicked);
        executionToolbar.setOnStop(this::onStopTaskButtonClicked);

        tasksItemsListView.setOnItemClicked(this::onTaskClicked);

        tasksItemsListView.getItemsList().setViewFactory(this::getTaskView);
    }

    public void setTasks(Task... tasks)
    {
        clear();

        addTasks(tasks);
    }

    public void addTasks(Task... tasks)
    {
        tasksItemsListView.getItemsList().addAll(tasks);
    }

    public void addResult(Node node)
    {
        resultsVBox.getChildren().add(node);
    }

    public void clearTaskList()
    {
        tasksItemsListView.getItemsList().clear();
    }

    public void clearResults()
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

    private void onStopTaskButtonClicked(ActionEvent event)
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
}
