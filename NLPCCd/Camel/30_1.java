//,temp,sample_2610.java,2,17,temp,sample_3327.java,2,17
//,3
public class xxx {
public void dummy_method(){
super.doStart();
if (entityManagerFactory == null) {
Map<String, EntityManagerFactory> map = getCamelContext().getRegistry().findByTypeWithName(EntityManagerFactory.class);
if (map != null) {
if (map.size() == 1) {
entityManagerFactory = map.values().iterator().next();
} else {
}
}
} else {


log.info("using entitymanagerfactory configured");
}
}

};