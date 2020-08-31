package pg.registerModel;

public class Country {
	String countryId;
	String countryName;
	String userId;
	
	public Country() {}
	
	public Country(String countryId, String countryName, String userId) {
		super();
		this.countryId = countryId;
		this.countryName = countryName;
		this.userId = userId;
	}
	public String getCountryId() {
		return countryId;
	}
	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
