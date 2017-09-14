package com.chatbox.model;

public class Location
{
	private String city;

    public String getCity ()
    {
        return city;
    }

    public void setCity (String city)
    {
        this.city = city;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [city = "+city+"]";
    }

}
