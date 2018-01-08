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
    private FrequencyListSetupView frequencyListSetupView;

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
        frequencyListSetupView = new FrequencyListSetupView();

        Button testButton = new Button("Start Test");
        VBox containerVBox = new VBox();

        testButton.setOnAction(event -> frequencyListPaneTest());

        containerVBox.getChildren().addAll(frequencyListSetupView, testButton);

        primaryStage.setScene(new Scene(containerVBox, 600, 800));
        primaryStage.setTitle("CenditLab.Reduced | Test FrequencyListSetupView");
        primaryStage.show();
    }

    private void frequencyListPaneTest()
    {
        boolean isValid = frequencyListSetupView.validate();

        List<String> frequenciesList = frequencyListSetupView.getFrequencies();

        System.out.printf("Frequencies list valid: %s\n", isValid);

        System.out.println("Frequencies list");

        frequenciesList.stream()
                .forEach(f -> System.out.printf("%s ", f));
    }

    private void frequencyRangePaneTest(Stage primaryStage)
    {
        FrequencyRangeSetupView frequencyRangeSetupView = new FrequencyRangeSetupView();
        Button viewFieldsButton = new Button("View Fields");
        TextArea fieldsTextArea = new TextArea();

        VBox containerVBox = new VBox();
        containerVBox.getChildren().addAll(frequencyRangeSetupView, viewFieldsButton, fieldsTextArea);

        viewFieldsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event)
            {
                FrequencySetup frequencySetup = frequencyRangeSetupView.getFrequencySetup();

                String data = String.format("Max: %s\nMin: %s\nCenter: %s\nSpan: %s ",
                    frequencySetup.getMaxFrequencyData().toString(),
                    frequencySetup.getMinFrequencyData().toString(),
                    frequencySetup.getCentralFrequencyData().toString(),
                    frequencySetup.getSpanFrequencyData().toString());

                fieldsTextArea.setText(data);
            }
        });

        primaryStage.setScene(new Scene(containerVBox));
        primaryStage.setTitle("CenditLab.Reduced | Test FrequencyRangeSetupView");
        primaryStage.show();
    }

    private void frequencyFixedTest(Stage primaryStage)
    {
        FrequencyFixedSetupView frequencyFixedSetupView = new FrequencyFixedSetupView();
        Label frequencyLabel = new Label();
        VBox containerVBox = new VBox();

        FrequencyData frequencyDat = new FrequencyData();
        frequencyFixedSetupView.setFixedFrequency(frequencyDat);

        frequencyFixedSetupView.getFixedFrequency()
                .addUpdateListener(source -> {
                    String data = String.format("Field: %s\nControl: %s",
                            frequencyDat.toString(),
                            frequencyFixedSetupView.getFixedFrequency().toString());

                    frequencyLabel.setText(data);
                });

        containerVBox.getChildren().addAll(frequencyFixedSetupView, frequencyLabel);

        primaryStage.setScene(new Scene(containerVBox, 600.0, 400.0));
        primaryStage.setTitle("CenditLab.Reduced | Test FrequencyFixedSetupView");
        primaryStage.show();
    }

    private void enrListPaneTest(Stage primaryStage)
    {
        VBox containerVBox = new VBox();
        EnrTableSetupView enrTableSetupView = new EnrTableSetupView();

        containerVBox.getChildren().addAll(enrTableSetupView);

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
