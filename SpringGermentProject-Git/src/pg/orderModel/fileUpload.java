package pg.orderModel;

public class fileUpload {
	
	String user;
	String autoid;
	String filename;
	String uploadby;
	String uploadip;
	String uploadmachine;
	String purpose;
	String uploaddate;
	String datetime;
	
	
	String downloadby;
	String downloadip;
	String downloadmachine;
	String downloaddate;
	String downloadtime;
	
	
	
	
	public fileUpload(String autoid,String filename, String uploadby, String uplodip, String uploadmachine, String purpose,
			String uploaddate, String datetime, String downloadby, String downloadip, String downloadmachine,
			String downloaddate, String downloadtime) {
		super();
		this.autoid=autoid;
		this.filename = filename;
		this.uploadby = uploadby;
		this.uploadip = uplodip;
		this.uploadmachine = uploadmachine;
		this.purpose = purpose;
		this.uploaddate = uploaddate;
		this.datetime = datetime;
		this.downloadby = downloadby;
		this.downloadip = downloadip;
		this.downloadmachine = downloadmachine;
		this.downloaddate = downloaddate;
		this.downloadtime = downloadtime;
	}
	
	
	public String getAutoid() {
		return autoid;
	}


	public void setAutoid(String autoid) {
		this.autoid = autoid;
	}


	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getUploadby() {
		return uploadby;
	}
	public void setUploadby(String uploadby) {
		this.uploadby = uploadby;
	}
	public String getUploadmachine() {
		return uploadmachine;
	}
	public void setUploadmachine(String uploadmachine) {
		this.uploadmachine = uploadmachine;
	}
	public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getDownloadby() {
		return downloadby;
	}
	public void setDownloadby(String downloadby) {
		this.downloadby = downloadby;
	}
	public String getDownloadmachine() {
		return downloadmachine;
	}
	public void setDownloadmachine(String downloadmachine) {
		this.downloadmachine = downloadmachine;
	}
	public String getDownloaddate() {
		return downloaddate;
	}
	public void setDownloaddate(String downloaddate) {
		this.downloaddate = downloaddate;
	}
	public String getUploadip() {
		return uploadip;
	}
	public void setUploadip(String uploadip) {
		this.uploadip = uploadip;
	}
	public String getUploaddate() {
		return uploaddate;
	}
	public void setUploaddate(String uploaddate) {
		this.uploaddate = uploaddate;
	}
	public String getDownloadip() {
		return downloadip;
	}
	public void setDownloadip(String downloadip) {
		this.downloadip = downloadip;
	}
	public String getDownloadtime() {
		return downloadtime;
	}
	public void setDownloadtime(String downloadtime) {
		this.downloadtime = downloadtime;
	}
	
	
	

}
