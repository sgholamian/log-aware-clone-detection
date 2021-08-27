//,temp,sample_6240.java,2,18,temp,sample_5248.java,2,16
//,3
public class xxx {
public void initAnnotatedFields() {
for (Class<?> cl : models) {
List<Field> linkFields = new ArrayList<Field>();
for (Field field : cl.getDeclaredFields()) {
KeyValuePairField keyValuePairField = field.getAnnotation(KeyValuePairField.class);
if (keyValuePairField != null) {
if (LOG.isDebugEnabled()) {


log.info("key declared in the class key field");
}
}
}
}
}

};