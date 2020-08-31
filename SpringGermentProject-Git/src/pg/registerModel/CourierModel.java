package pg.registerModel;

public class CourierModel {
	
	String user;
	String couriername;
	String courierid;
	String couriercode;
	String courierAddress;
	String consigneeAddress;
	String notifyAddress;
	String country;
	String telephone;
	String mobile;
	String email;
	String fax;
	String skypeid;
	String bankname;
	String bankaddress;
	String swiftcode;
	String bankcountry;
	
	
	public CourierModel() {
		
	}
	
	
	
	
	
	public CourierModel( String courierid,String couriername, String couriercode, String courierAddress,
			String consigneeAddress, String notifyAddress, String country, String telephone, String mobile,
			String email, String fax, String skypeid, String bankname, String bankaddress, String swiftcode,
			String bankcountry) {
		super();
		this.couriername = couriername;
		this.courierid = courierid;
		this.couriercode = couriercode;
		this.courierAddress = courierAddress;
		this.consigneeAddress = consigneeAddress;
		this.notifyAddress = notifyAddress;
		this.country = country;
		this.telephone = telephone;
		this.mobile = mobile;
		this.email = email;
		this.fax = fax;
		this.skypeid = skypeid;
		this.bankname = bankname;
		this.bankaddress = bankaddress;
		this.swiftcode = swiftcode;
		this.bankcountry = bankcountry;
	}





	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getCouriername() {
		return couriername;
	}
	public void setCouriername(String couriername) {
		this.couriername = couriername;
	}
	public String getCourierid() {
		return courierid;
	}
	public void setCourierid(String courierid) {
		this.courierid = courierid;
	}
	public String getCouriercode() {
		return couriercode;
	}
	public void setCouriercode(String couriercode) {
		this.couriercode = couriercode;
	}
	public String getCourierAddress() {
		return courierAddress;
	}
	public void setCourierAddress(String courierAddress) {
		this.courierAddress = courierAddress;
	}
	public String getConsigneeAddress() {
		return consigneeAddress;
	}
	public void setConsigneeAddress(String consigneeAddress) {
		this.consigneeAddress = consigneeAddress;
	}
	public String getNotifyAddress() {
		return notifyAddress;
	}
	public void setNotifyAddress(String notifyAddress) {
		this.notifyAddress = notifyAddress;
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
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getSkypeid() {
		return skypeid;
	}
	public void setSkypeid(String skypeid) {
		this.skypeid = skypeid;
	}
	public String getBankname() {
		return bankname;
	}
	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
	public String getBankaddress() {
		return bankaddress;
	}
	public void setBankaddress(String bankaddress) {
		this.bankaddress = bankaddress;
	}
	public String getSwiftcode() {
		return swiftcode;
	}
	public void setSwiftcode(String swiftcode) {
		this.swiftcode = swiftcode;
	}
	public String getBankcountry() {
		return bankcountry;
	}
	public void setBankcountry(String bankcountry) {
		this.bankcountry = bankcountry;
	}
	
	

}
