package ve.gob.cendit.cenditlab.ui;

import javafx.scene.Node;
import ve.gob.cendit.cenditlab.control.Component;
import ve.gob.cendit.cenditlab.control.System;
import ve.gob.cendit.cenditlab.control.Task;
import ve.gob.cendit.cenditlab.ui.base.ViewType;

import java.util.HashMap;
import java.util.Map;

public class ComponentViewFactory
{
    private static final ComponentViewFactory globalFactory =
            new ComponentViewFactory();

    private Map<String, IViewFactory> viewFactoriesMap;

    private Map<Component, Map<ViewType, Node>> viewsCacheMap;

    public ComponentViewFactory()
    {
        viewFactoriesMap = new HashMap();
        viewsCacheMap = new HashMap<>();
    }

    public static final ComponentViewFactory get()
    {
        return globalFactory;
    }

    public boolean existFactory(Component component)
    {
        return viewFactoriesMap.containsKey(component.getClass().getName());
    }

    public boolean hasView(Component component, ViewType viewType)
    {
        Map<ViewType, Node> viewTypeNodeMap = viewsCacheMap.get(component);

        if (viewTypeNodeMap != null)
        {
            return viewTypeNodeMap.containsKey(viewType);
        }
        else
        {
            return false;
        }
    }

    public void registerFactory(Component component, IViewFactory factory)
    {
        if (component == null || factory == null)
        {
            throw new IllegalArgumentException("Arguments must not be null");
        }

        viewFactoriesMap.put(component.getClass().getName(), factory);
    }

    public IViewFactory getFactory(Component component)
    {
        return viewFactoriesMap.get(component.getClass().getName());
    }

    public IViewFactory removeFactory(Component component)
    {
        return viewFactoriesMap.remove(component.getClass().getName());
    }

    public Node getView(Component component, ViewType viewType)
    {
        if (hasView(component, viewType))
        {
            return viewsCacheMap.get(component).get(viewType);
        }
        else
        {
            return createView(component, viewType);
        }
    }

    public Node createView(Component component, ViewType viewType)
    {
        Node viewNode = null;

        if (existFactory(component))
        {
            IViewFactory viewFactory = getFactory(component);

            viewNode = viewFactory.createView(component, viewType);
        }
        else if (component instanceof System)
        {
            switch (viewType)
            {
                case ICON:
                    viewNode = new ComponentIconView(component);
                    break;

                case DESCRIPTION:
                    viewNode = new ComponentDescriptionView(component);
                    break;

                case DETAILS:
                    viewNode = new ComponentDescriptionView(component);
                    break;

                default:
                    viewNode =  new ComponentIconView(component);
            }
        }
        else if (component instanceof Task)
        {
            switch (viewType)
            {
                case ICON:
                    viewNode = new ComponentIconView(component);
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
                    viewNode = new ComponentIconView(component);
            }
        }
        else
        {
            viewNode = new ComponentIconView(component);
        }

        storeViewOnCache(component, viewType, viewNode);

        return viewNode;
    }

    private void storeViewOnCache(Component component, ViewType viewType, Node viewNode)
    {
        if (viewsCacheMap.containsKey(component))
        {
            viewsCacheMap.get(component).put(viewType, viewNode);
        }
        else
        {
            Map<ViewType, Node> viewTypeNodeMap = new HashMap<>();
            viewTypeNodeMap.put(viewType, viewNode);

            viewsCacheMap.put(component, viewTypeNodeMap);
        }
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
