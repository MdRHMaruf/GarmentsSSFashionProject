package pg.registerModel;

public class Bank {
	String id;
	String bankName;
	String branchName;
	String address;
	
	public Bank() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Bank(String id, String bankName, String branchName, String address) {
		super();
		this.id = id;
		this.bankName = bankName;
		this.branchName = branchName;
		this.address = address;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	
}
