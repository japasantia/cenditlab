package ve.gob.cendit.cenditlab.ui;

import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import ve.gob.cendit.cenditlab.control.Task;
import ve.gob.cendit.cenditlab.ui.base.ViewType;

public class TasksExecutionStepView extends SectionedView
{
    private SectionedView resultsStatusSectionedView;

    private HeaderComponentListView<Task> executionListView;

    private HeaderComponentListView<Task> resultsListView;

    private VBox statusVBox;

    public TasksExecutionStepView()
    {
        initialize();

        executionListView.setCellFactory(listView -> new ExecutionListCell());
    }

    public TasksExecutionStepView(Task... tasks)
    {
        initialize();
    }

    private void initialize()
    {
        resultsStatusSectionedView = new SectionedView();

        executionListView = new HeaderComponentListView<>();
        executionListView.setCellFactory(listView -> new ExecutionListCell());

        resultsListView = new HeaderComponentListView<>();

        statusVBox = new VBox();
        ScrollPane scrollPane = new ScrollPane(statusVBox);

        scrollPane.setMaxWidth(Double.POSITIVE_INFINITY);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        resultsStatusSectionedView.setCenterSectionOrientation(Orientation.VERTICAL);
        resultsStatusSectionedView.createCenterSection("Results", resultsListView);
        resultsStatusSectionedView.createCenterSection("Status", statusVBox);

        this.createCenterSection("Execution", executionListView);
        this.createCenterSection("ResultsStatus", resultsStatusSectionedView);
    }

    public void loadTasks(Task... tasks)
    {
        unloadTasks();

        addTasks(tasks);
    }

    public void addTasks(Task... tasks)
    {
        executionListView.getItems().addAll(tasks);
    }

    public void unloadTasks()
    {
        executionListView.getItems().clear();

        resultsListView.getItems().clear();

        statusVBox.getChildren().clear();
    }

    private class ExecutionListCell extends ListCell<Task>
    {
        public ExecutionListCell()
        { }

        @Override
        protected void updateItem(Task taskItem, boolean empty)
        {
            super.updateItem(taskItem, empty);

            if (empty || taskItem == null)
                return;

            Node node = taskItem.getView(ViewType.EXECUTION);

            setGraphic(node);
        }
    }
}
