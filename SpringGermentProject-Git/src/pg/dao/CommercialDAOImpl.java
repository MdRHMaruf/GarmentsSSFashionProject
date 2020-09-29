package pg.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import pg.Commercial.deedOfContacts;
import pg.orderModel.Style;
import pg.share.HibernateUtil;

@Repository
public class CommercialDAOImpl implements CommercialDAO{

	@Override
	public boolean insertDeedOfContact(deedOfContacts v) {
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		try{
			tx=session.getTransaction();
			tx.begin();
			
			
			
			
			String sql="insert into tbDEEDOfContract "
					+ "( ContractId, "
					+ "PONumber, "
					+ "StyleNo,"
					+ " ItemDescription,"
					+ " goodsDescription, "
					+ "color, "
					+ "rollQty,"
					+ " ctnQty, "
					+ "grossWeight, "
					+ "netWeight, "
					+ "unit, "
					+ "unitPrice,"
					+ " currency,"
					+ " amount, "
					+ "ETDDate,"
					+ " ETADate, "
					+ "ETCDate, "
					+ "ClearDate, "
					+ "ContractNo,"
					+ " ReadyDate, "
					+ "SubmitDate, "
					+ "ReceivedDate,"
					+ " ExpireyDate,"
					+ " AmmendmentDate,"
					+ " ExtendedDate, "
					+ "ExportDate, "
					+ "InvoiceNumber, "
					+ "InvoiceDate, "
					+ "AWBNumber,"
					+ " BLDate, "
					+ "TrachingNumber,"
					+ " ShipperAddress,"
					+ " consignAddress, "
					+ "CNFHandoverDate,"
					+ " CNFAddress,"
					+ " Telephone, "
					+ "Mobile, "
					+ "FaxNo, "
					+ "ContactPerson,"
					+ " CouirerName,"
					+ " ForwardAddress,"
					+ " UdMakingDate, "
					+ "UdAmmendmentDate,"
					+ "UdSubmitDate, "
					+ "UdReceivedDate, "
					+ "UdHoverDate, "
					+ "BirthingDate, "
					+ "BuyerId, "
					+ "MasterLc, "
					+ "BBLc,   "
					+ "VesselName, "
					+ "InvoiceQty,"
					+ " OnBoardDate, "
					+ "entryTime, "
					+ "UserId) values"
			+ "('"+v.getContractId()+"',"
				+ "'"+v.getPurchaseOrder()+"',"
				+ "'"+v.getStyleNo()+"',"
				+ "'"+v.getItemName()+"',"
				+ "'"+v.getGoodsDescription()+"',"
				+ "'"+v.getItemColor()+"',"
				+ "'"+v.getRollQty()+"',"
				+ "'"+v.getCtnQty()+"',"
				+ "'"+v.getGrossWeight()+"',"
				+ "'"+v.getNetWeight()+"',"
				+ "'"+v.getUnit()+"',"
				+ "'"+v.getUnitPrice()+"',"
				+ "'"+v.getCurrency()+"',"
			   + "'"+v.getAmount()+"',"
			+ "'"+v.getEtdDate()+"',"
			+ "'"+v.getEtaDate()+"',"
			+ "'"+v.getEtcDate()+"',"
			+ "'"+v.getClearDate()+"',"
			+ "'"+v.getContactNo()+"',"
			+ "'"+v.getReadyDate()+"',"
			+ "'"+v.getSubmitDate()+"',"
			+ "'"+v.getReceivedDate()+"',"
			+ "'"+v.getExpiryDate()+"',"
			+ "'"+v.getAmmendmentDate()+"',"
			+ "'"+v.getExtendedDate()+"',"
			+ "'"+v.getExportDate()+"',"
			+ "'"+v.getInvoiceNumber()+"',"
			+ "'"+v.getInvoiceDate()+"',"
		+ "'"+v.getAwbNumber()+"',"
		+ "'"+v.getBlDate()+"',"
		+ "'"+v.getTrackingNumber()+"',"
		+ "'"+v.getShipperAddress()+"',"
		+ "'"+v.getConsignAddress()+"',"
		+ "'"+v.getCfHandoverDate()+"',"
		+ "'"+v.getCfAddress()+"',"
		+ "'"+v.getTelephone()+"',"
		+ "'"+v.getMobile()+"',"
		+ "'"+v.getFaxNo()+"',"
		+ "'"+v.getContactPerson()+"',"
		+ "'"+v.getCourieer()+"',"
		+ "'"+v.getForwardAddress()+"',"
		+ "'"+v.getUnMakeingDate()+"',"
		+ "'"+v.getAmmendmentDate()+"',"
		+ "'"+v.getUnSubmitDate()+"',"
		+ "'"+v.getUNReceivedDate()+"',"
		+ "'"+v.getUnHoverDate()+"',"
		+ "'"+v.getBirthingDate()+"',"
		+ "'"+v.getBuyerId()+"',"
		+ "'"+v.getMasterLC()+"',"
		+ "'"+v.getBblc()+"',"
		+ "'"+v.getVvsselName()+"',"
		+ "'"+v.getInvoiceQty()+"'"
		+ ",'"+v.getOnBoardDate()+"'"
	+ ",CURRENT_TIMESTAMP,"
	+ "'"+v.getUserid()+"')";
			session.createSQLQuery(sql).executeUpdate();
			
			
			
			tx.commit();
			return true;
		}
		catch(Exception ee){

			if (tx != null) {
				tx.rollback();
				return false;
			}
			ee.printStackTrace();
		}

		finally {
			session.close();
		}

		return false;
	}

	@Override
	public List<deedOfContacts> deedOfContractsList() {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.openSession();
		Transaction tx=null;
		List<deedOfContacts> dataList=new ArrayList<deedOfContacts>();
		try{
			tx=session.getTransaction();
			tx.begin();

			String sql=" select a.ContractId, (select purchaseorder from TbBuyerOrderEstimateDetails where buyerorderid=a.PONumber group by PurchaseOrder) as po,(select styleno from tbstylecreate where styleid=a.StyleNo) as style,(select itemname from tbItemDescription b where b.itemid=a.ItemDescription ) as item from tbDEEDOfContract a";

			List<?> list = session.createSQLQuery(sql).list();
			for(Iterator<?> iter = list.iterator(); iter.hasNext();)
			{	

				Object[] element = (Object[]) iter.next();

				dataList.add(new deedOfContacts(element[0].toString(),element[1].toString(),element[2].toString(),element[3].toString()));
			}
			tx.commit();
		}
		catch(Exception e){
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		}
		finally {
			session.close();
		}
		return dataList;
	}

}
