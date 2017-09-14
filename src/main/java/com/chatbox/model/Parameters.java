package com.chatbox.model;

/*import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)*/
public class Parameters {
	 private String in_time;

	    private String location;

	    private String task;

	    private String out_time;

	    private String emp_id;

	    private String date;

	    public String getIn_time ()
	    {
	        return in_time;
	    }

	    public void setIn_time (String in_time)
	    {
	        this.in_time = in_time;
	    }

	    public String getLocation ()
	    {
	        return location;
	    }

	    public void setLocation (String location)
	    {
	        this.location = location;
	    }

	    public String getTask ()
	    {
	        return task;
	    }

	    public void setTask (String task)
	    {
	        this.task = task;
	    }

	    public String getOut_time ()
	    {
	        return out_time;
	    }

	    public void setOut_time (String out_time)
	    {
	        this.out_time = out_time;
	    }

	    public String getEmp_id ()
	    {
	        return emp_id;
	    }

	    public void setEmp_id (String emp_id)
	    {
	        this.emp_id = emp_id;
	    }

	    public String getDate ()
	    {
	        return date;
	    }

	    public void setDate (String date)
	    {
	        this.date = date;
	    }

	    @Override
	    public String toString()
	    {
	        return "ClassPojo [in_time = "+in_time+", location = "+location+", task = "+task+", out_time = "+out_time+", emp_id = "+emp_id+", date = "+date+"]";
	    }
}
