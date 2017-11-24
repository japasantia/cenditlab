package ve.gob.cendit.cenditlab.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.VBox;
import ve.gob.cendit.cenditlab.control.Task;
import ve.gob.cendit.cenditlab.ui.base.ViewType;

import java.util.Arrays;

public class TasksExecutionStepViewProto1 extends SplitPane
{
    private static final String FXML_URL = "fxml/tasks-execution-step-view.fxml";

    private static final ViewLoader viewLoader =
            new ViewLoader(FXML_URL);

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

    private ComponentListView<Task> tasksListView;

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

        tasksContainerView.setCenter(tasksListView);
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
}
