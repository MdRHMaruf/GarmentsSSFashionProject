package pg.registerModel;

public class Factory {
	String factoryId;
	String factoryName;
	String telePhone;
	String mobile;
	String fax;
	String email;
	String skypeId;
	String address;
	String bankName;
	String bankAddress;
	String swiftCode;
	String bankCountry;
	String accountsName;
	String accountsNo;
	String bondLicense;
	String entryTime;
	String userId;
	public Factory() {}
	public Factory(String factoryId,String factoryName){
		this.factoryId = factoryId;
		this.factoryName = factoryName;
	}
	
	public Factory(String factoryId, String factoryName, String telePhone, String mobile, String fax, String email,
			String skypeId, String address, String bankName, String bankAddress, String swiftCode, String bankCountry,
			String accountsName, String accountsNo, String bondLicense, String entryTime, String userId) {
		super();
		this.factoryId = factoryId;
		this.factoryName = factoryName;
		this.telePhone = telePhone;
		this.mobile = mobile;
		this.fax = fax;
		this.email = email;
		this.skypeId = skypeId;
		this.address = address;
		this.bankName = bankName;
		this.bankAddress = bankAddress;
		this.swiftCode = swiftCode;
		this.bankCountry = bankCountry;
		this.accountsName = accountsName;
		this.accountsNo = accountsNo;
		this.bondLicense = bondLicense;
		this.entryTime = entryTime;
		this.userId = userId;
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
	public String getTelePhone() {
		return telePhone;
	}
	public void setTelePhone(String telePhone) {
		this.telePhone = telePhone;
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
	public String getSkypeId() {
		return skypeId;
	}
	public void setSkypeId(String skypeId) {
		this.skypeId = skypeId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankAddress() {
		return bankAddress;
	}
	public void setBankAddress(String bankAddress) {
		this.bankAddress = bankAddress;
	}
	public String getSwiftCode() {
		return swiftCode;
	}
	public void setSwiftCode(String swiftCode) {
		this.swiftCode = swiftCode;
	}
	public String getBankCountry() {
		return bankCountry;
	}
	public void setBankCountry(String bankCountry) {
		this.bankCountry = bankCountry;
	}
	public String getAccountsName() {
		return accountsName;
	}
	public void setAccountsName(String accountsName) {
		this.accountsName = accountsName;
	}
	public String getAccountsNo() {
		return accountsNo;
	}
	public void setAccountsNo(String accountsNo) {
		this.accountsNo = accountsNo;
	}
	public String getBondLicense() {
		return bondLicense;
	}
	public void setBondLicense(String bondLicense) {
		this.bondLicense = bondLicense;
	}
	public String getEntryTime() {
		return entryTime;
	}
	public void setEntryTime(String entryTime) {
		this.entryTime = entryTime;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
