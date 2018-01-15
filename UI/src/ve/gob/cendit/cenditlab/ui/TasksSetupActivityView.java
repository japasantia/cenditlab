package ve.gob.cendit.cenditlab.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import ve.gob.cendit.cenditlab.control.ComponentDescriptor;
import ve.gob.cendit.cenditlab.control.System;
import ve.gob.cendit.cenditlab.control.Task;
import ve.gob.cendit.cenditlab.ui.base.ViewType;


import java.util.Arrays;
import java.util.List;

public class TasksSetupActivityView extends SplitPane
{
    private static final String FXML_URL = "fxml/tasks-setup-activity-view.fxml";

    private static final ViewLoader viewLoader = new ViewLoader(FXML_URL);

    @FXML
    private ContainerView<Task> tasksContainerView;

    @FXML
    private VBox taskSetupVBox;

    @FXML
    private ItemsListView<ComponentDescriptor> taskDescriptorsItemsListView;

    private ImageButton addButton;
    private ImageButton removeButton;

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
        taskDescriptorsItemsListView.enableMultipleSelection(true);

        taskDescriptorsItemsListView.setOnItemClicked(this::onTaskDescriptorClicked);

        tasksContainerView.setOnItemClickedListener(this::onTaskClicked);

        taskDescriptorsItemsListView.getItemsList().setViewFactory(this::getTaskDescriptorView);

        tasksContainerView.getItemsList().setViewFactory(this::getTaskView);

        addButton = new ImageButton();
        removeButton = new ImageButton();

        addButton.setOnMouseClicked(this::onAddButtonClicked);
    }

    public void setSystems(System... systems)
    {
        clear();

        addSystems(systems);
    }

    public void addSystems(System... systems)
    {
        Arrays.stream(systems)
            .forEach(system -> taskDescriptorsItemsListView.getItemsList().addAll(system.getTaskDescriptors()));
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
        return taskDescriptorsItemsListView.getItemsList().getAllSelected();
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
        taskDescriptorsItemsListView.unload();
        taskDescriptorsItemsListView.getItemsList().clear();
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
        taskDescriptorsItemsListView.load();
    }

    public void unload()
    {
        taskDescriptorsItemsListView.unload();
        tasksContainerView.unload();

        clearTaskSetup();
    }

    private Node getTaskDescriptorView(Item<ComponentDescriptor> item)
    {
        ItemView itemView = (ItemView)item.getView();
        ComponentDescriptor descriptor = item.getValue();

        if (itemView == null && descriptor != null)
        {
            itemView = new ItemView(new IconView(descriptor));
        }

        return itemView;
    }

    private Node getTaskView(Item<Task> item)
    {
        ItemView itemView = (ItemView) item.getView();

        if (itemView == null)
        {
            itemView = new ItemView(item.getValue().getView(ViewType.DESCRIPTION));
        }

        return itemView;
    }

    private void onTaskClicked(Item<Task> item)
    {
        setTaskSetupView(item.getValue().getView(ViewType.SETUP));
    }

    private void onTaskDescriptorClicked(Item<ComponentDescriptor> item)
    {
        ItemView itemView = (ItemView) item.getView();

        itemView.add(addButton, IndicatorsView.Position.RIGHT, IndicatorsView.Position.TOP,
                -40.0, 0.0);
    }

    private void onAddButtonClicked(MouseEvent event)
    {
        Item<ComponentDescriptor> item = taskDescriptorsItemsListView.getLastClickedItem();

        if (item != null)
        {
            Task task = (Task) item.getValue().create(null);

            addTask(task);

            setTaskSetupView(task.getView(ViewType.SETUP));
        }
    }

    /*
    private void onTaskDescriptorClicked(Item<ComponentDescriptor> item)
    {
        ComponentDescriptor descriptor = item.getValue();

        Task task = (Task) descriptor.create(null);
        addTask(task);

        setTaskSetupView(task.getView(ViewType.SETUP));
    }
    */
}