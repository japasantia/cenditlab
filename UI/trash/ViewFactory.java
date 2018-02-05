package ve.gob.cendit.cenditlab.ui;

import javafx.scene.Node;
import ve.gob.cendit.cenditlab.control.Component;
import ve.gob.cendit.cenditlab.control.ComponentDescriptor;
import ve.gob.cendit.cenditlab.control.System;
import ve.gob.cendit.cenditlab.control.Task;
import ve.gob.cendit.cenditlab.ui.base.ViewType;

import javax.management.Descriptor;
import java.util.HashMap;
import java.util.Map;

public class ViewFactory
{
    private static final ViewFactory globalFactory = new ViewFactory();


    protected ViewFactory()
    {

    }

    public static final ViewFactory get()
    {
        return globalFactory;
    }

    public Node getView(ComponentDescriptor descriptor, ViewType viewType)
    {

    }


    public Node getView(ComponentDescriptor descriptor, ViewType viewType)
    {
        Node viewNode = null;

        switch (viewType)
        {
            case ICON:
                viewNode = new IconView(descriptor);
                break;

            case DESCRIPTION:
                break;
        }
    }

    public Node createView(Component component, ViewType viewType)
    {
        Node viewNode = null;

        if (component instanceof System)
        {
            switch (viewType)
            {
                case ICON:
                    viewNode = new IconView(component.getComponentDescriptor());
                    break;

                case DESCRIPTION:
                    viewNode = new ComponentDescriptionView(component);
                    break;

                case DETAILS:
                    viewNode = new ComponentDescriptionView(component);
                    break;

                default:
                    viewNode =  new IconView(component.getComponentDescriptor());
            }
        }
        else if (component instanceof Task)
        {
            switch (viewType)
            {
                case ICON:
                    viewNode = new IconView(component.getComponentDescriptor());
                    break;

                case DESCRIPTION:
                    viewNode = new TaskDescriptionView((Task)component);
                    break;

                case DETAILS:
                    viewNode = new TaskDescriptionView((Task)component);
                    break;

                case EXECUTION:
                    viewNode = new TaskExecutionView((Task)component);
                    break;

                default:
                    viewNode = new IconView(component.getComponentDescriptor());
            }
        }
        else
        {
            viewNode = new IconView(component.getComponentDescriptor());
        }



        return viewNode;
    }


    public interface IViewFactory
    {
        Node createView(Component component, ViewType viewType);
    }
}

/*
public class ComponentViewFactory
{
    private Map<String, Node> viewsMap;

    public ComponentViewFactory()
    {
        viewsMap = new HashMap<>(5);
    }

    public ComponentViewFactory(Pair<ViewType, Node>... viewPairs)
    {
        this();

        Arrays.stream(viewPairs)
                .forEach(pair -> setView(pair.getKey(), pair.getValue()));
    }

    public void setView(ViewType viewType, Node viewNode)
    {
        if (viewType == null)
            return;

        setView(viewType.toString(), viewNode);
    }

    public void setView(String viewId, Node viewNode)
    {
        if (viewId == null)
            return;

        viewsMap.put(viewId, viewNode);
    }

    public void setView(String viewId, String viewUrl)
    {
        Node viewNode = ViewLoader.load(viewUrl);
        setView(viewId, viewNode);
    }

    public Node getView(ViewType viewType)
    {
        return getView(viewType.toString());
    }

    public Node getView(String viewId)
    {
        return viewsMap.get(viewId);
    }

    public Node removeView(ViewType viewType)
    {
        return removeView(viewType.toString());
    }

    public Node removeView(String viewId)
    {
        return viewsMap.remove(viewId);
    }
}
*/
