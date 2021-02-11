//,temp,sample_6346.java,2,13,temp,sample_6348.java,2,14
//,3
public class xxx {
private static Object getField(BaseRecord record, String fieldName) {
Object result = null;
Method m = locateGetter(record, fieldName);
if (m != null) {
try {
result = m.invoke(record);
} catch (Exception e) {


log.info("cannot get field on object");
}
}
}

};