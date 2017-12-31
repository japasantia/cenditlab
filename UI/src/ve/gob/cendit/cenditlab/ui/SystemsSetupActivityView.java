package ve.gob.cendit.cenditlab.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import ve.gob.cendit.cenditlab.control.System;
import ve.gob.cendit.cenditlab.ui.base.ViewType;

import java.util.List;
import java.util.stream.Collectors;

public class SystemsSetupActivityView extends SplitPane
{
    private static final String FXML_URL = "fxml/systems-setup-activity-view.fxml";

    private static final ViewLoader viewLoader = new ViewLoader(FXML_URL);

    private static final Image DEFAULT_ICON =
            new Image(SystemsSetupActivityView.class.getResource("/ve/gob/cendit/cenditlab/ui/images/system-icon.png").toExternalForm());

    @FXML
    private HeaderFrameView systemsContainerView;

    @FXML
    private HeaderFrameView detailContainerView;

    @FXML
    private HeaderFrameView setupContainerView;

    @FXML
    private VBox detailVBox;

    @FXML
    private VBox setupVBox;

    @FXML
    private ToolboxListView<System> systemsToolboxListView;

    public SystemsSetupActivityView()
    {
        viewLoader.load(this, this);
        initialize();
    }

    public SystemsSetupActivityView(System... systems)
    {
        initialize();
        loadSystems(systems);
    }

    private void initialize()
    {
        systemsToolboxListView.enableMultipleSelection(true);

        systemsToolboxListView.setViewFactory(this::getSystemView);

        systemsToolboxListView.setOnItemClicked(this::onSystemClicked);
    }

    public List<System> getSystems()
    {
        return systemsToolboxListView.getValues();
    }

    public List<System> getSelectedSystems()
    {
        return systemsToolboxListView.getSelectedValues();
    }

    public void loadSystems(System... systems)
    {
        unloadSystems();
        addSystems(systems);
    }

    public void addSystems(System... systems)
    {
        systemsToolboxListView.addItems(systems);
    }

    public void unloadSystems()
    {
        systemsToolboxListView.clearItems();

        detailVBox.getChildren().clear();
    }

    public void setSelectedSytems(System system)
    {
        setSetupView(system.getView(ViewType.SETUP));
        setDetailView(system.getView(ViewType.DETAILS));
    }

    public void setSetupView(Node view)
    {
        clearSetupView();
        setupVBox.getChildren().add(view);
    }

    public void setDetailView(Node view)
    {
        clearDetailView();
        detailVBox.getChildren().add(view);
    }

    public void clearDetailView()
    {
        detailVBox.getChildren().clear();
    }

    public void clearSetupView()
    {
        setupVBox.getChildren().clear();
    }

    private Node getSystemView(ToolboxListView<System>.Item item)
    {
        Node view = item.getView();

        if (view == null)
        {
            view = new ComponentIconView(item.getValue());
            view = new ItemFrameView(view);
        }

        return view;
    }

    private void onSystemClicked(ToolboxListView<System>.Item item)
    {
        ItemFrameView frameView = (ItemFrameView) item.getView();

        if (frameView != null)
        {
            if (item.isSelected())
            {
                frameView.showIcon(ItemFrameView.SELECTED_ICON);
                setSelectedSytems(item.getValue());
            }
            else
            {
                frameView.hideIcon();
                clearSetupView();
                clearDetailView();
            }
        }
    }
}
