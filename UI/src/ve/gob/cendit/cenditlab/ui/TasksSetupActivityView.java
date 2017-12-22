package ve.gob.cendit.cenditlab.ui;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import ve.gob.cendit.cenditlab.control.ComponentDescriptor;
import ve.gob.cendit.cenditlab.control.System;
import ve.gob.cendit.cenditlab.control.Task;
import ve.gob.cendit.cenditlab.ui.base.ViewType;

import java.util.Arrays;

public class TasksSetupActivityView extends SplitPane
{
    private static final String FXML_URL =
            "fxml/tasks-setup-activity-view.fxml";

    private static final ViewLoader viewLoader =
            new ViewLoader(FXML_URL);

    @FXML
    private HeaderContainerView tasksContainerView;

    @FXML
    private HeaderContainerView detailContainerView;

    @FXML
    private HeaderContainerView setupContainerView;

    @FXML
    private VBox detailVBox;

    @FXML
    private VBox setupVBox;

    @FXML
    private ListView<ComponentDescriptor> taskDescriptorsListView;

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
        taskDescriptorsListView.setCellFactory(listView ->
            new TaskDescriptorCell());

        taskDescriptorsListView.getSelectionModel()
                .selectedItemProperty()
                .addListener(this::onTaskSelectionChange);
    }

    public void loadSystems(System... systems)
    {
        unloadSystems();

        addSystems(systems);
    }

    public void addSystems(System... systems)
    {
        for (System system : systems)
        {
            ComponentDescriptor[] descriptors = system.getTaskDescriptors();

            taskDescriptorsListView.getItems().addAll(descriptors);
        }
        /*
        Arrays.stream(systems)
                .forEach(system -> tasksListView.addComponents(system.getTasks()));
        */
    }

    public void unloadSystems()
    {
        clearTasksList();

        clearSetup();

        clearDetail();
    }

    public void clearTasksList()
    {
        taskDescriptorsListView.getItems().clear();
    }

    public void addDetail(Node node)
    {
        detailVBox.getChildren().add(node);
    }

    public void clearDetail()
    {
        detailVBox.getChildren().clear();
    }

    public void addSetup(Node node)
    {
        setupVBox.getChildren().add(node);
    }

    public void clearSetup()
    {
        setupVBox.getChildren().clear();
    }

    private void onTaskSelectionChange(ObservableValue<? extends ComponentDescriptor> observable,
                                       ComponentDescriptor oldDescriptor, ComponentDescriptor newDescriptor)
    {
        // TODO: revisar actualizacion de vistas

        if (newDescriptor == null)
            return;

        clearDetail();
        clearSetup();

        String taskName = newDescriptor.getName();

        Node node = new IconView(taskName, newDescriptor.getIcon());

        if (node != null)
        {
            String caption = String.format("%s info", taskName);
            detailContainerView.setCaption(caption);
            addDetail(node);
        }

        node = new IconView(taskName, newDescriptor.getIcon());

        if (node != null)
        {
            String caption = String.format("%s setup", taskName);
            setupContainerView.setCaption(caption);
            addSetup(node);
        }
    }

    private void onTaskClicked(MouseEvent event)
    {
        IconView iconView = (IconView) event.getSource();

        if (iconView != null)
        {

        }
    }

    private class TaskDescriptorCell extends ListCell<ComponentDescriptor>
    {
        @Override
        protected void updateItem(ComponentDescriptor item, boolean empty)
        {
            super.updateItem(item, empty);

            if (empty || item == null)
                return;

            IconView iconView = new IconView(item.getName(), item.getIcon());
            iconView.setOnMouseClicked(TasksSetupActivityView.this::onTaskClicked);
            setGraphic(iconView);
        }
    }
}