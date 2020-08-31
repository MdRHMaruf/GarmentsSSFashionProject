package pg.registerModel;

public class InchargeInfo {
	String inchargeId="";
	String factoryId="";
	String depId="";
	String name="";
	String telephone="";
	String mobile="";
	String fax="";
	String email="";
	String skype="";
	String userId="";
	String address="";
	String sl="";
	
	public InchargeInfo() {
		
	}
	
	public InchargeInfo(String Sl,String InchargeId, String InchargeName,String FactoryId,String DepId, String TelePhone,String Mobile,String Fax,String Email,String SkypeId,String Address) {
		this.sl=Sl;
		this.inchargeId=InchargeId;
		this.name=InchargeName;
		this.factoryId=FactoryId;
		this.depId=DepId;
		this.telephone=TelePhone;
		this.mobile=Mobile;
		this.fax=Fax;
		this.email=Email;
		this.skype=SkypeId;
		this.address=Address;
		
	}

	public String getInchargeId() {
		return inchargeId;
	}

	public void setInchargeId(String inchargeId) {
		this.inchargeId = inchargeId;
	}

	public String getFactoryId() {
		return factoryId;
	}

	public void setFactoryId(String factoryId) {
		this.factoryId = factoryId;
	}

	public String getDepId() {
		return depId;
	}

	public void setDepId(String depId) {
		this.depId = depId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSkype() {
		return skype;
	}

	public void setSkype(String skype) {
		this.skype = skype;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSl() {
		return sl;
	}

	public void setSl(String sl) {
		this.sl = sl;
	}
	
	
	
}
