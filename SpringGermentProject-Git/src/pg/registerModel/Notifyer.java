package pg.registerModel;

public class Notifyer {
	String id;
	String buyerId;
	String name;
	String address;
	String country;
	String telephone;
	String email;
	String userId;
	public Notifyer() {
		super();
	}
	
	
	public Notifyer(String id, String buyerId, String name, String address, String country, String telephone,
			String email, String userId) {
		super();
		this.id = id;
		this.buyerId = buyerId;
		this.name = name;
		this.address = address;
		this.country = country;
		this.telephone = telephone;
		this.email = email;
		this.userId = userId;
	}


	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
