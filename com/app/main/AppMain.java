package com.app.main;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.app.commons.AppConstants;
import com.app.commons.ExceptionFormatter;
import com.app.commons.MailManager;
import com.app.commons.ResponseVO;
import com.app.commons.TextFormatter;
import com.app.commons.TokensVO;
import com.app.db.dao.InsertDataDAO;
import com.app.db.dao.QueryGenerator;
import com.app.excel.ExcelReader;
import com.app.win.WinUtils;
import com.app.xml.XMLTaskReader;

public class AppMain {

	private final static Logger log = Logger.getLogger(AppMain.class.getName());
	private final String xmlPath = "." + File.separator + "properties"+ File.separator + "app.xml";
	private final static String log4jPath = "." + File.separator + "properties" + File.separator + "log4j.properties";

	public static void main(String[] args) throws Exception {
		
		Properties props = new Properties();
		props.load(new FileInputStream(log4jPath));
		PropertyConfigurator.configure(props);
		
		AppMain obj = new AppMain();
		obj.startProcess();

	}

	public void startProcess() {

		// log start-time

		// read xml and get list of tasks
		// validate each task against values configured  --pending
		// check number of columns in xml and excel are provided correct  --tbd
		// check excel file-name contains valid name	--tbd1
		// check database name and if we have connection properties for that --
		// database
		// check post action and attributes for that action --tbd

		
		XMLTaskReader xmlReader = new XMLTaskReader();
		
		try{
			//reading XML
			List<TaskVO> list = xmlReader.read(xmlPath);
			
			log.info("Number of tasks configure in xml = " + list.size());
			
			for (TaskVO vo : list) {
				stepExcelToDB(vo);
			}
			
		}catch(Exception e){
			//send error wala mail
			
			String mailBody = ExceptionFormatter.getExceptionTrace(e);
			
			
			MailManager.send( "cc@aa.com", 
							  "Csss@aaa.com", 
							  null,
							  null, 
							  "***ERROR*** App4dt config",
							  mailBody
					  );
		}

		

		// is excel exist on the path provided
		// read excel according to column data provided in excel


		

		log.info("***DONE*** ");

		// generate insert statement with the data in excel sheet

		// check is db table exist
		// insert all sql statements in database
		// log each failure record and count

		// send response mail with number of record failed and success

		// log task end-time and progress status

		// repeat for each task

		// log end-time

	}

	private InsertDataDAO dao = new InsertDataDAO();
	private ExcelReader er = new ExcelReader();

	public void stepExcelToDB(TaskVO taskVo) {
		
		TokensVO tokensVo = new TokensVO();
		ResponseVO responseVo = null;
		
		try{
			
			tokensVo.setDatabaseName(taskVo.getConnection4db());
			tokensVo.setFileName(taskVo.getExcelFileName());
			tokensVo.setTableName(taskVo.getDbTableName());
			tokensVo.setTaskName(taskVo.getTaskName());
			
			String excelFilePath = taskVo.getExcelPath() + taskVo.getExcelFileName();
	
			Map<String, Integer> map = new LinkedHashMap<String, Integer>();
	
			String excelColumns = taskVo.getExcelColumns();
			String[] columnsArray = excelColumns.split(",");
			for (String str : columnsArray) {
				map.put(str.trim(), -1);
			}
	
			//reading data from excel   :: throws Exception  
			List<ArrayList<Object>> excelData = er.getExcelData(excelFilePath, map);
	
			if (excelData != null) {
				
//				String[] datatypeArr = taskVo.getDbDatatype().split(","); 
				
				String tableName = taskVo.getDbTableName();
				String[] colArray = taskVo.getDbTableColumns().split(",");
				
				String query = QueryGenerator.generateInsertQuery(tableName, colArray);

				
				log.debug(query);
				// log.info("Inserting task "+ vo.getTaskName());
				
				//inserting to database    :: throws Exception --removed
				responseVo = dao.insertData(taskVo.getConnection4db(), query, excelData);
				
				tokensVo.setRecordCount(responseVo.getInsertionCount());
				
				if( ! responseVo.isAnyException() ){
					//do post actions 
					doPostActions(taskVo);
					
					MailManager.send( taskVo.getMailTo(), 
									  taskVo.getMailTo(), 
									  taskVo.getMailCc(),
									  taskVo.getMailBcc(), 
									  taskVo.getMailSubject(),
									  TextFormatter.formatText(taskVo.getMailSuccessBody(), tokensVo )
									  );
					
				}else{
					log.info("Post-action skipped. Sending error mail.");
					
					throw responseVo.getExceptionObj(); 
					
				}
	
	
				log.info("done inserting one task.");
				
			}else{
				log.info(excelData + " :excelData");
			}
		
		}catch(Exception e){
			
			tokensVo.setExceptionTrace(ExceptionFormatter.getExceptionTrace( e ));
			
			MailManager.send( taskVo.getMailTo(), 
					  taskVo.getMailTo(), 
					  taskVo.getMailCc(),
					  taskVo.getMailBcc(), 
					  "***ERROR*** "+taskVo.getMailSubject(),
					  TextFormatter.formatText(taskVo.getMailFailureBody(), tokensVo )
					  );
			
		}
	}
	
	public void doPostActions(TaskVO vo){
		String action  = vo.getActionName();
		String originalFile = vo.getExcelPath()+vo.getExcelFileName();
		
		if(action.equalsIgnoreCase(AppConstants.ACTION_DELETE)){
			WinUtils.deleteFile(originalFile);
			
		}else if(action.equalsIgnoreCase(AppConstants.ACTION_MOVE)){
			String newFilePath = vo.getActionPath() + getPreffix() + vo.getExcelFileName() ;
			WinUtils.moveFile(originalFile, newFilePath);
			
		}else if(action.equalsIgnoreCase(AppConstants.ACTION_RENAME)){
			String newFilePath = vo.getExcelPath() + getPreffix() + vo.getExcelFileName();
			WinUtils.renameFile(originalFile, newFilePath);
			
		}else{
			log.error("Action "+action+"in invalid...");
		}
		
		
	}
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd.hhmmss");
	public String getPreffix(){
		return sdf.format(new Date()) + "_" ;
	}

}
