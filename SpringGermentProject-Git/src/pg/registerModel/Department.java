package pg.registerModel;

public class Department {
	
	String departmentId;
	String factoryId;
	String factoryName;
	String departmentName;
	String userId;
	
	public Department() {}
	public Department(String departmentId, String factoryId, String factoryName, String departmentName, String userId) {
		super();
		this.departmentId = departmentId;
		this.factoryId = factoryId;
		this.factoryName = factoryName;
		this.departmentName = departmentName;
		this.userId = userId;
	}
	
	public Department(String DepartmentId, String DepartmentName) {
		this.departmentId = DepartmentId;
		this.departmentName = DepartmentName;
	}
	
	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	public String getFactoryId() {
		return factoryId;
	}
	public void setFactoryId(String factoryId) {
		this.factoryId = factoryId;
	}
	public String getFactoryName() {
		return factoryName;
	}
	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
