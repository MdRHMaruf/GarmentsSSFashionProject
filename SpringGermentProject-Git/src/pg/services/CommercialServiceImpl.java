package pg.services;

import java.util.List;

import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pg.Commercial.ImportLC;
import pg.Commercial.MasterLC;
import pg.Commercial.MasterLC.StyleInfo;
import pg.Commercial.deedOfContacts;
import pg.dao.CommercialDAO;
@Service
public class CommercialServiceImpl implements CommercialService{
	
	@Autowired 
	CommercialDAO commercialDao;

	@Override
	public boolean masterLCSubmit(MasterLC masterLC) {
		// TODO Auto-generated method stub
		return commercialDao.masterLCSubmit(masterLC);
	}
	
	@Override
	public String masterLCEdit(MasterLC masterLC) {
		// TODO Auto-generated method stub
		return commercialDao.masterLCEdit(masterLC);
	}
	
	@Override
	public String masterLCAmendment(MasterLC masterLC) {
		// TODO Auto-generated method stub
		return commercialDao.masterLCAmendment(masterLC);
	}
	
	@Override
	public List<MasterLC> getMasterLCAmendmentList(String masterLCNo, String buyerId) {
		// TODO Auto-generated method stub
		return commercialDao.getMasterLCAmendmentList(masterLCNo, buyerId);
	}
	
	@Override
	public List<MasterLC> getMasterLCList() {
		// TODO Auto-generated method stub
		return commercialDao.getMasterLCList();
	}
	
	
	@Override
	public MasterLC getMasterLCInfo(String masterLCNo, String buyerId, String amendmentNo) {
		// TODO Auto-generated method stub
		return commercialDao.getMasterLCInfo(masterLCNo, buyerId, amendmentNo);
	}

	@Override
	public List<StyleInfo> getMasterLCStyles(String masterLCNo, String buyerId, String amendmentNo) {
		// TODO Auto-generated method stub
		return commercialDao.getMasterLCStyles(masterLCNo, buyerId, amendmentNo);
	}
	
	@Override
	public boolean importLCSubmit(ImportLC importLC) {
		// TODO Auto-generated method stub
		return commercialDao.importLCSubmit(importLC);
	}
	
	@Override
	public String importLCEdit(ImportLC importLC) {
		// TODO Auto-generated method stub
		return commercialDao.importLCEdit(importLC);
	}
	
	@Override
	public String importLCAmendment(ImportLC importLC) {
		// TODO Auto-generated method stub
		return commercialDao.importLCAmendment(importLC);
	}
	
	@Override
	public List<ImportLC> getImportLCAmendmentList(String masterLCNo, String invoiceNo) {
		// TODO Auto-generated method stub
		return commercialDao.getImportLCAmendmentList(masterLCNo, invoiceNo);
	}

	@Override
	public List<ImportLC> getImportLCList(String masterLCNo) {
		// TODO Auto-generated method stub
		return commercialDao.getImportLCList(masterLCNo);
	}
	

	@Override
	public ImportLC getImportLCInfo(String masterLCNo, String invoiceNo, String amendmentNo) {
		// TODO Auto-generated method stub
		return commercialDao.getImportLCInfo(masterLCNo, invoiceNo, amendmentNo);
	}
	
	@Override
	public JSONArray getImportInvoiceItems(String importInvoiceAutoId) {
		// TODO Auto-generated method stub
		return commercialDao.getImportInvoiceItems(importInvoiceAutoId);
	}

	@Override
	public boolean insertDeedOfContact(deedOfContacts deedcontact) {
		// TODO Auto-generated method stub
		return commercialDao.insertDeedOfContact(deedcontact);
	}

	@Override
	public List<deedOfContacts> deedOfContractsList() {
		// TODO Auto-generated method stub
		return commercialDao.deedOfContractsList();
	}

	@Override
	public List<deedOfContacts> deedOfContractDetails(String id) {
		// TODO Auto-generated method stub
		return commercialDao.deedOfContractDetails( id);
	}

	


	


}
