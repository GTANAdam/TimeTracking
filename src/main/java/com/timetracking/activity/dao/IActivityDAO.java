package com.timetracking.activity.dao;

import com.timetracking.activity.model.Activity;

import java.util.List;

public interface IActivityDAO 
{
    List<Activity> getActivities(int start, int total);
    List<Activity> getActivitiesForUser(int userId, int start, int total);
    void add(Activity activity);
    boolean delete(int id);
    Activity getById(int id);
    boolean update(Activity activity);

    // Pagination
    int getNumberOfRows();
}
