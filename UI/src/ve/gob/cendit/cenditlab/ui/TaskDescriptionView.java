package ve.gob.cendit.cenditlab.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import ve.gob.cendit.cenditlab.control.Task;

public class TaskDescriptionView extends GridPane
{
    private static final String FXML_URL = "fxml/task-description-view.fxml";

    private static final String DEFAULT_TASK_NAME = "";

    @FXML
    private ImageView taskIconImageView;

    @FXML
    private Label taskNameLabel;

    @FXML
    private FlowPane taskIndicatorsFlowPane;

    private Task task;

    public TaskDescriptionView()
    {
        ViewLoader.load(FXML_URL, this, this);
    }

    public TaskDescriptionView(Task task)
    {
        this();

        setTask(task);
    }

    public void setTask(Task task)
    {
        this.task = task;

        setName(task.getName());
        setIcon(task.getIcon());
    }

    public Task getTask()
    {
        return task;
    }

    public void setName(String value)
    {
        taskNameLabel.setText(value != null ? value : DEFAULT_TASK_NAME);
    }

    public void setIcon(Image image)
    {
        if (image != null)
        {
            taskIconImageView.setDisable(false);
            taskIconImageView.setImage(image);
        }
        else
        {
            taskIconImageView.setDisable(true);
        }
    }

    public Image getImage()
    {
        return taskIconImageView.getImage();
    }
}

