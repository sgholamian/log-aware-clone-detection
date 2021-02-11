//,temp,sample_530.java,2,9,temp,sample_8067.java,2,9
//,3
public class xxx {
public static SaslPropertiesResolver getSaslPropertiesResolver( Configuration conf) {
String qops = conf.get(DFS_DATA_TRANSFER_PROTECTION_KEY);
if (qops == null || qops.isEmpty()) {


log.info("datatransferprotocol not using saslpropertiesresolver no qop found in configuration for");
}
}

};