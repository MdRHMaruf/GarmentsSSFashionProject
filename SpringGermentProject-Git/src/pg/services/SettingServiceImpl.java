package pg.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pg.OrganizationModel.OrganizationInfo;
import pg.dao.SettingDAO;
import pg.dao.SettingDAOImpl;
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

@Service
public class SettingServiceImpl implements SettingService{

	@Autowired
	private SettingDAOImpl settingDAO=new SettingDAOImpl();
	
	
	@Transactional
	public List<module> getAllModuleName() {
		// TODO Auto-generated method stub
		return settingDAO.getAllModuleName();
	}
	
	
	@Transactional
	public List<menu> getAllModuleWiseMenu(int i) {
		// TODO Auto-generated method stub
		return settingDAO.getAllModuleWiseMenu(i);
	}
	
	@Transactional
	public List<modulewisemenu> getAllModuleWiseMenu() {
		// TODO Auto-generated method stub
		return settingDAO.getAllModuleWiseMenu();
	}


	@Autowired
	private SettingDAO settDAO;

	@Transactional
	public boolean  addWare(ware ware) throws UserBlockedException{
		return settDAO.addWare(ware);
	}


	@Override
	public List<modulewisemenusubmenu> getAllModuleWiseMenuSubMenuName(int i, String menulist) {
		// TODO Auto-generated method stub
		return settDAO.getAllModuleWiseMenuSubMenuName(i, menulist);
	}

	@Override
	public List<modulewisemenusubmenu> getAllModuleWiseSubmenu() {
		// TODO Auto-generated method stub
		return settDAO.getAllModuleWiseSubmenu();
	}
	
	@Transactional
	public boolean  addUser(password pass) throws UserBlockedException{
		return settDAO.addUser(pass);
	}

	@Transactional
	public boolean  addModule(moduleinfo m) throws UserBlockedException{
		return settDAO.addModule(m);
	}

	@Transactional
	public boolean  addMenu(menuinfo m) throws UserBlockedException{
		return settDAO.addMenu(m);
	}
	
	@Transactional
	public boolean  addSubMenu(submenuinfo m) throws UserBlockedException{
		return settDAO.addSubMenu(m);
	}


	@Override
	public List<wareinfo> getAllWareName() {
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
