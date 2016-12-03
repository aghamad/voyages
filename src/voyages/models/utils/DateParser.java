package voyages.models.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateParser {
	public static final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static Date parse(String dateString) throws ParseException {
		if(dateString == null)
			return null;
		return dateFormatter.parse(dateString);
	}
	public static String format(Date date) {
		if(date == null)
			return "";
		return dateFormatter.format(date);
	}
}
