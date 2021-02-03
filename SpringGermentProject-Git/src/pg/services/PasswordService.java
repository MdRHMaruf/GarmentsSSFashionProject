/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pg.services;

import java.util.List;

import noticeModel.noticeModel;
import pg.OrganizationModel.OrganizationInfo;
import pg.exception.UserBlockedException;
import pg.model.Login;
import pg.model.Menu;
import pg.model.Module;
import pg.model.Password;
import pg.model.WareInfo;

public interface PasswordService {
	public List<Login> login(String loginName,String password) throws UserBlockedException;
	public List<WareInfo> getAllStoreName();
	public List<Module> getAllModuleName();
	public List<Menu> getAllMenuName();

	public List<Module> getUserModule(int i);
	public List<Menu> getUserMenu(int i,int moduleId);
	public List<Menu> getAdminUserMenu(int i,int moduleId);
	
	public List<OrganizationInfo> getOrganizationInfo();
	public boolean changePassword(String userId, String userName, String password);
	public String getUserDepartmentId(String userId);
	
	public List<noticeModel> getNotice(String depid,noticeModel nm);
}
