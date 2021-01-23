package pg.share;

public class SessionBean {

	long sessionValue;
	String userId;
	String userName;
	String passWord;
	
	public SessionBean() {
		
	}
	
	public SessionBean(long SessionValue,String UserId,String Username,String Password) {
		this.sessionValue=SessionValue;
		this.userId=UserId;
		this.userName=Username;
		this.passWord=Password;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public long getSessionValue() {
		return sessionValue;
	}

	public void setSessionValue(long sessionValue) {
		this.sessionValue = sessionValue;
	}
	
	
}
