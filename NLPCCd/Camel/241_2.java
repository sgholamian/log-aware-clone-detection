//,temp,sample_3897.java,2,18,temp,sample_3075.java,2,13
//,3
public class xxx {
public static String getTextlineBody(Object body, Exchange exchange, TextLineDelimiter delimiter, boolean autoAppendDelimiter) throws NoTypeConversionAvailableException {
String s = exchange.getContext().getTypeConverter().mandatoryConvertTo(String.class, exchange, body);
if (autoAppendDelimiter) {
if (TextLineDelimiter.LINE.equals(delimiter)) {
if (!s.endsWith("\n")) {


log.info("auto appending missing newline delimiter to body");
}
}
}
}

};