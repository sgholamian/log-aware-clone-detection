//,temp,sample_261.java,2,16,temp,sample_6407.java,2,16
//,2
public class xxx {
public void dummy_method(){
Integer keyId = exchange.getIn().getHeader(DigitalOceanHeaders.ID, Integer.class);
String fingerprint = exchange.getIn().getHeader(DigitalOceanHeaders.KEY_FINGERPRINT, String.class);
Key key;
if (ObjectHelper.isNotEmpty(keyId)) {
key = getEndpoint().getDigitalOceanClient().getKeyInfo(keyId);
} else if (ObjectHelper.isNotEmpty(fingerprint)) {
key = getEndpoint().getDigitalOceanClient().getKeyInfo(fingerprint);
} else {
throw new IllegalArgumentException(DigitalOceanHeaders.ID + " or " + DigitalOceanHeaders.KEY_FINGERPRINT + " must be specified");
}


log.info("key");
}

};