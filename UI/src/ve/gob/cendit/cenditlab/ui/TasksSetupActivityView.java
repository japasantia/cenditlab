package ve.gob.cendit.cenditlab.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.VBox;
import ve.gob.cendit.cenditlab.control.ComponentDescriptor;
import ve.gob.cendit.cenditlab.control.System;
import ve.gob.cendit.cenditlab.control.Task;
import ve.gob.cendit.cenditlab.ui.base.ViewType;

import java.util.Arrays;
import java.util.List;

public class TasksSetupActivityView extends SplitPane
{
    private static final String FXML_URL =
            "fxml/tasks-setup-activity-view.fxml";

    private static final ViewLoader viewLoader = new ViewLoader(FXML_URL);

    /*
    @FXML
    private HeaderFrameView tasksHeaderFrameView;

    @FXML
    private HeaderFrameView selectedTasksHeaderFrameView;

    @FXML
    private HeaderFrameView taskSetupHeaderFrameView;
    */

    @FXML
    private ContainerView<Task> tasksContainerView;

    @FXML
    private VBox taskSetupVBox;

    @FXML
    private ToolboxListView<ComponentDescriptor> taskDescriptorsToolboxListView;

    public TasksSetupActivityView()
    {
        viewLoader.load(this, this);

        initialize();
    }

    public TasksSetupActivityView(System... systems)
    {
        this();

        loadSystems(systems);
    }

    private void initialize()
    {
        taskDescriptorsToolboxListView.enableMultipleSelection(true);

        taskDescriptorsToolboxListView.setViewFactory(this::getTaskDescriptorView);

        taskDescriptorsToolboxListView.setOnItemClicked(this::onTaskDescriptorClicked);

        tasksContainerView.setViewFactory(this::getTaskView);

        tasksContainerView.setOnItemClickedListener(this::onTaskClicked);
    }

    public void loadSystems(System... systems)
    {
        unloadSystems();

        addSystems(systems);
    }

    public void addSystems(System... systems)
    {
        Arrays.stream(systems)
            .forEach(system -> taskDescriptorsToolboxListView.addItems(system.getTaskDescriptors()));
    }

    public void unloadSystems()
    {
        clearTaskDescrptorsList();

        clearTaskSetupView();

        clearTasks();
    }

    public void clearTaskDescrptorsList()
    {
        taskDescriptorsToolboxListView.clearItems();
    }

    public void addTasks(Task... tasks)
    {
        tasksContainerView.addItems(tasks);
    }

    public void addTask(Task task)
    {
       tasksContainerView.addItem(task);
    }

    public void removeTasks(Task... tasks)
    {
        tasksContainerView.removeItems(tasks);
    }

    public void removeTask(Task task)
    {
        tasksContainerView.removeItem(task);
    }

    public void clearTasks()
    {
        tasksContainerView.clearItems();
    }

    public List<ComponentDescriptor> getSelectedTaskDescriptors()
    {
        return taskDescriptorsToolboxListView.getSelectedValues();
    }

    public List<Task> getTasks()
    {
        return tasksContainerView.getItems();
    }

    public void setTaskSetupView(Node node)
    {
        taskSetupVBox.getChildren().clear();
        taskSetupVBox.getChildren().add(node);
    }

    public void clearTaskSetupView()
    {
        taskSetupVBox.getChildren().clear();
    }

    private Node getTaskDescriptorView(ToolboxListView<ComponentDescriptor>.Item item)
    {
        ItemFrameView frameView = (ItemFrameView) item.getView();
        ComponentDescriptor descriptor = item.getValue();

        if (frameView == null && descriptor != null)
        {
            frameView =
                new ItemFrameView(new ComponentIconView(descriptor.getName(), descriptor.getDescription()));
            frameView.showButton(ItemFrameView.ADD_BUTTON);

            frameView.setOnButtonClicked(event -> onTaskDescriptorClicked(item));
        }

        return frameView;
    }

    private Node getTaskView(ContainerView<Task>.Item item)
    {
        ItemFrameView frameView = (ItemFrameView) item.getView();

        if (frameView == null)
        {
            frameView =
                new ItemFrameView(item.getValue().getView(ViewType.DESCRIPTION));
            frameView.showButton(ItemFrameView.REMOVE_BUTTON);

            frameView.setOnButtonClicked(event -> tasksContainerView.removeItem(item.getValue()));
        }

        return frameView;
    }

    private void onTaskClicked(ContainerView<Task>.Item item)
    {
        setTaskSetupView(item.getValue().getView(ViewType.SETUP));
    }

    private void onTaskDescriptorClicked(ToolboxListView<ComponentDescriptor>.Item item)
    {
        ComponentDescriptor descriptor = item.getValue();

        Task task = (Task) descriptor.create(null);
        addTask(task);

        setTaskSetupView(task.getView(ViewType.SETUP));
    }
}