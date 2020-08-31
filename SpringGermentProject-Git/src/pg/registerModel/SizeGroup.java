package pg.registerModel;

public class SizeGroup {
	//These Property for Jason 
	String id;
	String value;
	
	String groupId;
	String groupName;
	String userId;
	
	public SizeGroup() {
	}

	public SizeGroup(String groupId, String groupName, String userId) {
		super();
		this.groupId = groupId;
		this.groupName = groupName;
		this.userId = userId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	

}
