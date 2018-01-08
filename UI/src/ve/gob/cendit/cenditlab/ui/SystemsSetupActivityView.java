package ve.gob.cendit.cenditlab.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import ve.gob.cendit.cenditlab.control.System;
import ve.gob.cendit.cenditlab.ui.base.ViewType;

import java.util.List;

public class SystemsSetupActivityView extends SplitPane
{
    private static final String FXML_URL = "fxml/systems-setup-activity-view.fxml";

    private static final ViewLoader viewLoader = new ViewLoader(FXML_URL);

    private static final Image DEFAULT_ICON =
            new Image(SystemsSetupActivityView.class.getResource("/ve/gob/cendit/cenditlab/ui/images/system-icon.png").toExternalForm());

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
        setSystems(systems);
    }

    private void initialize()
    {
        systemsToolboxListView.enableMultipleSelection(true);

        systemsToolboxListView.getItemsList().setItemViewFactory(this::getSystemView);

        systemsToolboxListView.setOnItemClicked(this::onSystemClicked);
    }

    public List<System> getSystems()
    {
        return systemsToolboxListView.getItemsList().getAll();
    }

    public List<System> getSelectedSystems()
    {
        return systemsToolboxListView.getItemsList().getAllSelected();
    }

    public void setSystems(System... systems)
    {
        clear();
        addSystems(systems);
    }

    public void addSystems(System... systems)
    {
        systemsToolboxListView.getItemsList().addAll(systems);
    }

    public void load()
    {
        systemsToolboxListView.load();
    }

    public void unload()
    {
        systemsToolboxListView.unload();

        detailVBox.getChildren().clear();
        setupVBox.getChildren().clear();
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

    public void clearListView()
    {
        systemsToolboxListView.unload();
    }

    public void clearDetailView()
    {
        detailVBox.getChildren().clear();
    }

    public void clearSetupView()
    {
        setupVBox.getChildren().clear();
    }

    public void clear()
    {
        clearListView();
        clearDetailView();
        clearSetupView();
    }

    private ItemView getSystemView(Item<System> item)
    {
        ItemView itemView = item.getView();

        if (itemView == null)
        {
            itemView = new ItemView(item.getValue().getView(ViewType.ICON));
        }

        return itemView;
    }

    private void onSystemClicked(Item<System> item)
    {
        ItemView itemView = (ItemView) item.getView();

        if (itemView != null)
        {
            if (item.isSelected())
            {
                itemView.showIcon(ItemView.SELECTED_ICON);
                setSelectedSytems(item.getValue());
            }
            else
            {
                itemView.hideIcon();
                clearSetupView();
                clearDetailView();
            }
        }
    }
}
