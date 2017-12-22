package ve.gob.cendit.cenditlab.ui;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import ve.gob.cendit.cenditlab.data.*;

import java.util.LinkedList;
import java.util.List;

public class FieldInput extends HBox
{
    private static final String FXML_URL = "fxml/field-input.fxml";

    @FXML
    private TextField valueTextField;

    @FXML
    private ChoiceBox<Unit> unitsChoiceBox;

    private List<IUpdateListener> listenersList;
    private boolean updateEnabled;

    private Field field;

    public FieldInput()
    {
        ViewLoader.load(FXML_URL, this, this);

        field = new Field();

        initialize();
    }

    public FieldInput(Field field)
    {
        ViewLoader.load(FXML_URL, this, this);

        setField(field);

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

            field.setValue(valueTextField.getText());

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

            field.setUnit(newUnit);

            updateFieldView();

            // TODO: revisar operacion flags para update
            setUpdateEnabled(true);

            callUpdateListeners();
        }
    }

    private void updateFieldView()
    {
        valueTextField.setText(field.getValue());
        unitsChoiceBox.setValue(field.getUnit());
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
                    .forEach(listener -> listener.onUpdate());
        }
    }

    public void setField(Field field)
    {
        if (field == null)
        {
            throw new IllegalArgumentException("field must not be null");
        }

        this.field = field;

        valueTextField.setText(field.getValue());
        setChoiceUnits(field.getValidUnits());
    }

    public Field getField()
    {
        return field;
    }

    @Override
    public String toString()
    {
        return field.toString();
    }

    public void setChoiceUnits(FieldUnits units)
    {
        if (units != null && units != FieldUnits.EMPTY_UNITS)
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

    public FieldUnits getChoiceUnits()
    {
        return field.getValidUnits();
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
