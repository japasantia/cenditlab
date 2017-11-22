package ve.gob.cendit.cenditlab.tests.app;

import javafx.application.Application;
import javafx.stage.Stage;

import ve.gob.cendit.cenditlab.app.CenditLabApplication;
import ve.gob.cendit.cenditlab.control.MeasurementManager;
import ve.gob.cendit.cenditlab.control.System;
import ve.gob.cendit.cenditlab.systems.*;
import ve.gob.cendit.cenditlab.ui.MeasurementBarView;

public class CenditLabApplicationTests extends Application
{
    private CenditLabApplication app;

    NoiseFigureAnalyzer8975A nfa;
    AttenuatorSwitchDriver11713 asd;

    MeasurementManager measurementManager;
    MeasurementBarView measurementBarView;

    SystemsSetupStep systemsSetupStep;
    TasksSetupStep tasksSetupStep;
    TasksExecutionStep tasksExecutionStep;

    public static void main(String[] args)
    {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        CenditLabApplication.initialize(primaryStage);

        app = CenditLabApplication.getApp();

        initializeApp();
        initializeGui();

        app.show();
    }

    private void initializeApp()
    {
        nfa = new NoiseFigureAnalyzer8975A();
        asd = new AttenuatorSwitchDriver11713();

        System[] systemArray = { nfa, asd };

        systemsSetupStep = new SystemsSetupStep("Systems setup", systemArray);
        tasksSetupStep = new TasksSetupStep("Task setup", systemArray);
        tasksExecutionStep = new TasksExecutionStep("Task execution", systemArray);
        
        measurementManager = new MeasurementManager("Measurement session", 
                systemsSetupStep, tasksSetupStep, tasksExecutionStep);
        
        measurementBarView = new MeasurementBarView(measurementManager);
    }

    private void initializeGui()
    {
        app.setTopToolbar(measurementBarView);
    }
}
