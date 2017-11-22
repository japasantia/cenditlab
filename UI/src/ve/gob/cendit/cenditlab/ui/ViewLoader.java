package ve.gob.cendit.cenditlab.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import java.io.IOException;

public class ViewLoader
{
    private FXMLLoader fxmlLoader;

    public ViewLoader(String fxmlUrl)
    {
        if (fxmlUrl == null)
            throw new IllegalArgumentException("fxmUrl must not be null");

        fxmlLoader = new FXMLLoader(getClass().getResource(fxmlUrl));
    }

    // FIXME: Load in thread
    /*
    new Thread(() -> {
    try {
     FXMLLoader loader = new FXMLLoader();
     loader.setResources(null);
     loader.setLocation(this.getClass().getResource("FXML.fxml"));
     Parent parent = (Parent) loader.load(this.getClass().getResource("FXML.fxml").openStream());
     Parent load = parent;

     Platform.runLater(() -> {
       vBox.getChildren().add(load);
     });

   } catch (IOException ex) {
     // error handling
   }
    }).start();
    */

    public void load(Object controller, Object root)
    {
        fxmlLoader.setController(controller);
        fxmlLoader.setRoot(root);

        try
        {
            fxmlLoader.load();
        }
        catch (IOException ex)
        {
            throw new RuntimeException(ex);
        }
    }

    public static void load(String fxmlUrl, Object controller, Object root)
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

    public static Node load(String fxmlUrl)
    {
        try
        {
            return FXMLLoader.load(ViewLoader.class.getResource(fxmlUrl));
        }
        catch (IOException ex)
        {
            throw new RuntimeException(ex);
        }
    }
}
