package pg.registerModel;

public class Designation {
	
	String id;
	String designationId;
	String departmentId;
	String departmentName;
	String designation;
	String UserId;

	public Designation() {

	}

	public Designation(String departmentId, String departmentName, String designationId, String designation) {
		
		this.departmentId=departmentId;
		this.departmentName=departmentName;
		this.designationId=designationId;
		this.designation=designation;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDesignationId() {
		return designationId;
	}
	public void setDesignationId(String designationId) {
		this.designationId = designationId;
	}
	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getUserId() {
		return UserId;
	}
	public void setUserId(String userId) {
		UserId = userId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}


}
