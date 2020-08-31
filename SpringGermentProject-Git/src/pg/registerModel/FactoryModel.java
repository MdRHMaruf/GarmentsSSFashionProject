package pg.registerModel;

public class FactoryModel {
	
	String user;
	String factoryname;
	String factoryid;
	String factoryaddress;
	String telephone;
	String mobile;
	String email;
	String fax;
	String skypeid;
	String bondlicense;
	String bankname;
	String bankaddress;
	String accountno;
	String swiftcode;
	String accountname;
	String bankcountry;
	
	
	public FactoryModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FactoryModel(String Factoryid, String factoryname) {
		this.factoryid=Factoryid;
		this.factoryname=factoryname;
	}
	

	public FactoryModel(String factoryid, String factoryname,  String telephone,
			String mobile, String fax, String email, String skypeid,String factoryaddress,  String bankname,
			String bankaddress, String swiftcode,String bankcountry, String accountname,String accountno,  String bondlicense) {
		super();
		this.user = user;
		this.factoryname = factoryname;
		this.factoryid = factoryid;
		this.factoryaddress = factoryaddress;
		this.telephone = telephone;
		this.mobile = mobile;
		this.email = email;
		this.fax = fax;
		this.skypeid = skypeid;
		this.bondlicense = bondlicense;
		this.bankname = bankname;
		this.bankaddress = bankaddress;
		this.accountno = accountno;
		this.swiftcode = swiftcode;
		this.accountname = accountname;
		this.bankcountry = bankcountry;
	}




	public String getUser() {
		return user;
	}


	public void setUser(String user) {
		this.user = user;
	}


	public String getFactoryname() {
		return factoryname;
	}


	public void setFactoryname(String factoryname) {
		this.factoryname = factoryname;
	}


	public String getFactoryid() {
		return factoryid;
	}


	public void setFactoryid(String factoryid) {
		this.factoryid = factoryid;
	}


	public String getFactoryaddress() {
		return factoryaddress;
	}


	public void setFactoryaddress(String factoryaddress) {
		this.factoryaddress = factoryaddress;
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


	public String getBondlicense() {
		return bondlicense;
	}


	public void setBondlicense(String bondlicense) {
		this.bondlicense = bondlicense;
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


	public String getAccountno() {
		return accountno;
	}


	public void setAccountno(String accountno) {
		this.accountno = accountno;
	}


	public String getSwiftcode() {
		return swiftcode;
	}


	public void setSwiftcode(String swiftcode) {
		this.swiftcode = swiftcode;
	}


	public String getAccountname() {
		return accountname;
	}


	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}


	public String getBankcountry() {
		return bankcountry;
	}


	public void setBankcountry(String bankcountry) {
		this.bankcountry = bankcountry;
	}
	
	
	

}
