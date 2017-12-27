package com.app.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.app.commons.ResponseVO;

public class InsertDataDAO {
	
	private final Logger log = Logger.getLogger(getClass().getName());
	private ResponseVO responseVO;
	
	public ResponseVO insertData(String Connection4db, String sql, List<ArrayList<Object>> listOfRows) {
		responseVO = new ResponseVO();
		Connection conn = null;
		PreparedStatement pstmt = null;
		int recordInserted = 0;
//		int totalRecord = listOfRows.size(); 
		
		try {
			conn = ConnectionMgr.getConnectionForDB(Connection4db);
			System.out.println("connection = "+ conn);
			pstmt = conn.prepareStatement(sql);

			for (ArrayList<Object> row : listOfRows) {

				pstmt.clearParameters();
				
				Object obj=null;
				java.sql.Timestamp sqlDate = null;
				
				for (int i = 1; i <= row.size(); i++) {
//					log.debug(i+" param in query "+ row.get(i-1));
//					pstmt.setString(i, row.get(i - 1));
					
					obj = row.get(i - 1);
					
					if(obj instanceof String){
						pstmt.setString(i, (String)obj );
						
					}else if(obj instanceof java.util.Date){
						sqlDate = new java.sql.Timestamp( ((java.util.Date)obj).getTime() );
						pstmt.setTimestamp(i, sqlDate);
						
					}else if(obj instanceof Integer){
						pstmt.setInt(i, (Integer)obj);
						
					}else if(obj instanceof Double){
						pstmt.setDouble(i, (Double)obj);
						
					}else{
						pstmt.setString(i, obj.toString() );
						log.error("Could not found correct datatype while inserting. inserting object as string");
						log.error("object:- "+obj);
					}
					
				}

				int result = pstmt.executeUpdate();
				
				if(result>0)
					recordInserted++;

				log.debug(result + " record inserted.");
				
			}
			
			responseVO.setInsertionCount(recordInserted);
			log.info("Total "+recordInserted + " record inserted.");
			
		} catch (Exception e) {
			log.error("Exception while inserting data. Exception "+ e.getMessage());

			if(log.isDebugEnabled()){
				for(StackTraceElement st : e.getStackTrace()){
					log.debug(st.toString());
				}
			}
			
			responseVO.setAnyException(true);
			responseVO.setExceptionObj(e);
			responseVO.setInsertionCount(recordInserted);
			
//			throw e;

		} finally {
			ConnectionMgr.closeConnection(null, pstmt, conn);
			
		}
		
//		return "Number of success record = "+recordInserted +" out of "+tatalRecord;
		return responseVO;
	}

}
