//,temp,sample_2349.java,2,14,temp,sample_2350.java,2,15
//,3
public class xxx {
protected void doStart() throws Exception {
if (objectMapper == null) {
if (camelContext != null) {
Set<ObjectMapper> set = camelContext.getRegistry().findByType(ObjectMapper.class);
if (set.size() == 1) {
objectMapper = set.iterator().next();


log.info("found single objectmapper in registry to use");
}
}
}
}

};