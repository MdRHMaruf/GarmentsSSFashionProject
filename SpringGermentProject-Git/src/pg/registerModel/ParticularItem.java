package pg.registerModel;

public class ParticularItem {
	String particularItemId;
	String particularItemName;
	String userId;
	
	
	public ParticularItem() {}
	public ParticularItem(String particularItemId, String particularItemName, String userId) {
		super();
		this.particularItemId = particularItemId;
		this.particularItemName = particularItemName;
		this.userId = userId;
	}
	public String getParticularItemId() {
		return particularItemId;
	}
	public void setParticularItemId(String particularItemId) {
		this.particularItemId = particularItemId;
	}
	public String getParticularItemName() {
		return particularItemName;
	}
	public void setParticularItemName(String particularItemName) {
		this.particularItemName = particularItemName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
