package com.example.lenovo.baiduditu.myClass;

import android.app.Activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by lenovo on 2017/12/22.
 */

public class activityCollector {
    private static List<Activity> activities = new ArrayList<>();
    public static void addActivity(Activity activity){
/*        int count = Collections.frequency(activities,activity);
        if (count==0){
            activities.add(activity);
        }   */
        activities.add(activity);
    }
    public static void removeActivity(Activity activity){
        activities.remove(activity);
    }
    public static void finishAll(){
        for (Activity activity : activities){
            if (!activity.isFinishing()){
                activity.finish();
            }
        }
        activities.clear();
    }
}
