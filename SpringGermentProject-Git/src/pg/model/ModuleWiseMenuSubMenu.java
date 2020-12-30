package pg.model;

import java.io.Serializable;

public class ModuleWiseMenuSubMenu implements Serializable{
	int moduleid;
	int menuid;
	int submenuid;
	String menu;
	String module;
	String submenu;
	
	String links;
	
	public ModuleWiseMenuSubMenu(int moduleid,int menuid, int submenuid,String module, String menu, String SubMenu,String links) {
		
		this.moduleid = moduleid;
		this.menuid = menuid;
		this.submenuid = submenuid;
		this.module = module;
		this.menu = menu;
		this.submenu = SubMenu;
		this.links = links;
	}
	
	
	public int getModuleid() {
		return moduleid;
	}


	public void setModuleid(int moduleid) {
		this.moduleid = moduleid;
	}


	public int getMenuid() {
		return menuid;
	}
	public void setMenuid(int menuid) {
		this.menuid = menuid;
	}
	public int getSubmenuid() {
		return submenuid;
	}
	public void setSubmenuid(int submenuid) {
		this.submenuid = submenuid;
	}
	public String getMenu() {
		return menu;
	}
	public void setMenu(String menu) {
		this.menu = menu;
	}
	public String getSubMenu() {
		return submenu;
	}
	public void setSubMenu(String SubMenu) {
		this.submenu = SubMenu;
	}


	public String getModule() {
		return module;
	}


	public void setModule(String module) {
		this.module = module;
	}


	public String getSubmenu() {
		return submenu;
	}


	public void setSubmenu(String submenu) {
		this.submenu = submenu;
	}


	public String getLinks() {
		return links;
	}


	public void setLinks(String links) {
		this.links = links;
	}
	
	
}
