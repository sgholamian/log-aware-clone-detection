//,temp,VmwareStorageProcessor.java,2083,2091,temp,VmwareResource.java,4763,4771
//,2
public class xxx {
    private static String getSecondaryDatastoreUUID(String storeUrl) {
        String uuid = null;
        try{
            uuid=UUID.nameUUIDFromBytes(storeUrl.getBytes("UTF-8")).toString();
        }catch(UnsupportedEncodingException e){
            s_logger.warn("Failed to create UUID from string " + storeUrl + ". Bad storeUrl or UTF-8 encoding error." );
        }
        return uuid;
    }

};