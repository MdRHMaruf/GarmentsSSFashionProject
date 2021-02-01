package pg.model;

public class CommonModel {
	
	String empCode[];
	String userId;
	int type;
	String indentType;
	String dept;
	String id;
	String name;
	String qty;
	String value;
	String date;
	
	public CommonModel() {
		
	}
	
	//this constractor useing for qty and dept
	public CommonModel( String qty) {
		this.qty = qty;
	}
	//
	public CommonModel(String id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public CommonModel(String id,String name,String value) {
		this.id = id;
		this.name = name;
		this.value = value;
	}
	
	public CommonModel(String id, String name, String none, String none1) {
		this.id = id;
		this.name = name;
	}
	
	public CommonModel(String[] empCode, String dept, String userId, int type) {
		this.empCode = empCode;
		this.userId = userId;
		this.dept = dept;
		this.type = type;
	}
	
	
	public CommonModel(String dept, String id,String name, String value,String n2) {
		this.dept = dept;
		this.id = id;
		this.name = name;
		this.value = value;
	}
	
	public CommonModel(String[] empCode, String dept, String userId, int type, String id) {
		this.empCode = empCode;
		this.userId = userId;
		this.dept = dept;
		this.type = type;
		this.id = id;
	}
	
	public String getQty() {
		return qty;
	}
	public void setQty(String qty) {
		this.qty = qty;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String[] getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String[] empCode) {
		this.empCode = empCode;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getIndentType() {
		return indentType;
	}

	public void setIndentType(String indentType) {
		this.indentType = indentType;
	}
	
	
	
}
