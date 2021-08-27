//,temp,sample_3897.java,2,18,temp,sample_3075.java,2,13
//,3
public class xxx {
public void dummy_method(){
try {
if (parameterType.isInstance(result)) {
answer = result;
} else {
answer = exchange.getContext().getTypeConverter().mandatoryConvertTo(parameterType, result);
}
if (LOG.isTraceEnabled()) {
}
} catch (NoTypeConversionAvailableException e) {
if (LOG.isDebugEnabled()) {


log.info("cannot convert from type to type for parameter");
}
}
}

};