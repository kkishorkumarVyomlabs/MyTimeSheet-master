package com.chatbox.bussiness;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;


public class TimeSheet
{

	public String check_valid(String user_Emp_Id,String loc,String dt,String in,String out,String task) throws IOException, ParseException
	{
		
		Date date=new SimpleDateFormat("dd/MM/yyyy").parse(new SimpleDateFormat("dd/MM/yyyy 00:00:00").format(new Date()));
		String new_date=new SimpleDateFormat("dd/MM/yyyy").format(date);
		System.out.println(new_date);

		Date user_date =new SimpleDateFormat("dd/MM/yyyy").parse(new SimpleDateFormat("dd/MM/yyyy 00:00:00").format(new SimpleDateFormat("yyyy-MM-dd").parse(dt)));
		System.out.println("System Date="+date);
		System.out.println("User Enterd date="+user_date);

		if(!date.after(user_date) || date.equals(user_date))
			return "Sorry! You can not able to apply for today or future date.";

		String emp_id ="";
		int diff_in_out=0;
		String result=null;
		String workingHrs=null;
		String status=null;
		InputStream stream =TimeSheet.class.getResourceAsStream("/Time sheet format.xls");
		/*File file=new File("D:\\Time sheet format.xls");
		FileInputStream fin = new FileInputStream(file);*/
		HSSFWorkbook wb = new HSSFWorkbook(stream);
		HSSFRow row=null;
		HSSFSheet ws = wb.getSheetAt(0);
		HSSFRow rowHeader = ws.getRow(0);
		HSSFCell cell=null;

		int rowNum = ws.getLastRowNum() + 1;
		int colNum = ws.getRow(0).getLastCellNum();
		int empidindexheader=0;
		int dateindexheader=-1;
		int inTimeIndexheader  = -1;	
		int outTimeIndexheader = -1;

		for (int k = 1; k <=rowNum; k++)
		{
			try{
				row = ws.getRow(k);
				emp_id = cellToString(row.getCell(empidindexheader));
				System.out.println("Excel Employee Id="+emp_id);
			}catch(Exception e){
				e.printStackTrace();
			}
			if(user_Emp_Id.equals(emp_id))
			{
				break;
			}else if(k >= rowNum)
			{
				//System.out.println("Sorry...!! You Entered Invalid Employee ID "+empidindex);	
				result="Sorry...!! You Entered Invalid Employee ID ";
				return result;
			}
		}

		System.out.println("Employee Header Index="+empidindexheader);
		//String empidindex = cellToString(row.getCell(empidindexheader));
		try{
			ws=wb.getSheet(emp_id);
			colNum = ws.getRow(0).getLastCellNum();
		}catch(Exception e){
			e.printStackTrace();
		}
		for (int i = 0; i < colNum; i++)
		{
			row = ws.getRow(0);
			cell = row.getCell(i);

			String cellValue1 = cellToString(cell);
			rowNum = ws.getLastRowNum();

			if ("Date".equalsIgnoreCase(cellValue1)) 
			{ 	
				dateindexheader=i;	
				System.out.println("Date Index="+dateindexheader);
				continue;
			}
			else if("TIME IN(AM)".equalsIgnoreCase(cellValue1))
			{
				inTimeIndexheader=i;
				System.out.println("intime Index="+inTimeIndexheader);
				continue;
			}
			else if("TIME OUT(PM)".equalsIgnoreCase(cellValue1))
			{
				outTimeIndexheader=i;
				System.out.println("Out Time Index="+outTimeIndexheader);
			}
		}


		System.out.println("User Employee Id="+user_Emp_Id);

		rowNum = ws.getLastRowNum();



		for (int l = 1; l <=rowNum;l++)
		{
			int intime =0;
			row = ws.getRow(l);
			String dateindex = cellToString(row.getCell(dateindexheader));
			System.out.println(dateindex);
			//rowHeader = ws.getRow(i);

			if(dt.equals(dateindex))
			{
				//System.out.println("Sorry...!! You Entered this date before.Please enter new date");
				result="Sorry...!! You allready apply for this date before.";
				return result;
			}else
			{	
				System.out.println(inTimeIndexheader);
				try{
					row = ws.getRow(l);
					String intimeindex = cellToString(row.getCell(inTimeIndexheader));
					intime = Integer.valueOf((String)intimeindex);
					System.out.println(intime);
				}catch(Exception e){
					e.printStackTrace();
				}

				/*if(intime != 0)
				{
					String outtimeindex = cellToString(row.getCell(outTimeIndexheader));
					int  outtime= Integer.valueOf((String) outtimeindex);
					System.out.println(outtime);
					workingHrs=getDiff(in, out);
					String arr[]=workingHrs.split(":");
					diff_in_out=Integer.valueOf((String)arr[0]);
					System.out.println("Difference between "+in +" and "+out+" is "+diff_in_out);
				}*/
				
				
				String arr[]=in.split(":");
				intime=Integer.valueOf((String)arr[0]);
				arr=out.split(":");
				int outtime=Integer.valueOf((String)arr[0]);
				diff_in_out=outtime-intime;
				System.out.println("diff_in_out="+diff_in_out);

				if(diff_in_out>=9 && l<rowNum)
				{
					//WorkingHrs=Integer.toString(diff_in_out);
					status="Full-Time"; 
					stream.close();

					System.out.println(" getLastRowNum "+ws.getLastRowNum());
					HSSFRow row1 = ws.createRow((short)  ws.getLastRowNum()+1);
					row1.createCell(0).setCellValue(loc);
					row1.createCell(1).setCellValue(dt);
					row1.createCell(2).setCellValue(in);
					row1.createCell(3).setCellValue(out);
					row1.createCell(4).setCellValue(workingHrs);
					row1.createCell(5).setCellValue(status);
					
					
					ClassLoader classLoader = TimeSheet.class.getClassLoader();
	                File excel =  new File(classLoader.getResource("/Time sheet format.xls").getFile());
					//File excel = new File("D:\\Time sheet format.xls");
					FileOutputStream fos = new FileOutputStream(excel);
					wb.write(fos);
					fos.close();
					result="I am Pleased to confirm that Your time sheet entry is succefully commited to records.Thank you for reaching to us";
					return result;
				}
				else if(l>=rowNum)
				{
					workingHrs=Integer.toString(diff_in_out);
					status="Half-Time";
					System.out.println(" getLastRowNum "+ws.getLastRowNum());
					HSSFRow row1 = ws.createRow((short)  ws.getLastRowNum()+1);
					row1.createCell(0).setCellValue(loc);
					row1.createCell(1).setCellValue(dt);
					row1.createCell(2).setCellValue(in);
					row1.createCell(3).setCellValue(out);
					row1.createCell(4).setCellValue(workingHrs);
					row1.createCell(5).setCellValue(status);
					File excel = new File("D:\\Time sheet format.xls");
					FileOutputStream fos = new FileOutputStream(excel);
					wb.write(fos);
					//wb.close();
					fos.close();//else
					result="I am Pleased to confirm that Your time sheet entry is succefully commited to records.Thank you for reaching to us";
					return result;
				}
			}
		}

		return result;
	}

	@SuppressWarnings("deprecation")
	public static String cellToString(HSSFCell cell) 
	{	
		String date ="";
		Boolean Test=true;
		Object result = null;
		switch (cell.getCellType()) 
		{
		case HSSFCell.CELL_TYPE_NUMERIC:
			//Date date = new Date();
			if (DateUtil.isCellDateFormatted(cell))
			{
				SimpleDateFormat sdfdate = new SimpleDateFormat("MM-dd-yyyy");
				//SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");

				date = sdfdate.format(cell.getDateCellValue());
				// System.out.println(date.toString());
				result=date.toString();
			}
			else 
			{
				result = Integer.valueOf((int) cell.getNumericCellValue())
						.toString();
			}
			break;
		case HSSFCell.CELL_TYPE_STRING:

			result = cell.getStringCellValue();
			break;
		case HSSFCell.CELL_TYPE_BLANK:
			result = "";
			break;
		case HSSFCell.CELL_TYPE_FORMULA:
			result = cell.getCellFormula();

		}
		return result.toString();
	}	

}



