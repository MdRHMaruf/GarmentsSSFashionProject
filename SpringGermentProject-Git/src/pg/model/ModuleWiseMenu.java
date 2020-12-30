package pg.model;

public class ModuleWiseMenu {

	int id;
	String module;
	String name;

	public ModuleWiseMenu(int id, String module, String name) {
		this.id = id;
		this.module = module;
		this.name = name;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
