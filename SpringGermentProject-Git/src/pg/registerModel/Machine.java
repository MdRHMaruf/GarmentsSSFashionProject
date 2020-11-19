package pg.registerModel;

public class Machine {

	String factoryId="";
	String departmentId="";
	String lineId="";
	String machineId;
	String name;
	String brand;
	String modelNo;
	String motor;
	String employeeName;
	String employeeId;
	String UserId;
	
	public Machine() {
		
	}
	
	public Machine(String machineId, String name, String brand, String modelNo, String motor, String employeeId, String employeeName) {
		this.machineId=machineId;
		this.name=name;
		this.brand=brand;
		this.modelNo=modelNo;
		this.motor=motor;
		this.employeeId=employeeId;
		this.employeeName=employeeName;
	}
	
	public Machine(String machineId,String machineName,String employeeId,String employeeName) {
		this.machineId = machineId;
		this.name = machineName;
		this.employeeId = employeeId;
		this.employeeName = employeeName;
	}

	public String getMachineId() {
		return machineId;
	}
	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getModelNo() {
		return modelNo;
	}
	public void setModelNo(String modelNo) {
		this.modelNo = modelNo;
	}
	public String getMotor() {
		return motor;
	}
	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public void setMotor(String motor) {
		this.motor = motor;
	}

	public String getUserId() {
		return UserId;
	}
	public void setUserId(String userId) {
		UserId = userId;
	}

	public String getFactoryId() {
		return factoryId;
	}

	public void setFactoryId(String factoryId) {
		this.factoryId = factoryId;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getLineId() {
		return lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
	}
	
	
}
