package pg.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;


public class module implements Serializable{

	int id;
	int ware;
	String modulename;



	public module(int id, String Modulename,int ware) {
		this.id = id;
		this.modulename = Modulename;
		this.ware = ware;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	

	public int getWare() {
		return ware;
	}

	public void setWare(int ware) {
		this.ware = ware;
	}

	public String getModulename() {
		return modulename;
	}

	public void setModulename(String modulename) {
		this.modulename = modulename;
	}



}
