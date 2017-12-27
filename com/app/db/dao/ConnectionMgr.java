package com.app.db.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import com.app.commons.AppConstants;

public class ConnectionMgr { 
	
	public static Connection getConnectionForDB(String connectionType) throws Exception{
		
		if(connectionType.equals(AppConstants.aa)){
			return openConnection(AppConstants.aa, AppConstants.aa, AppConstants.aa);
		}
		if(connectionType.equals(AppConstants.bb)){
			return openConnection(AppConstants.bb, 
					AppConstants.bb, AppConstants.bb);
		}
		if(connectionType.equals(AppConstants.cc)){
			return openConnection(AppConstants.cc, AppConstants.cc, AppConstants.cc);
		}
		
		if(connectionType.equals(AppConstants.dd)){
			return openConnection(AppConstants.dd, 
					AppConstants.dd, AppConstants.dd);
		}
		
		System.out.println("Dont have connection for connectionType= "+connectionType);
		return null;
	}
	
	private static Connection openConnection(String url, String usr, String pass) throws Exception {
		Connection connection = null;
		Class.forName("oracle.jdbc.driver.OracleDriver");
		connection = DriverManager.getConnection(url, usr, pass);
		System.out.println("connected to DB");
		return connection;
	}
	
	public static void closeConnection(ResultSet rs, Statement pstmt, Connection conn){
		try	{
			if (rs != null)	{
				rs.close();
			}
			if (pstmt != null)	{
				pstmt.close();
			}
			if (null != conn)	{
				System.out.println("Connection closed.");
				conn.close();
			}
		} catch (Exception e)	{
			e.printStackTrace();
		}
	}
}
