//,temp,sample_937.java,2,17,temp,sample_3895.java,2,19
//,3
public class xxx {
public void dummy_method(){
if (parameterValue instanceof String) {
parameterValue = StringHelper.removeLeadingAndEndingQuotes((String) parameterValue);
}
if (parameterValue != null) {
try {
answer = exchange.getContext().getTypeConverter().mandatoryConvertTo(parameterType, exchange, parameterValue);
if (LOG.isTraceEnabled()) {
}
} catch (Exception e) {
if (LOG.isDebugEnabled()) {


log.info("cannot convert from type to type for parameter");
}
}
}
}

};