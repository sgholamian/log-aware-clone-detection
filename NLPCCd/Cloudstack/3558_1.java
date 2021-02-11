//,temp,sample_1297.java,2,11,temp,sample_6663.java,2,11
//,2
public class xxx {
private static String getSecondaryDatastoreUUID(String storeUrl) {
String uuid = null;
try{
uuid=UUID.nameUUIDFromBytes(storeUrl.getBytes("UTF-8")).toString();
}catch(UnsupportedEncodingException e){


log.info("failed to create uuid from string bad storeurl or utf encoding error");
}
}

};