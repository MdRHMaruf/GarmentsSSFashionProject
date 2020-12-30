package pg.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pg.OrganizationModel.OrganizationInfo;
import pg.dao.SettingDAO;
import pg.dao.SettingDAOImpl;
import pg.exception.UserBlockedException;

import pg.model.Menu;
import pg.model.MenuInfo;
import pg.model.Module;
import pg.model.ModuleInfo;
import pg.model.ModuleWiseMenu;
import pg.model.ModuleWiseMenuSubMenu;
import pg.model.Password;

import pg.model.SubMenuInfo;
import pg.model.Ware;
import pg.model.WareInfo;

@Service
public class SettingServiceImpl implements SettingService{

	@Autowired
	private SettingDAOImpl settingDAO=new SettingDAOImpl();
	
	@Transactional
	public List<Module> getAllModuleName() {
		// TODO Auto-generated method stub
		return settingDAO.getAllModuleName();
	}
	
	
	@Transactional
	public List<Menu> getAllModuleWiseMenu(int i) {
		// TODO Auto-generated method stub
		return settingDAO.getAllModuleWiseMenu(i);
	}
	
	@Transactional
	public List<ModuleWiseMenu> getAllModuleWiseMenu() {
		// TODO Auto-generated method stub
		return settingDAO.getAllModuleWiseMenu();
	}


	@Autowired
	private SettingDAO settDAO;

	@Transactional
	public boolean  addWare(Ware ware) throws UserBlockedException{
		return settDAO.addWare(ware);
	}


	@Override
	public List<ModuleWiseMenuSubMenu> getAllModuleWiseMenuSubMenuName(int i, String menulist) {
		// TODO Auto-generated method stub
		return settDAO.getAllModuleWiseMenuSubMenuName(i, menulist);
	}

	@Override
	public List<ModuleWiseMenuSubMenu> getAllModuleWiseSubmenu() {
		// TODO Auto-generated method stub
		return settDAO.getAllModuleWiseSubmenu();
	}
	
	@Transactional
	public boolean  addUser(Password pass) throws UserBlockedException{
		return settDAO.addUser(pass);
	}

	@Transactional
	public boolean  addModule(ModuleInfo m) throws UserBlockedException{
		return settDAO.addModule(m);
	}

	@Transactional
	public boolean  addMenu(MenuInfo m) throws UserBlockedException{
		return settDAO.addMenu(m);
	}
	
	@Transactional
	public boolean  addSubMenu(SubMenuInfo m) throws UserBlockedException{
		return settDAO.addSubMenu(m);
	}


	@Override
	public List<WareInfo> getAllWareName() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<OrganizationInfo> getOrganization() {
		// TODO Auto-generated method stub
		return settDAO.getOrganization();
	}


	@Override
	public boolean editOrganization(OrganizationInfo v) {
		// TODO Auto-generated method stub
		return settDAO.editOrganization(v);
	}
	

}