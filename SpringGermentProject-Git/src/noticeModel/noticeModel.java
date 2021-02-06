package noticeModel;

public class noticeModel {
	String id;
	String noticeHeader;
	String noticeBody;
	String filename;
	String date;
	
	public noticeModel() {
		
	}
	
	public noticeModel(String id, String header,String body,String filename) {
		this.id=id;
		this.noticeHeader=header;
		this.noticeBody=body;
		
	}
	
	
	public noticeModel(String noticeheader, String body,String filename) {
		this.noticeHeader=noticeheader;
		this.noticeBody=body;
		this.filename=filename;
	}
	
	
	public noticeModel(String id,String noticeheader, String body,String filename,String date) {
		this.id=id;
		this.noticeHeader=noticeheader;
		this.noticeBody=body;
		this.filename=filename;
		this.date=date;
		
	}
	
	public String getNoticeHeader() {
		return noticeHeader;
	}
	public void setNoticeHeader(String noticeHeader) {
		noticeHeader = noticeHeader;
	}
	public String getNoticeBody() {
		return noticeBody;
	}
	public void setNoticeBody(String noticeBody) {
		noticeBody = noticeBody;
	}


	public String getFilename() {
		return filename;
	}


	public void setFilename(String filename) {
		this.filename = filename;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}
	
	
	

}
