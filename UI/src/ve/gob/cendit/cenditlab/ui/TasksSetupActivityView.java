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

        setSystems(systems);
    }

    private void initialize()
    {
        taskDescriptorsToolboxListView.enableMultipleSelection(true);

        taskDescriptorsToolboxListView.setOnItemClicked(this::onTaskDescriptorClicked);

        tasksContainerView.setOnItemClickedListener(this::onTaskClicked);

        taskDescriptorsToolboxListView.getItemsList().setItemViewFactory(this::getTaskDescriptorView);

        tasksContainerView.getItemsList().setItemViewFactory(this::getTaskView);
    }

    public void setSystems(System... systems)
    {
        clear();

        addSystems(systems);
    }

    public void addSystems(System... systems)
    {
        Arrays.stream(systems)
            .forEach(system -> taskDescriptorsToolboxListView.getItemsList().addAll(system.getTaskDescriptors()));
    }

    public void addTasks(Task... tasks)
    {
        tasksContainerView.getItemsList().addAll(tasks);
    }

    public void addTask(Task task)
    {
       tasksContainerView.getItemsList().add(task);
    }

    public void removeTasks(Task... tasks)
    {
        tasksContainerView.getItemsList().removeAll(tasks);
    }

    public void removeTask(Task task)
    {
        tasksContainerView.getItemsList().remove(task);
    }

    public List<ComponentDescriptor> getSelectedTaskDescriptors()
    {
        return taskDescriptorsToolboxListView.getItemsList().getAllSelected();
    }

    public List<Task> getTasks()
    {
        return tasksContainerView.getItemsList().getAll();
    }

    public void setTaskSetupView(Node node)
    {
        taskSetupVBox.getChildren().clear();
        taskSetupVBox.getChildren().add(node);
    }

    public void clear()
    {
        clearTaskDescriptors();
        clearTaskContainer();
        clearTaskSetup();
    }

    public void clearTaskDescriptors()
    {
        taskDescriptorsToolboxListView.unload();
        taskDescriptorsToolboxListView.getItemsList().clear();
    }

    public void clearTaskContainer()
    {
        tasksContainerView.unload();
        tasksContainerView.getItemsList().clear();
    }

    public void clearTaskSetup()
    {
        taskSetupVBox.getChildren().clear();
    }

    public void load()
    {
        tasksContainerView.load();
        taskDescriptorsToolboxListView.load();
    }

    public void unload()
    {
        taskDescriptorsToolboxListView.unload();
        tasksContainerView.unload();

        clearTaskSetup();
    }

    private ItemView getTaskDescriptorView(Item<ComponentDescriptor> item)
    {
        ItemView itemView = (ItemView)item.getView();
        ComponentDescriptor descriptor = item.getValue();

        if (itemView == null && descriptor != null)
        {
            itemView =
                new ItemView(new ComponentIconView(descriptor.getName(), descriptor.getDescription()));
            itemView.showButton(ItemView.ADD_BUTTON);

            itemView.setOnButtonClicked(event -> onTaskDescriptorClicked(item));
        }

        return itemView;
    }

    private ItemView getTaskView(Item<Task> item)
    {
        ItemView itemView = (ItemView) item.getView();

        if (itemView == null)
        {
            itemView =
                new ItemView(item.getValue().getView(ViewType.DESCRIPTION));

            itemView.showButton(ItemView.REMOVE_BUTTON);

            itemView.setOnButtonClicked(event -> tasksContainerView.getItemsList().remove(item.getValue()));
        }

        return itemView;
    }

    private void onTaskClicked(Item<Task> item)
    {
        setTaskSetupView(item.getValue().getView(ViewType.SETUP));
    }

    private void onTaskDescriptorClicked(Item<ComponentDescriptor> item)
    {
        ComponentDescriptor descriptor = item.getValue();

        Task task = (Task) descriptor.create(null);
        addTask(task);

        setTaskSetupView(task.getView(ViewType.SETUP));
    }
}