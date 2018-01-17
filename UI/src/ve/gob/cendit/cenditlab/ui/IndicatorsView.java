package ve.gob.cendit.cenditlab.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import jdk.nashorn.internal.ir.IdentNode;

public class IndicatorsView extends StackPane
{
    private ViewLoader viewLoader = new ViewLoader("fxml/indicators-view.fxml");

    @FXML
    AnchorPane indicatorsAnchorPane;

    public IndicatorsView(Node content)
    {
        viewLoader.load(this, this);
        this.getChildren().add(content);
    }

    public Indicator createIndicator(Node content)
    {
        return new Indicator(this, content);
    }

    public boolean containsIndicator(Indicator indicator)
    {
        return indicatorsAnchorPane.getChildren().contains(indicator.getContent());
    }

    public boolean containsIndicator(Node content)
    {
        return indicatorsAnchorPane.getChildren().contains(content);
    }

    public void clearIndicators()
    {
        indicatorsAnchorPane.getChildren().clear();
    }

    public Indicator add(Node content)
    {
        Indicator indicator = new Indicator(content);

        if (! containsIndicator(content) )
        {
            indicatorsAnchorPane.getChildren().add(indicator.getContent());
            return indicator;
        }

    }

    private void addIndicator(Indicator indicator)
    {
        if (! containsIndicator(indicator) )
        {
            indicatorsAnchorPane.getChildren().add(indicator.getContent());
        }
    }

    private void removeIndicator(Indicator indicator)
    {
        indicatorsAnchorPane.getChildren().remove(indicator.getContent());
    }

    public class Indicator
    {
        private IndicatorsView parentIndicatorsView;
        private Node contentNode;

        private Indicator(Node content)
        {
            contentNode = content;
        }

        private Indicator(IndicatorsView parent, Node indicator)
        {
            parentIndicatorsView = parent;
            contentNode = indicator;
        }

        private Node getContent()
        {
            return contentNode;
        }

        public void show()
        {
            addIndicator(this);
        }

        public void hide()
        {
            removeIndicator(this);
        }

        public void setVisible(boolean value)
        {
            contentNode.setVisible(value);
        }

        public boolean isVisible()
        {
            return contentNode.isVisible();
        }

        public Indicator attachLeft(double offset)
        {
            AnchorPane.setLeftAnchor(contentNode,
                    offset != -1.0 ? offset : null);

            return this;
        }

        public Indicator attachRight(double offset)
        {
            AnchorPane.setLeftAnchor(contentNode,
                    offset != -1.0 ? offset : null);

            return this;
        }

        public Indicator attachTop(double offset)
        {
            AnchorPane.setTopAnchor(contentNode,
                    offset != -1.0 ? offset : null);

            return this;
        }

        public Indicator attachBottom(double offset)
        {
            AnchorPane.setBottomAnchor(contentNode,
                    offset != -1.0 ? offset : null);

            return this;
        }
    }
}
