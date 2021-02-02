package pg.model;

import java.io.Serializable;

public class Login implements Serializable{
	String user;
	int id;
	int type;
	int ware;
	String factoryId;
	String departmentId;
	String pass;
	String fullName;
	
	
	public Login(int id,int type,String factoryId,String departmentId,String FullName,String user,String pass) {
		this.user = user;
		this.id = id;
		this.type = type;
		this.factoryId = factoryId;
		this.departmentId = departmentId;
		this.pass = pass;
		this.fullName=FullName;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getWare() {
		return ware;
	}
	public void setWare(int ware) {
		this.ware = ware;
	}
	public String getFactoryId() {
		return factoryId;
	}
	public void setFactoryId(String factoryId) {
		this.factoryId = factoryId;
	}
	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	
	
	
}
