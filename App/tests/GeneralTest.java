import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ve.gob.cendit.cenditlab.ui.*;

import java.io.IOException;

public class GeneralTest extends Application
{
    public static void main(String[] args)
    {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        //testFrequencySetupPane(primaryStage);
        testFrequencySetupIndividualPanes(primaryStage);
    }

    private static void testFrequencySetupPane(Stage primaryStage)
    {
        try
        {
            FrequencySetupView frequencySetup = new FrequencySetupView();

            Scene scene = new Scene(frequencySetup, 600, 400);
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    private static void testFrequencySetupIndividualPanes(Stage primaryStage)
    {
        FieldInput fieldInput = new FieldInput();

        FrequencyRangePane frequencyRangePane = new FrequencyRangePane();

        FrequencyListPane frequencyListPane = new FrequencyListPane();

        FrequencyFixedPane frequencyFixedPane = new FrequencyFixedPane();

        VBox vBox = new VBox();
        vBox.getChildren().addAll(fieldInput, frequencyRangePane, frequencyListPane, frequencyFixedPane);

        primaryStage.setScene(new Scene(vBox, 600.0, 400.0));
        primaryStage.show();
    }
}
