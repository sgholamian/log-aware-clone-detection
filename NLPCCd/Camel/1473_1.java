//,temp,sample_6240.java,2,18,temp,sample_5248.java,2,16
//,3
public class xxx {
public void initAnnotatedFields() {
for (Class<?> cl : models) {
List<Field> linkFields = new ArrayList<Field>();
if (LOG.isDebugEnabled()) {
}
for (Field field : cl.getDeclaredFields()) {
DataField dataField = field.getAnnotation(DataField.class);
if (dataField != null) {
if (LOG.isDebugEnabled()) {


log.info("position defined in the class position field");
}
}
}
}
}

};