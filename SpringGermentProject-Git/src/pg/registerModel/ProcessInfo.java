package pg.registerModel;

public class ProcessInfo {
	String processId="";
	String processName="";
	String userId="";
	
	public ProcessInfo() {
		
	}
	
	public ProcessInfo(String ProcessId, String ProcessName) {
		this.processId=ProcessId;
		this.processName=ProcessName;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
