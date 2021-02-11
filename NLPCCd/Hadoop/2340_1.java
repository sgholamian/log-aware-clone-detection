//,temp,sample_7149.java,2,18,temp,sample_7153.java,2,18
//,3
public class xxx {
public void dummy_method(){
iter.seek(bytes(RM_RESERVATION_KEY_PREFIX));
while (iter.hasNext()) {
Entry<byte[],byte[]> entry = iter.next();
String key = asString(entry.getKey());
if (!key.startsWith(RM_RESERVATION_KEY_PREFIX)) {
break;
}
String planReservationString = key.substring(RM_RESERVATION_KEY_PREFIX.length());
String[] parts = planReservationString.split(SEPARATOR);
if (parts.length != 2) {


log.info("incorrect reservation state key");
}
}
}

};