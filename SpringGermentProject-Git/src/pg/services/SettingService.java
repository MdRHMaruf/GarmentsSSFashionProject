package pg.services;

import java.util.List;

import org.json.simple.JSONArray;

import noticeModel.noticeModel;
import pg.OrganizationModel.OrganizationInfo;
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

public interface SettingService{
	public boolean addWare(Ware ware) throws UserBlockedException;
	
	public List<ModuleWiseMenuSubMenu> getAllModuleWiseMenuSubMenuName(int i,String menulist);
	public List<ModuleWiseMenuSubMenu> getAllModuleWiseSubmenu();
	public List<Module> getAllModuleName();
	public List<Menu> getAllModuleWiseMenu(int i);
	public List<ModuleWiseMenu> getAllModuleWiseMenu();

	
	public List<WareInfo> getAllWareName();
	
	public boolean addUser(Password str) throws UserBlockedException;
	public boolean addModule(ModuleInfo m) throws UserBlockedException;
	public boolean addMenu(MenuInfo m) throws UserBlockedException;
	public boolean addSubMenu(SubMenuInfo m) throws UserBlockedException;
	
	public List<OrganizationInfo> getOrganization();
	public boolean editOrganization(OrganizationInfo v);
	
	
	public int getMaxNoticeNo();
	public boolean savenotice(String heading, String departs, String textbody, String filename,String userid);
	public List<noticeModel>getAllNoitice(String deptid,noticeModel nm);
	
	public List<noticeModel>getAllnoticesforSearch();
	
	public JSONArray getUserList();
	public String saveGroup(String group);
	public String editGroup(String group);
	public JSONArray getGroupList();
	public JSONArray getGroupMembers(String groupId);
	
	public JSONArray getNotificationList(String targetId);

	public JSONArray getMenus(String userId);
	
	public JSONArray getFormPermitInvoiceList(String formId,String ownerId,String permittedUserId);
	public JSONArray getFormPermitedUsers(String formId,String ownerId);
	public String submitFileAccessPermit(String fileAccessPermit);

}