package pg.dao;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import noticeModel.noticeModel;
import pg.OrganizationModel.OrganizationInfo;
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
import pg.model.RoleManagement;

public interface SettingDAO {


	
	//User,Module,Menu,Submenu
	public boolean  addUser(Password pass);
	public boolean  addModule(ModuleInfo m);
	public boolean  addMenu(MenuInfo m);
	public boolean  addSubMenu(SubMenuInfo m);
	public boolean  addWare(Ware ware);

	
	public List<Module> getAllModuleName();
	public List<Menu> getAllModuleWiseMenu(int i);
	public List<ModuleWiseMenu> getAllModuleWiseMenu();
	public List<ModuleWiseMenuSubMenu> getAllModuleWiseSubmenu();
	public List<ModuleWiseMenuSubMenu> getAllModuleWiseMenuSubMenuName(int i,String menulist);
	public List<WareInfo> getAllWareName();
	
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
	
	public JSONArray getDepartmentWiseUserList(String departmentIds);
	public JSONArray getNotificationList(String targetId);
	public String notificationTargetAdd(JSONObject notificationObject,JSONArray targetList);
	public String notificationSeen(String targetId);
	

	public JSONArray getFormPermitInvoiceList(String formId,String ownerId,String permittedUserId);
	public JSONArray getFormPermitedUsers(String formId,String ownerId);
	public String submitFileAccessPermit(String fileAccessPermit);

	public JSONArray getMenus(String userId);
	public List<RoleManagement> getSubmenu(String moduleId);
	public boolean saveRolePermission(RoleManagement v);
	public List<RoleManagement> getAllRoleName();
	public List<RoleManagement> getAllPermissions(String id);
	public boolean editRolePermission(RoleManagement v);

}