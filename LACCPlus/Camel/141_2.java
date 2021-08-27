//,temp,DigitalOceanKeysProducer.java,85,101,temp,DigitalOceanKeysProducer.java,62,77
//,2
public class xxx {
    private void getKey(Exchange exchange) throws Exception {
        Integer keyId = exchange.getIn().getHeader(DigitalOceanHeaders.ID, Integer.class);
        String fingerprint = exchange.getIn().getHeader(DigitalOceanHeaders.KEY_FINGERPRINT, String.class);
        Key key;

        if (ObjectHelper.isNotEmpty(keyId)) {
            key = getEndpoint().getDigitalOceanClient().getKeyInfo(keyId);
        } else if (ObjectHelper.isNotEmpty(fingerprint)) {
            key = getEndpoint().getDigitalOceanClient().getKeyInfo(fingerprint);
        } else {
            throw new IllegalArgumentException(
                    DigitalOceanHeaders.ID + " or " + DigitalOceanHeaders.KEY_FINGERPRINT + " must be specified");
        }
        LOG.trace("Key [{}] ", key);
        exchange.getMessage().setBody(key);
    }

};