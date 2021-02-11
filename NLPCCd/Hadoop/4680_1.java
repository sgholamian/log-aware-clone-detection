//,temp,sample_6346.java,2,13,temp,sample_6348.java,2,14
//,3
public class xxx {
private static boolean setField( BaseRecord record, String fieldName, Object data) {
Method m = locateSetter(record, fieldName);
if (m != null) {
try {
m.invoke(record, data);
} catch (Exception e) {


log.info("cannot set field on object to data of type");
}
}
}

};