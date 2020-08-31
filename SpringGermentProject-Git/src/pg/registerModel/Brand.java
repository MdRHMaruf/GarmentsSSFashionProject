package pg.registerModel;

public class Brand {
	String brandId;
	String brandName;
	String brandCode;
	String userId;
	
	public Brand() {}
	
	public Brand(String brandId, String brandName, String brandCode, String userId) {
		
		this.brandId = brandId;
		this.brandName = brandName;
		this.brandCode = brandCode;
		this.userId = userId;
	}
	public String getBrandId() {
		return brandId;
	}
	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getBrandCode() {
		return brandCode;
	}
	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
