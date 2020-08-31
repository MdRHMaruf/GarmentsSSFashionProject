package pg.registerModel;

public class WareHouse {
	
	String wareHouseId;
	String factoryId;
	String factoryName;
	String wareHouseName;
	String userId;
	
	public WareHouse() {}
	public WareHouse(String wareHouseId, String factoryId, String factoryName, String wareHouseName, String userId) {
		super();
		this.wareHouseId = wareHouseId;
		this.factoryId = factoryId;
		this.factoryName = factoryName;
		this.wareHouseName = wareHouseName;
		this.userId = userId;
	}
	public String getWareHouseId() {
		return wareHouseId;
	}
	public void setWareHouseId(String wareHouseId) {
		this.wareHouseId = wareHouseId;
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
	public String getWareHouseName() {
		return wareHouseName;
	}
	public void setWareHouseName(String wareHouseName) {
		this.wareHouseName = wareHouseName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
