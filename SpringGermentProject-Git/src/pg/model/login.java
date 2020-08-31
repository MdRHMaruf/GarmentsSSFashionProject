package pg.model;

import java.io.Serializable;

public class login implements Serializable{
	String user;
	int id;
	int type;
	int ware;
	String pass;
	
	
	
	public login(int id,int type,String user,String pass) {
		this.user = user;
		this.id = id;
		this.type = type;
		this.pass = pass;
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

	
	
	
}
