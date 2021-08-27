//,temp,sample_4737.java,2,16,temp,sample_263.java,2,16
//,2
public class xxx {
public void dummy_method(){
Integer keyId = exchange.getIn().getHeader(DigitalOceanHeaders.ID, Integer.class);
String fingerprint = exchange.getIn().getHeader(DigitalOceanHeaders.KEY_FINGERPRINT, String.class);
Delete delete;
if (ObjectHelper.isNotEmpty(keyId)) {
delete = getEndpoint().getDigitalOceanClient().deleteKey(keyId);
} else if (ObjectHelper.isNotEmpty(fingerprint)) {
delete = getEndpoint().getDigitalOceanClient().deleteKey(fingerprint);
} else {
throw new IllegalArgumentException(DigitalOceanHeaders.ID + " or " + DigitalOceanHeaders.KEY_FINGERPRINT + " must be specified");
}


log.info("delete key");
}

};