package ve.gob.cendit.cenditlab.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class DialogView extends VBox
{
    private static ViewLoader viewLoader = new ViewLoader("fxml/dialog-view.fxml");

    // Buttons constants
    private static final int OK_BUTTON = 0;
    private static final int CANCEL_BUTTON = 1;
    private static final int RETRY_BUTTON = 2;
    private static final int IGNORE_BUTTON = 3;
    private static final int ABORT_BUTTON = 4;

    // Buttons groups
    public static final int NONE = 0;
    public static final int OK = 1;
    public static final int OK_CANCEL = 2;
    public static final int RETRY_ABORT_IGNORE = 3;

    private static final String[] BUTTON_CAPTIONS =
    {
        "Ok",
        "Cancel",
        "Retry",
        "Ignore",
        "Abort"
    };

    private Stage stage;

    private Image iconImage;

    @FXML
    private Pane contentPane;

    @FXML
    private HBox buttonsHBox;

    @FXML
    private Button leftButton;

    @FXML
    private Button centerButton;

    @FXML
    private Button rightButton;

    private int leftButtonId;
    private int centerButtonId;
    private int rightButtonId;

    private int clickedButtonId = NONE;

    public static DialogView createModal(Window owner)
    {
        Stage stage = new Stage();
        stage.initOwner(owner);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setResizable(false);

        DialogView dialogView = new DialogView(stage);
        Scene scene = new Scene(dialogView);

        stage.setScene(scene);

        return dialogView;
    }

    public DialogView()
    {
        viewLoader.load(this, this);
    }

    public DialogView(Node content, Image icon)
    {
        this();

        setContent(content);
        setIconImage(icon);
    }

    public DialogView(Node content)
    {
        this(content, null);
    }

    protected DialogView(Stage stage)
    {
        this();

        this.stage = stage;

        initialize();
    }

    private void initialize()
    {
        leftButton.setOnAction(this::onButtonClicked);
        centerButton.setOnAction(this::onButtonClicked);
        rightButton.setOnAction(this::onButtonClicked);
    }

    public void setTitle(String value)
    {
        stage.setTitle(value);
    }

    public String getTitle()
    {
        return stage.getTitle();
    }

    public void setIconImage(Image value)
    {
        if (stage != null)
        {
            iconImage = value;
            stage.getIcons().add(iconImage);
        }
    }

    public Image getIconImage()
    {
        return iconImage;
    }

    public void setContent(Node content)
    {
        clearContent();

        contentPane.getChildren().add(content);
    }

    public Node getContent(Node content)
    {
        return contentPane.getChildren().get(0);
    }

    public int getClickedButton()
    {
        return clickedButtonId;
    }

    public void clearContent()
    {
        contentPane.getChildren().clear();
    }

    public void show()
    {
        if (stage != null)
            stage.show();
    }

    public void hide()
    {
        if (stage != null)
            stage.hide();
    }

    public void close()
    {
        if (stage != null)
            stage.close();
    }

    public void setButtons(int buttons)
    {
        unloadButtons();

        switch (buttons)
        {
            case OK_CANCEL:
                loadRightButton(OK_BUTTON);
                loadCenterButton(CANCEL_BUTTON);
                break;

            case RETRY_ABORT_IGNORE:
                loadRightButton(RETRY_BUTTON);
                loadCenterButton(ABORT_BUTTON);
                loadLeftButton(IGNORE_BUTTON);
                break;

            case OK:
                loadRightButton(OK_BUTTON);
                break;

            case NONE:
            default:
                break;
        }
    }

    private void unloadButtons()
    {
        buttonsHBox.getChildren().clear();
    }

    private void loadLeftButton(int buttonId)
    {
        leftButtonId = buttonId;
        leftButton.setText(BUTTON_CAPTIONS[buttonId]);

        buttonsHBox.getChildren().add(leftButton);
    }

    private void loadCenterButton(int buttonId)
    {
        centerButtonId = buttonId;
        centerButton.setText(BUTTON_CAPTIONS[buttonId]);

        buttonsHBox.getChildren().add(centerButton);
    }

    private void loadRightButton(int buttonId)
    {
        rightButtonId = buttonId;
        rightButton.setText(BUTTON_CAPTIONS[buttonId]);

        buttonsHBox.getChildren().add(rightButton);
    }

    private void onButtonClicked(ActionEvent event)
    {
        Button button = (Button) event.getSource();

        clickedButtonId = (button == leftButton ? leftButtonId :
                (button == centerButton ? centerButtonId :
                        rightButtonId));

        close();
    }
}
