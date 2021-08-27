//,temp,sample_2436.java,2,11,temp,sample_2435.java,2,11
//,2
public class xxx {
public boolean remove(String s) {
byte[] bytes = serializer.serialize(s);
try {
return dataSet.delete(bytes);
} catch (Exception e) {


log.info("error removing item from krati idempotent repository this exception is ignored");
}
}

};