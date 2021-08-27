//,temp,sample_5904.java,2,18,temp,sample_5903.java,2,18
//,2
public class xxx {
public void dummy_method(){
while (it.hasNext() && isRunAllowed()) {
Map.Entry<Buffer, Buffer> entry = it.next();
Buffer keyBuffer = entry.getKey();
String exchangeId;
try {
exchangeId = codec.unmarshallKey(keyBuffer);
} catch (IOException e) {
throw new RuntimeException("Error unmarshalling confirm key: " + keyBuffer, e);
}
if (exchangeId != null) {


log.info("scan exchangeid");
}
}
}

};