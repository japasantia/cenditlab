import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ve.gob.cendit.cenditlab.data.FrequencyData;
import ve.gob.cendit.cenditlab.data.FrequencySetup;
import ve.gob.cendit.cenditlab.ui.*;

import java.util.List;

public class PaneTests extends Application
{
    private FrequencyListPane frequencyListPane;

    private Stage rootStage;

    public static void main(String[] args)
    {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        rootStage = primaryStage;

        // frequencyListPaneTest(primaryStage);
        frequencyRangePaneTest(primaryStage);
        // frequencyFixedTest(primaryStage);
        // enrListPaneTest(primaryStage);
        // noiseSourcePaneTest(primaryStage);

        // enrSetupTest(primaryStage);
    }

    private void frequencyListPaneTest(Stage primaryStage)
    {
        frequencyListPane = new FrequencyListPane();

        Button testButton = new Button("Start Test");
        VBox containerVBox = new VBox();

        testButton.setOnAction(event -> frequencyListPaneTest());

        containerVBox.getChildren().addAll(frequencyListPane, testButton);

        primaryStage.setScene(new Scene(containerVBox, 600, 800));
        primaryStage.setTitle("CenditLab.Reduced | Test FrequencyListPane");
        primaryStage.show();
    }

    private void frequencyListPaneTest()
    {
        boolean isValid = frequencyListPane.validate();

        List<String> frequenciesList = frequencyListPane.getFrequencies();

        System.out.printf("Frequencies list valid: %s\n", isValid);

        System.out.println("Frequencies list");

        frequenciesList.stream()
                .forEach(f -> System.out.printf("%s ", f));
    }

    private void frequencyRangePaneTest(Stage primaryStage)
    {
        FrequencyRangePane frequencyRangePane = new FrequencyRangePane();
        Button viewFieldsButton = new Button("View Fields");
        TextArea fieldsTextArea = new TextArea();

        VBox containerVBox = new VBox();
        containerVBox.getChildren().addAll(frequencyRangePane, viewFieldsButton, fieldsTextArea);

        viewFieldsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event)
            {
                FrequencySetup frequencySetup = frequencyRangePane.getFrequencySetup();

                String data = String.format("Max: %s\nMin: %s\nCenter: %s\nSpan: %s ",
                    frequencySetup.getMaxFrequencyData().toString(),
                    frequencySetup.getMinFrequencyData().toString(),
                    frequencySetup.getCentralFrequencyData().toString(),
                    frequencySetup.getSpanFrequencyData().toString());

                fieldsTextArea.setText(data);
            }
        });

        primaryStage.setScene(new Scene(containerVBox));
        primaryStage.setTitle("CenditLab.Reduced | Test FrequencyRangePane");
        primaryStage.show();
    }

    private void frequencyFixedTest(Stage primaryStage)
    {
        FrequencyFixedPane frequencyFixedPane = new FrequencyFixedPane();
        Label frequencyLabel = new Label();
        VBox containerVBox = new VBox();

        FrequencyData frequencyDat = new FrequencyData();
        frequencyFixedPane.setFixedFrequency(frequencyDat);

        frequencyFixedPane.getFixedFrequency()
                .addUpdateListener(source -> {
                    String data = String.format("Field: %s\nControl: %s",
                            frequencyDat.toString(),
                            frequencyFixedPane.getFixedFrequency().toString());

                    frequencyLabel.setText(data);
                });

        containerVBox.getChildren().addAll(frequencyFixedPane, frequencyLabel);

        primaryStage.setScene(new Scene(containerVBox, 600.0, 400.0));
        primaryStage.setTitle("CenditLab.Reduced | Test FrequencyFixedPane");
        primaryStage.show();
    }

    private void enrListPaneTest(Stage primaryStage)
    {
        VBox containerVBox = new VBox();
        EnrTablePane enrTablePane = new EnrTablePane();

        containerVBox.getChildren().addAll(enrTablePane);

        primaryStage.setScene(new Scene(containerVBox, 600.0, 400.0));
        primaryStage.setTitle("CenditLab.Reduced | Test EnrTablePaneTest");
        primaryStage.show();
    }

    private void noiseSourcePaneTest(Stage primaryStage)
    {
        VBox containerVBox = new VBox();
        NoiseSourceSetup noiseSourceSetup = new NoiseSourceSetup();

        containerVBox.getChildren().addAll(noiseSourceSetup);

        primaryStage.setScene(new Scene(containerVBox, 600, 400));
        primaryStage.setTitle("CenditLab.Reduced | Test EnrTablePaneTest");
        primaryStage.show();
    }

    private void enrSetupTest(Stage primaryStage)
    {
        EnrSetupView enrSetupView = new EnrSetupView();

        primaryStage.setScene(new Scene(enrSetupView, 600, 800));
        primaryStage.setTitle("CenditLab.Reduced | Test EnrSetupView Test");
        primaryStage.show();
    }

    private void showView(Parent parent, String title, double width, double height)
    {
        rootStage.setScene(new Scene(parent, width, height));
        rootStage.setTitle(title);
        rootStage.show();
    }

}
