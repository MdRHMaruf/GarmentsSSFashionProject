package pg.registerModel;

public class SampleType {
	String sampleTypeId;
	String sampleTypeName;
	String userId;
	
	public SampleType() {}
	
	
	public SampleType(String sampleTypeId, String sampleTypeName, String userId) {
		super();
		this.sampleTypeId = sampleTypeId;
		this.sampleTypeName = sampleTypeName;
		this.userId = userId;
	}


	public String getSampleTypeId() {
		return sampleTypeId;
	}


	public void setSampleTypeId(String sampleTypeId) {
		this.sampleTypeId = sampleTypeId;
	}


	public String getSampleTypeName() {
		return sampleTypeName;
	}


	public void setSampleTypeName(String sampleTypeName) {
		this.sampleTypeName = sampleTypeName;
	}


	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
	
}
