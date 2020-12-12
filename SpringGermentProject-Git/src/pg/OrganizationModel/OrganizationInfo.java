package pg.OrganizationModel;

import java.awt.Image;

public class OrganizationInfo {
	String userId;
	String organizationId;
	String organizationName;
	String organizationContact;
	String organizationAddress;
	


	public OrganizationInfo() {
		
	}
	
	
	//(SELECT * FROM OPENROWSET(BULK N'C:\logo.png', SINGLE_BLOB) as T1)
	
	
	public OrganizationInfo(String organizationName, String organizationContact, String organizationAddress) {
		this.organizationName = organizationName;
		this.organizationContact = organizationContact;
		this.organizationAddress = organizationAddress;

	}

	public OrganizationInfo(String organizationId, String organizationName, String organizationContact,
			String organizationAddress) {
		this.organizationId = organizationId;
		this.organizationName = organizationName;
		this.organizationContact = organizationContact;
		this.organizationAddress = organizationAddress;
	}





	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getOrganizationContact() {
		return organizationContact;
	}

	public void setOrganizationContact(String organizationContact) {
		this.organizationContact = organizationContact;
	}

	public String getOrganizationAddress() {
		return organizationAddress;
	}

	public void setOrganizationAddress(String organizationAddress) {
		this.organizationAddress = organizationAddress;
	}



	public String getUserId() {
		return userId;
	}



	public void setUserId(String userId) {
		this.userId = userId;
	}



	public String getOrganizationId() {
		return organizationId;
	}



	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}


	
	
}