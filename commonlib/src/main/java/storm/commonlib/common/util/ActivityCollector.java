package storm.commonlib.common.util;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gdq on 16/1/14.
 */
public class ActivityCollector {
    public static List<Activity> activityList = new ArrayList<Activity>();
    public static final ActivityCollector collector = new ActivityCollector();

    public static ActivityCollector getInstance() {
        return collector;
    }

    public void add(Activity activity) {
        if (activityList != null && activity != null) {
            activityList.add(activity);
        }
    }

    public void remove(Activity activity) {
        if (activityList != null && activity != null) {
            activityList.remove(activity);
            if (activityList.size() == 0) {
                //TODO 程序退出
            }
        }
    }

    public void exit() {
        if (activityList != null) {
            for (Activity activity : activityList) {
                if (activity != null) activity.finish();
            }
        }
    }
}
