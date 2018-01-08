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
    private ToolboxListView<Task> tasksToolboxListView;

    @FXML
    private ExecutionToolbar mainExecutionToolbar;

    private ExecutionToolbar executionToolbar;

    private Task selectedTask;

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

        tasksToolboxListView.setOnItemClicked(this::onTaskClicked);

        tasksToolboxListView.getItemsList().setItemViewFactory(this::getTaskView);
    }

    public void setTasks(Task... tasks)
    {
        clear();

        addTasks(tasks);
    }

    public void addTasks(Task... tasks)
    {
        tasksToolboxListView.getItemsList().addAll(tasks);
    }

    public void addResult(Node node)
    {
        resultsVBox.getChildren().add(node);
    }

    public void clearTaskList()
    {
        tasksToolboxListView.getItemsList().clear();
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
        tasksToolboxListView.load();
    }

    public void unload()
    {
        tasksToolboxListView.unload();

        clearResults();

        clearOutput();
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

    private void onTaskClicked(Item<Task> item)
    {
        try
        {
            if (selectedTask != null)
            {
                TaskExecutionView taskExecutionView = (TaskExecutionView) selectedTask.getView(ViewType.EXECUTION);
                taskExecutionView.removeExecutionToolbar();
            }

            selectedTask = item.getValue();

            TaskExecutionView taskExecutionView = (TaskExecutionView) selectedTask.getView(ViewType.EXECUTION);
            taskExecutionView.setExecutionToolbar(executionToolbar);
        }
        catch (Exception ex)
        {
            System.err.println(ex.getMessage());
        }
    }

    private ItemView getTaskView(Item<Task> item)
    {
        ItemView itemView = item.getView();

        if (itemView == null)
        {
            itemView = new ItemView(item.getValue().getView(ViewType.EXECUTION));
        }

        return itemView;
    }
}
