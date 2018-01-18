package ve.gob.cendit.cenditlab.ui;

import javafx.beans.DefaultProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.util.List;

//@DefaultProperty("indicators")
public class IndicatorsView extends StackPane
{
    private ViewLoader viewLoader = new ViewLoader("fxml/indicators-view.fxml");

    private ObservableList<Node> list;

    private ListChangeListener<Node> listChangeListener;

    @FXML
    AnchorPane indicatorsAnchorPane;

    @FXML
    Pane contentPane;

    public IndicatorsView()
    {
        viewLoader.load(this, this);

        list = FXCollections.observableArrayList();

        //indicatorsAnchorPane.getChildren().setAll(list);
    }

    public IndicatorsView(Node content)
    {
        this();

        setContent(content);
    }

    public void setContent(Node content)
    {
        contentPane.getChildren().setAll(content);
    }


    public Node getContent()
    {
        return contentPane.getChildren().get(0);
    }


    public ObservableList<Node> getIndicators()
    {
        return indicatorsAnchorPane.getChildren();
    }

    public void setIndicators(ObservableList<Node> list)
    {
        this.list = list;
        indicatorsAnchorPane.getChildren().setAll(list);
    }

    public boolean contains(Indicator indicator)
    {
        return indicatorsAnchorPane.getChildren().contains(indicator.getContent());
    }

    public boolean contains(Node content)
    {
        return indicatorsAnchorPane.getChildren().contains(content);
    }

    public Indicator add(Node content)
    {
        Indicator indicator = new Indicator(this, content);

        if (! contains(content) )
        {
            indicatorsAnchorPane.getChildren().add(indicator.getContent());
        }

        return indicator;
    }

    public void remove(Node content)
    {
        indicatorsAnchorPane.getChildren().remove(content);
    }

    public void clear()
    {
        indicatorsAnchorPane.getChildren().clear();
    }

    public static void setAttachLeft(Node indicator, double offset)
    {
        AnchorPane.setLeftAnchor(indicator, offset != -1.0 ? offset : null);
    }

    public static double getAttachLeft(Node indicator)
    {
        return AnchorPane.getLeftAnchor(indicator);
    }

    public static void setAttachRight(Node indicator, double offset)
    {
        AnchorPane.setRightAnchor(indicator, offset != -1.0 ? offset : null);
    }

    public static double getAttachRight(Node indicator)
    {
        return AnchorPane.getRightAnchor(indicator);
    }

    public static void setAttachTop(Node indicator, double offset)
    {
        AnchorPane.setTopAnchor(indicator, offset != -1.0 ? offset : null);
    }

    public static double getAttachTop(Node indicator)
    {
        return AnchorPane.getTopAnchor(indicator);
    }

    public static void setAttachBottom(Node indicator, double offset)
    {
        AnchorPane.setBottomAnchor(indicator, offset != -1.0 ? offset : null);
    }

    public static double getAttachBottom(Node indicator)
    {
        return AnchorPane.getBottomAnchor(indicator);
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

        public void add()
        {
            IndicatorsView.this.add(contentNode);
        }

        public void remove()
        {
            IndicatorsView.this.remove(contentNode);
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
            AnchorPane.setRightAnchor(contentNode,
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
