//,temp,sample_3461.java,2,8,temp,sample_3433.java,2,12
//,3
public class xxx {
public static TimestampTZ parseOrNull(String s, ZoneId defaultTimeZone) {
try {
return parse(s, defaultTimeZone);
} catch (DateTimeParseException e) {
if (LOG.isDebugEnabled()) {


log.info("invalid string for timestamp with time zone");
}
}
}

};