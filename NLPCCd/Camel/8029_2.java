//,temp,sample_1686.java,2,16,temp,sample_1685.java,2,18
//,3
public class xxx {
public void dummy_method(){
Class<?> type = null;
try {
type = findFactory("headers-map-factory", context);
} catch (Exception e) {
}
if (type != null) {
if (LOG.isDebugEnabled()) {
}
if (HeadersMapFactory.class.isAssignableFrom(type)) {
HeadersMapFactory answer = (HeadersMapFactory) context.getInjector().newInstance(type);


log.info("detected and using custom headersmapfactory");
}
}
}

};