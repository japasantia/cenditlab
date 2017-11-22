package ve.gob.cendit.cenditlab.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import java.io.IOException;

public class View<T extends Parent> extends Parent
{
    private final String fxmlUrl;
    private T root;

    public View(String fxmlUrl)
    {
        this.fxmlUrl = fxmlUrl;

        View.load(fxmlUrl, (T) this, (T) this);
    }

    public T getRoot()
    {
        return (T) this;
    }

    public static <T> void load(String fxmlUrl, T controller, T root)
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(root.getClass().getResource(fxmlUrl));
            fxmlLoader.setController(controller);
            fxmlLoader.setRoot(root);
            fxmlLoader.load();
        }
        catch (IOException ex)
        {
            throw new RuntimeException(ex);
        }
    }
}
