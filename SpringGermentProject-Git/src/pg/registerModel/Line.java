package pg.registerModel;

public class Line {
	String lineId;
	String factoryId;
	String factoryName;
	String departmentId;
	String departmentName;
	String lineName;
	String userId;

	
	public Line() {}
	
	public Line(String lineId, String factoryId, String factoryName, String departmentId, String departmentName,
			String lineName, String userId) {
		super();
		this.lineId = lineId;
		this.factoryId = factoryId;
		this.factoryName = factoryName;
		this.departmentId = departmentId;
		this.departmentName = departmentName;
		this.lineName = lineName;
		this.userId = userId;
	}

	public Line(String LineId, String LineName) {
		this.lineId = LineId;
		this.lineName = LineName;
	}
	

	public Line(String LineId, String LineName,String DepartmentId) {
		this.lineId = LineId;
		this.lineName = LineName;
		this.departmentId=DepartmentId;
	}

	
	public String getLineId() {
		return lineId;
	}
	public void setLineId(String lineId) {
		this.lineId = lineId;
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
	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getLineName() {
		return lineName;
	}
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
	
}
