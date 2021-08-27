//,temp,sample_4949.java,2,18,temp,sample_1684.java,2,15
//,3
public class xxx {
public HeadersMapFactory resolve(CamelContext context) {
Class<?> type = null;
try {
type = findFactory("headers-map-factory", context);
} catch (Exception e) {
}
if (type != null) {
if (LOG.isDebugEnabled()) {


log.info("found headersmapfactory via headers map factory");
}
}
}

};