package pg.services;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pg.Commercial.BillOfEntry;
import pg.Commercial.ExportLC;
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
	public JSONArray getMasterUdAmendmentList(String masterLCNo, String udNo) {
		// TODO Auto-generated method stub
		return commercialDao.getMasterUdAmendmendList(masterLCNo, udNo);
	}

	@Override
	public JSONObject getMasterUdInfo(String autoId) {
		return commercialDao.getMasterUdInfo(autoId);
	}
	
	@Override
	public List<StyleInfo> getMasterUDStyles(String udAutoId, String amendmentNo) {
		// TODO Auto-generated method stub
		return commercialDao.getMasterUDStyles(udAutoId, amendmentNo);
	}
	@Override
	public String masterUDEdit(MasterLC masterLC) {
		// TODO Auto-generated method stub
		return commercialDao.masterUDEdit(masterLC);
	}

	@Override
	public String masterUDAmendment(MasterLC masterLC) {
		// TODO Auto-generated method stub
		return commercialDao.masterUDAmendment(masterLC);
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
	public boolean importInvoiceUdAdd(String udInfo) {
		// TODO Auto-generated method stub
		return commercialDao.importInvoiceUdAdd(udInfo);
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

	@Override
	public boolean billOfEntrySubmit(BillOfEntry billOfEntry) {
		// TODO Auto-generated method stub
		return commercialDao.billOfEntrySubmit(billOfEntry);
	}

	@Override
	public String billOfEntryEdit(BillOfEntry billOfEntry) {
		// TODO Auto-generated method stub
		return commercialDao.billOfEntryEdit(billOfEntry);
	}

	@Override
	public List<BillOfEntry> getBillOfEntryList(String masterLCNo, String invoiceNo) {
		// TODO Auto-generated method stub
		return commercialDao.getBillOfEntryList(masterLCNo, invoiceNo);
	}

	@Override
	public BillOfEntry getBillOfEntryInfo(String masterLCNo, String invoiceNo, String billEntryNo) {
		// TODO Auto-generated method stub
		return commercialDao.getBillOfEntryInfo(masterLCNo, invoiceNo, billEntryNo);
	}

	@Override
	public JSONArray getBillOfEntryItems(String billEntryAutoId, String billEntryNo) {
		// TODO Auto-generated method stub
		return commercialDao.getBillOfEntryItems(billEntryAutoId, billEntryNo);
	}

	@Override
	public boolean exportLCSubmit(ExportLC exportLc) {
		// TODO Auto-generated method stub
		return commercialDao.exportLCSubmit(exportLc);
	}

	@Override
	public String exportLCEdit(ExportLC exportLc) {
		// TODO Auto-generated method stub
		return commercialDao.exportLCEdit(exportLc);
	}

	@Override
	public List<ExportLC> getExportInvoiceList(String masterLCNo) {
		// TODO Auto-generated method stub
		return commercialDao.getExportInvoiceList(masterLCNo);
	}

	@Override
	public ExportLC getExportLCInfo(String masterLCNo, String exportInvoiceNo) {
		// TODO Auto-generated method stub
		return commercialDao.getExportLCInfo(masterLCNo, exportInvoiceNo);
	}

	@Override
	public JSONArray getExportStyleItems(String exportInvoiceAutoId) {
		// TODO Auto-generated method stub
		return commercialDao.getExportStyleItems(exportInvoiceAutoId);
	}

	@Override
	public JSONObject getMasterLCSummaryForPassBook(String masterLCNo) {
		// TODO Auto-generated method stub
		return commercialDao.getMasterLCSummaryForPassBook(masterLCNo);
	}

	@Override
	public String savePassBookData(String passBookData) {
		// TODO Auto-generated method stub
		return commercialDao.savePassBookData(passBookData);
	}

}
