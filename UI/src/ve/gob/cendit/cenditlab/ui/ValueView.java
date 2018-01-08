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
    private ChoiceBox<Unit> unitChoiceBox;

    @FXML
    private ChoiceBox<Multiplier> multiplierChoiceBox;

    private ValueData data;

    private boolean updateViewEnabled;

    private IUpdateListener dataUpdateListener = source -> updateValueView();

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
        valueTextField.setOnAction(event -> updateValue());

        // unitChoiceBox.setOnMouseClicked(event -> updateUnit(unitChoiceBox.getValue()));

        valueTextField.focusedProperty()
                .addListener((observable, oldValue, newValue) ->
                    {
                        if (!newValue)
                            updateValue();
                    });

        unitChoiceBox.valueProperty()
                .addListener((observable, oldValue, newValue) -> updateUnit(newValue));

        multiplierChoiceBox.getItems().setAll(Multiplier.values());

        multiplierChoiceBox.valueProperty()
                .addListener((observable, oldValue, newValue) -> updateMultiplier(newValue));

    }

    public void setData(ValueData value)
    {
        if (value == null)
        {
            throw new IllegalArgumentException("data must not be null");
        }

        if (data != null)
        {
            data.removeListener(dataUpdateListener);
        }

        data = value;
        data.addUpdateListener(dataUpdateListener);

        updateValueView();
    }

    public ValueData getData()
    {
        return data;
    }

    private void updateValue()
    {
        if (isUpdateViewEnabled())
        {
            data.setValue(valueTextField.getText());

            // updateValueView();
        }
    }

    private void updateUnit(Unit newUnit)
    {
        if (isUpdateViewEnabled() && newUnit != null)
        {
            data.setUnit(newUnit);

            // updateValueView();
        }
    }

    private void updateMultiplier(Multiplier newMultiplier)
    {
        if (isUpdateViewEnabled() && newMultiplier != null)
        {
            data.setMultiplier(newMultiplier);
        }
    }

    private void updateValueView()
    {
        setUpdateViewEnabled(false);

        loadValue(data);
        loadMultiplier(data);
        loadValidUnits(data);
        loadUnit(data);

        setUpdateViewEnabled(true);
    }

    private void loadValue(ValueData valueData)
    {
        if (valueData != null)
        {
            valueTextField.setText(valueData.getValue());
        }
        else
        {
            valueTextField.clear();
        }
    }

    private void loadMultiplier(ValueData valueData)
    {
        Multiplier multiplier = valueData.getMultiplier();

        if (multiplier != null)
        {
            multiplierChoiceBox.setVisible(true);
            multiplierChoiceBox.setValue(multiplier);
        }
        else
        {
            multiplierChoiceBox.setVisible(false);
        }
    }

    private void loadUnit(ValueData valueData)
    {
        Unit unit = valueData.getUnit();

        if (unit != null)
        {
            unitChoiceBox.setVisible(true);
            unitChoiceBox.setValue(unit);
        }
        else
        {
            unitChoiceBox.setVisible(false);
        }
    }

    private void loadValidUnits(ValueData valueData)
    {
        Unit[] units = valueData.getValidUnits();

        if (units != null)
        {
            unitChoiceBox.getItems().setAll(units);
        }
    }

    public Unit getUnit()
    {
        return unitChoiceBox.getValue();
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

    public boolean validate(IValueValidator validator)
    {
        return validator.isValid(toString());
    }
}
