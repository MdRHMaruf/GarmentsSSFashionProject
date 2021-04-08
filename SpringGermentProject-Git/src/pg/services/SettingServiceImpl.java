package pg.services;

import java.util.List;

import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import noticeModel.noticeModel;
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
import pg.model.roleManagement;

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


	@Override
	public boolean savenotice(String heading, String departs, String textbody, String filename,String userid) {
		// TODO Auto-generated method stub
		return settDAO.savenotice(heading, departs, textbody, filename, userid);
	}


	public int getMaxNoticeNo() {
		// TODO Auto-generated method stub
		return settDAO.getMaxNoticeNo();
	}


	@Override
	public List<noticeModel> getAllNoitice(String deptid,noticeModel nm) {
		// TODO Auto-generated method stub
		return settDAO.getAllNoitice( deptid, nm);
	}


	@Override
	public List<noticeModel> getAllnoticesforSearch() {
		// TODO Auto-generated method stub
		return settDAO.getAllnoticesforSearch();
	}


	@Override
	public JSONArray getNotificationList(String targetId) {
		// TODO Auto-generated method stub
		return settDAO.getNotificationList(targetId);
	}


	@Override
	public JSONArray getUserList() {
		// TODO Auto-generated method stub
		return settDAO.getUserList();
	}


	@Override
	public List<roleManagement> getSubmenu(String moduleId) {
		// TODO Auto-generated method stub
		return settDAO.getSubmenu(moduleId);
	}


	@Override
	public boolean saveRolePermission(roleManagement v) {
		// TODO Auto-generated method stub
		return settDAO.saveRolePermission(v);
	}


	@Override
	public List<roleManagement> getAllRoleName(roleManagement v) {
		// TODO Auto-generated method stub
		return settDAO.getAllRoleName(v);
	}


	@Override
	public List<roleManagement> getAllPermissions(String id) {
		// TODO Auto-generated method stub
		return settDAO.getAllPermissions(id);
	}


	@Override
	public boolean editRolePermission(roleManagement v) {
		// TODO Auto-generated method stub
		return settDAO.editRolePermission(v);
	}
	
}