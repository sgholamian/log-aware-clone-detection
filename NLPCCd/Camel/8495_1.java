//,temp,sample_2166.java,2,18,temp,sample_2165.java,2,18
//,2
public class xxx {
public void dummy_method(){
if (value instanceof Calendar) {
message.getEntity().setModificationDate(((Calendar) value).getTime());
} else if (value instanceof Date) {
message.getEntity().setModificationDate((Date) value);
} else if (value instanceof String) {
SimpleDateFormat format = new SimpleDateFormat(RFC_2822_DATE_PATTERN, Locale.ENGLISH);
try {
Date date = format.parse((String) value);
message.getEntity().setModificationDate(date);
} catch (ParseException e) {


log.info("header with value cannot be converted as a date the value will be ignored");
}
}
}

};