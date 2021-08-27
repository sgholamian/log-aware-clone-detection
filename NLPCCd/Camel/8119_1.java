//,temp,sample_6248.java,2,15,temp,sample_2998.java,2,19
//,3
public class xxx {
public String unbind(CamelContext camelContext, Map<String, Object> model) throws Exception {
StringBuilder buffer = new StringBuilder();
Map<Integer, List<String>> results = new HashMap<Integer, List<String>>();
for (Class<?> clazz : models) {
if (model.containsKey(clazz.getName())) {
Object obj = model.get(clazz.getName());
if (LOG.isDebugEnabled()) {


log.info("model object class");
}
}
}
}

};