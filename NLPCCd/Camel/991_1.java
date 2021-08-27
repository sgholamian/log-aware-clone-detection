//,temp,sample_6544.java,2,13,temp,sample_3076.java,2,17
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