package ve.gob.cendit.cenditlab.ui;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import ve.gob.cendit.cenditlab.data.*;

public class ValueView extends HBox
{
    private static final String FXML_URL = "fxml/value-view.fxml";

    private static final ViewLoader viewLoader = new ViewLoader(FXML_URL);

    @FXML
    private TextField valueTextField;

    @FXML
    private ChoiceBox<Unit> unitsChoiceBox;

    private ValueData data;

    private boolean updateViewEnabled;

    public ValueView()
    {
        this(new ValueData());
    }

    public ValueView(ValueData data)
    {
        viewLoader.load(FXML_URL, this, this);

        setUpdateViewEnabled(true);

        setData(data);

        initialize();
    }

    private void initialize()
    {
        valueTextField.setOnAction(event -> onUpdateValue());

        unitsChoiceBox.setOnMouseClicked(event -> onUpdateUnit(unitsChoiceBox.getValue()));

        valueTextField.focusedProperty()
                .addListener((observable, oldValue, newValue) ->
                    {
                        if (!newValue)
                            onUpdateValue();
                    });

        unitsChoiceBox.focusedProperty()
                .addListener((observable, oldValue, newValue) ->
                    {
                        if (!newValue)
                            onUpdateUnit(unitsChoiceBox.getValue());
                    });

        /*
        unitsChoiceBox.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) ->
                    {
                        onUpdateUnit(newValue);
                    });
        */
    }

    private void onUpdateValue()
    {
        if (isUpdateViewEnabled())
        {
            setUpdateViewEnabled(false);

            data.setValue(valueTextField.getText());

            onUpdateFieldView();

            // TODO: revisar operacion flags para update
            setUpdateViewEnabled(true);
        }
    }

    private void onUpdateUnit(Unit newUnit)
    {
        // TODO: revisar proceso de actualizacion -> unit == null
        if (isUpdateViewEnabled() && newUnit != null)
        {
            setUpdateViewEnabled(false);

            data.setUnit(newUnit);

            onUpdateFieldView();

            // TODO: revisar operacion flags para update
            setUpdateViewEnabled(true);
        }
    }

    private void onUpdateFieldView()
    {
        valueTextField.setText(data.getValue());
        unitsChoiceBox.setValue(data.getUnit());
    }

    public void setData(ValueData value)
    {
        if (value == null)
        {
            throw new IllegalArgumentException("data must not be null");
        }

        data = value;
        data.addUpdateListener(source -> onUpdateFieldView());

        valueTextField.setText(data.getValue());
        setChoiceUnits(data.getValidUnits());
    }

    public ValueData getData()
    {
        return data;
    }

    public void setUpdateViewEnabled(boolean value)
    {
        updateViewEnabled = value;
    }

    public boolean isUpdateViewEnabled()
    {
        return updateViewEnabled;
    }

    @Override
    public String toString()
    {
        return data.toString();
    }

    public void setChoiceUnits(DataUnits units)
    {
        if (units != null && units != DataUnits.EMPTY_UNITS)
        {
            if (unitsChoiceBox.getItems().size() > 0)
                unitsChoiceBox.getItems().clear();

            unitsChoiceBox.getItems().addAll(units.getUnits());
            unitsChoiceBox.setVisible(true);
            unitsChoiceBox.setValue(units.get(0));
        }
        else
        {
            unitsChoiceBox.setVisible(false);
        }
    }

    public DataUnits getChoiceUnits()
    {
        return data.getValidUnits();
    }

    public Unit getUnit()
    {
        return unitsChoiceBox.getValue();
    }

    public void setUnit(Unit unit)
    {
        unitsChoiceBox.setValue(unit);
    }

    public boolean validate(IValueValidator validator)
    {
        return validator.isValid(toString());
    }
}
