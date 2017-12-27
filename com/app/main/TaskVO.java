package com.app.main;

public class TaskVO {
	private String excelPath;
	private String excelFileName;
	private String excelColumns;

//	private String dbURL;
//	private String dbUserName;
//	private String dbPassword;
	private String connection4db;
	private String dbTableName;
	private String dbTableColumns;
	
//	private String dbDatatype; 

	private String actionName;
	private String actionPath;
	private String actionSuffix;

	private String mailTo;
	private String mailCc;
	private String mailBcc;
	private String mailSubject;
	private String mailSuccessBody;
	private String mailFailureBody;

	private String taskName;
	private String taskOwner;
	private String taskStatus;
	
	
	
//	public String getDbDatatype() {
//		return dbDatatype;
//	}
//
//	public void setDbDatatype(String dbDatatype) {
//		this.dbDatatype = dbDatatype;
//	}

	public String getMailFailureBody() {
		return mailFailureBody;
	}

	public void setMailFailureBody(String mailFailureBody) {
		this.mailFailureBody = mailFailureBody;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		if(taskName != null)
			this.taskName = taskName.trim();
	}

	public String getTaskOwner() {
		return taskOwner;
	}

	public void setTaskOwner(String taskOwner) {
		if(taskOwner != null)
			this.taskOwner = taskOwner.trim();
	}

	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		if(taskStatus != null)
			this.taskStatus = taskStatus.trim();
	}

	public String getConnection4db() {
		return connection4db;
	}

	public void setConnection4db(String connection4db) {
		if(connection4db != null)
			this.connection4db = connection4db.trim();
	}

	public String getExcelPath() {
		return excelPath;
	}

	public void setExcelPath(String excelPath) {
		if(excelPath != null)
			this.excelPath = excelPath.trim();
	}

	public String getExcelFileName() {
		return excelFileName;
	}

	public void setExcelFileName(String excelFileName) {
		if(excelFileName != null)
			this.excelFileName = excelFileName.trim();
	}

	public String getExcelColumns() {
		return excelColumns;
	}

	public void setExcelColumns(String excelColumns) {
		if(excelColumns != null)
			this.excelColumns = excelColumns.trim();
	}

	public String getDbTableName() {
		return dbTableName;
	}

	public void setDbTableName(String dbTableName) {
		if(dbTableName != null)
			this.dbTableName = dbTableName.trim();
	}

	public String getDbTableColumns() {
		return dbTableColumns;
	}

	public void setDbTableColumns(String dbTableColumns) {
		if(dbTableColumns != null)
			this.dbTableColumns = dbTableColumns.trim();
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		if(actionName != null)
			this.actionName = actionName.trim();
	}

	public String getActionPath() {
		return actionPath;
	}

	public void setActionPath(String actionPath) {
		if(actionPath != null)
			this.actionPath = actionPath.trim();
	}

	public String getActionSuffix() {
		return actionSuffix;
	}

	public void setActionSuffix(String actionSuffix) {
		if(actionSuffix != null)
			this.actionSuffix = actionSuffix.trim();
	}

	public String getMailTo() {
		return mailTo;
	}

	public void setMailTo(String mailTo) {
		if(mailTo != null)
			this.mailTo = mailTo.trim();
	}

	public String getMailCc() {
		return mailCc;
	}

	public void setMailCc(String mailCc) {
		if(mailCc != null)
			this.mailCc = mailCc.trim();
	}

	public String getMailBcc() {
		return mailBcc;
	}

	public void setMailBcc(String mailBcc) {
		if(mailBcc != null)
			this.mailBcc = mailBcc.trim();
	}

	public String getMailSubject() {
		return mailSubject;
	}

	public void setMailSubject(String mailSubject) {
		if(mailSubject != null)
			this.mailSubject = mailSubject.trim();
	}

	public String getMailSuccessBody() {
		return mailSuccessBody;
	}

	public void setMailSuccessBody(String mailBody) {
		if(mailBody != null)
			this.mailSuccessBody = mailBody.trim();
	}

	@Override
	public String toString() {
		return "TaskVO [excelPath=" + excelPath + ", excelFileName="
				+ excelFileName + ", excelColumns=" + excelColumns
				+ ", connection4db=" + connection4db + ", dbTableName="
				+ dbTableName + ", dbTableColumns=" + dbTableColumns
				+ ", actionName=" + actionName
				+ ", actionPath=" + actionPath + ", actionSuffix="
				+ actionSuffix + ", mailTo=" + mailTo + ", mailCc=" + mailCc
				+ ", mailBcc=" + mailBcc + ", mailSubject=" + mailSubject
				+ ", mailSuccessBody=" + mailSuccessBody + ", mailFailureBody="
				+ mailFailureBody + ", taskName=" + taskName + ", taskOwner="
				+ taskOwner + ", taskStatus=" + taskStatus + "]";
	}

}
