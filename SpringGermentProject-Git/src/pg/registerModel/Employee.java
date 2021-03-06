package pg.registerModel;

public class Employee {

	String employeeCode;
	String employeeName;
	String cardNo;
	String designation;
	String department;
	
	String designationId;
	String departmentId;
	
	String line;
	String grade;
	String joinDate;
	String UserId;
	String employeeId="";
	
	public Employee() {
		
	}
	
	public Employee(String AutoId,String employeeCode, String employeeName, String cardNo, String departmentId, String department, String designationId, String designation, String line, String grade, String joinDate) {
		super();
		this.employeeId=AutoId;
		this.employeeCode = employeeCode;
		this.employeeName = employeeName;
		this.designation = designation;
		this.department = department;
		this.departmentId=departmentId;
		this.designationId=designationId;
		this.line=line;
		this.cardNo=cardNo;
		this.grade=grade;
		this.joinDate=joinDate;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
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

	public String getUserId() {
		return UserId;
	}

	public void setUserId(String userId) {
		UserId = userId;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	
}
