package pg.registerModel;

public class Unit {
	String unitId;
	String unitName;
	String unitValue;
	String userId;
	
	public Unit() {}
	public Unit(String unitId, String unitName, String unitValue, String userId) {
		super();
		this.unitId = unitId;
		this.unitName = unitName;
		this.unitValue = unitValue;
		this.userId = userId;
	}
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getUnitValue() {
		return unitValue;
	}
	public void setUnitValue(String unitValue) {
		this.unitValue = unitValue;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
