//,temp,sample_1079.java,2,18,temp,sample_1078.java,2,15
//,3
public class xxx {
private void loadModels(Class<?> root) {
models.add(root);
modelClassNames.add(root.getName());
for (Field field : root.getDeclaredFields()) {
Link linkField = field.getAnnotation(Link.class);
if (linkField != null) {
if (LOG.isDebugEnabled()) {


log.info("class linked field");
}
}
}
}

};