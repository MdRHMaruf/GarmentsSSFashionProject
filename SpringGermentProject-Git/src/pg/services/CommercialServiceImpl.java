package pg.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pg.Commercial.deedOfContacts;
import pg.dao.CommercialDAO;
@Service
public class CommercialServiceImpl implements CommercialService{
	
	@Autowired 
	CommercialDAO commDao;

	@Override
	public boolean insertDeedOfContact(deedOfContacts deedcontact) {
		// TODO Auto-generated method stub
		return commDao.insertDeedOfContact(deedcontact);
	}

	@Override
	public List<deedOfContacts> deedOfContractsList() {
		// TODO Auto-generated method stub
		return commDao.deedOfContractsList();
	}

}
