package pg.model;

public class CommonModel {
	
	String id;
	String name;
	String qty;
	String value;
	
	public CommonModel() {
		
	}
	public CommonModel( String qty) {
		super();
		
		this.qty = qty;
	}
	
	public CommonModel(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public CommonModel(String id,String name,String value) {
		super();
		this.id = id;
		this.name = name;
		this.value = value;
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
}
