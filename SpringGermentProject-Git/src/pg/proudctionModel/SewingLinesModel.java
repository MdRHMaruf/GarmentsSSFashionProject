package pg.proudctionModel;

import java.util.List;

public class SewingLinesModel {
	
	String selectid;
	String user;
	String style;
	String Line[];
	String start;
	String end;
	String duration;
	String selectUnselect;
	String selectedLines;
	
	String styleId="";
	String styleNo="";
	String allLineList="";
	String startDate="";
	String endDate="";
	
	public SewingLinesModel() {
		
	}
	
	public SewingLinesModel(String StyleId,String StyleNo,String AllLineList,String StartDate,String EndDate) {
		
		System.out.println("ho");
		this.styleId=StyleId;
		this.styleNo=StyleNo;
		this.allLineList=AllLineList;
		this.startDate=StartDate;
		this.endDate=EndDate;
	}
	
	public SewingLinesModel(String selectid,String style,String selectedLines, String start, String end, String selectUnselect,String non) {
		this.selectid=selectid;
		this.style=style;
		this.selectedLines=selectedLines;
		this.start=start;
		this.end=end;
		this.duration=duration;
		this.selectUnselect=selectUnselect;
	}
	
	public SewingLinesModel(String user, String style,String[] Line, String start, String end, String duration) {
		this.user=user;
		this.style=style;
		this.Line=Line;
		this.start=start;
		this.end=end;
		this.duration=duration;
	}
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String[] getLine() {
		return Line;
	}
	public void setLine(String line[]) {
		Line = line;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getSelectid() {
		return selectid;
	}

	public void setSelectid(String selectid) {
		this.selectid = selectid;
	}

	public String getSelectUnselect() {
		return selectUnselect;
	}

	public void setSelectUnselect(String selectUnselect) {
		this.selectUnselect = selectUnselect;
	}

	public String getSelectedLines() {
		return selectedLines;
	}

	public void setSelectedLines(String selectedLines) {
		this.selectedLines = selectedLines;
	}

	public String getStyleId() {
		return styleId;
	}

	public void setStyleId(String styleId) {
		this.styleId = styleId;
	}

	public String getStyleNo() {
		return styleNo;
	}

	public void setStyleNo(String styleNo) {
		this.styleNo = styleNo;
	}

	public String getAllLineList() {
		return allLineList;
	}

	public void setAllLineList(String allLineList) {
		this.allLineList = allLineList;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	
	
	
	

}
