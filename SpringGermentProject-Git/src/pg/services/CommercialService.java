package pg.services;

import java.util.List;

import org.springframework.stereotype.Service;

import pg.Commercial.ImportLC;
import pg.Commercial.MasterLC;
import pg.Commercial.deedOfContacts;
import pg.Commercial.MasterLC.StyleInfo;


public interface CommercialService {
	
	public boolean masterLCSubmit(MasterLC masterLC);
	public String masterLCEdit(MasterLC masterLC);
	public String masterLCAmendment(MasterLC masterLC);
	public List<MasterLC> getMasterLCAmendmentList(String masterLCNo,String buyerId);
	public List<MasterLC> getMasterLCList();
	public MasterLC getMasterLCInfo(String masterLCNo,String buyerId,String amendmentNo);
	public List<StyleInfo> getMasterLCStyles(String masterLCNo,String buyerId,String amendmentNo);
	
	public boolean importLCSubmit(ImportLC importLC);
	public String importLCEdit(ImportLC importLC);
	public String importLCAmendment(ImportLC importLC);
	public List<ImportLC> getImportLCAmendmentList(String masterLCNo,String invoiceNo);
	public List<ImportLC> getImportLCList(String masterLCNo);
	
	public boolean insertDeedOfContact(deedOfContacts deedcontact);
	
	public List<deedOfContacts> deedOfContractsList();
	
	public List<deedOfContacts> deedOfContractDetails(String id);

}
