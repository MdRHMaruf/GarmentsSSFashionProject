package pg.dao;

import java.util.List;

import pg.OrganizationModel.OrganizationInfo;
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

public interface SettingDAO {


	
	//User,Module,Menu,Submenu
	public boolean  addUser(password pass);
	public boolean  addModule(moduleinfo m);
	public boolean  addMenu(menuinfo m);
	public boolean  addSubMenu(submenuinfo m);
	public boolean  addWare(ware ware);

	
	public List<module> getAllModuleName();
	public List<menu> getAllModuleWiseMenu(int i);
	public List<modulewisemenu> getAllModuleWiseMenu();
	public List<modulewisemenusubmenu> getAllModuleWiseSubmenu();
	public List<modulewisemenusubmenu> getAllModuleWiseMenuSubMenuName(int i,String menulist);
	public List<wareinfo> getAllWareName();
	
	public List<OrganizationInfo> getOrganization();
	public boolean editOrganization(OrganizationInfo v);
	
}
