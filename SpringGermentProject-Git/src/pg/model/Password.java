/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pg.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


public class Password {
	
	int id;
	String user;
	String password;
	String userId="";
	int ware;
	int type;
    int active;
    String by;
    int status;
    String session;
    String factoryId;
    String departmentId;
    String fullName;
    
    String accesslist;
    
    String selectedItemsModule;
    String selectedItemsWare;
    
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getWare() {
		return ware;
	}
	public void setWare(int ware) {
		this.ware = ware;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}
	public String getBy() {
		return by;
	}
	public void setBy(String by) {
		this.by = by;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getSession() {
		return session;
	}
	public void setSession(String session) {
		this.session = session;
	}
	public String getAccesslist() {
		return accesslist;
	}
	public void setAccesslist(String accesslist) {
		this.accesslist = accesslist;
	}
	public String getSelectedItemsModule() {
		return selectedItemsModule;
	}
	public void setSelectedItemsModule(String selectedItemsModule) {
		this.selectedItemsModule = selectedItemsModule;
	}
	
	public String getSelectedItemsWare() {
		return selectedItemsWare;
	}
	public void setSelectedItemsWare(String selectedItemsWare) {
		this.selectedItemsWare = selectedItemsWare;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
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
