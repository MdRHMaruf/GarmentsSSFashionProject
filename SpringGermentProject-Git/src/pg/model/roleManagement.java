package pg.model;

public class roleManagement {

	int subId;
	int head;
	String subName;
	String roleId;
	String roleName;
	String userId;
	String accesslist;
	String moduleId;
	String moduleName;
	String entry;
	String edit;
	String del;
	String view;
	
	public roleManagement() {
		
	}

	public roleManagement(String roleId, String roleName) {
		this.roleId = roleId;
		this.roleName = roleName;
	}

	public roleManagement(String moduleId, String moduleName, int head,int subId, String subName) {
		this.moduleId = moduleId;
		this.moduleName = moduleName;
		this.head = head;
		this.subId = subId;
		this.subName = subName;
	}

	public roleManagement(String moduleId, int head, int subId, String del, String entry, String edit, String view) {
		this.subId = subId;
		this.head = head;
		this.moduleId = moduleId;
		this.entry = entry;
		this.edit = edit;
		this.del = del;
		this.view = view;
	}

	public int getSubId() {
		return subId;
	}

	public void setSubId(int subId) {
		this.subId = subId;
	}

	public String getSubName() {
		return subName;
	}

	public void setSubName(String subName) {
		this.subName = subName;
	}

	public int getHead() {
		return head;
	}

	public void setHead(int head) {
		this.head = head;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAccesslist() {
		return accesslist;
	}

	public void setAccesslist(String accesslist) {
		this.accesslist = accesslist;
	}

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getEntry() {
		return entry;
	}

	public void setEntry(String entry) {
		this.entry = entry;
	}

	public String getEdit() {
		return edit;
	}

	public void setEdit(String edit) {
		this.edit = edit;
	}

	public String getDel() {
		return del;
	}

	public void setDel(String del) {
		this.del = del;
	}

	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	
}
