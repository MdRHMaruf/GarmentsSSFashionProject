package pg.dao;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Repository;

import pg.Commercial.BillOfEntry;
import pg.Commercial.ExportLC;
import pg.Commercial.ImportLC;
import pg.Commercial.MasterLC;
import pg.Commercial.MasterLC.StyleInfo;
import pg.Commercial.deedOfContacts;


public interface CommercialDAO {
	
	
	public boolean masterLCSubmit(MasterLC masterLC);
	public String masterLCEdit(MasterLC masterLC);
	public String masterLCAmendment(MasterLC masterLC);
	public List<MasterLC> getMasterLCAmendmentList(String masterLCNo,String buyerId);
	public List<MasterLC> getMasterLCList();
	public MasterLC getMasterLCInfo(String masterLCNo,String buyerId,String amendmentNo);
	public List<StyleInfo> getMasterLCStyles(String masterLCNo,String buyerId,String amendmentNo);
	public JSONArray getMasterUdAmendmendList(String masterLCNo,String udNo);
	public JSONObject getMasterUdInfo(String autoId);
	public List<StyleInfo> getMasterUDStyles(String udAutoId,String amendmentNo);
	public String masterUDEdit(MasterLC masterLC);
	public String masterUDAmendment(MasterLC masterLC);
	
	
	public boolean importLCSubmit(ImportLC importLC);
	public String importLCEdit(ImportLC importLC);
	public String importLCAmendment(ImportLC importLC);
	public List<ImportLC> getImportLCAmendmentList(String masterLCNo,String invoiceNo);
	public List<ImportLC> getImportLCList(String masterLCNo);
	public ImportLC getImportLCInfo(String masterLCNo, String invoiceNo, String amendmentNo);
	public JSONArray getImportInvoiceItems(String importInvoiceAutoId);
	public boolean importInvoiceUdAdd(String udInfo);
	
	//Bill Of Entry
	public boolean billOfEntrySubmit(BillOfEntry billOfEntry);
	public String billOfEntryEdit(BillOfEntry billOfEntry);
	public List<BillOfEntry> getBillOfEntryList(String masterLCNo,String invoiceNo);
	public BillOfEntry getBillOfEntryInfo(String masterLCNo,String invoiceNo,String billEntryNo);
	public JSONArray getBillOfEntryItems(String billEntryAutoId,String bollEntryNo);
	
	//Export
	public boolean exportLCSubmit(ExportLC exportLc);
	public String exportLCEdit(ExportLC exportLc);
	public List<ExportLC> getExportInvoiceList(String masterLCNo);
	public ExportLC getExportLCInfo(String masterLCNo,String exportInvoiceNo);
	public JSONArray getExportStyleItems(String exportInvoiceAutoId);
	
	//Deed of contract
	public boolean insertDeedOfContact(deedOfContacts deedcontact);
	public List<deedOfContacts> deedOfContractsList();
	public List<deedOfContacts> deedOfContractDetails(String id);
	
	//Pass Book Form
	public JSONObject getMasterLCSummaryForPassBook(String masterLCNo);
	public String savePassBookData(String passBookData);

}
