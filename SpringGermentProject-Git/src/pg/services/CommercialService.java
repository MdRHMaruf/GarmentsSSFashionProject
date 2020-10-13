package pg.services;

import java.util.List;

import org.springframework.stereotype.Service;

import pg.Commercial.deedOfContacts;


public interface CommercialService {
	
	
	public boolean insertDeedOfContact(deedOfContacts deedcontact);
	
	public List<deedOfContacts> deedOfContractsList();
	
	public List<deedOfContacts> deedOfContractDetails(String id);

}
