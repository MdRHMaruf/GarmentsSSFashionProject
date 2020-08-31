package pg.share;

public class MonthAndYear {
	

	
	public String getMonthName(String month) {
		String monthName="";
		
		switch (month) {
		case "01":
			monthName="January";
			return monthName;
		case "02":
			monthName="February";
			return monthName;
		case "03":
			monthName="March";
			return monthName;
		case "04":
			monthName="April";
			return monthName;
		case "05":
			monthName="May";
			return monthName;
		case "06":
			monthName="June";
			return monthName;
		case "07":
			monthName="July";
			return monthName;
		case "08":
			monthName="August";
			return monthName;
		case "09":
			monthName="September";
			return monthName;
		case "10":
			monthName="October";
			return monthName;
		case "11":
			monthName="November";
			return monthName;
		case "12":
			monthName="December";
			return monthName;
		default:
			break;
		}
		
		return monthName;
	}
	
	
}
