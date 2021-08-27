//,temp,sample_2351.java,2,18,temp,sample_2608.java,2,15
//,3
public class xxx {
protected void doStart() throws Exception {
super.doStart();
if (entityManagerFactory == null) {
Map<String, EntityManagerFactory> map = getCamelContext().getRegistry().findByTypeWithName(EntityManagerFactory.class);
if (map != null) {
if (map.size() == 1) {
entityManagerFactory = map.values().iterator().next();


log.info("using entitymanagerfactory found in registry with id");
}
}
}
}

};