//,temp,sample_2436.java,2,11,temp,sample_2435.java,2,11
//,2
public class xxx {
public boolean contains(String s) {
byte[] bytes = serializer.serialize(s);
try {
return dataSet.has(bytes);
} catch (Exception e) {


log.info("error checking item exists in krati idempotent repository this exception is ignored");
}
}

};