package com.timetracking.activity.model;

import java.sql.Date;

import com.timetracking.user.model.User;

public class Activity
{
	private long id;
    private User user;
    private String name;
    private Date startDate;
    private Date endDate;

    private String status;

    public Activity() { }

    public Activity(long id, User user, String name, Date startDate, Date endDate, String status) 
    {
    	this.id = id;
        this.user = user;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public long getId()
    {
    	return id;
    }
    
    public void setId(long id)
    {
    	this.id = id;
    }

    public User getUser() 
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name) 
    {
        this.name = name;
    }

    public Date getStartDate() 
    {
        return startDate;
    }

    public void setStartDate(Date startDate) 
    {
        this.startDate = startDate;
    }

    public Date getEndDate()
    {
        return endDate;
    }

    public void setEndDate(Date endDate) 
    {
        this.endDate = endDate;
    }

    public String getStatus() 
    {
        return status;
    }

    public void setStatus(String status) 
    {
        this.status = status;
    }
    
    @Override
    public String toString() 
    {
        return "Activity [id=" + id + ", name=" + name + ", status=" + status + "]";
    }
}