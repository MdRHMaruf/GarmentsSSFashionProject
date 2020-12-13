/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pg.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pg.OrganizationModel.OrganizationInfo;
import pg.dao.PasswordDAO;
import pg.exception.UserBlockedException;
import pg.model.login;
import pg.model.menu;
import pg.model.module;
import pg.model.password;
import pg.model.wareinfo;

@Service
public class PasswordServiceImpl implements PasswordService {
	@Autowired
	private PasswordDAO passDAO;

	@Transactional
	public List<login> login(String username, String password) throws UserBlockedException {
		
		return passDAO.login(username,password);

	}


	@Transactional
	public List<wareinfo> getAllStoreName() {
		// TODO Auto-generated method stub
		return passDAO.getAllStoreName();
	}
	
	@Transactional
	public List<module> getAllModuleName() {
		// TODO Auto-generated method stub
		return passDAO.getAllModuleName();
	}
	
	@Transactional
	public List<menu> getAllMenuName() {
		// TODO Auto-generated method stub
		return passDAO.getAllMenuName();
	}


	
	@Transactional
	public List<module> getUserModule(int i) {
		// TODO Auto-generated method stub
		return passDAO.getUserModule(i);
	}
	
	@Transactional
	public List<menu> getUserMenu(int i,int moduleId) {

		return passDAO.getUserMenu(i,moduleId);
	}


	@Override
	public List<menu> getAdminUserMenu(int i, int moduleId) {
		// TODO Auto-generated method stub
		return passDAO.getAdminUserMenu(i, moduleId);
	}


	@Override
	public boolean changePassword(String userId, String userName, String password) {
		// TODO Auto-generated method stub
		return passDAO.changePassword(userId, userName, password);
	}


	@Override
	public List<OrganizationInfo> getOrganizationInfo() {
		// TODO Auto-generated method stub
		return passDAO.getOrganizationInfo();
	}
}
