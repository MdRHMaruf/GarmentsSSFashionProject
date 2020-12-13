package pg.services;

import java.util.List;

import pg.OrganizationModel.OrganizationInfo;
import pg.exception.UserBlockedException;
import pg.model.menu;
import pg.model.menuinfo;
import pg.model.module;
import pg.model.moduleinfo;
import pg.model.modulewisemenu;
import pg.model.modulewisemenusubmenu;
import pg.model.password;
import pg.model.submenuinfo;
import pg.model.ware;
import pg.model.wareinfo;

public interface SettingService{
	public boolean addWare(ware ware) throws UserBlockedException;
	
	public List<modulewisemenusubmenu> getAllModuleWiseMenuSubMenuName(int i,String menulist);
	public List<modulewisemenusubmenu> getAllModuleWiseSubmenu();
	public List<module> getAllModuleName();
	public List<menu> getAllModuleWiseMenu(int i);
	public List<modulewisemenu> getAllModuleWiseMenu();

	
	public List<wareinfo> getAllWareName();
	
	public boolean addUser(password str) throws UserBlockedException;
	public boolean addModule(moduleinfo m) throws UserBlockedException;
	public boolean addMenu(menuinfo m) throws UserBlockedException;
	public boolean addSubMenu(submenuinfo m) throws UserBlockedException;
	
	public List<OrganizationInfo> getOrganization();
	public boolean editOrganization(OrganizationInfo v);


}