package com.chatbox.main;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;

import com.chatbox.bussiness.TimeSheet;
import com.chatbox.bussiness.API_AI_Responce;
import com.chatbox.model.API_AI_Response_Mdl;
import com.chatbox.model.Parameters;
import com.chatbox.model.Response_Mdl;
import com.chatbox.model.Result;
import com.chatbox.model.Messages;
import com.chatbox.model.Fulfillment;
import com.chatbox.model.Location;

@Path("timesheet")
public class RequestResponce {

	@GET
	public Response getMsg() {
		return Response.status(200).entity("Welcome User...!").build();

	}

	@POST 
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getflxrecuiter(String outputJSON) throws IOException{
		String str1="";
		System.out.println("Request recieved");
		API_AI_Responce response = new API_AI_Responce();

		System.out.println("responceBO : "+response.toString());
		API_AI_Response_Mdl apiAiResponse = response.jsonToJava(outputJSON);

		System.out.println("apiAiResponse : " +apiAiResponse);

		Result rs=apiAiResponse.getResult();

		System.out.println("rs :"+rs.toString());
		Parameters params=rs.getParameters();

		Parameters p=rs.getParameters();
		try{
			params=rs.getParameters();
			String empid = params.getEmp_id();
			String date=params.getDate();
			String t_in=params.getIn_time();
			String t_out=params.getOut_time();
			String task=params.getTask();
			String location=params.getLocation();
			
			TimeSheet cobj=new TimeSheet();
			str1=cobj.check_valid(empid,location,date,t_in,t_out,task);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		
		Response_Mdl res=new Response_Mdl();
		res.setSource("policyWS");
		res.setSpeech(str1);
		res.setDisplayText(str1);
		
		
		ObjectMapper om=new ObjectMapper();
		String str2=om.writeValueAsString(res);

		return Response.status(200).entity(str2).header("Content-Type", "application/json").build();
	}
}
