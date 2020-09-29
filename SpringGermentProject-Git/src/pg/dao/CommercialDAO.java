package pg.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import pg.Commercial.deedOfContacts;


public interface CommercialDAO {
	
	
	public boolean insertDeedOfContact(deedOfContacts deedcontact);
	
	public List<deedOfContacts> deedOfContractsList();

}
