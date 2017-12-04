package ve.gob.cendit.cenditlab.ui;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import ve.gob.cendit.cenditlab.data.*;

import java.util.LinkedList;
import java.util.List;

public class ValueView extends HBox
{
    private static final String FXML_URL = "fxml/value-view.fxml";

    private static final ViewLoader viewLoader = new ViewLoader(FXML_URL);

    @FXML
    private TextField valueTextField;

    @FXML
    private ChoiceBox<Unit> unitsChoiceBox;

    private List<IUpdateListener> listenersList;
    private boolean updateEnabled;

    private ValueData data;

    public ValueView()
    {
        this(new ValueData());
    }

    public ValueView(ValueData data)
    {
        viewLoader.load(FXML_URL, this, this);

        setData(data);

        initialize();
    }

    private void initialize()
    {
        valueTextField.focusedProperty()
                .addListener((observable, oldValue, newValue) ->
                    {
                        if (!newValue)
                            onUpdateValue();
                    });
        unitsChoiceBox.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) ->
                    {
                        onUpdateUnit(newValue);
                    });

        setUpdateEnabled(true);
    }

    private void onUpdateValue()
    {
        if (isUpdateEnabled())
        {
            setUpdateEnabled(false);

            data.setValue(valueTextField.getText());

            updateFieldView();

            // TODO: revisar operacion flags para update
            setUpdateEnabled(true);

            callUpdateListeners();
        }
    }

    private void onUpdateUnit(Unit newUnit)
    {
        // TODO: revisar proceso de actualizacion -> unit == null
        if (isUpdateEnabled() && newUnit != null)
        {
            setUpdateEnabled(false);

            data.setUnit(newUnit);

            updateFieldView();

            // TODO: revisar operacion flags para update
            setUpdateEnabled(true);

            callUpdateListeners();
        }
    }

    private void updateFieldView()
    {
        valueTextField.setText(data.getValue());
        unitsChoiceBox.setValue(data.getUnit());
    }

    public void addUpdateListener(IUpdateListener listener)
    {
        if (listenersList == null)
        {
            listenersList = new LinkedList<>();
        }

        if (listener != null)
        {
            listenersList.add(listener);
        }
    }

    public void removeUpdateListener(IUpdateListener listener)
    {
        if (listenersList != null)
        {
            listenersList.remove(listener);
        }
    }

    public void setUpdateEnabled(boolean enable)
    {
        updateEnabled = enable;
    }

    public boolean isUpdateEnabled()
    {
        return updateEnabled;
    }

    private void callUpdateListeners()
    {
        if (listenersList != null && isUpdateEnabled())
        {
            listenersList.stream()
                    .forEach(listener -> listener.onUpdate(this));
        }
    }

    public void setData(ValueData data)
    {
        if (data == null)
        {
            throw new IllegalArgumentException("data must not be null");
        }

        this.data = data;

        valueTextField.setText(data.getValue());
        setChoiceUnits(data.getValidUnits());
    }

    public ValueData getData()
    {
        return data;
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
