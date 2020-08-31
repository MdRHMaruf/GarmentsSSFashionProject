package pg.registerModel;

public class BuyerModel {
	String user;
	String buyername;
	String buyerid;
	String buyercode;
	String buyerAddress;
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
	
	
	public BuyerModel() {
		
	}
	
	


	public BuyerModel(String user, String buyername, String buyerid, String buyercode, String buyerAddress,
			String consigneeAddress, String notifyAddress, String country, String telephone, String mobile,
			String email, String fax, String skypeid, String bankname, String bankaddress, String swiftcode,
			String bankcountry) {
		super();
		this.user = user;
		this.buyername = buyername;
		this.buyerid = buyerid;
		this.buyercode = buyercode;
		this.buyerAddress = buyerAddress;
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

	
	public BuyerModel(String buyerid,String buyername,  String buyercode, String buyerAddress,
			String consigneeAddress, String notifyAddress, String country, String telephone, String mobile,
			String email, String fax, String skypeid, String bankname, String bankaddress, String swiftcode,
			String bankcountry) {
		super();
		
		this.buyername = buyername;
		this.buyerid = buyerid;
		this.buyercode = buyercode;
		this.buyerAddress = buyerAddress;
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



	public String getBuyername() {
		return buyername;
	}


	public void setBuyername(String buyername) {
		this.buyername = buyername;
	}


	public String getBuyerid() {
		return buyerid;
	}


	public void setBuyerid(String buyerid) {
		this.buyerid = buyerid;
	}


	public String getBuyercode() {
		return buyercode;
	}


	public void setBuyercode(String buyercode) {
		this.buyercode = buyercode;
	}


	public String getBuyerAddress() {
		return buyerAddress;
	}


	public void setBuyerAddress(String buyerAddress) {
		this.buyerAddress = buyerAddress;
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


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getUser() {
		return user;
	}


	public void setUser(String user) {
		this.user = user;
	}
	
	
	

}
