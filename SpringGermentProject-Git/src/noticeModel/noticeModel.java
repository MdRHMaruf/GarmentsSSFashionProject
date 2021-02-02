package noticeModel;

public class noticeModel {
	
	String NoticeHeader;
	String NoticeBody;
	String filename;
	
	public noticeModel() {
		
	}
	
	
	public noticeModel(String noticeheader, String body,String filename) {
		this.NoticeHeader=noticeheader;
		this.NoticeBody=body;
		this.filename=filename;
	}
	
	public String getNoticeHeader() {
		return NoticeHeader;
	}
	public void setNoticeHeader(String noticeHeader) {
		NoticeHeader = noticeHeader;
	}
	public String getNoticeBody() {
		return NoticeBody;
	}
	public void setNoticeBody(String noticeBody) {
		NoticeBody = noticeBody;
	}


	public String getFilename() {
		return filename;
	}


	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	
	

}
