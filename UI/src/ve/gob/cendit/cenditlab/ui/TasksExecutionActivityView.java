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
    private HeaderFrameView tasksContainerView;

    @FXML
    private HeaderFrameView resultsContainerView;

    @FXML
    private HeaderFrameView outputContainerView;

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

        loadTasks(tasks);
    }

    private void initialize()
    {
        executionToolbar = new ExecutionToolbar();
        executionToolbar.setOnStart(this::onStartTaskButtonClicked);
        executionToolbar.setOnStop(this::onStopTaskButtonClicked);

        tasksToolboxListView.setOnItemClicked(this::onTaskClicked);

        tasksToolboxListView.setViewFactory(this::getTaskView);
    }

    public void loadTasks(Task... tasks)
    {
        unloadTasks();

        addTasks(tasks);
    }

    public void addTasks(Task... tasks)
    {
        tasksToolboxListView.addItems(tasks);
    }

    public void unloadTasks()
    {
        clearTasksList();

        clearResult();

        clearOutput();
    }

    public void clearTasksList()
    {
        tasksToolboxListView.clearItems();
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

    private void onTaskClicked(ToolboxListView<Task>.Item item)
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
        /*
        Node view = null;

        if (selectedTask != null)
        {
            view = selectedTask.getView(ViewType.EXECUTION);

            if (view != null && view instanceof TaskExecutionView)
            {
                TaskExecutionView taskExecutionView = (TaskExecutionView) view;
                taskExecutionView.removeExecutionToolbar();
            }
        }

        selectedTask = item.getValue();

        if (selectedTask != null)
        {
            view  = selectedTask.getView(ViewType.EXECUTION);

            if (view != null && view instanceof TaskExecutionView)
            {
                TaskExecutionView taskExecutionView = (TaskExecutionView) view;
                taskExecutionView.setExecutionToolbar(executionToolbar);
            }
        }
        */
    }

    private Node getTaskView(ToolboxListView<Task>.Item item)
    {
        Node view = item.getView();

        if (view == null)
        {
            view = item.getValue().getView(ViewType.EXECUTION);
        }

        return view;
    }
}
