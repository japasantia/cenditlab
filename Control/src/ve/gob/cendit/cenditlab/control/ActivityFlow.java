package ve.gob.cendit.cenditlab.control;

import java.util.*;

public class ActivityFlow
{
    private String name;

    private int currentIndex = -1;
    private Activity currentActivity;
    private Activity previousActivity;

    private EventEmitter<IChangeListener<ActivityFlow, Activity>> activityChangeEventEmitter;

    private List<Activity> activityList;

    public ActivityFlow(String name, Activity... activities)
    {
        this.name = name;

        activityChangeEventEmitter =
                new EventEmitter<>("onActivityChange", this::onActivityChangeCaller);

        activityList = new LinkedList<Activity>();

        addActivities(activities);
    }

    public void addActivity(Activity activity)
    {
        if (activity == null)
        {
            throw new IllegalArgumentException("activity must not be null");

        }

        activityList.add(activity);
    }

    public void addActivities(Activity... activities)
    {
        if (activities == null)
        {
            throw new IllegalArgumentException("activities must not be null");
        }

        Arrays.stream(activities)
            .forEach(activity -> {
                if (activity == null)
                {
                    throw new IllegalArgumentException("no activity in activities must not be null");
                }
                activity.setActivityFlow(this);
                activityList.add(activity);
            });
    }

    public void removeActivity(Activity activity)
    {
        if (activity != null && activity.getActivityFlow() == this)
        {
            activityList.remove(activity);
        }
    }

    public void clearActivities()
    {
        activityList.clear();
    }

    public int getAtivitiesCount()
    {
        return activityList.size();
    }

    public int getCurrentActivityIndex()
    {
        return currentIndex;
    }

    public Activity getCurrentActivity()
    {
        return currentActivity;
    }

    public Activity getActivity(int index)
    {
        return activityList.get(index);
    }

    public List<Activity> getActivities()
    {
        return Collections.unmodifiableList(activityList);
    }

    public boolean hasNextActivity()
    {
        return getCurrentActivityIndex() < getAtivitiesCount() - 1;
    }

    public boolean hasPrevActivity()
    {
        return getCurrentActivityIndex() > 0;
    }

    public boolean canGoNextActivity()
    {
        return canGoToActivity(getCurrentActivityIndex() + 1);
    }

    public boolean canGoPrevActivity()
    {
        return canGoToActivity(getCurrentActivityIndex() - 1);
    }

    public boolean canGoToActivity(Activity activity)
    {
        if (activity == null || activity == currentActivity)
        {
            return false;
        }

        if (currentActivity != null)
        {
            if (currentActivity.canExitToActivity(activity) && activity.canEnterFromActivity(currentActivity))
            {
                return true;
            }
        }
        else if (activity.canEnterFromActivity(currentActivity))
        {
            return true;
        }

        return false;
    }

    public boolean canGoToActivity(int index)
    {
        if (index >= 0 && index < activityList.size())
        {
            return canGoToActivity(activityList.get(index));
        }

        return false;
    }

    public boolean nextActivity()
    {
        return toActivity(getCurrentActivityIndex() + 1);
    }

    public boolean prevActivity()
    {
         return toActivity(getCurrentActivityIndex() - 1);
    }

    public boolean toActivity(int index)
    {
        if (canGoToActivity(index))
        {
            return toActivity(activityList.get(index));
        }

        return false;
    }

    public boolean toActivity(Activity activity)
    {
        if (canGoToActivity(activity))
        {
            switchToActivity(activity);

            return true;
        }

        return false;
    }

    public Activity loadCurrentActivity()
    {
        Activity activity = getCurrentActivity();

        if (toActivity(activity))
        {
            return activity;
        }
        else if (nextActivity())
        {
            return getCurrentActivity();
        }

        return null;
    }

    public Activity unloadCurrentActivity()
    {
        Activity activity = getCurrentActivity();

        if (activity != null)
        {
            activity.executeUnload();
        }

        return activity;
    }

    private void switchToActivity(Activity activity)
    {
        if (currentActivity != null)
        {
            currentActivity.executeUnload();
        }

        previousActivity = currentActivity;
        currentActivity = activity;
        currentIndex = activityList.indexOf(activity);

        currentActivity.executeInitialize();

        currentActivity.executeLoad();

        currentActivity.executeRun();

        // Emite evento al cambiar de actividad
        activityChangeEventEmitter.call(this, previousActivity, currentActivity);
    }

    public void initialize()
    {
        activityList.stream()
                .forEach(activity ->  activity.initialize());
    }

    public void addOnActivityChangeListener(IChangeListener<ActivityFlow, Activity> listener)
    {
        activityChangeEventEmitter.addListener(listener);
    }

    public void removeOnActivityChangeListener(IChangeListener<ActivityFlow, Activity> listener)
    {
        activityChangeEventEmitter.removeListener(listener);
    }

    private void onActivityChangeCaller(Object source, IChangeListener<ActivityFlow, Activity> handler,
                                        Object... args)
    {
        handler.onChange(this, previousActivity, currentActivity);
    }
}
