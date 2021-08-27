//,temp,sample_7583.java,2,14,temp,sample_2609.java,2,16
//,3
public class xxx {
protected void doStart() throws Exception {
super.doStart();
if (entityManagerFactory == null) {
Map<String, EntityManagerFactory> map = getCamelContext().getRegistry().findByTypeWithName(EntityManagerFactory.class);
if (map != null) {
if (map.size() == 1) {
entityManagerFactory = map.values().iterator().next();
} else {


log.info("could not find a single entitymanagerfactory in registry as there was instances");
}
}
}
}

};