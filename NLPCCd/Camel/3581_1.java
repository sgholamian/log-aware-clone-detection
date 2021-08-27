//,temp,sample_2351.java,2,18,temp,sample_2608.java,2,15
//,3
public class xxx {
public void dummy_method(){
if (objectMapper == null) {
if (camelContext != null) {
Set<ObjectMapper> set = camelContext.getRegistry().findByType(ObjectMapper.class);
if (set.size() == 1) {
objectMapper = set.iterator().next();
} else if (set.size() > 1) {
}
}
if (objectMapper == null) {
objectMapper = new ObjectMapper();


log.info("creating new objectmapper to use");
}
}
}

};