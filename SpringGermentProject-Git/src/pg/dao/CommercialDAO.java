package pg.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import pg.Commercial.MasterLC;
import pg.Commercial.MasterLC.StyleInfo;
import pg.Commercial.deedOfContacts;


public interface CommercialDAO {
	
	
	public boolean masterLCSubmit(MasterLC masterLC);
	public List<MasterLC> getMasterLCAmendmentList(String masterLCNo,String buyerId);
	public List<MasterLC> getMasterLCList();
	public MasterLC getMasterLCInfo(String masterLCNo,String buyerId,String amendmentNo);
	public List<StyleInfo> getMasterLCStyles(String masterLCNo,String buyerId,String amendmentNo);
	
	public boolean insertDeedOfContact(deedOfContacts deedcontact);
	
	public List<deedOfContacts> deedOfContractsList();
	public List<deedOfContacts> deedOfContractDetails(String id);

}
